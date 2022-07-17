package com.lemon.vo;

import com.lemon.enumeration.Code;
import com.lemon.util.ResponseUtil;
import org.springframework.http.HttpStatus;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName CreatedVO
 **/
public class CreatedVO extends UnifyResponseVO<String> {

    public CreatedVO() {
        super(Code.CREATED.getCode(),"创建成功");
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public CreatedVO(int code) {
        super(code);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public CreatedVO(String message) {
        super(message);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    public CreatedVO(int code, String message) {
        super(code, message);
        ResponseUtil.setCurrentResponseHttpStatus(HttpStatus.CREATED.value());
    }

    @Override
    public String toString() {
        return super.toString();
    }
}