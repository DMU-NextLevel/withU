package NextLevel.demo.repository;

import NextLevel.demo.entity.UserDetailEntity;
import NextLevel.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select u from UserEntity u left join fetch UserDetailEntity d on d.id = u.id where u.id = :userId")
    UserEntity findUserFullInfoByUserId(@Param("userId")Long userId);
}
