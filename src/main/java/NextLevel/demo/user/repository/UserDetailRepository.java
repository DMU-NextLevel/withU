package NextLevel.demo.user.repository;

import NextLevel.demo.user.entity.UserDetailEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

    // use refresh token filter
    @Query("select d from UserDetailEntity d where d.id = :userId")
    UserDetailEntity findUserDetailByUserId(@Param("userId")Long userId);

    Optional<UserDetailEntity> findByEmailAndPassword(String email, String password);

    Optional<UserDetailEntity> findBySocialProviderAndSocialId(String socialProvider, String socialId);

    Optional<UserDetailEntity> findByEmail(String email);
}
