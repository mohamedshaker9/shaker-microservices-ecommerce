package com.shaker.ecommicro.aichat.filter;


import com.shaker.ecommicro.aichat.ChatContext;
import com.shaker.ecommicro.aichat.exceptions.BusinessException;
import com.shaker.ecommicro.aichat.exceptions.ResourceNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

@Component
public class ChatIdentityFilter extends OncePerRequestFilter {
    //TODO user db to store users and only store encrypted conversation id in cookie in user browser
    private static final String COOKIE_NAME = "CHAT_USER_ID";
    private final ChatContext chatContext;
//    private final AuthUtils authUtils;

    public ChatIdentityFilter(ChatContext chatContext) {
        this.chatContext = chatContext;

    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        setCookie(request, response);


        filterChain.doFilter(request, response);
    }

        private void setCookie(HttpServletRequest request, HttpServletResponse response) {
            final Cookie cookie = WebUtils.getCookie(request, COOKIE_NAME);
            if(cookie != null ) {
                chatContext.setConversationId(cookie.getValue());
                //TODO check the userId if still logged in or not
                return;
            }

            Long loggedInUserId = getLoggedInUserId();
            String chatId = UUID.randomUUID().toString();

            String userKey = (loggedInUserId == null)
                    ? UUID.randomUUID().toString()
                    : String.valueOf(loggedInUserId);

             chatContext.setUserKey(userKey);
             chatContext.setChatId(chatId);
             chatContext.setConversationId(buildConversationId(loggedInUserId, userKey, chatId));

            addConversationIdCookie(response);
        }

    private String buildConversationId(Long userId, String userKey, String chatId) {
        String prefix = (userId == null) ? "ANON-" : "USER-";
        return prefix + userKey + ":CHAT_ID-" + chatId;
    }

    private Long getLoggedInUserId() {
        return 1L;
//        try {
//            return authUtils.getLoggedInUser().getId();
//        } catch (ResourceNotFoundException | BusinessException ex) {
//            return null;
//        }
    }

    private void addConversationIdCookie(HttpServletResponse response) {
        ResponseCookie cookie = ResponseCookie.from(COOKIE_NAME, chatContext.getConversationId())
                .httpOnly(false)
                .secure(false)
                .path("/api")
                .maxAge(Duration.ofDays(1))
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return !request.getRequestURI().startsWith("/api/v1/chat");
    }
}