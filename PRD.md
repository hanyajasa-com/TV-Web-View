# Product Requirements Document (PRD) - TV Web View

## 1. Project Overview
**Nama Proyek:** TV Web View  
**Paket:** `com.hanyajasa.tvwebview`  
**Deskripsi:** Aplikasi Android (Mobile & TV) berbasis Jetpack Compose yang menggunakan WebView untuk mengakses layanan streaming IndiHome TV dengan mode Desktop.

## 2. Tujuan & Target Pengguna
*   **Tujuan:** Memberikan akses penuh ke konten streaming yang biasanya dibatasi pada browser mobile dengan mensimulasikan lingkungan Desktop.
*   **Target:** Pengguna STB Android TV, HP Android, atau perangkat Android lainnya yang ingin menonton tayangan IndiHome TV (khususnya channel ANTV) tanpa kendala player.

## 3. Analisis Masalah & Solusi
| Masalah | Solusi Teknis (Implementasi) |
| :--- | :--- |
| Pemutar video tidak muncul/error di mobile | Menggunakan User Agent Chrome Windows v126 (Desktop Mode). |
| Tombol 'Play' tidak merespons klik mouse | Implementasi `setOnTouchListener` dengan `requestFocus()` paksaan. |
| Video DRM/Protected Content tidak jalan | Override `onPermissionRequest` di `WebChromeClient` untuk auto-grant. |
| Sesi login sering terputus | Aktivasi `CookieManager` dengan dukungan *Third-party cookies*. |
| Navigasi sulit kembali ke halaman sebelumnya | Integrasi `OnBackPressedCallback` dengan `webView.goBack()`. |
| Tidak ada indikator saat loading web | **Implementasi `LinearProgressIndicator` yang sinkron dengan `onProgressChanged`.** |

## 4. Fitur Utama (MVP)
*   **WebView Engine**: Berbasis `android.webkit.WebView` yang dibungkus dalam `AndroidView` Compose.
*   **Desktop Simulation**: String User Agent: `Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/126.0.0.0 Safari/537.36`.
*   **Visual Feedback**: **Linear Loading Progress Bar di bagian atas layar.**
*   **Media Support**: 
    *   `mixedContentMode`: `MIXED_CONTENT_ALWAYS_ALLOW`.
    *   `mediaPlaybackRequiresUserGesture`: `false` (Auto-play).
    *   `domStorageEnabled` & `databaseEnabled`: `true`.
*   **Default Target**: `https://www.indihometv.com/livetv/antv`.

## 5. Persyaratan Teknis & Performa
*   **Minimum SDK**: API 24 (Android 7.0).
*   **UI Framework**: Jetpack Compose dengan `androidx.tv.material3`.
*   **Akselerasi**: `hardwareAccelerated="true"` diaktifkan di AndroidManifest untuk mencegah lag pada video 1080p.
*   **Konektivitas**: Izin `INTERNET` dan `usesCleartextTraffic="true"` untuk mendukung segmen video HTTP.
