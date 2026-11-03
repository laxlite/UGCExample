package io.github.laxlite.UGCExample.controller.web;

import io.github.laxlite.UGCExample.dto.LegalSectionShort;
import io.github.laxlite.UGCExample.dto.TopicDTO;
import io.github.laxlite.UGCExample.service.LegalService;
import io.github.laxlite.UGCExample.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

// TODO: уточнить области действия для каждого метода
@ControllerAdvice(basePackages = "io.github.laxlite.UGCExample.controller.web")
public class GlobalViewAttributes {
    private final LegalService legalService;
    private final TopicService topicService;


    @ModelAttribute("infoPages")
    public List<LegalSectionShort> addFooterAttributes(){
        return legalService.getAllSections();
    }

    // TODO: только для /feed
    @ModelAttribute("topics")
    public List<TopicDTO> addTopics(){
        return topicService.getAllTopics();
    }

    @Autowired
    public GlobalViewAttributes(LegalService legalService, TopicService topicService){
        this.legalService = legalService;
        this.topicService = topicService;
    }
}
