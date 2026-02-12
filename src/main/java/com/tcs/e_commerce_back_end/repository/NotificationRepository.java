package com.tcs.e_commerce_back_end.repository;

import com.tcs.e_commerce_back_end.model.entity.NotificationEntity;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository  extends JpaRepository<NotificationEntity,Long> {
    public List<NotificationEntity> findAllByUserAccountIdOrUserAccountNull(Long userId,Sort sort);
    public List<NotificationEntity> findAllByUserAccountNull(Sort sort);
}
