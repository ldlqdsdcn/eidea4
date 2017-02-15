package com.dsdl.eidea.core.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 刘大磊 on 2016/12/6 17:12.
 */
@Controller
public class HomeController {

    @RequestMapping("/")
    public String showHome(HttpServletRequest request) {
        System.out.print("Hello------------------------>");
        return "index";
    }
}
