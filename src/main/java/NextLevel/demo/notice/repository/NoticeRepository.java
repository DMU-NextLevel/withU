package NextLevel.demo.notice.repository;

import NextLevel.demo.notice.entity.NoticeEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    @Query("select n from NoticeEntity n")
    List<NoticeEntity> findAll();

    @Modifying
    @Query("delete from NoticeEntity n where n.id = :id")
    void deleteById(@Param("id") Long id);

}
