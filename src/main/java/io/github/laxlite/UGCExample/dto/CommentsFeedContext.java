package io.github.laxlite.UGCExample.dto;

public record CommentsFeedContext(
        int groupsCount, int commentsPerGroup, int groupNumber, int hours
) {}
