package com.southwind.vo;

import lombok.Data;

@Data
public class ResultVO<T> {
    private Integer code; // 状态码(-1-用户名不存在，-2密码错误，0为通过)
    private T data; // 数据
}
