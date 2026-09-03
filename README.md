# Quiz App — Native Android (Kotlin)

Aplikasi Quiz sederhana berbasis Android Native yang dibuat menggunakan **Kotlin**, **Jetpack Compose**, **StateFlow**, dan **DataStore Preferences** sebagai bagian dari *Android Developer Technical Test*.

---

## Ringkasan Aplikasi & Fitur Utama

- **3 Tingkat Kesulitan (*Difficulty*)**: Easy, Medium, dan Hard.
- **Random Question Generator**: Mengisi 10 pertanyaan secara acak di setiap sesi kuis dengan pengacakan ganda (urutan soal & urutan opsi jawaban A/B/C/D).
- **Interaktif & Visual Feedback**: Indikator warna langsung saat memilih jawaban (Hijau untuk jawaban benar, Merah untuk jawaban salah).
- **Best Score Persistence**: Menyimpan skor tertinggi secara lokal per tingkat kesulitan menggunakan Jetpack Preferences DataStore.
- **UDF (Unidirectional Data Flow)**: Pengelolaan status UI yang konsisten dan *lifecycle-aware* menggunakan `ViewModel` & `StateFlow`.
- **Unit Testing**: Pengujian otomatis untuk komponen kritikal seperti `QuestionGenerator`, `UserPreferencesRepository`, dan `QuizViewModel`.

---

## Tech Stack & Library Utama

- **Bahasa**: Kotlin (100%)
- **UI Framework**: Jetpack Compose + Material 3
- **Architecture**: MVVM (Model-View-ViewModel) + Clean Layering
- **State Management**: StateFlow & Kotlin Coroutines
- **Navigation**: Jetpack Navigation Compose
- **Local Persistence**: Jetpack Preferences DataStore
- **Testing**: JUnit 4 & Kotlinx Coroutines Test

---

## Cara Menjalankan Project

1. **Clone Repository**:
   ```bash
   git clone <repository-url>
   cd QuizApp
   ```
2. **Buka di Android Studio**:
   - Gunakan Android Studio Ladybug / Jellyfish (atau versi terbaru yang mendukung AGP 8.x+ dan Compose).
   - Biarkan Gradle Sync berjalan hingga selesai.
3. **Jalankan Aplikasi**:
   - Pilih emulator Android (API 24+) atau perangkat fisik.
   - Klik **Run (`Shift + F10`)**.
4. **Menjalankan Unit Test**:
   - Jalankan perintah gradle berikut di terminal:
     ```bash
     ./gradlew testDebugUnitTest
     ```

---

## Arsitektur & Struktur Proyek

Proyek ini menerapkan pola **MVVM (Model-View-ViewModel)** dengan pemisahan *layer* yang jelas (*Clean Layering*):

```text
com.example.quizapp/
├── data/
│   ├── generator/
│   │   └── QuestionGenerator.kt      # Algoritma generator 10 soal acak & pengacakan opsi
│   ├── model/
│   │   ├── Difficulty.kt             # Enum EASY, MEDIUM, HARD
│   │   └── Question.kt               # Data class soal & opsi jawaban
│   └── repository/
│       └── UserPreferencesRepository.kt # DataStore persistence untuk Best Score
│
├── ui/
│   ├── navigation/
│   │   └── NavGraph.kt               # Routing Jetpack Navigation Compose
│   ├── screen/
│   │   ├── HomeScreen.kt             # Layar awal & pemilihan difficulty
│   │   ├── QuizScreen.kt             # Layar kuis (soal, opsi, progress, score)
│   │   └── ResultScreen.kt           # Layar hasil akhir & rekor baru
│   ├── theme/                        # Material 3 Theme, Color, Type
│   └── viewmodel/
│       ├── QuizUiState.kt            # Immutable UI state data class
│       └── QuizViewModel.kt          # ViewModel pengelola status kuis & business logic
│
└── MainActivity.kt                   # Entry point aplikasi
```

---

## Cara Kerja Question Generator & Pembeda Difficulty

1. **Pembeda Difficulty**:
   - Class `QuestionGenerator` memiliki 3 bank soal terpisah (`easyQuestions`, `mediumQuestions`, `hardQuestions`).
   - Setiap bank soal berisi 15 pertanyaan dengan tingkat kesulitan topik yang terukur:
     - **Easy**: Trivia umum dasar & ilmu pengetahuan populer.
     - **Medium**: Geografi, sejarah, dan sains menengah.
     - **Hard**: Logika, algoritma ilmu komputer, dan pengetahuan sains spesifik.

2. **Algoritma Pengacakan Ganda (*Double Shuffling*)**:
   - `generateQuestions(difficulty, count = 10)` memilih bank soal sesuai `difficulty`.
   - Mengacak daftar soal (`.shuffled()`) dan mengambil 10 soal pertama.
   - Untuk setiap soal, daftar 4 opsi jawaban **juga diacak kembali** secara dinamis, lalu `correctAnswerIndex` disesuaikan secara otomatis.
   - **Hasil**: Setiap kali kuis dimulai, urutan soal dan posisi opsi A/B/C/D selalu berbeda.

---

## Local Persistence (DataStore)

- Menggunakan **Jetpack Preferences DataStore** (`androidx.datastore:datastore-preferences`).
- Membaca skor tertinggi sebagai `Flow<Int>` yang reaktif.
- Metode `saveScoreIfBest(difficulty, newScore)` secara otomatis membandingkan skor baru dengan *best score* yang tersimpan di DataStore, dan meng-update nilai jika skor baru lebih tinggi.

---

## Testing

Proyek ini dilengkapi dengan Unit Test otomatis untuk menguji logika bisnis utama:
- `QuestionGeneratorTest`: Memastikan 10 soal acak selalu berhasil dibuat dengan 4 opsi & index jawaban valid.
- `UserPreferencesRepositoryTest`: Memastikan pembacaan & penyimpanan high score ke DataStore berjalan benar.
- `QuizViewModelTest`: Memastikan perubahan StateFlow (mulai kuis, jawab soal, hitung skor, & pindah soal) berfungsi akurat.


