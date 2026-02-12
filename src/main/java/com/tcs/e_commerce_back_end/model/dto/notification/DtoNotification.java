package com.tcs.e_commerce_back_end.model.dto.notification;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcs.e_commerce_back_end.emuns.NotificationType;
import com.tcs.e_commerce_back_end.model.entity.NotificationEntity;
import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DtoNotification {
  private Long id;
  private String title;
  private String subtitle;
  private boolean haveRead;
  private NotificationType notificationType;
  private Object jsonObject;
  private Long userId;

  public DtoNotification(NotificationEntity entity, UserAccount userAccount) {
    this.id = entity.getId();
    this.title = entity.getTitle();
    this.subtitle = entity.getSubtitle();
    this.haveRead = entity.isHaveRead();
    this.notificationType = entity.getNotificationType();
    if (Objects.nonNull(userAccount)) this.userId = userAccount.getId();
  }
}
