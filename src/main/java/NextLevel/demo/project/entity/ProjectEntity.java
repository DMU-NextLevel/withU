package NextLevel.demo.project.entity;

import NextLevel.demo.funding.entity.FundingEntity;
import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.recommend.entity.RecommendEntity;
import NextLevel.demo.user.entity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "project")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class ProjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "img_id")
    private ImgEntity titleImg;

    @Column(nullable = false)
    private Long goal;

    @Column(nullable = false)
    private Date expired;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<ProjectTagEntity> tags;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<ProjectImgEntity> imgs;

    @OneToMany(mappedBy="project", fetch = FetchType.LAZY)
    private List<FundingEntity> fundings;

    @OneToMany(mappedBy = "project", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<RecommendEntity> recommends;

    public void setImgs(List<ProjectImgEntity> imgs) {
        this.imgs = imgs;
    }
    public void setTags(List<ProjectTagEntity> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ProjectEntity{" +
            "id=" + id +
            ", user=" + user +
            ", title='" + title + '\'' +
            ", content='" + content + '\'' +
            ", titleImg=" + titleImg +
            ", tags=" + tags +
            ", imgs=" + imgs +
            '}';
    }
}
