package io.github.laxlite.UGCExample.controller;

import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Validated
public class FeedController {
    @GetMapping("/")
    public String root(){
        return "redirect:/feed";
    }

    @GetMapping("/feed")
    public String feed(
            @RequestParam(defaultValue = "0")
            @Min(value = 0, message = "page must be <= 0") int page,
            @RequestParam(defaultValue = "10")
            @Min(value = 10, message = "size must be <= 10 and >= 50") int size,
            Model model
    ){


        return "user/layout/main";
    }
}
