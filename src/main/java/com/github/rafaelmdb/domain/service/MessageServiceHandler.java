package com.github.rafaelmdb.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class MessageServiceHandler implements MessageService{

    @Autowired
    private MessageSource messageSource;

    @Override
    public String getMessage(String message, Object[] objects) {
        //Locale ptBr = new Locale("pt", "BR");
        return messageSource.getMessage(message, objects, Locale.getDefault());
    }
}
