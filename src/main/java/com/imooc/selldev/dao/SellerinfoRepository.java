package com.imooc.selldev.dao;

import com.imooc.selldev.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 11:35
 * @description ：com.imooc.selldev.dao
 * @function ：
 */
public interface SellerinfoRepository extends JpaRepository<SellerInfo, String> {

    SellerInfo findByOpenId(String openId);
}
