package com.vifrin.chat.controller;

import com.vifrin.chat.domain.SessionProfanity;
import com.vifrin.chat.dto.MessageDto;
import com.vifrin.chat.event.LoginEvent;
import com.vifrin.chat.event.ParticipantRepository;
import com.vifrin.chat.service.MessageService;
import com.vifrin.chat.util.ProfanityChecker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

/**
 * Controller that handles WebSocket chat messages
 *
 * @author Sergi Almar
 */
@RestController
@RequestMapping("messages")
@CrossOrigin(origins = "*")
@Slf4j
public class MessageController {

    @Autowired
    MessageService messageService;
    @Autowired
    private ProfanityChecker profanityFilter;
    @Autowired
    private SessionProfanity profanity;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @SubscribeMapping("/chat.participants")
    public Collection<LoginEvent> retrieveParticipants() {
        return participantRepository.getActiveSessions().values();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @MessageMapping("/private.chat.{threadId}")
    @SendTo("/topic/private.chat.{threadId}")
    public MessageDto sendMessage(@DestinationVariable String threadId, @RequestBody MessageDto message, @AuthenticationPrincipal Principal principal) {
        return messageService.addNewMessage(message, principal.getName());
    }

    @GetMapping("by-thread/{threadId}")
    @ResponseStatus(HttpStatus.OK)
    public List<MessageDto> getMessages(@PathVariable long threadId){
        return messageService.getMessages(threadId);
    }
}