package com.rikardholm.insurance.admin.test;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping("/html")
public class HtmlController {
    @RequestMapping(method = GET)
    public String index(Model model) {
        model.addAttribute("helpText","This is helpful.");
        return "test/index";
    }
}
