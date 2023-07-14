package com.SigortaKampanyaApi.SigortaKampanyaApi.service;

import com.SigortaKampanyaApi.SigortaKampanyaApi.dto.*;
import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.Kampanya;
import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.KampanyaDegisiklik;
import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.KampanyaKategori;
import com.SigortaKampanyaApi.SigortaKampanyaApi.exception.KampanyaBulunamadiException;
import com.SigortaKampanyaApi.SigortaKampanyaApi.exception.KampanyaUpdateException;
import com.SigortaKampanyaApi.SigortaKampanyaApi.exception.KategoriException;
import com.SigortaKampanyaApi.SigortaKampanyaApi.repository.KampanyaDegisiklikRepository;
import com.SigortaKampanyaApi.SigortaKampanyaApi.repository.KampanyaKategoriRepository;
import com.SigortaKampanyaApi.SigortaKampanyaApi.repository.KampanyaRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.util.*;

@Slf4j
@Service
public class KampanyaServiceImpl implements KampanyaService{

    private KampanyaRepository kampanyaRepository;
    private KampanyaDegisiklikRepository kampanyaDegisiklikRepository;

    private KampanyaKategoriRepository kampanyaKategoriRepository;

    @Autowired
    public KampanyaServiceImpl(KampanyaRepository kampanyaRepository, KampanyaDegisiklikRepository kampanyaDegisiklikRepository, KampanyaKategoriRepository kampanyaKategoriRepository) {
        this.kampanyaRepository = kampanyaRepository;
        this.kampanyaDegisiklikRepository = kampanyaDegisiklikRepository;
        this.kampanyaKategoriRepository=kampanyaKategoriRepository;
    }

    @Override
    public Kampanya kampanyaKaydet(KampanyaValidDTO kampanyaValidDTO) {

        //Dto daki verileri kampanya klasına attık
        Kampanya kampanya=new Kampanya();
        kampanya.setIlanBasligi(kampanyaValidDTO.getIlanBasligi());
        kampanya.setDetayAciklamasi(kampanyaValidDTO.getDetayAciklamasi());
        kampanya.setKampanyaKategori(kampanyaValidDTO.getKampanyaKategori());

        //bu kampanya ile aynı kategorideki kampnayaları listeledik
        Optional<List<Kampanya>> kategoriKontrolKampanya = kampanyaRepository.findByKampanyaKategori_Id(kampanya.getKampanyaKategori().getId());

        //Bu kampanya ile aynı kategoride kampanyanlar var ise kontrol ediyoruz
        //ilan başlığı ve açıklasıda aynı mı
        //onlarda aynı ise mükerrer olarak işaretlenir
        if (kategoriKontrolKampanya.isPresent() && !kategoriKontrolKampanya.get().isEmpty()){
            List<Kampanya> kampanyaListe=kategoriKontrolKampanya.get();
            for(Kampanya k:kampanyaListe){
                if(k.getIlanBasligi().equals(kampanya.getIlanBasligi()) && k.getDetayAciklamasi().equals(kampanya.getDetayAciklamasi())){
                    //System.out.println("Aynı kategoride, aynı başlık ve açıklamaya sahip");
                    kampanya.setDurum("Mükerrer");
                    break;
                }
            }

        }

        //aynı kategori, başlık ve detaya sahip kampanya yok ise
        if(kampanya.getDurum()==null || !(kampanya.getDurum().equals("Mükerrer"))){
            //System.out.println("Böyle bir kampanya başlığı yok.");
            if (kampanya.getKampanyaKategori().getKategoriAdi().equals("TSS") ||
                    kampanya.getKampanyaKategori().getKategoriAdi().equals("ÖSS") ||
                    kampanya.getKampanyaKategori().getKategoriAdi().equals("Diğer")) {
                kampanya.setDurum("Onay Bekliyor");
            } else {
                kampanya.setDurum("Aktif");
            }
        }
        kampanya=kampanyaRepository.save(kampanya);
        kampanyaDegisiklikOldu(kampanya);
        return kampanya;
    }

