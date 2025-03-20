package NextLevel.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "user")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @ColumnDefault("'0'")
    private int point;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String number;

    @OneToOne(mappedBy = "user")
    UserDetailEntity userDetail;

    public UserEntity(String name, int point, String address, String number) {
        this.name = name;
        this.point = point;
        this.address = address;
        this.number = number;
    }
}
