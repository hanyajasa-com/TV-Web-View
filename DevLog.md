# Development Log - TV Web View

## [2024-05-20] - Sinkronisasi & Perbaikan Utama (Status: Stabil)

### 1. Perbaikan Playback Video IndiHome TV
- **Masalah**: Video pada `https://www.indihometv.com/livetv/antv` tidak dapat diputar di WebView Android (muncul error atau blank), padahal lancar di browser Desktop.
- **Penyebab**: Server mendeteksi User Agent mobile yang dibatasi dan WebView tidak memberikan izin DRM/Protected Content secara otomatis.
- **Solusi**:
    - Mengatur `userAgentString` ke Chrome Windows 126: `Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36`.
    - Mengaktifkan `CookieManager` dengan dukungan *third-party cookies* untuk validasi token streaming.
    - Mengimplementasikan `onPermissionRequest` pada `WebChromeClient` untuk mengizinkan akses media secara otomatis.
    - Mengatur `mixedContentMode` ke `MIXED_CONTENT_ALWAYS_ALLOW`.
- **Status**: **SOLVED**

### 2. Optimasi Interaksi Mouse (Air Mouse/Emulator)
- **Masalah**: Tombol pada web player sulit diklik atau tidak merespons input dari mouse emulator.
- **Penyebab**: WebView kehilangan fokus input saat berada di dalam container Jetpack Compose.
- **Solusi**:
    - Menambahkan `setOnTouchListener` pada WebView untuk memicu `v.requestFocus()` secara paksa pada setiap interaksi sentuh/klik.
    - Mengaktifkan `isFocusable`, `isFocusableInTouchMode`, dan `isClickable`.
    - Memanggil `view?.requestFocus()` pada callback `onPageFinished`.
- **Status**: **SOLVED**

### 3. Penanganan Error Lingkungan (Android Studio)
- **Masalah**: Muncul error `java.util.IllegalFormatConversionException: d != com.android.sdklib.AndroidApiLevel` saat menggunakan Live Edit.
- **Solusi**: Melakukan mitigasi manual dengan menginstruksikan pengembang untuk berhenti menggunakan *Live Edit* dan melakukan **Full Run (Shift + F10)** setiap kali ada perubahan pada WebView atau Manifest.
- **Status**: **MITIGATED**

### 4. Konfigurasi Sistem & Performa
- **Tindakan**:
    - Menambahkan `android:hardwareAccelerated="true"` pada `AndroidManifest.xml` untuk kelancaran video.
    - Menambahkan `android:usesCleartextTraffic="true"` untuk mendukung segmen video yang ditarik via HTTP.
    - Memastikan navigasi tombol fisik "Back" terintegrasi dengan `webView.goBack()`.
- **Status**: **DONE**