    public void kampanyaDegisiklikOldu(Kampanya kampanya){
        KampanyaDegisiklik kampanyaDegisiklik=new KampanyaDegisiklik();
        kampanyaDegisiklik.setIlanBasligi(kampanya.getIlanBasligi());
        kampanyaDegisiklik.setDetayAciklamasi(kampanya.getDetayAciklamasi());
        kampanyaDegisiklik.setDurum(kampanya.getDurum());
        kampanyaDegisiklik.setKategoriAdi(kampanya.getKampanyaKategori().getKategoriAdi());
        kampanyaDegisiklik.setKampanyaId(kampanya.getId());
        kampanyaDegisiklikRepository.save(kampanyaDegisiklik);
    }
    public Page<Kampanya> getAllKampanyalar(int page, int size){
        Pageable pageable = PageRequest.of(page, size);
        return kampanyaRepository.findAll(pageable);
    }

    public Kampanya kampanyaGuncelle(KampanyaUpdateDTO kampanyaUpdateDTO){
        Optional<Kampanya> boyleBirKampanyaVarMi=kampanyaRepository.findById(kampanyaUpdateDTO.getId());

        if(boyleBirKampanyaVarMi.isPresent()){

            if(kampanyaUpdateDTO.getDurum().toLowerCase().trim().equals("aktif") ||
                    kampanyaUpdateDTO.getDurum().toLowerCase().trim().equals("deaktif") ||
                    kampanyaUpdateDTO.getDurum().toLowerCase().trim().equals("onay bekliyor") ||
                    kampanyaUpdateDTO.getDurum().toLowerCase().trim().equals("mükerrer") ){

                Kampanya kampanyaDb=boyleBirKampanyaVarMi.get();

                if(kampanyaDb.getDurum()!=null && kampanyaDb.getDurum().equals("Mükerrer")){
                    throw new KampanyaUpdateException("Durumu Mükerrer olan kampanyalar güncellenemez.");
                }else if(kampanyaDb.getDurum()!=null && !(kampanyaDb.getDurum().equals("Mükerrer"))){


                    switch (kampanyaUpdateDTO.getDurum().toLowerCase().trim()){
                        case "aktif":
                            kampanyaDb.setDurum("Aktif");
                            break;

                        case "deaktif":
                            kampanyaDb.setDurum("Deaktif");
                            break;

                        case "onay bekliyor":
                            kampanyaDb.setDurum("Onay Bekliyor");
                            break;

                        case "mükerrer":
                            kampanyaDb.setDurum("Mükerrer");
                            break;
                    }

                    Kampanya updatedKampanya=kampanyaRepository.save(kampanyaDb);
                    kampanyaDegisiklikOldu(updatedKampanya);
                    return updatedKampanya;

                }else{
                    throw new KampanyaUpdateException("Kampanya güncellenirken hata oluştu.");
                }

            }else{
                throw new KampanyaUpdateException("Kampanya durumu aktif, deaktif, onay bekliyor veya mükerrer olmalıdır. Bunlar dışındaki isimlendirmeler kabul edilmeyecektir.");
            }

        }else{
            throw new KampanyaBulunamadiException("Böyle bir kampanya bulunamadı.");
        }
    }


    public List<KampanyaIstatistiklerResponse> kampanyaIstatistikler() {
        Map<String, Integer> kampanyaIstatistikler = new HashMap<>();
        ArrayList<KampanyaIstatistiklerResponse> response=new ArrayList<>();

        // Tüm kampanyaları getir
        List<Kampanya> kampanyalar = kampanyaRepository.findAll();

        // Kampanya durumlarını ve sayılarını hesapla
        for (Kampanya kampanya : kampanyalar) {
            String durum = kampanya.getDurum();

            // Kampanya durumu zaten hesaplanmışsa sayıyı bir artır
            if (kampanyaIstatistikler.containsKey(durum)) {
                int sayi = kampanyaIstatistikler.get(durum);
                kampanyaIstatistikler.put(durum, sayi + 1);
            } else {
                // Kampanya durumu hesaplanmamışsa sayıyı 1 olarak ekle
                kampanyaIstatistikler.put(durum, 1);
            }
        }

        // KampanyaIstatistiklerResponse nesnelerini oluştur ve response listesine ekle
        for (Map.Entry<String, Integer> entry : kampanyaIstatistikler.entrySet()) {
            String durum = entry.getKey();
            int sayi = entry.getValue();

            KampanyaIstatistiklerResponse istatistik = new KampanyaIstatistiklerResponse();
            istatistik.setDurum(durum);
            istatistik.setSayi(String.valueOf(sayi));

            response.add(istatistik);
        }

        return response;
    }



