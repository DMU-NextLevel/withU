package NextLevel.demo.project.community.entity;

import NextLevel.demo.BasedEntity;
import NextLevel.demo.project.community.dto.request.SaveCommunityDto;
import NextLevel.demo.project.project.entity.ProjectEntity;
import NextLevel.demo.user.entity.UserEntity;
import jakarta.persistence.*;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "project_community_ask")
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
public class ProjectCommunityAskEntity extends BasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = ProjectEntity.class)
    @JoinColumn(name = "project_id", nullable = false)
    private ProjectEntity project;

    @Column(nullable = false)
    private String content;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToOne(mappedBy = "ask")
    private ProjectCommunityAnswerEntity answer;

    public void update(SaveCommunityDto dto) {
        if(dto.getContent()!=null && !dto.getContent().isEmpty())
            this.content = dto.getContent();
    }

}
