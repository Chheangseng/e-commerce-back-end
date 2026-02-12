package com.tcs.e_commerce_back_end.service;

import com.tcs.e_commerce_back_end.exception.ApiExceptionStatusException;
import com.tcs.e_commerce_back_end.model.dto.notification.DtoNotification;
import com.tcs.e_commerce_back_end.model.entity.NotificationEntity;
import com.tcs.e_commerce_back_end.model.mapper.NotificationMapper;
import com.tcs.e_commerce_back_end.repository.NotificationRepository;
import com.tcs.e_commerce_back_end.service.user.UserAuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {
  private final NotificationRepository notificationRepository;
  private final UserAuthService userAuthService;
  private final SimpMessagingTemplate messagingTemplate;
  private final ObjectMapper objectMapper;

  public void createNotification(DtoNotification notification) {
    var entity =
        notificationRepository
            .findById(ObjectUtils.getIfNull(notification.getId(), 0L))
            .orElse(new NotificationEntity());
    entity.setTitle(notification.getTitle());
    entity.setNotificationType(notification.getNotificationType());
    entity.setId(notification.getId());
    entity.setSubtitle(notification.getSubtitle());
    if (Objects.nonNull(notification.getUserId())) {
      var user = userAuthService.findById(notification.getUserId());
      entity.setUserAccount(user);
    }
    if (Objects.nonNull(notification.getJsonObject())) {
      try {
        entity.setJsonObject(objectMapper.writeValueAsString(notification.getJsonObject()));
      } catch (JsonProcessingException e) {
        throw new ApiExceptionStatusException(
            "Something went wrong with convert Json to string", 400);
      }
    }
    notificationRepository.save(entity);
  }

  public List<DtoNotification> listPublicNotification() {
    Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
    var listEntity = notificationRepository.findAllByUserAccountNull(sort);
    return NotificationMapper.mapEntityToDto(listEntity,this.objectMapper);
  }

  public List<DtoNotification> listPrivateNotification() {
    var user = userAuthService.securityContextUser();
    Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
    var listEntity =
        notificationRepository.findAllByUserAccountIdOrUserAccountNull(
            ObjectUtils.getIfNull(user.getId(), 0L), sort);
    return NotificationMapper.mapEntityToDto(listEntity,this.objectMapper);
  }

  public void pushNotification() {
    messagingTemplate.convertAndSend("/topic", "notification-update");
  }
}
