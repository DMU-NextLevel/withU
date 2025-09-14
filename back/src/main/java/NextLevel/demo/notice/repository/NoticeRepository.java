package NextLevel.demo.notice.repository;

import NextLevel.demo.notice.entity.NoticeEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    @Query("select n from NoticeEntity n left join fetch n.imgs where n.id = :id")
    Optional<NoticeEntity> findById(@Param("id") Long id);

    @Query("select n from NoticeEntity n order by n.createdAt desc")
    List<NoticeEntity> findAll();

    @Modifying
    @Query("delete from NoticeEntity n where n.id = :id")
    void deleteById(@Param("id") Long id);

}
