package com.imooc.selldev.service;

import com.imooc.selldev.dataobject.SellerInfo;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 11:35
 * @description ：com.imooc.selldev.service
 * @function ：
 */
public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openId);

}
