package io.github.laxlite.UGCExample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FeedController {
    @GetMapping("/")
    public String root(){
        return "redirect:/feed";
    }

    @GetMapping("/feed")
    public String feed(){
        return "user/layout/main";
    }
}
