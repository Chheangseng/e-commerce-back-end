package com.tcs.e_commerce_back_end.model.entity;

import com.tcs.e_commerce_back_end.emuns.NotificationType;
import com.tcs.e_commerce_back_end.model.entity.common.AbstractEntity;
import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class NotificationEntity extends AbstractEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String subtitle;
  @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
  @JoinColumn(name = "userId", referencedColumnName = "id" , foreignKey = @ForeignKey(name = "fk_user_id"))
  private UserAccount userAccount;
  private boolean haveRead;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  private String jsonObject;
}
