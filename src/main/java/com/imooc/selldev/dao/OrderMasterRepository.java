package com.imooc.selldev.dao;

import com.imooc.selldev.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/26 10:32 PM
 * @description ：com.imooc.selldev.dao
 * @function ：
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster, String> {

    /** 根据openid，分页查询订单. */
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
