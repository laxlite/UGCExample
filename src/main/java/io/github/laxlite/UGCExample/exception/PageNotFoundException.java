package io.github.laxlite.UGCExample.exception;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException() {
        super("Legal page not found");
    }
}
