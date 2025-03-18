package NextLevel.demo.repository;

import NextLevel.demo.dto.UserDto.RequestUserCreateDto;
import NextLevel.demo.model.User;
import NextLevel.demo.model.UserDetail;
import NextLevel.demo.role.UserRole;
import java.sql.Connection;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class UserTransaction {
    private final SqlSessionFactory factory;

    public boolean login(RequestUserCreateDto requestDto) {
        User user = User.builder()
                .name(requestDto.getName())
                .address(requestDto.getAddress())
                .number(requestDto.getNumber())
                .build();

        UserDetail userDetail = UserDetail.builder()
                .userRole(UserRole.USER)
                .uuid(UUID.randomUUID().toString())
                .email(requestDto.getEmail())
                .password(requestDto.getPassword())
                .socialProvider(requestDto.getSocialProvider())
                .socialId(requestDto.getSocialId())
                .build();

        SqlSession sqlSession = null;
        try{
            sqlSession = factory.openSession();
            Connection conn = sqlSession.getConnection();
            conn.setAutoCommit(false);

            sqlSession.insert("NextLevel.demo.repository.insertUser", user);

            userDetail.setId(user.getId());

            sqlSession.insert("NextLevel.demo.repository.insertUserDetail", userDetail);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.rollback();
            return false;
        } finally {
            if(sqlSession != null)
                sqlSession.close();
        }

        return true;
    }
}
