package NextLevel.demo.user.entity;

import NextLevel.demo.img.entity.ImgEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String nickName;

    @Column(nullable = false)
    @ColumnDefault("'0'")
    private int point;

    @Column
    private String address;

    @Column
    private String number;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.REMOVE, optional = true)
    @JoinColumn(name = "img_id", nullable = true)
    private ImgEntity img;

    @OneToOne(mappedBy = "user")
    UserDetailEntity userDetail;

    public UserEntity(Long id, String name,String nickName, int point, String address, String number, ImgEntity img) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.point = point;
        this.address = address;
        this.number = number;
        this.img = img;
    }
}
