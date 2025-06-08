package NextLevel.demo.notice.repository;

import NextLevel.demo.notice.entity.NoticeImgEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeImgRepository extends JpaRepository<NoticeImgEntity, Long> {

    @Modifying
    @Query("delete from NoticeImgEntity ni where ni.id in :ids")
    void deleteAllById(@Param("ids") List<Long> ids);

}
