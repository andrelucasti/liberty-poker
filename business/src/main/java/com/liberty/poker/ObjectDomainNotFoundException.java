package com.liberty.poker;

public class ObjectDomainNotFoundException extends RuntimeException {
    public ObjectDomainNotFoundException(final String message) {
        super(message);
    }

    public ObjectDomainNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
