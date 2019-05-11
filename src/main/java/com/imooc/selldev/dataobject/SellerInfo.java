package com.imooc.selldev.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.simpleframework.xml.core.Commit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019-02-07 11:33
 * @description ：com.imooc.selldev.dataobject
 * @function ：卖家信息
 */
@Entity
@Data
@DynamicUpdate
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    @Column(name = "openid")
    private String openId;

}
