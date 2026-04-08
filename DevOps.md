# DevOps Guide - TV Web View

## 1. Environment & Tools
*   **IDE**: Android Studio Ladybug atau versi terbaru.
*   **Build System**: Gradle dengan Kotlin DSL.
*   **Target SDK**: Android API 34 (Android 14).
*   **Minimum SDK**: Android API 24 (Android 7.0).

## 2. Build & Run
### Cara Mengatasi Error Live Edit
Jika saat pengembangan muncul error `IllegalFormatConversionException` di Android Studio:
1.  Klik tombol **Stop** (Kotak Merah).
2.  Lakukan **Clean Project** (Build > Clean Project).
3.  Jalankan ulang dengan tombol **Run** (Shift + F10).
*Jangan mengandalkan Hot Reload/Live Edit untuk perubahan pada `WebView` dan `Manifest`.*

## 3. Deployment (Sideloading)
Untuk menginstal APK ke Android TV atau STB:
1.  Aktifkan **Developer Options** dan **USB Debugging** pada perangkat target.
2.  Hubungkan via kabel USB atau ADB over Wi-Fi:
    ```bash
    adb connect <IP_ADDRESS_TV>
    ```
3.  Instal APK hasil build:
    ```bash
    adb install -r app/build/outputs/apk/debug/app-debug.apk
    ```

## 4. Debugging WebView
Karena aplikasi ini menggunakan Mode Desktop, debugging sangat krusial:
1.  Aktifkan **USB Debugging** di perangkat Android.
2.  Buka browser Chrome di komputer, lalu ketik: `chrome://inspect/#devices`.
3.  Klik **Inspect** pada URL `https://www.indihometv.com/livetv/antv` untuk melihat Console Log dan mendebug elemen CSS/JS secara langsung di TV.

## 5. Security & Network
*   **Cleartext**: Diizinkan melalui `android:usesCleartextTraffic="true"` untuk kompatibilitas streaming.
*   **Mixed Content**: WebView diatur ke `MIXED_CONTENT_ALWAYS_ALLOW` agar segmen video HTTP tetap bisa ditarik di halaman HTTPS.
