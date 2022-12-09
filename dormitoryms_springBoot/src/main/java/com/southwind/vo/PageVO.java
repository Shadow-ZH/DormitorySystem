package com.southwind.vo;

import lombok.Data;

@Data
public class PageVO {
    private Object Data; // 当前页的数据
    private Long total; // 所有页的总记录数
}
