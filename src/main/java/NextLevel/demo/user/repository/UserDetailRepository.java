package NextLevel.demo.user.repository;

import NextLevel.demo.user.entity.UserDetailEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

    Optional<UserDetailEntity> findBySocialProviderAndSocialId(String socialProvider, String socialId);

    Optional<UserDetailEntity> findByEmail(String email);

    @Query("update UserDetailEntity u set u.password = :password where u.userId = :userId")
    @Modifying
    void updatePasswordByUserId(@Param("password") String password, @Param("userId") Long userId);
}
