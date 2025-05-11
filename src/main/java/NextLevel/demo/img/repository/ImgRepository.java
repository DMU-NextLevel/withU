package NextLevel.demo.img.repository;

import NextLevel.demo.img.entity.ImgCountEntity;
import NextLevel.demo.img.entity.ImgEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository extends JpaRepository<ImgEntity, Long> {

    @Modifying
    @Query("delete from ImgEntity img where img.id =:id")
    void deleteById(@Param("id") Long imgId);

    @Query(
        value = "SELECT ic.id FROM img_count ic LEFT JOIN ( " +
            "SELECT CAST(SUBSTRING(i.uri, :start, 5) AS UNSIGNED) AS img_number " +
            "FROM img i " +
            "WHERE i.uri LIKE CONCAT(:name, '%') " +
            ") AS imgs ON ic.id = imgs.img_number " +
            "WHERE imgs.img_number IS NULL " +
            "ORDER BY ic.id " +
            "LIMIT 1",
        nativeQuery = true
    )
    Long getImgCount(@Param("start") int start, @Param("name") String name);

}
