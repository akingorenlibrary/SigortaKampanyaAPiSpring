package com.SigortaKampanyaApi.SigortaKampanyaApi.service;

import com.SigortaKampanyaApi.SigortaKampanyaApi.dto.*;
import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.Kampanya;
import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.KampanyaDegisiklik;
import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.KampanyaKategori;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface KampanyaService {
   Kampanya kampanyaKaydet(KampanyaValidDTO kampanyaValidDTO);

   Page<Kampanya> getAllKampanyalar(int page, int size);

   Kampanya kampanyaGuncelle(KampanyaUpdateDTO kampanyaUpdateDTO);

   List<KampanyaIstatistiklerResponse> kampanyaIstatistikler();

   Page<KampanyaDegisiklik> kampanyaDegisiklikleriniGetir(int page, int size, Long id);

   List<KategorilerResponse> tumKategorileriGetir();

   KampanyaKategori kategoriKaydet(KategoriKaydetRequest kategoriKaydetRequest);

   Page<KampanyaKategori> tumKategorileriSayfalandiripGetir(int page, int size);

   void kategoriSil(Long id);

   Page<Kampanya> kampanyaAra(String searchText, int page, int size);

   void kampanyaSil(Long id);
}
