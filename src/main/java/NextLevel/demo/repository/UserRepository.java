package NextLevel.demo.repository;

import NextLevel.demo.entity.UserDetailEntity;
import NextLevel.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // use refresh token filter
    @Query("select u from UserEntity u where u.id = :userId")
    UserDetailEntity findUserDetailByUserId(@Param("userId")Long userId);
}
