package com.repo.one.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller responsible check if the service is alive
 */
@Controller
public class LiveController {

    @RequestMapping("/siesta/rest//isalive")
    public @ResponseBody Boolean isAlive(){
        return true;
    }
}
