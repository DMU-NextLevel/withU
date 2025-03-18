package NextLevel.demo.repository;

import NextLevel.demo.model.User;
import NextLevel.demo.model.UserDetail;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserRepository {
    // use refresh token filter
    UserDetail findUserDetailByUserId(@Param("userId")Long userId);

    // register : insert user, user_detail table bost
    int insertUser(@Param("user") User user);
    void insertUserDetail(@Param("userDetail") UserDetail userDetail);
}
