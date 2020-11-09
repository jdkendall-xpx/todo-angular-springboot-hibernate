package com.xpanxion.todo.exceptions;

public class ModifyTodoValidatorException extends Throwable {
    private final String message;
    public ModifyTodoValidatorException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
