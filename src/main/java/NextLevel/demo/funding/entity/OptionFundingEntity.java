package NextLevel.demo.funding.entity;

import NextLevel.demo.BasedEntity;
import NextLevel.demo.option.OptionEntity;
import NextLevel.demo.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "option_funding")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class OptionFundingEntity extends BasedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = OptionEntity.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "option_id")
    private OptionEntity option;

    @ManyToOne(targetEntity = UserEntity.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column
    private long count;

    public void updateCount(int count) {
        this.count += count;
    }

    @Override
    public String toString() {
        return "FundingEntity{" +
            "id=" + id +
            ", count=" + count +
            '}';
    }
}
