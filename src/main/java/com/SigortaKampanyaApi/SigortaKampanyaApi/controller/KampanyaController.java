package com.SigortaKampanyaApi.SigortaKampanyaApi.controller;

import com.SigortaKampanyaApi.SigortaKampanyaApi.dto.*;
import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.Kampanya;
import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.KampanyaDegisiklik;
import com.SigortaKampanyaApi.SigortaKampanyaApi.entity.KampanyaKategori;
import com.SigortaKampanyaApi.SigortaKampanyaApi.service.KampanyaServiceImpl;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api")
public class KampanyaController {

    private KampanyaServiceImpl kampanyaService;

    @Autowired
    public KampanyaController(KampanyaServiceImpl kampanyaService) {
        this.kampanyaService = kampanyaService;
    }

    @GetMapping("/kategoriler")
    public ResponseEntity<List<KategorilerResponse>> tumKategorileriGetir(){
        long startTime = System.currentTimeMillis();
        List<KategorilerResponse> kategoriListesi=kampanyaService.tumKategorileriGetir();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kategoriler - Get", duration);
        }
        return new ResponseEntity<>(kategoriListesi, HttpStatus.OK);
    }

    @PostMapping("/kategoriler")
    public ResponseEntity<KampanyaKategori> kategoriKaydet(@Valid @RequestBody KategoriKaydetRequest kategoriKaydetRequest){
        long startTime = System.currentTimeMillis();
        KampanyaKategori kampanyaKategori=this.kampanyaService.kategoriKaydet(kategoriKaydetRequest);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kategoriler - Post", duration);
        }
        return new ResponseEntity<>(kampanyaKategori, HttpStatus.OK);
    }

    @GetMapping("/kategoriler/sayfalandirilmis")
    public ResponseEntity<Page<KampanyaKategori>> tumKategorileriSayfalandiripGetir(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue = "10") int size){
        long startTime = System.currentTimeMillis();
        Page<KampanyaKategori> kategoriListesi=kampanyaService.tumKategorileriSayfalandiripGetir(page, size);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kategoriler/sayfalandirilmis - Get", duration);
        }
        return new ResponseEntity<>(kategoriListesi, HttpStatus.OK);
    }

    @DeleteMapping("/kategoriler/{id}")
    public ResponseEntity<Void> kategoriSil(@PathVariable(name = "id") Long id) {
        long startTime = System.currentTimeMillis();
        this.kampanyaService.kategoriSil(id);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kategoriler/{id} - DELETE", duration);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/kampanyalar")
    public ResponseEntity<Page<Kampanya>> kampanyalariGetir(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size){
        long startTime = System.currentTimeMillis();
        Page<Kampanya> kampanyalar=kampanyaService.getAllKampanyalar(page, size);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kampanyalar - Get", duration);
        }
        return new ResponseEntity<>(kampanyalar, HttpStatus.OK);
    }

    @PostMapping("/kampanyalar")
    public ResponseEntity<Kampanya> kampanyaKaydet(@Valid @RequestBody KampanyaValidDTO kampanyaValidDTO){
        long startTime = System.currentTimeMillis();
        Kampanya kampanya=kampanyaService.kampanyaKaydet(kampanyaValidDTO);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kampanyalar - Post", duration);
        }
        return new ResponseEntity(kampanya, HttpStatus.OK);
    }

    @PutMapping("/kampanyalar")
    public ResponseEntity<Kampanya> kampanyaGuncelle(@RequestBody KampanyaUpdateDTO kampanyaUpdateDTO){
        long startTime = System.currentTimeMillis();
        Kampanya kampanya=kampanyaService.kampanyaGuncelle(kampanyaUpdateDTO);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kampanyalar - Put", duration);
        }
        return new ResponseEntity<>(kampanya, HttpStatus.OK);
    }

    @GetMapping("/kampanyalar/istatistikler")
    public ResponseEntity<List<KampanyaIstatistiklerResponse>> istatistikler(){
        long startTime = System.currentTimeMillis();
        List<KampanyaIstatistiklerResponse> istatistikler=kampanyaService.kampanyaIstatistikler();
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kampanyalar/istatistikler - Get", duration);
        }
        return new ResponseEntity<>(istatistikler, HttpStatus.OK);
    }

    @GetMapping("/kampanyalar/{id}")
    public ResponseEntity<Kampanya> kampanyaGetir(@PathVariable(name = "id") Long id){
        long startTime = System.currentTimeMillis();
        Kampanya kampanya=kampanyaService.kampanyaGetir(id);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kampanyalar/{id} - Get", duration);
        }
        return new ResponseEntity<>(kampanya, HttpStatus.OK);
    }

    @GetMapping("/kampanyalar/degisiklikler/{id}")
    public ResponseEntity<Page<KampanyaDegisiklik>> kampanyaDegisiklikleriniGetir(
            @RequestParam(defaultValue="0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable(name="id") Long id){

        long startTime = System.currentTimeMillis();
        Page<KampanyaDegisiklik> KampanyaDegisiklikleriListesi=kampanyaService.kampanyaDegisiklikleriniGetir(page, size, id);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kampanyalar/degisiklikler/{id} - Get", duration);
        }
        return new ResponseEntity<>(KampanyaDegisiklikleriListesi, HttpStatus.OK);
    }

    @GetMapping("/kampanyalar/search")
    public ResponseEntity<Page<Kampanya>> kampanyaAra(
            @RequestParam(name="q", defaultValue = "") String searchText,
            @RequestParam(name="page", defaultValue = "0") int page,
            @RequestParam(name="size", defaultValue = "10") int size) {

        long startTime = System.currentTimeMillis();
        Page<Kampanya> kampanyalar=kampanyaService.kampanyaAra(searchText, page, size);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kampanyalar/search - Get", duration);
        }
        return new ResponseEntity<>(kampanyalar, HttpStatus.OK);
    }

    @DeleteMapping("kampanyalar/{id}")
    public ResponseEntity<Void> kampanyaSil(@PathVariable(name = "id") Long id){
        long startTime = System.currentTimeMillis();
        this.kampanyaService.kampanyaSil(id);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        if(duration>5){
            log.info("Alinan İstek {} ms: /api/kampanyalar/{id} - DELETE", duration);
        }
        return ResponseEntity.ok().build();
    }

}
