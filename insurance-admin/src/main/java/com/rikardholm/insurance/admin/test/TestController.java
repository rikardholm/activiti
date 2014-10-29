package com.rikardholm.insurance.admin.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequestMapping("/test")
public class TestController {

    @RequestMapping(method = GET)
    public String index() {
        return "index";
    }
}
