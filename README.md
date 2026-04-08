# TVWebView - IndiHome TV Desktop Mode
```
echo "# TV-Web-View" >> README.md
git init
git add README.md
git commit -m "first commit"
git branch -M main
git remote add origin https://github.com/hanyajasa-com/TV-Web-View.git
git push -u origin main
```

Aplikasi WebView Android khusus untuk mengakses streaming IndiHome TV (ANTV) dengan tampilan Desktop yang optimal untuk interaksi mouse dan kelancaran streaming.

## Fitur Utama
- **User Agent Desktop Terbaru**: Menyamarkan WebView sebagai Google Chrome (Windows) agar situs IndiHome TV tidak memblokir pemutar video.
- **Dukungan Interaksi Mouse**: Menggunakan `setOnTouchListener` untuk memastikan WebView selalu mendapatkan fokus saat diklik dengan mouse emulator.
- **Manajemen Cookie & Sesi**: Mengaktifkan `CookieManager` untuk menjaga sesi login dan token streaming video tetap valid.
- **Izin Media Otomatis**: Secara otomatis memberikan izin `onPermissionRequest` untuk *Protected Content* (DRM) sehingga tombol play dapat berfungsi.
- **Hardware Acceleration**: Diaktifkan melalui Manifest untuk rendering streaming video yang lancar.

## Catatan Penting untuk Pengembangan

### 1. Masalah "Live Edit" di Android Studio
Jika Anda melihat error `java.util.IllegalFormatConversionException` saat mengedit kode, itu adalah bug internal Android Studio.
- **Solusi**: Jangan gunakan *Live Edit*. Klik tombol **Stop** (Kotak Merah) dan jalankan ulang aplikasi dengan **Run** (Segitiga Hijau / `Shift + F10`).

### 2. Konfigurasi Keamanan (Cleartext)
Beberapa sumber video streaming mungkin masih menggunakan protokol HTTP. Pastikan `AndroidManifest.xml` memiliki atribut berikut:
```xml
android:usesCleartextTraffic="true"
android:hardwareAccelerated="true"
```

### 3. Kompatibilitas Player
Jika video tidak muncul (hanya hitam) tetapi suara ada:
- Pastikan koneksi internet stabil (mode desktop menarik bandwidth lebih tinggi).
- Periksa apakah `mixedContentMode` diatur ke `MIXED_CONTENT_ALWAYS_ALLOW`.

## Cara Menjalankan
1. Clone project ini.
2. Buka di Android Studio.
3. Hubungkan HP Android atau Android TV.
4. Tekan **Shift + F10** (Run).
