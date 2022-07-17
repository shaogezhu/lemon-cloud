package com.lemon.util;

import com.lemon.enumeration.Code;
import com.lemon.vo.UnifyResponseVO;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @author shaogezhu
 * @version 1.0.0
 * @ClassName ResponseUtil
 **/
public class ResponseUtil {
    /**
     * 获得当前响应
     *
     * @return 响应
     */
    public static HttpServletResponse getResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }

    public static void setCurrentResponseHttpStatus(int httpStatus) {
        getResponse().setStatus(httpStatus);
    }

}

