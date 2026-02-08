package com.shaker.ecommicro.aichat;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
@Getter
@Setter
public class ChatContext {

    private String userKey;
    private String chatId;
    private String conversationId;



}