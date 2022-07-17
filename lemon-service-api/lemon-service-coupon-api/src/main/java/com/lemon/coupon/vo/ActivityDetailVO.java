package com.lemon.coupon.vo;

import com.lemon.coupon.pojo.Activity;
import lombok.Data;

import java.util.List;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ActivityDetailVO
 **/
@Data
public class ActivityDetailVO extends Activity {

    private List<Long> couponIds;

}
