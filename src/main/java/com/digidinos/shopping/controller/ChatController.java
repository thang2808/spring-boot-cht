package com.digidinos.shopping.controller;

import com.digidinos.shopping.model.ChatMessage;
import com.digidinos.shopping.serviceWithRepo.ChatService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/messages")
    public void sendMessageToManager(ChatMessage chatMessage) {
        chatService.sendMessageToManager(chatMessage);
    }

    @MessageMapping("/reply")
    @SendTo("/topic/messages")
    public void replyToUser(ChatMessage chatMessage) {
        chatService.sendMessageToUser(chatMessage);
    }
}
