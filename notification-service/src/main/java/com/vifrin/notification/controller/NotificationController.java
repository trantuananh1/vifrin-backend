package com.vifrin.notification.controller;

import com.vifrin.common.constant.BaseConstant;
import com.vifrin.common.dto.NotificationDto;
import com.vifrin.common.response.ResponseTemplate;
import com.vifrin.common.response.ResponseType;
import com.vifrin.notification.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * @author: trantuananh1
 * @since: Thu, 23/12/2021
 **/

@RestController
@RequestMapping("/notifications")
@Slf4j
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping
    public ResponseEntity<?> getNotifications(@AuthenticationPrincipal Principal principal,
                                               @RequestParam(value = "page", defaultValue = BaseConstant.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size", defaultValue = BaseConstant.DEFAULT_PAGE_SIZE) int size){
        List<NotificationDto> notificationDtoList = notificationService.getNotifications(principal.getName(), page, size);
        return notificationDtoList.isEmpty()?
                ResponseEntity.noContent().build() :
                ResponseEntity.ok(new ResponseTemplate<>(ResponseType.SUCCESS, notificationDtoList));
    }
}
