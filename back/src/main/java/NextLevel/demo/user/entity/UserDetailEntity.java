package NextLevel.demo.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "user_detail")
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailEntity {
    @Id
    @Column(name = "user_id")
    private Long userId;

    @MapsId
    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="user_id")
    private UserEntity user;

    @Column(length = 36, columnDefinition = "CHAR(36)", unique = true, nullable = false) // default 값 없음 무조건 코드로 넣기 필수 !!
    private String UUID;

    @Column(nullable = true) // 추후 변경
    private String email;

    @Column(nullable = true)
    private String password;

    @Column(nullable = true)
    private String socialProvider;

    @Column(nullable = true)
    private String socialId;

    @Builder
    public UserDetailEntity(UserEntity user, String UUID, String role, String email, String password,
        String socialProvider, String socialId) {
        this.user = user;
        this.UUID = UUID;
        this.email = email;
        this.password = password;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
    }

    // used at user info update
    public UserDetailEntity(UserEntity user, String UUID, String role, String email) {
        this.user = user;
        this.UUID = UUID;
        this.email = email;
    }

    public void setEmail(String email) {this.email = email;}
}
