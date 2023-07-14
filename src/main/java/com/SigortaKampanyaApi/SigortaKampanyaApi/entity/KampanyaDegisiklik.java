package com.SigortaKampanyaApi.SigortaKampanyaApi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "kampanya_degisiklik")
@Data
public class KampanyaDegisiklik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="ilan_basligi")
    private String ilanBasligi;

    @Column(name="detay_aciklamasi")
    private String detayAciklamasi;

    @Column(name = "durum")
    private String durum;

    @Column(name = "kategori_adi")
    private String kategoriAdi;

    @Column(name = "degisiklik_tarihi")
    @CreationTimestamp
    private Date degisiklikTarihi;

    @Column(name = "kampanya_id")
    private Long kampanyaId;
}
