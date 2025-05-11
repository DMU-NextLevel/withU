package NextLevel.demo.project.notoce.entity;

import NextLevel.demo.img.entity.ImgEntity;
import NextLevel.demo.project.project.entity.ProjectEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_notice")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class ProjectNoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = ProjectEntity.class)
    @JoinColumn(name = "project_id")
    private ProjectEntity project;

    @Column(name = "created_at")
    private Date createdAt;

    @Column
    private String title;

    @Column
    private String content;

    @ManyToOne(targetEntity = ImgEntity.class, fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private ImgEntity img;
}
