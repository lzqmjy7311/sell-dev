package com.imooc.selldev.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/24 2:36 PM
 * @description ：com.imooc.selldev.dataobject
 * @function ：类目类
 */
@Entity // 标记为实体映射类
@DynamicUpdate  // 自动更新时间
@Data // lombok 省去get/set 精简代码
public class ProductCategory {

    /** 类目id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    /** 类目名字. */
    @Column(name = "category_name")
    private String categoryName;
    /** 类目编号. */
    @Column(name = "category_Type")
    private Integer categoryType;

    private Date createTime;

    private Date updateTime;

    public ProductCategory() {
    }

}
