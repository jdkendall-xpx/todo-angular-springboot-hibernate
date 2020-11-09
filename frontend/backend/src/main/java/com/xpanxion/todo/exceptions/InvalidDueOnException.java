package com.xpanxion.todo.exceptions;

public class InvalidDueOnException extends RuntimeException {
    public InvalidDueOnException(String message) {
        this.message = message;

    }
    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
