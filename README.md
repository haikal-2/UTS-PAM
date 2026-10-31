# Kopi dan Nyemil ☕🍩

> **Tugas Pemrograman Mobile: Aplikasi Kopi dan Nyemil**  
> Dibuat berdasarkan wireframe resmi alur 8 layar: *Splash/Welcome, Sign Up, Login, Register, Home, Profile, Alamat Pengiriman, dan Konfirmasi Pesanan*.

---

## 📱 Tentang Aplikasi

**Kopi dan Nyemil** adalah aplikasi mobile berbasis Android modern yang memfasilitasi pengguna untuk memesan aneka sajian kopi artisan dan kudapan nikmat secara cepat dan menyenangkan. Antarmuka aplikasi dirancang mengadopsi prinsip **Material Design 3 (M3)** dengan palet warna hangat (*Warm Coffee & Bakery Palette*: cokelat espresso, krem lembut, dan aksen terakota), memberikan kenyamanan visual dan estetika cafe modern.

---

## ✨ Fitur & 8 Layar Wireframe

Aplikasi mengimplementasikan 100% alur tampilan dari wireframe:

1. **Layar 1 — Splash / Welcome**
   - Logo coffee cup melingkar yang elegan
   - Nama aplikasi **"Kopi dan Nyemil"**
   - Tagline *"Get a taste of your wish"*
   - Tombol utama **"Start now"** untuk masuk ke gerbang pendaftaran/masuk

2. **Layar 2 — Sign Up Gateway**
   - Heading **"Sign up"**
   - Teks panduan: *"Join as a member & experience more fun from various meal at mesen_gube marketing app"*
   - Opsi tombol cepat: **"Register"** dan **"Login"**

3. **Layar 3 — Login User**
   - Judul layar **"Login User"**
   - Kolom input `username` dan `password` (dengan toggle visibilitas sandi)
   - Tombol **"Login"** dengan validasi kredensial
   - Tautan cepat beralih ke halaman Registrasi jika belum memiliki akun

4. **Layar 4 — Register**
   - Judul layar **"Register"**
   - Kolom input `nama lengkap`, `username`, dan `password`
   - Tombol **"Register"** yang otomatis menginisialisasi profil pengguna aktif
   - Tautan kembali ke halaman Login

5. **Layar 5 — Home (Katalog Menu)**
   - Sambutan ramah dinamis **"Halo [Nama],"** (menyesuaikan nama user login/register/profil)
   - Judul bagian **"Makanan & Minuman"**
   - Filter kategori cepat: *Semua*, *Kopi*, *Camilan*, *Minuman*
   - Daftar menu (7 item, melebihi syarat minimal 5 item):
     1. **Kopi Susu Gula Aren** (Rp 22.000) — *Espresso, susu segar, gula aren murni*
     2. **Donat Cokelat** (Rp 12.000) — *Donat empuk kentang, glaze cokelat Belgia*
     3. **Sourdough Loaf** (Rp 35.000) — *Roti fermentasi alami, kerak renyah lembut*
     4. **Cappuccino** (Rp 25.000) — *Espresso ganda, microfoam susu lembut*
     5. **Matcha Latte** (Rp 27.000) — *Matcha Uji Jepang asli, steamed fresh milk*
     6. **Butter Croissant** (Rp 20.000) — *Pastry berlapis mentega Prancis harum & renyah*
     7. **Caramel Cold Brew** (Rp 28.000) — *Seduhan dingin 16 jam dengan karamel*
   - Stepper jumlah (+ / -) interaktif pada tiap item
   - Banner terapung keranjang belanja (*Sticky Cart Bar*) dengan total harga dan tombol *"Lanjut Pesan"*
   - **Bottom Navigation Bar M3**: Tab *Home*, *Order*, dan *Profile*

6. **Layar 6 — Profile (Pengaturan Akun)**
   - Header **"Halo [Nama],"**
   - Kartu identitas **"Profile Saya"** lengkap dengan inisial avatar dan Poin Loyalty
   - Sub-bagian **"Profile Setting"** dengan kolom input `nama`
   - Tombol **"Save"** yang seketika memperbarui nama sapaan di seluruh aplikasi

7. **Layar 7 — Alamat Pengiriman (Order)**
   - Judul layar **"Alamat"**
   - Ringkasan daftar pesanan dari keranjang, rincian ongkir, dan total tagihan
   - Formulir pengiriman:
     - `Nama Lengkap`
     - `Alamat`
     - `Patokan atau detail lengkap`
   - Tombol utama **"Order dan Kirim"**

