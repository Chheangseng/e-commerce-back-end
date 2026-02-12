package com.tcs.e_commerce_back_end.repository.user;


import com.tcs.e_commerce_back_end.model.entity.user.UserAccount;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserAccount, Long> {
  @Query("SELECT u FROM UserAccount u WHERE u.username = :username")
  Optional<UserAccount> findByUsername(@Param("username") String username);

  @Query("SELECT u from UserAccount u where u.email = :email")
  Optional<UserAccount> findByUserEmail(@Param("email") String email);

  Optional<UserAccount> findByResetToken(String resetToken);

  @Query("SELECT u from UserAccount u WHERE u.status != 'ADMIN' AND " +
          "((LENGTH(TRIM(:search)) = 0 OR LOWER(TRIM(u.firstName)) LIKE  LOWER(CONCAT('%',:search,'%'))) OR" +
          "(LENGTH(TRIM(:search)) = 0 OR LOWER(TRIM(u.lastName)) LIKE  LOWER(CONCAT('%',:search,'%'))) OR" +
          "(LENGTH(TRIM(:search)) = 0 OR LOWER(TRIM(u.email)) LIKE  LOWER(CONCAT('%',:search,'%'))))"
  )
  List<UserAccount> filterUserAccount ( @Param("search") String search);
}
