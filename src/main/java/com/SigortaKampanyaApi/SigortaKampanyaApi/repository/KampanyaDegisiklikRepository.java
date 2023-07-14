package com.SigortaKampanyaApi.SigortaKampanyaApi.repository;

import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.KampanyaDegisiklik;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface KampanyaDegisiklikRepository extends JpaRepository<KampanyaDegisiklik, Long> {
    Page<KampanyaDegisiklik> findByKampanyaId(Pageable pageable, Long kampanyaId);
}
