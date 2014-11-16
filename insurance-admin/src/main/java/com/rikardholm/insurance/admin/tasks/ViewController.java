package com.rikardholm.insurance.admin.tasks;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class ViewController {
    @RequestMapping(value = "/start", method = GET)
    public void start() {

    }

    @RequestMapping(value = "/form/{key}", method = GET)
    public String form(@PathVariable String key, Model model) {
        model.addAttribute("key",key);

        return "form";
    }
}
