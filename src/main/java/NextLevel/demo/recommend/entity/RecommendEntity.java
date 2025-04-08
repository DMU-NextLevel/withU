package NextLevel.demo.recommend.entity;

import NextLevel.demo.project.entity.ProjectEntity;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.sql.Timestamp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "recommend", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "project_id"}, name ="recommendUnique"))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="user_id", nullable=false)
    private UserEntity user;

    @ManyToOne(targetEntity = ProjectEntity.class)
    @JoinColumn(name="project_id", nullable=false)
    private ProjectEntity project;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private Timestamp createdAt;

}
