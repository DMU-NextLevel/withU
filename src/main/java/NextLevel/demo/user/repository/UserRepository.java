package NextLevel.demo.user.repository;

import NextLevel.demo.user.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query("select u from UserEntity u left join fetch UserDetailEntity d on d.userId = u.id where u.id = :userId")
    UserEntity findUserFullInfoByUserId(@Param("userId")Long userId);

    List<UserEntity> findUserByNickName(String nickName);

    @Modifying
    @Query("update UserEntity u set u.point = u.point + :point where u.id = :userId")
    void addPointByUserId(@Param("point")int point, @Param("userId") Long userId);

    @Modifying
    @Query("update UserEntity u set u.point = u.point - :point where u.id = :userId")
    void minusPointByUserId(@Param("point")int point, @Param("userId") Long userId);

}
