package com.reactive.spring.reactiveLearning587.fluxMontotest;

public class MyNewException extends Throwable {
    private String exceptionMessage;

    public MyNewException(Throwable e) {
        this.exceptionMessage = e.getMessage();
    }
}
