package com.jay.getinline.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@ControllerAdvice(basePackageClasses = BaseController.class)    // BaseController 가 위치한 패키지 전체를 대 / basePackages : 문자열로 특정 패키지 지정 (Type safe 하지 않다)
@Controller
public class BaseController
//        implements ErrorController
{

    @GetMapping("/")
    public String root() throws Exception{
        throw new Exception("테스트");
//        return "index";
    }

//    @RequestMapping("/error")
//    public String error() {
//        return "error";
//    }
}
