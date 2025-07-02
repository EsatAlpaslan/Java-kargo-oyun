# java-projem


SAAS KARGO OYUNU YAPIM SÜRECİ RAPORU
1. PROJE FİKRİ:
İki kişilik bir ekip tarafından Java tabanlı Android Studio kullanılarak geliştirilen bu mobil oyun, yasa dışı silah ticareti yapan bir kargo şirketini konu alır. Oyuncu, çeşitli günlerde gelen silah siparişlerini zamanında ve doğru şekilde tamamlamakla sorumludur.


2. GELİŞTİRME ORTAMI:
- Android Studio (Java)
- Fragment yapısı ile arayüz kontrolü
- Git versiyon kontrolü ve GitHub ile ekip çalışması

  
3. ANA EKRANLAR VE FRAGMENTLER:
- IntroFragment: Tanıtım metni ve uyarı ekranı (!WARNING!)
- RoomSelectionFragment: 3 kapılı geçiş ekranı
- MainRoomFragment: Masada parça birleştirme işlemi
- CargoRoomFragment: Kargo kolilerinin bulunduğu alanı
- CustomerServiceFragment: Müşteri hizmetleri bölümü.

  
4. GÜNLERE GÖRE OYNANIŞ YAPISI:
- Gün 1: Revolver parçaları (namlu, kabza, tetik) birleşince kilitlenir.
- Gün 2: Glock ve Deagle parçaları aynı anda masada olur, her iki silah da tamamlanınca gün biter.
- Gün 3: USP parçaları (ust, uc, yay, sarjor, govde) sürüklenerek birleşir, yay ve ust birleşip birlikte hareket eder.

  
5. SES ve GÖRSEL ÖGELER:
- Arka plan müziği her fragment geçişinde giderek kısılır.
- Sayaç, başarı metni, gibi grafik ve animasyonlar kullanıldı.


6. İLERLEME SİSTEMİ:
- Her gün için süreli bir sayaç başlar.
- Tüm parçalar doğru şekilde birleştirildiğinde sayaç durur ve “Sonraki Güne Geç” butonu görünür.

  
7. EKSTRA:
- Oyuncuya görevi anlatan bilgi metni oyun başında siyah ekran üzerinde gösterilir.
- İkinci günde iki silah aynı anda birleştirilmelidir.
- Silah parçaları sürüklenip doğru konuma bırakıldığında kilitlenir ve birlikte hareket eder.

  
8. TEKNİK ÖZELLİKLER:
- makeDraggable(): ImageView nesnelerinin sürüklenebilir olmasını sağlar.
- Kilitlenme kontrolü: Her parça için boolean bayraklar kullanılır (örneğin: isUstLockedToGovde).
- Parça etkileşimi: onTouchListener içinde ACTION_MOVE ve ACTION_UP olayları ile kontrol edilir.
- Parçalar birleştiklerinde setX/setY ile konumları sabitlenir ve setEnabled(false) ile sürüklenmeleri engellenir.
- Birleştirilen parçalar ViewGroup içinde birlikte taşınmaları için ebeveyn-child ilişkisiyle yönetilir.
- Sayaç: CountDownTimer ile günlük süre kontrol edilir.
- Fragment geçişleri Navigation Component ile yapılır (örnek: Navigation.findNavController().navigate()).
- Ses yönetimi: Müzik her fragmentte setVolume ile giderek kısılır, efekt sesleri MediaPlayer ile ayrı oynatılır.
