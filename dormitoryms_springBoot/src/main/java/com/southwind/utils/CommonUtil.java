package com.southwind.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CommonUtil {

    public static String createDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }
}
