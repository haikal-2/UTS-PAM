# Kopi dan Nyemil - Mobile Application

Implementasi aplikasi mobile pemesanan kopi dan kudapan ("Kopi dan Nyemil") berbasis Jetpack Compose sesuai dengan wireframe 8 layar dan lembar ketentuan tugas pemrograman mobile.

---

### User Review & Critical Decisions

> [!IMPORTANT]
> Berdasarkan jawaban klarifikasi yang telah Anda pilih, berikut adalah arahan utama yang akan diimplementasikan:

- **Tema Visual**: Warm Coffee & Bakery (palet warna krem lembut `#FFFDF9`, cokelat espresso hangat `#4E342E`, aksen terakota `#C85A32` dan karamel emas).
- **Alur Pemesanan Menu**: Keranjang belanja interaktif terintegrasi pada layar Home dan checkout langsung menuju layar Alamat Pengiriman dan Konfirmasi Pesanan.
- **Navigasi Bawah**: Material 3 Navigation Bar modern dengan ikon aktif/inaktif dan badge keranjang belanja, menghubungkan layar Home, Order (Alamat & Ringkasan), dan Profile.
- **Penyimpanan Data**: Sesuai ketentuan tugas ("Tidak perlu menggunakan database. Data dapat menggunakan data statis"), state aplikasi dikelola dengan `ViewModel` + `StateFlow` reaktif di memori untuk autentikasi user dummy, keranjang belanja, data profil, dan riwayat alamat pemesanan.

---

### 1. Overview & Core Concept

- **Apa Fungsinya**: Aplikasi "Kopi dan Nyemil" memfasilitasi pengguna untuk melihat menu kopi dan camilan artisan (minimal 5 item lengkap dengan foto, nama, deskripsi, dan harga), mendaftar atau masuk ke akun, mengubah profil pengguna, memasukkan detail alamat pengiriman, dan menyelesaikan pesanan hingga menerima konfirmasi pemesanan kurir.
- **Target Pengguna**: Pecinta kopi dan kudapan santai (pelajar, mahasiswa, pekerja kantor) yang menginginkan pengalaman pemesanan yang cepat, visual yang hangat, dan antarmuka yang intuitif.
- **Nilai Utama**: Pemenuhan 100% komponen penilaian tugas (Kelengkapan 8 tampilan wireframe 60 poin, Antarmuka menarik 20 poin, serta dokumentasi README 20 poin).

---

### 2. User Experience & Visual Design

#### Key User Flows (8 Tampilan Wireframe)
1. **Layar 1 - Splash / Welcome**: Logo coffee cup melingkar yang elegan, judul "Kopi dan Nyemil", tagline *"Get a taste of your wish"*, dan tombol aksi utama *"Start now"*.
2. **Layar 2 - Sign Up Gateway**: Ajakan bergabung *"Join as a member & experience more fun from various meal at mesen_gube marketing app"*, dengan dua tombol opsi: *"Register"* dan *"Login"*.
3. **Layar 3 - Login User**: Input username dan password dengan validasi form, tombol *"Login"*, serta opsi beralih ke registrasi.
4. **Layar 4 - Register**: Input nama lengkap, username, dan password dengan validasi, tombol *"Register"*, serta update state nama aktif pengguna.
5. **Layar 5 - Home (Katalog Menu)**:
   - Sambutan personal *"Halo [Nama],*"
   - Header kategori *"Makanan & Minuman"* dengan filter cepat (Semua, Kopi, Camilan, Artisan)
   - Minimal 5 item menu (Kopi Susu Gula Aren, Donat Cokelat, Sourdough Loaf, Cappuccino, Matcha Latte, plus Cold Brew Caramel) lengkap dengan gambar, badge harga Rupiah, dan tombol tambah/kurang jumlah keranjang
   - Bottom Bar dengan tab *Home*, *Order*, dan *Profile*.
6. **Layar 6 - Profile (Profile Setting)**:
   - Header *"Halo [Nama],*"
   - Card informasi *"Profile Saya"*
   - Formulir *"Profile Setting"* dengan input edit nama pengguna dan tombol *"Save"* yang langsung memperbarui greeting di seluruh layar
   - Bottom navigation aktif di tab *Profile*.
7. **Layar 7 - Alamat Pengiriman (Order)**:
   - Header *"Alamat"* dan ringkasan pesanan item terpilih dari Home
   - Formulir input: *Nama Lengkap*, *Alamat*, dan *Patokan atau detail lengkap*
   - Tombol utama *"Order dan Kirim"*.
8. **Layar 8 - Konfirmasi Pesanan**:
   - Header *"Halo [Nama],*"
   - Badge animasi sukses hijau centang *"OK"*
   - Pesan ramah: *"Terima kasih sudah memesan tunggu aja sebentar kurir kami segera mengantarkan pesananmu!"*
   - Tombol *"Oke"* untuk mereset keranjang dan kembali ke Home.

