package com.imooc.selldev.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.selldev.VO.ResultVO;
import com.imooc.selldev.dataobject.OrderDetail;
import com.imooc.selldev.dto.OrderDTO;
import com.imooc.selldev.enums.ResultEnum;
import com.imooc.selldev.exception.SellException;
import com.imooc.selldev.form.OrderForm;
import com.imooc.selldev.service.impl.OrderServiceImpl;
import com.imooc.selldev.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/28 10:37 AM
 * @description ：com.imooc.selldev.controller
 * @function ：买家订单
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderServiceImpl orderService;

    /**
     * 创建订单.
     *
     * @param orderForm     订单参数
     * @param bindingResult 验证错误返回
     * @return
     */
    @PostMapping("/create")
    public ResultVO<Map<String, String>> createOrder(@Valid OrderForm orderForm, BindingResult bindingResult) {
        // 如果有捕获到参数不合法
        if (bindingResult.hasErrors()) {
            log.error("【创建订单】订单参数不正确，OrderForm={}", orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(), bindingResult.getFieldError().getDefaultMessage());
        }
        // 数据转换 (json数组装换成集合)
        final List<OrderDetail> detailList = JSONObject.parseArray(orderForm.getItems(), OrderDetail.class);
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        orderDTO.setOrderDetailList(detailList);

        if (orderDTO.getOrderDetailList() == null || orderDTO.getOrderDetailList().size() == 0) {
            log.error("【创建订单】购物车不能为空");
            throw new SellException(ResultEnum.CART_NOT_EMPIY);
        }
        final OrderDTO dtoResult = this.orderService.createOrder(orderDTO);
        if (dtoResult == null) {
            log.error("【创建订单】订单创建失败，Result={}", dtoResult);
            throw new SellException(ResultEnum.ORDER_CREATE_ERROR);
        }
        final Map<String, Object> map = new HashMap<>();
        map.put("orderId", dtoResult.getOrderId());
        final ResultVO success = ResultVOUtil.success(map);
        return success;
    }

    /**
     * 查询订单列表（分页显示）
     *
     * @param openId 用户
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> queryOrderList(@RequestParam("openid") String openId,
                                                   @RequestParam(value = "page", defaultValue = "0") Integer page,
                                                   @RequestParam(value = "size", defaultValue = "10") Integer size) {
        if (StringUtils.isEmpty(openId)) {
            log.error("【查询订单列表】openid为空");
            throw new SellException(ResultEnum.OPENID_NOT_EMPIY);
        }
        final PageRequest pageRequest = PageRequest.of(page, size);
        final Page<OrderDTO> list = this.orderService.findList(openId, pageRequest);

        return ResultVOUtil.success(list.getContent());
    }

    /**
     * 查询订单详情
     *
     * @param openId  用户
     * @param orderId 订单号
     * @return
     */
    @GetMapping("/detail")
    public ResultVO queryOrderDetails(@RequestParam("openid") String openId,
                                                @RequestParam("orderid") String orderId) {
        if (StringUtils.isEmpty(openId)) {
            log.error("【订单详情】openId不能为空，openid={}", openId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        final OrderDTO orderDTO = this.orderService.findOne(orderId);
        // 判断是否为空
        if (orderDTO == null) {
            return null;
        }
        // 效验订单属不属于当前用户
        if (!openId.equals(orderDTO.getBuyerOpenid())) {
            log.error("【查询订单】该订单不属于当前用户，openId={},orderId={}", openId, orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return ResultVOUtil.success(orderDTO);
    }

    /**
     * 取消订单
     *
     * @param openid  用户
     * @param orderId 订单号
     * @return
     */
    @PostMapping("/cancel")
    public ResultVO cancelOrder(@RequestParam("openid") String openid,
                                @RequestParam("orderid") String orderId) {
        if (StringUtils.isEmpty(openid)) {
            log.error("【取消订单】openId不能为空，openid={}", openid);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        final OrderDTO orderDTO = this.orderService.findOne(orderId);
        if (orderDTO == null) {
            log.error("【取消订单】查询不到该订单，orderId={}", orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        // 效验用户
        if (!openid.equals(orderDTO.getBuyerOpenid())) {
            log.error("【取消订单】该订单不属于当前用户，无法取消，openId={},orderId={}", openid, orderId);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        final OrderDTO result = this.orderService.cancelOrder(orderDTO);
        if (result == null) {
            log.error("【取消订单】订单取消失败，稍后重试。 order={}", result);
            throw new SellException(ResultEnum.ORDER_CANCEL_ERROR);
        }
        return ResultVOUtil.success();
    }

}

















