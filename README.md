## Uyarı
Veritabanına kampanya kaydetmek istiyorsanız öncelikle kategori tablosuna kategorileri eklemek zorundasınız. Herhangi bir kategori kaydetmeden bir kampanya kaydedemezsiniz. 

## Kampanya Kategorilerini Veritabanına Kaydetmek

Bu adımları izleyerek kampanya kategorilerini veritabanına kaydedebilirsiniz.

### Veritabanı Ayarları

1. H2 Console'a erişmek için tarayıcınızda aşağıdaki URL'i açın:
2. `localhost:8080/h2-console`
3. `jdbc:h2:mem:sigorta_kampanya`
4. Kullanıcı adı olarak "admin" yazın ve şifre alanını boş bırakın.

### Kategori Kaydetme

Aşağıdaki SQL sorgularını kullanarak kampanya kategorilerini veritabanına kaydedin:

```sql
insert into kampanya_kategoriler (kategori_adi) values ('TSS');
insert into kampanya_kategoriler (kategori_adi) values ('ÖSS');
insert into kampanya_kategoriler (kategori_adi) values ('HS');
insert into kampanya_kategoriler (kategori_adi) values ('Diğer');
```


-------------------------------
```

### Postman Doküman
```
https://documenter.getpostman.com/view/25996098/2s93zB736Q
```
