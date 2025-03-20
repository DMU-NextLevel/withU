package NextLevel.demo.repository;

import NextLevel.demo.entity.UserDetailEntity;
import NextLevel.demo.entity.UserEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetailEntity, Long> {

    Optional<UserDetailEntity> findByEmailAndPassword(String email, String password);

    Optional<UserDetailEntity> findBySocialId(String socialId);

    Optional<UserDetailEntity> findBySocialProviderAndSocialId(String socialProvider, String socialId);
}
