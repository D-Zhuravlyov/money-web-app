package com.money.account.demo.exception.custom;

import org.springframework.web.context.request.WebRequest;

public class ExceptionsUtils {

    private ExceptionsUtils() {
    }

    public static String getRequesInfo(WebRequest request){
        StringBuilder builder = new StringBuilder("Request parameters: ");
        request.getParameterMap().forEach((k,v) -> builder.append(k + " - " + v) );
        return builder.toString();
    }
}
