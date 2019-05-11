package com.imooc.selldev.dao;

import com.imooc.selldev.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/26 10:44 PM
 * @description ：com.imooc.selldev.dao
 * @function ：
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail, String> {

    List<OrderDetail> findByOrderId(String orderId);

}
