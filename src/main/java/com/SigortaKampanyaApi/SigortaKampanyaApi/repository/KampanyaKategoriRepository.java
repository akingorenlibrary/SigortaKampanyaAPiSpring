package com.SigortaKampanyaApi.SigortaKampanyaApi.repository;

import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.Kampanya;
import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.KampanyaKategori;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KampanyaKategoriRepository extends JpaRepository<KampanyaKategori, Long> {
    Optional<KampanyaKategori> findByKategoriAdi(String name);
    Page<KampanyaKategori> findAll(Pageable pageable);

}
