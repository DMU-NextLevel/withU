package NextLevel.demo.notice.entity;

import NextLevel.demo.img.entity.ImgEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notice_img")
@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
public class NoticeImgEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = NoticeEntity.class)
    @JoinColumn(name = "notice_id", nullable = false)
    private NoticeEntity notice;

    @ManyToOne(fetch = FetchType.EAGER)
    private ImgEntity img;

    @Override
    public String toString() {
        return "NoticeImgEntity{" +
            "img=" + img +
            ", notice=" + notice +
            ", id=" + id +
            '}';
    }
}
