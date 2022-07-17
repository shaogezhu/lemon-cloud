package com.lemon.order.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName OrderUtil
 **/
@Component
public class OrderUtil {

    private static String[] yearCodes;

    @Value("${lemon.year-codes}")
    public void setYearCodes(String yearCodes) {
        String[] chars = yearCodes.split(",");
        OrderUtil.yearCodes = chars;
    }

    public static String makeOrderNo() {
        StringBuffer joiner = new StringBuffer();
        Calendar calendar = Calendar.getInstance();
        String mills = String.valueOf(calendar.getTimeInMillis());
        String micro = LocalDateTime.now().toString();
        String random = String.valueOf(Math.random()).substring(2,4);
        joiner.append(OrderUtil.yearCodes[calendar.get(Calendar.YEAR) - 2021])
                .append(Integer.toHexString(calendar.get(Calendar.MONTH)+1).toUpperCase())
                .append(calendar.get(Calendar.DAY_OF_MONTH))
                .append(mills.substring(mills.length()-5, mills.length()))
                .append(micro.substring(micro.length()-3, micro.length()))
                .append(random);
        return joiner.toString();
    }
}
