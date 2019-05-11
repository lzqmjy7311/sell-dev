package com.imooc.selldev.VO;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/25 3:15 PM
 * @description ：com.imooc.selldev.VO
 * @function ：返回给前端的数据
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 8269275843181906484L;
    
    /** 返回错误码. */
    private Integer code;
    /** 提示信息. */
    private String message;
    /** 具体信息. */
    private T data;

}


