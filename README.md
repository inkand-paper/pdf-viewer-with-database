# PDF Viewer with Database (MVVM Architecture)

A modern Android app for viewing and managing PDF books, built with MVVM architecture and Room database.

##Screenshot
<img width="427" height="890" alt="Screenshot 2025-07-24 232023" src="https://github.com/user-attachments/assets/0c9140be-806e-4bf7-b051-71eac46a4ea3" />
<img width="396" height="867" alt="Screenshot 2025-07-24 232036" src="https://github.com/user-attachments/assets/47353829-14ee-4acf-a12b-57ada096cfe0" />


## Features

- **PDF Book List:** View, add, edit, and delete PDF book entries (metadata).
- **Details Screen:** View details about individual books.
- **Database Integration:** All book data is stored locally using Room.
- **MVVM Architecture:** Clean separation between View (UI), Model (data), and ViewModel (logic).
- **Material UI:** Modern interface with bottom navigation and RecyclerView.
- **Async Operations:** Uses Kotlin coroutines for database and UI updates.
- **Unit & Instrumentation Tests:** Sample tests included.

## Architecture Overview

This project uses MVVM (Model-View-ViewModel):

- **Model:**  
  - Data classes (e.g., `PdfData`) represent book entries.
  - Room Database (`PdfDatabase`) and DAO (`PdfDao`) manage local data.
- **View:**  
  - Fragments (`Home`, `Add`) and Activities (`MainActivity`, `DetailsActivity`) handle UI.
  - RecyclerView + Adapter (`PdfAdapter`) display data lists.
- **ViewModel:**  
  - Not explicitly separated (logic is in Fragments), but can be extended for full MVVM.
  - Uses lifecycle-aware coroutines to interact with the database and update UI.

## Main Components

- **MainActivity:** Hosts navigation and initializes the Room database.
- **Home Fragment:** Displays a list of books, allows edits/deletes.
- **Add Fragment:** Add/edit book metadata.
- **DetailsActivity:** (Stub) Shows book details.
- **Room Database:**  
  - `PdfData`: Entity for book.
  - `PdfDao`: Data access methods (CRUD).
  - `PdfDatabase`: Database instance.
- **Adapter:**  
  - `PdfAdapter`: Binds book data to RecyclerView items.

## How MVVM Is Used

- UI events (add/edit/delete) are handled in Fragments.
- Fragments interact with the Room database via DAO methods.
- Database operations use coroutines for smooth UI.
- Data is loaded and updated through lifecycle-aware scopes.
- Separation of concerns: UI (Fragment), Data (Model/DAO/Database).

## Getting Started

### Prerequisites

- Android Studio Electric Eel or newer
- JDK 11+
- Android SDK 21+

### Setup

1. **Clone the repo:**
    ```sh
    git clone https://github.com/inkand-paper/pdf-viewer-with-database.git
    cd pdf-viewer-with-database
    ```
2. **Open in Android Studio**  
   Sync Gradle and build the project.

3. **Run the app**  
   Choose an emulator or real device and run.

## Project Structure

```
app/
├── src/
│   ├── main/
│   │   ├── java/com/example/pdfbooks/
│   │   │   ├── view/adapter/PdfAdapter.kt
│   │   │   ├── view/activity/MainActivity.kt
│   │   │   ├── view/fragment/Home.kt
│   │   │   ├── view/fragment/Add.kt
│   │   │   ├── DetailsActivity.kt
│   │   │   ├── database/PdfDatabase.kt
│   │   │   ├── model/PdfData.kt
│   │   │   ├── dao/PdfDao.kt
│   │   ├── res/layout/...
│   ├── test/
│   ├── androidTest/
├── build.gradle.kts
├── settings.gradle.kts
```

## Usage

- **Add Book:** Use the "+" navigation to add a new book (name, author, version, publish year).
- **Edit/Delete:** Long-press a book for edit/delete options.
- **View Details:** Tap a book to view details.

## License

MIT License

---

**Contribution:**  
Pull requests and issues are welcome!

**Contact:**  
Open an issue for questions or suggestions.