#### Visual Identity & Theme
- **Gaya Estetika**: Warm Artisanal Coffeehouse.
- **Palet Warna**:
  - `Primary`: Deep Coffee Brown (`#4A2E1B` / `#3B2213`)
  - `Secondary`: Warm Terracotta / Cinnamon (`#C85A32`)
  - `Surface / Background`: Warm Soft Cream (`#FDFBF7` / `#F8F3EB`)
  - `Card Background`: White Card dengan border halus hangat (`#FFFFFF`, border `#EFE8DF`)
  - `Accent / Success`: Fresh Matcha Mint (`#2E7D32` / `#4CAF50` untuk badge OK konfirmasi)
  - `Text Colors`: Espresso Dark (`#1C140E`) dan Muted Roast (`#6D5D53`)
- **Tipografi**: Display serif/rounded modern yang bersahabat untuk heading, dan sans-serif geometris yang sangat mudah dibaca untuk harga dan deskripsi item.
- **Layout & Accessibility**: Touch target minimal 48dp, elevation M3 yang lembut, padding konsisten 16dp/20dp, serta penanganan safe insets tepi-ke-tepi (`enableEdgeToEdge`, status bar dan navigation bar insets).

---

### 3. Key Product Decisions & Trade-Offs

- **State Management di ViewModel**:
  - *Keputusan*: Menggunakan single source of truth `KopiViewModel` dengan `StateFlow` yang menampung user profile (nama aktif), authentication state, item menu statis, item keranjang belanja, formulir alamat, dan status pesanan.
  - *Alasan*: Menjamin transisi mulus antar 8 layar tanpa hilangnya state, instan merespons perubahan nama di Profile ke header *"Halo Nama,"* di Home, Profile, dan Konfirmasi Pesanan.
- **Navigasi Berbasis State & BackHandler**:
  - *Keputusan*: Menggunakan state routing terpadu dengan sealed class `Screen` dan `BackHandler` Android di setiap sub-layar.
  - *Alasan*: Memastikan tombol fisik/gesture back Android bekerja secara alami pada alur auth dan order tanpa crash atau backstack yang berantakan.
- **Visual Assets**:
  - *Keputusan*: Membuat aset grafis vektor dan ilustrasi makanan/minuman yang detail serta icon launcher kustom untuk branding "Kopi dan Nyemil".
  - *Alasan*: Memenuhi syarat penilaian antarmuka yang menarik (20 poin) dan instruksi platform wajib kustomisasi launcher icon.

---

### 4. Technical Architecture & Data Strategy

```
┌────────────────────────────────────────────────────────┐
│                   MainActivity                         │
│             (enableEdgeToEdge + Theme)                 │
└──────────────────────────┬─────────────────────────────┘
                           │
                           ▼
┌────────────────────────────────────────────────────────┐
│                   KopiViewModel                        │
│  - uiState: StateFlow<KopiUiState>                     │
│  - currentUser: UserProfile ("Ade Haikal" / "Nama")    │
│  - menuList: List<MenuItem> (5+ items)                 │
│  - cartItems: Map<MenuItem, Int>                       │
│  - orderAddress: DeliveryAddress                       │
│  - Functions: login(), register(), updateProfile(),    │
│               addToCart(), checkout(), reset()         │
└──────────────────────────┬─────────────────────────────┘
                           │
                           ▼
┌────────────────────────────────────────────────────────┐
│                  KopiAppNavigation                     │
│  when (currentScreen) {                                │
│    Screen.Splash -> SplashScreen(...)                  │
│    Screen.SignUp -> SignUpScreen(...)                  │
│    Screen.Login -> LoginScreen(...)                    │
│    Screen.Register -> RegisterScreen(...)              │
│    Screen.Home -> HomeScreen(BottomBar)                │
│    Screen.Profile -> ProfileScreen(BottomBar)          │
│    Screen.OrderAddress -> OrderAddressScreen(...)      │
│    Screen.OrderConfirmation -> OrderConfirmScreen(...) │
│  }                                                     │
└────────────────────────────────────────────────────────┘
```

#### Data Entities:
```kotlin
data class MenuItem(
    val id: String,
    val name: String,
    val category: String,
    val description: String,
    val price: Long,
    val iconRes: Int? = null,
    val emoji: String = "☕"
)

data class DeliveryAddress(
    val recipientName: String = "",
    val fullAddress: String = "",
    val landmark: String = ""
)

data class UserSession(
    val fullName: String = "Ade Haikal",
    val username: String = "adehaikal",
    val isLoggedIn: Boolean = false
)
```

#### Dokumentasi README.md (Komponen Penilaian 20 poin):
Akan disertakan file `README.md` berstandar tinggi yang mencakup:
1. Deskripsi Aplikasi "Kopi dan Nyemil".
2. Daftar Fitur Lengkap (8 Alur Layar).
3. Teknologi yang Digunakan (Kotlin, Jetpack Compose, Material 3, StateFlow, Coroutines).
4. Panduan Cara Menjalankan Aplikasi di Android Studio.
5. Struktur Proyek & Panduan Pengumpulan.
