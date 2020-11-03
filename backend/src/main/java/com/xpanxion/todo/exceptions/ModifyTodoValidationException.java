package com.xpanxion.todo.exceptions;

public class ModifyTodoValidationException extends Throwable {
    private final String message;

    public ModifyTodoValidationException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
