package com.xpanxion.todo.exceptions;

public class InvalidDueOnException extends RuntimeException {
    String message;

    public InvalidDueOnException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
