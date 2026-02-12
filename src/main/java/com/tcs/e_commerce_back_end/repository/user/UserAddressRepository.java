package com.tcs.e_commerce_back_end.repository.user;

import com.tcs.e_commerce_back_end.model.entity.user.UserAddress;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress,Long> {
    public List<UserAddress> findAllByUserAccountId(Long id);
    @Modifying
    @Transactional
    @Query("update UserAddress ad set ad.isDefault = false where ad.userAccount.id = :userId")
    public void resetDefault( @Param("userId") Long userId);
}
