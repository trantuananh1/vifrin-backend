package com.vifrin.chat.controller;

import com.vifrin.chat.domain.ChatMessage;
import com.vifrin.chat.domain.SessionProfanity;
import com.vifrin.chat.dto.MessageDto;
import com.vifrin.chat.event.LoginEvent;
import com.vifrin.chat.event.ParticipantRepository;
import com.vifrin.chat.exception.TooMuchProfanityException;
import com.vifrin.chat.service.MessageService;
import com.vifrin.chat.util.ProfanityChecker;
import com.vifrin.common.dto.CommentDto;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collection;

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

	@Autowired private ProfanityChecker profanityFilter;
	
	@Autowired private SessionProfanity profanity;
	
	@Autowired private ParticipantRepository participantRepository;
	
	@Autowired private SimpMessagingTemplate simpMessagingTemplate;

	@Autowired
	MessageService messageService;
	
	@SubscribeMapping("/chat.participants")
	public Collection<LoginEvent> retrieveParticipants() {
		return participantRepository.getActiveSessions().values();
	}
	
	@MessageMapping("/chat.message")
	public ChatMessage filterMessage(@Payload ChatMessage message, Principal principal) {
//		checkProfanityAndSanitize(message);
		
		message.setUsername(principal.getName());
		
		return message;
	}

	@PostMapping
	@MessageMapping("/chat.private")
	@SendTo("/topic/chat.private.{username}")
	public ResponseEntity sendPrivateMessage(@RequestBody ChatMessage message, @AuthenticationPrincipal Principal principal) {
		MessageDto messageDto = messageService.addNewMessage(message, principal.getName());
		simpMessagingTemplate.convertAndSend("/user/" + "hung" + "/exchange/amq.direct/chat.message", message);
		return ResponseEntity.ok(new ResponseTemplate<MessageDto>(ResponseType.SUCCESS, messageDto));
	}
	
	private void checkProfanityAndSanitize(ChatMessage message) {
		long profanityLevel = profanityFilter.getMessageProfanity(message.getContent());
		profanity.increment(profanityLevel);
		message.setContent(profanityFilter.filter(message.getContent()));
	}
	
	@MessageExceptionHandler
	@SendToUser(value = "/exchange/amq.direct/errors", broadcast = false)
	public String handleProfanity(TooMuchProfanityException e) {
		return e.getMessage();
	}
}