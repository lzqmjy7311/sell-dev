package com.imooc.selldev.utils;

import com.imooc.selldev.VO.ResultVO;

/**
 * @author ：created by 刘召奇
 * @data ：Created in 2019/1/26 3:42 PM
 * @description ：com.imooc.selldev.utils
 * @function ：
 */
public class ResultVOUtil {

    public static ResultVO success(Object object) {

        final ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setMessage("成功");
        resultVO.setData(object);
        return resultVO;
    }

    public static ResultVO success() {
        return success(null);
    }

    public static ResultVO error(Integer code, String msg) {

        final ResultVO resultVO = new ResultVO();
        resultVO.setMessage(msg);
        resultVO.setCode(code);
        return resultVO;
    }
}
