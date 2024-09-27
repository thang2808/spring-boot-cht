package com.digidinos.shopping.serviceWithRepo;

import com.digidinos.shopping.model.ChatMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    private static final Logger logger = LoggerFactory.getLogger(ChatService.class);
    private final SimpMessagingTemplate messagingTemplate;

    public ChatService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Gửi tin nhắn từ user đến manager1
    public void sendMessageToManager(ChatMessage chatMessage) {
        // Lấy thông tin người gửi
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String senderName = authentication.getName();
            chatMessage.setFrom(senderName);
            logger.info("User '{}' gửi tin nhắn đến 'manager1': {}", senderName, chatMessage.getContent());
        }

        // Gửi tin nhắn tới manager1
        messagingTemplate.convertAndSendToUser("manager1", "/queue/messages", chatMessage);
        logger.info("Đã gửi tin nhắn tới 'manager1'");
    }

    // Gửi tin nhắn từ manager1 đến user cụ thể
    public void sendMessageToUser(ChatMessage chatMessage) {
        // Gửi tin nhắn tới user cụ thể
        messagingTemplate.convertAndSendToUser(chatMessage.getTo(), "/queue/messages", chatMessage);
        logger.info("Đã gửi tin nhắn tới user '{}'", chatMessage.getTo());
    }
}
