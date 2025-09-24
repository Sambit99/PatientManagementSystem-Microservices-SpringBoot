package com.pms.patientservice.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ApiResponse {
    public static <T> ResponseEntity<Object> Send(HttpServletRequest request, String message, HttpStatus status, T data){
        if (request == null || message == null || status == null) {
            throw new IllegalArgumentException("None of the parameters can be null.");
        }

        Map<String,Object> requestInfo = new HashMap<>();
        requestInfo.put("ip",request.getRemoteAddr());
        requestInfo.put("method",request.getMethod());
        requestInfo.put("url",request.getRequestURI());

        Map<String, Object> response = new HashMap<>();
        response.put("success",true);
        response.put("statuscode",status.value());
        response.put("message",message);
        response.put("data",data);
        response.put("request",requestInfo);

        return new ResponseEntity<>(response,status);
    }
}
