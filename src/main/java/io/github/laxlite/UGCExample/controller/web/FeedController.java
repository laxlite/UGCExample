package io.github.laxlite.UGCExample.controller.web;

import io.github.laxlite.UGCExample.service.CommentService;
import io.github.laxlite.UGCExample.service.TopicService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Validated
public class FeedController {

    private final CommentService commentService;

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

    @GetMapping("/feed/fragment/popular-comments")
    public String getPopularComments(
            @RequestParam(name = "groups_count") int groupsCount,
            @RequestParam(name = "comments_per_group") int commentsPerGroup,
            @RequestParam(name = "group_number") int groupNumber,
            @RequestParam int hours,
            Model model){
        groupNumber = (groupNumber + 1) % groupsCount;
        model.addAttribute("groupsCount", groupsCount);
        model.addAttribute("commentsPerGroup", commentsPerGroup);
        model.addAttribute("groupNumber", groupNumber);
        model.addAttribute("hours", hours);

        model.addAttribute("popularComments", commentService.getPopularCommentsByRating(groupsCount, commentsPerGroup, groupNumber, hours));

        return "user/fragment/feed/popular_comments::popularCommentsSection";
    }



    @Autowired
    public FeedController(CommentService commentService) {
        this.commentService = commentService;
    }
}
