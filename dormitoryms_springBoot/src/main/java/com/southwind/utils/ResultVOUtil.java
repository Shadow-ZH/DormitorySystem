package com.southwind.utils;

import com.southwind.vo.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object data) {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(0);
        resultVO.setData(data);
        return resultVO;
    }

    public static ResultVO fail() {
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(-1);
        return resultVO;
    }
}
