package com.SigortaKampanyaApi.SigortaKampanyaApi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="kampanyalar")
@Data
public class Kampanya {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="ilan_basligi")
    private String ilanBasligi;

    @Column(name="detay_aciklamasi")
    private String detayAciklamasi;

    @Column(name="durum")
    private String durum;

    @Column(name = "olusturulma_tarihi")
    @CreationTimestamp
    private Date olusturulmaTarihi;

    @Column(name="son_guncellenme_tarihi")
    @UpdateTimestamp
    private Date sonGuncellenmeTarihi;

    @ManyToOne
    @JoinColumn(name="kategori_id", nullable = false)
    private KampanyaKategori kampanyaKategori;
}