    public Page<KampanyaDegisiklik> kampanyaDegisiklikleriniGetir(int page, int size, Long id) {
        Pageable pageable = PageRequest.of(page, size);
        Page<KampanyaDegisiklik> kampanyaDegisiklikListesi = kampanyaDegisiklikRepository.findByKampanyaId(pageable, id);
        return kampanyaDegisiklikListesi;
    }

    public List<KategorilerResponse> tumKategorileriGetir() {
        List<KampanyaKategori> kampanyaKategoriListesi = this.kampanyaKategoriRepository.findAll();
        List<KategorilerResponse> kategoriler = new ArrayList<>();

        for (KampanyaKategori kampanyaKategori : kampanyaKategoriListesi) {
            KategorilerResponse kategori = new KategorilerResponse();
            // KategorilerResponse sınıfının uygun alanlarını kampanyaKategori'den ayarla
            kategori.setKategoriAdi(kampanyaKategori.getKategoriAdi());
            kategori.setId(kampanyaKategori.getId());
            // Diğer alanları da gerektiği gibi ayarla

            kategoriler.add(kategori);
        }

        return kategoriler;
    }

    @Override
    public KampanyaKategori kategoriKaydet(KategoriKaydetRequest kategoriKaydetRequest) {
        Optional<KampanyaKategori> kampanyaKategori=this.kampanyaKategoriRepository.findByKategoriAdi(kategoriKaydetRequest.getKategoriAdi().trim().toUpperCase());
        if(kampanyaKategori.isPresent()){
                throw new KategoriException("Böyle bir kategori var");
        }else{
            KampanyaKategori yeniKategori=new KampanyaKategori();
            yeniKategori.setKategoriAdi(kategoriKaydetRequest.getKategoriAdi().trim().toUpperCase());
            return this.kampanyaKategoriRepository.save(yeniKategori);
        }
    }

    @Override
    public Page<KampanyaKategori> tumKategorileriSayfalandiripGetir(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.kampanyaKategoriRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public void kategoriSil(Long id) {
        Optional<KampanyaKategori> kategori=this.kampanyaKategoriRepository.findById(id);
        if(kategori.isPresent()){
            Optional<List<Kampanya>> liste=this.kampanyaRepository.findByKampanyaKategori_Id(id);
            if(liste.isPresent()){
                this.kampanyaRepository.deleteAllByKampanyaKategori_Id(id);
            }
            this.kampanyaKategoriRepository.deleteById(id);
        }else{
            throw new KategoriException("Kategori bulunamadı.");
        }

    }

    @Override
    public Page<Kampanya> kampanyaAra(String searchText, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.kampanyaRepository.findByIlanBasligiContaining(searchText,pageable);
    }

    @Override
    public void kampanyaSil(Long id) {
        Optional<Kampanya> kampanyaVarMi=this.kampanyaRepository.findById(id);
        if(kampanyaVarMi.isPresent()){
            this.kampanyaRepository.deleteById(id);
        }else{
            throw new KampanyaBulunamadiException("Böyle bir kampanya bulunamadı.");
        }
    }


    public Kampanya kampanyaGetir(Long id){
        Optional<Kampanya> kampanya=kampanyaRepository.findById(id);
        if(kampanya.isPresent()){
            Kampanya alinanKampanya=kampanya.get();
            return alinanKampanya;
        }else{
            throw new KampanyaBulunamadiException("Böyle bir kampanya bulunamadı.");
        }
    }

}
