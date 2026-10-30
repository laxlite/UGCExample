package io.github.laxlite.UGCExample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LegalController {
    @GetMapping("/rules")
    public String rules(){

    }
}
