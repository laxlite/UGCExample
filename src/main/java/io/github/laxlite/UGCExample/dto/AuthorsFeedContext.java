package io.github.laxlite.UGCExample.dto;

public record AuthorsFeedContext(
        int groupsCount, int authorsPerGroup, int groupNumber, int hours
) {}