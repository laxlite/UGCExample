package io.github.laxlite.UGCExample.controller.web;

import io.github.laxlite.UGCExample.dto.LegalSection;
import io.github.laxlite.UGCExample.dto.LegalSectionShort;
import io.github.laxlite.UGCExample.service.LegalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice(basePackages = "io.github.laxlite.UGCExample.controller.web")
public class GlobalViewAttributes {
    private final LegalService legalService;

    @ModelAttribute("infoPages")
    public List<LegalSectionShort> addFooterAttributes(){
        return legalService.getAllSections();
    }

    @Autowired
    public GlobalViewAttributes(LegalService legalService){
        this.legalService = legalService;
    }
}
