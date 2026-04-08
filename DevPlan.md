# Development Plan - TV Web View

## Fase 1: Fondasi & Streaming (Selesai)
- [x] Inisialisasi Proyek menggunakan **Jetpack Compose** & **TV Material 3**.
- [x] Implementasi `AndroidView` untuk integrasi `WebView`.
- [x] Konfigurasi **User Agent Desktop** (Chrome 126 Windows) untuk bypass blokir mobile.
- [x] Implementasi `WebChromeClient` dengan `onPermissionRequest` untuk dukungan **DRM/Protected Content**.
- [x] Aktivasi `CookieManager` (Third-party cookies) untuk retensi sesi streaming.
- [x] Penanganan protokol campuran (*Mixed Content*) dan *Cleartext Traffic*.
- [x] Aktivasi **Akselerasi Perangkat Keras** di Manifest untuk performa video.

## Fase 2: Interaksi & Navigasi (Selesai)
- [x] Implementasi `OnBackPressedCallback` untuk navigasi internal WebView (History Back).
- [x] Implementasi `setOnTouchListener` untuk memperbaiki isu fokus **Mouse Emulator / Air Mouse**.
- [x] Integrasi `requestFocus()` pada event `onPageFinished`.

## Fase 3: Peningkatan UI/UX (Rencana)
- [ ] Penambahan **Loading ProgressBar** (linear) di bagian atas WebView.
- [ ] Implementasi *Splash Screen* dengan logo TV WebView.
- [ ] Penambahan *Overlay Menu* (Side Bar) untuk mengganti channel atau input URL manual.
- [ ] Fitur "Refresh" menggunakan gesture atau tombol remote tertentu.

## Fase 4: Optimasi & Pemeliharaan (Rencana)
- [ ] Penanganan kondisi *Offline* dengan tampilan error yang ramah pengguna.
- [ ] Dukungan *Picture-in-Picture* (PiP) untuk multitasking.
- [ ] Sistem pembaruan User Agent secara dinamis via API/Firebase Remote Config.
- [ ] Optimasi navigasi D-Pad untuk elemen web yang tidak memiliki fokus standar.
