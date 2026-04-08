# Development Log - TV Web View

## [2024-05-20] - Implementasi Visual Feedback (Loading Bar)
- **Masalah**: Pengguna tidak mendapatkan feedback saat halaman web sedang dimuat, sehingga aplikasi terlihat seperti membeku (freeze).
- **Solusi**:
    - Implementasi `LinearProgressIndicator` dari library TV Material 3.
    - Menghubungkan state `progress` UI dengan callback `onProgressChanged` dari `WebChromeClient`.
    - Mengatur logika visibilitas agar progress bar otomatis hilang saat mencapai 100%.
- **Status**: **SOLVED**

## [2024-05-20] - Sinkronisasi & Perbaikan Utama
- **Masalah Playback**: Video IndiHome TV tidak jalan di mobile. Solusi: User Agent Desktop & `onPermissionRequest`.
- **Masalah Mouse**: Klik mouse emulator tidak responsif. Solusi: `setOnTouchListener` & `requestFocus()`.
- **Status**: **SOLVED**

## [2024-05-20] - Penanganan Error Lingkungan
- **Masalah**: Error Live Edit di Android Studio.
- **Solusi**: Prosedur manual **Stop** & **Full Run (Shift + F10)**.
- **Status**: **MITIGATED**
