package com.alvin.web;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Created by Administrator on 2017/4/19.
 */
@RestControllerAdvice
public class ErrorController extends ResponseEntityExceptionHandler {

}