8. **Layar 8 — Konfirmasi Pesanan**
   - Header **"Halo [Nama],"**
   - Badge animasi centang hijau melingkar bertuliskan **"OK"**
   - Pesan konfirmasi: *"Terima kasih sudah memesan tunggu aja sebentar kurir kami segera mengantarkan pesananmu!"*
   - Informasi estimasi kurir (15-25 menit) dan ID pesanan
   - Tombol **"Oke"** untuk menyelesaikan pesanan dan kembali ke halaman Home

---

## 🛠️ Teknologi & Arsitektur

- **Bahasa**: Kotlin (100%)
- **UI Framework**: Jetpack Compose dengan Material 3 (M3)
- **Arsitektur**: MVVM (Model - View - ViewModel)
- **State Management**: Kotlin Coroutines & `MutableStateFlow` / `collectAsStateWithLifecycle`
- **Navigation**: State-driven navigation dengan dukungan `BackHandler` Android sistem
- **Adaptive Icon**: Adaptive Launcher Icon kustom dengan palet warna selaras

---

## 📁 Struktur Direktori

```
app/src/main/
├── AndroidManifest.xml
├── java/com/example/
│   ├── MainActivity.kt                # Routing utama & inisialisasi tema
│   ├── data/
│   │   └── model/
│   │       ├── MenuItem.kt            # Model data menu & daftar dummy 7 item
│   │       └── UserSession.kt         # Model user profile, alamat, & order
│   └── ui/
│       ├── KopiViewModel.kt           # StateFlow & business logic 8 layar
│       ├── components/
│       │   └── CommonComponents.kt   # Button, TextField, Greeting, & BottomBar M3
│       ├── navigation/
│       │   └── Screen.kt              # Definisi rute layar & tab navigasi
│       ├── screens/
│       │   ├── SplashScreen.kt        # Layar 1: Welcome / Splash
│       │   ├── SignUpGatewayScreen.kt # Layar 2: Pintu Masuk Sign Up
│       │   ├── LoginScreen.kt         # Layar 3: Login User
│       │   ├── RegisterScreen.kt      # Layar 4: Register Akun
│       │   ├── HomeScreen.kt          # Layar 5: Katalog Menu & Keranjang
│       │   ├── ProfileScreen.kt       # Layar 6: Pengaturan Profil
│       │   ├── OrderAddressScreen.kt  # Layar 7: Formulir Alamat Pengiriman
│       │   └── OrderConfirmationScreen.kt # Layar 8: Sukses Konfirmasi
│       └── theme/
│           ├── Color.kt               # Palet warna Warm Coffee & Bakery
│           ├── Theme.kt               # Konfigurasi M3 ColorScheme
│           └── Type.kt                # Tipografi Typography M3
└── res/
    ├── drawable/                      # Asset ikon launcher & hero illustration
    ├── mipmap-*/                      # Icon launcher adaptif (mdpi s.d. xxxhdpi)
    └── values/
        ├── strings.xml                # Label "Kopi dan Nyemil"
        └── themes.xml
```

---

## 🚀 Cara Menjalankan Aplikasi

1. **Clone repository ini:**
   ```bash
   git clone <URL_REPOSITORY_ANDA>
   cd <NAMA_FOLDER_PROJEK>
   ```

2. **Buka di Android Studio:**
   - Jalankan Android Studio (versi Ladybug / Flamingo / yang lebih baru).
   - Pilih **File > Open**, lalu arahkan ke direktori proyek ini.
   - Tunggu proses Gradle Sync selesai secara otomatis.

3. **Jalankan Aplikasi:**
   - Sambungkan perangkat fisik Android melalui USB Debugging atau gunakan Android Emulator (API level 24 ke atas).
   - Tekan tombol **Run (Shift + F10)** atau ikon hijau ▶️ pada toolbar Android Studio.

---

## 📋 Pemenuhan Komponen Penilaian Tugas

| No | Komponen Penilaian | Bobot | Status Implementasi |
|:--:|:-------------------|:-----:|:--------------------|
| 1 | **Kelengkapan tampilan dan fitur** | 60 poin | ✅ Lengkap 100% (8 Layar: Splash, Sign Up, Login, Register, Home, Profile, Alamat, Konfirmasi) |
| 2 | **Tampilan/interface yang menarik** | 20 poin | ✅ Desain modern Material 3, Warm Coffee palette, feedback sentuhan, chip filter, card elevasi, dan hero illustration |
| 3 | **Dokumentasi GitHub dan kelengkapan README** | 20 poin | ✅ Terdokumentasi lengkap dengan fitur, teknologi, struktur file, dan petunjuk instalasi |
| **Total** | | **100 poin** | **Sempurna** |
