package com.SigortaKampanyaApi.SigortaKampanyaApi.dto;

import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.KampanyaKategori;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class KampanyaValidDTO {
    @NotNull(message = "Kampanya ilan başlığı boş olamaz.")
    @NotBlank(message = "Kampanya ilan başlığı boşluk içeremez")
    @Size(min = 10, max = 50, message = "Kampanya ilan başlığı min 10, max 50 karakter uzunşuğunda olmalıdır.")
    @Pattern(regexp = "[A-Za-z0-9ÇçĞğİıÖöŞşÜü]+.*", message = "Kampanya ilan başlığı harf (Türkçe karakterler dahil) veya rakam ile başlamalıdır.")
    private String ilanBasligi;

    @NotNull
    @NotBlank(message = "Kampanya detay açıklaması boşluk içeremez")
    @Size(min = 20, max = 200, message = "Kampanya detay açıklaması min 20, max 200 karakter uzunşuğunda olmalıdır.")
    private String detayAciklamasi;

    private KampanyaKategori kampanyaKategori;
}
