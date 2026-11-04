package io.github.laxlite.UGCExample.controller.web;

import io.github.laxlite.UGCExample.dto.AuthorsFeedContext;
import io.github.laxlite.UGCExample.dto.CommentsFeedContext;
import io.github.laxlite.UGCExample.service.CommentService;
import io.github.laxlite.UGCExample.service.TopicService;
import io.github.laxlite.UGCExample.service.UserService;
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
    private final UserService userService;

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
        // В model вставляем feed
        model.addAttribute("templateName", "user/fragment/feed/feed");
        model.addAttribute("fragmentName", "feed");

        // TODO: change hardcode data
        // data for bestComments section
        CommentsFeedContext commentsFeedContext = new CommentsFeedContext(2, 3, 0, 5);
        model.addAttribute("commentsFeedContext", commentsFeedContext);
        model.addAttribute("popularComments", commentService.getPopularCommentsByRating(
                commentsFeedContext.groupsCount(),
                commentsFeedContext.commentsPerGroup(),
                commentsFeedContext.groupNumber(),
                commentsFeedContext.hours()));

        // TODO: change hardcode data
        // data for bestAuthors section
        AuthorsFeedContext authorsFeedContext = new AuthorsFeedContext(2, 2, 0, 5);
        model.addAttribute("authorsFeedContext", authorsFeedContext);
        model.addAttribute("popularAuthors", userService.getPopularAuthorsByRating(
                authorsFeedContext.groupsCount(),
                authorsFeedContext.authorsPerGroup(),
                authorsFeedContext.groupNumber(),
                authorsFeedContext.hours()));

        return "user/layout/main";
    }

    @GetMapping("/feed/fragment/popular-comments")
    public String getPopularComments(
            @RequestParam(name = "groups_count") int groupsCount,
            @RequestParam(name = "comments_per_group") int commentsPerGroup,
            @RequestParam(name = "group_number") int groupNumber,
            @RequestParam int hours,
            Model model){
        CommentsFeedContext commentsFeedContext = new CommentsFeedContext(groupsCount, commentsPerGroup, (groupNumber + 1) % groupsCount, hours);
        model.addAttribute("commentsFeedContext", commentsFeedContext);
        model.addAttribute("popularComments", commentService.getPopularCommentsByRating(
                commentsFeedContext.groupsCount(),
                commentsFeedContext.commentsPerGroup(),
                commentsFeedContext.groupNumber(),
                commentsFeedContext.hours()));
        return "user/fragment/feed/popular_comments::popularCommentsSection";
    }

    @GetMapping("/feed/fragment/popular-authors")
    public String getPopularAuthors(
            @RequestParam(name = "groups_count") int groupsCount,
            @RequestParam(name = "authors_per_group") int authorsPerGroup,
            @RequestParam(name = "group_number") int groupNumber,
            @RequestParam int hours,
            Model model){
        AuthorsFeedContext authorsFeedContext = new AuthorsFeedContext(groupsCount, authorsPerGroup, (groupNumber + 1) % groupsCount, hours);
        model.addAttribute("authorsFeedContext", authorsFeedContext);
        model.addAttribute("popularAuthors", userService.getPopularAuthorsByRating(
                authorsFeedContext.groupsCount(),
                authorsFeedContext.authorsPerGroup(),
                authorsFeedContext.groupNumber(),
                authorsFeedContext.hours()));
        return "user/fragment/feed/popular_authors::popularAuthorsSection";
    }



    @Autowired
    public FeedController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }
}
