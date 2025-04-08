package NextLevel.demo.project.entity;

import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.user.entity.UserEntity;
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
import jakarta.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(nullable = false,name="created_at", columnDefinition = " datetime default NOW() ")
    private Date createdAt = new Date();

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

    public void setImgs(List<ProjectImgEntity> imgs) {
        this.imgs = imgs;
    }
    public void setTags(List<ProjectTagEntity> tags) {
        this.tags = tags;
    }

    @Builder
    public ProjectEntity(Long id, UserEntity user, String title, String content,
        Long goal, ImgEntity titleImg, String expired, List<ProjectTagEntity> tags,
        List<ProjectImgEntity> imgs) throws ParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.createdAt = new Date();
        this.goal = goal;
        this.titleImg = titleImg;
        this.expired = new SimpleDateFormat("yyyy-MM-dd").parse(expired);
        this.tags = tags;
        this.imgs = imgs;
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
