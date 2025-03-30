package NextLevel.demo.img.repository;

import NextLevel.demo.img.entity.ImgEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgRepository extends JpaRepository<ImgEntity, Long> {

    @Query("select i from ImgEntity i where i.uri like CONCAT(:imgUri, '%')")
    List<ImgEntity> findContainImgUri(@Param("imgUri") String imgUri);
}
