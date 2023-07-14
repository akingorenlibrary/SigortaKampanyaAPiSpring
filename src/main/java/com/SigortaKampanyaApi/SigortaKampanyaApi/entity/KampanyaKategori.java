package com.SigortaKampanyaApi.SigortaKampanyaApi.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name="kampanya_kategoriler")
@Data
public class KampanyaKategori {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(name="kategori_adi")
    private String kategoriAdi;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kampanyaKategori")
    private Set<Kampanya> kampanya;
}
