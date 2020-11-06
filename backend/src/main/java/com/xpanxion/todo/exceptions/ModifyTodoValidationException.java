package com.xpanxion.todo.exceptions;

import net.bytebuddy.implementation.bytecode.Throw;

public class ModifyTodoValidationException extends Throwable {
    private final String message;

    public ModifyTodoValidationException(String message){
        this.message = message;

    }

    @Override
    public String getMessage() {
        return message;
    }
}
