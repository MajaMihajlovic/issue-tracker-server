package com.issuetracker.issuetracker.common.exception;

public class ForbiddenException extends Exception {
    private static final long serialVersionUID = -1300922631131923484L;

    public ForbiddenException(String message) {
        super(message);
    }


}