package NextLevel.demo.user.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "user_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @OneToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name="id", nullable = false)
    private UserEntity user;

    @Column(length = 36, columnDefinition = "CHAR(36)", unique = true, nullable = false) // default 값 없음 무조건 코드로 넣기 필수 !!
    private String UUID;

    @Column(length=5, columnDefinition = "char(6)", nullable = false)
    @ColumnDefault("USER")
    private String role;

    @Column(nullable = true) // 추후 변경
    private String email;
    @Column(nullable = true)
    private String password;
    @Column(nullable = true)
    private String socialProvider;
    @Column(nullable = true)
    private String socialId;

    public UserDetailEntity(Long id, String UUID, String role, String email, String password,
        String socialProvider, String socialId) {
        this.id = id;
        this.UUID = UUID;
        this.role = role;
        this.email = email;
        this.password = password;
        this.socialProvider = socialProvider;
        this.socialId = socialId;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
