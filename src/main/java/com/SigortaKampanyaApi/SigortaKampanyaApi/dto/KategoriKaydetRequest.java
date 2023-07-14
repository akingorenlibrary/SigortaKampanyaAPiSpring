package com.SigortaKampanyaApi.SigortaKampanyaApi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class KategoriKaydetRequest {

    @NotNull(message = "Kategori adi boş olamaz.")
    @NotBlank(message = "Kategori adi boşluk içeremez")
    private String kategoriAdi;
}
