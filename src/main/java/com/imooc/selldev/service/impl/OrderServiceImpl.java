package com.imooc.selldev.service.impl;

import com.imooc.selldev.dao.OrderDetailRepository;
import com.imooc.selldev.dao.OrderMasterRepository;
import com.imooc.selldev.dataobject.OrderDetail;
import com.imooc.selldev.dataobject.OrderMaster;
import com.imooc.selldev.dataobject.ProductInfo;
import com.imooc.selldev.dto.CartDTO;
import com.imooc.selldev.dto.OrderDTO;
import com.imooc.selldev.enums.OrderStatusEnum;
import com.imooc.selldev.enums.PayStatusEnum;
import com.imooc.selldev.enums.ResultEnum;
import com.imooc.selldev.exception.SellException;
import com.imooc.selldev.service.OrderService;
import com.imooc.selldev.service.ProductService;
import com.imooc.selldev.service.WebSocket;
import com.imooc.selldev.utils.KeyUtil;
import com.lly835.bestpay.model.RefundRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/27 1:38 PM
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayServiceImpl payService;

    @Autowired
    private PushMessageImpl pushMessage;

    @Autowired
    private WebSocket webSocket;

    /**
     * 创建订单.
     */
    @Override
    @Transactional
    public OrderDTO createOrder(OrderDTO orderDTO) {
        // 创建订单id
        String orderId = KeyUtil.generateUniquePrimaryKey();
        // 统计总价
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        // 1.查询商品（数量，价格)
        // 遍历商品详情集合
        for (OrderDetail orderDetail : orderDTO.getOrderDetailList()) {
            // 根据商品id查询商品
            final ProductInfo productInfo = this.productService.findOne(orderDetail.getProductId());
            // 如果商品不存在，抛出异常
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 2.计算总价 : 商品价格 * 商品数量 ，得到其中一件商品的总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity())).add(orderAmount);
            // 订单详情入库(orderDetail)
            orderDetail.setDetailId(KeyUtil.generateUniquePrimaryKey());
            orderDetail.setOrderId(orderId);
            orderDetail.setProductName(productInfo.getProductName());
            orderDetail.setProductPrice(productInfo.getProductPrice());
            orderDetail.setProductIcon(productInfo.getProductIcon());
            this.orderDetailRepository.save(orderDetail);
        }
        // 3.写入订单数据库（orderMaster）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        this.orderMasterRepository.save(orderMaster);

        // 4.减库存
        final List<CartDTO> list = orderDTO.getOrderDetailList().stream().map(orderDetail ->
                new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity())
        ).collect(Collectors.toList());
        this.productService.decreaseStock(list);

        // 5.发送webSocket消息
        this.webSocket.sendMessage(orderDTO.getOrderId());
        return orderDTO;
    }

    /**
     * 订单查询.
     */
    @Override
    public OrderDTO findOne(String orderId) {

        // 根据订单id查询订单
        final OrderMaster orderMaster = this.orderMasterRepository.findById(orderId).get();
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 根据订单id查询订单详情
        final List<OrderDetail> orderDetailList = this.orderDetailRepository.findByOrderId(orderId);
        if (CollectionUtils.isEmpty(orderDetailList)) {
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIST);
        }
        // 封装返回对象
        final OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 订单分页查询.
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {

        final Page<OrderMaster> orderMasterPage = this.orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        final List<OrderDTO> orderDTOList = orderMasterPage.getContent().stream().map(orderMaster ->
                {
                    final OrderDTO orderDTO = new OrderDTO();
                    BeanUtils.copyProperties(orderMaster, orderDTO);
                    return orderDTO;
                }
        ).collect(Collectors.toList());

        final PageImpl<OrderDTO> orderDTOPage = new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());

        return orderDTOPage;
    }

    /**
     * 取消订单.
     */
    @Override
    @Transactional
    public OrderDTO cancelOrder(OrderDTO orderDTO) {

        final OrderMaster orderMaster = new OrderMaster();

        // 1.查询订单状态 (非新订单状态不能修改)
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【取消订单】订单状态不正确，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERRROR);
        }
        // 2.修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO, orderMaster);
        final OrderMaster result = this.orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("【取消订单】订单取消失败，orderMaster={}", orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 3.返还库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())) {
            log.error("【取消订单】订单中没有商品，orderDTO={}", orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        final List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(orderDetail ->
                new CartDTO(orderDetail.getProductId(), orderDetail.getProductQuantity())
        ).collect(Collectors.toList());
        this.productService.increaseStock(cartDTOList);

        // 4.如果已支付，则退款
        if (orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())) {
            final RefundRequest refundRequest = this.payService.refund(orderDTO);
        }
        return orderDTO;
    }

    /**
     * 完结订单.
     */
    @Override
    @Transactional
    public OrderDTO finishOrder(OrderDTO orderDTO) {

        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【完结订单】订单状态不正确，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERRROR);
        }
        // 修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        final OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        final OrderMaster result = this.orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("【完结订单】订单更新失败，result={}", result);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        // 推送消息
        this.pushMessage.orderStatus(orderDTO);
        return orderDTO;
    }

    /**
     * 修改订单支付状态.
     */
    @Override
    @Transactional
    public OrderDTO paymentOrder(OrderDTO orderDTO) {

        // 判断订单状态
        if (!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())) {
            log.error("【订单支付】订单状态不正确，orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERRROR);
        }
        // 判断支付状态
        if (!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())) {
            log.error("【订单支付】订单支付状态不正确，orderId={} ,payStatus={}", orderDTO.getOrderId(), orderDTO.getPayStatus());
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        // 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        final OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        final OrderMaster result = this.orderMasterRepository.save(orderMaster);
        if (result == null) {
            log.error("【订单支付】订单支付状态更新失败，result={}", result);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    /**
     * 卖家端订单分页查询
     *
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        final Page<OrderMaster> orderMasterPage = this.orderMasterRepository.findAll(pageable);
        final List<OrderDTO> orderDTOList = orderMasterPage.stream().map(orderMaster ->
                {
                    final OrderDTO orderDTO = new OrderDTO();
                    BeanUtils.copyProperties(orderMaster, orderDTO);
                    return orderDTO;
                }
        ).collect(Collectors.toList());
        return new PageImpl<OrderDTO>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}
