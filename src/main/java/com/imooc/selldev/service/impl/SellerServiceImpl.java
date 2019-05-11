package com.imooc.selldev.service.impl;

import com.imooc.selldev.dao.SellerinfoRepository;
import com.imooc.selldev.dataobject.SellerInfo;
import com.imooc.selldev.service.SellerService;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 11:48
 * @description ：com.imooc.selldev.service.impl
 * @function ：
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerinfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openId) {

        final SellerInfo byOpenId = this.repository.findByOpenId("123456");

        Assert.assertNotNull(byOpenId);
        return byOpenId;
    }
}











