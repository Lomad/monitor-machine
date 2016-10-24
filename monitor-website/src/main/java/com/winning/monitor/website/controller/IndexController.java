package com.winning.monitor.website.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by Admin on 2016/10/20.
 */
@Controller
public class IndexController {
    @RequestMapping(value = {"/", ""})
    public ModelAndView index() {
        return new ModelAndView("index");
    }
}
