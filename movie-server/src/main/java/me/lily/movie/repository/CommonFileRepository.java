package me.lily.movie.repository;


import me.lily.movie.domain.CommonFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommonFileRepository extends JpaRepository<CommonFile, Long> {

    CommonFile findByFileHash(String fileHash);

    int deleteByFileHash(String fileHash);
}
