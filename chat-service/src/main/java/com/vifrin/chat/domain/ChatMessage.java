package com.vifrin.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ChatMessage {

    private String username;
    private String content;

    @Override
    public String toString() {
        return "ChatMessage [user=" + username + ", message=" + content + "]";
    }
}
