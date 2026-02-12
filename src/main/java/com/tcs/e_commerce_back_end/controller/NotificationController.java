package com.tcs.e_commerce_back_end.controller;

import com.tcs.e_commerce_back_end.model.dto.notification.DtoNotification;
import com.tcs.e_commerce_back_end.service.NotificationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService service;
    @GetMapping("/public-notification")
    public ResponseEntity<List<DtoNotification>> getPublicNotificationList() {
        return ResponseEntity.ok(service.listPublicNotification());
    }
    @GetMapping("/v1/private-notification")
    public ResponseEntity<List<DtoNotification>> getPrivateNotificationList() {
        return ResponseEntity.ok(service.listPrivateNotification());
    }
}
