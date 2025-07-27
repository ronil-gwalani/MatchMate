# MatchMate – Matrimonial App MVP

**Developed for Shadi.com as a coding assignment**

## 📱 Description

MatchMate is a modern, offline-capable matrimonial app developed as a coding assignment. It simulates core functionality similar to Shaadi.com, providing a swipe-based interface to explore, accept, or decline user profiles. It adheres to modern Android standards, is modular, and offline-friendly.

---

## 🚀 Features

- 🔥 Swipe-based user profile interaction (Right to Accept, Left to Decline)
- 📡 Retrofit-powered API Integration
- 📦 Room DB for local and offline-first profile storage
- 🔀 Offline support with request queuing for retry
- 🧠 MVVM architecture with multi-module structure
- 🎨 Jetpack Compose UI with clean, intuitive navigation
- 🧪 Repository pattern for clean code separation
- 🧩 BottomSheet for profile info, persistent Match Status
- ⚙️ Dependency injection via Koin

---

## 🔁 App Flow

1. **Splash Screen** ➝ **Registration Screen** (optional/skippable)
2. Redirects to **Home Screen** with:
   - `Explore`: Browse user cards, swipe, and view info
   - `Matches`: View accepted/declined profiles and update status
   - `Profile`: Add/Edit your profile
3. If no profiles available, and internet is on, new ones are fetched and saved to Room.
4. Offline swipe support – queue sync when connection resumes.

---

## 🛠️ Tech Stack

| Layer        | Technology       |
|--------------|------------------|
| UI           | Jetpack Compose  |
| DI           | Koin             |
| Network      | Retrofit         |
| Local DB     | Room             |
| Architecture | MVVM + Repository|
| Structure    | Multi-Module     |

---

## ⚡ How it works offline

- Profiles fetched from API are stored in Room
- App always reads from Room to display data
- Swipe actions are saved locally with status
- If offline, pending fetch/sync requests are queued
- Once internet is back, syncs automatically

---

## 📦 Modules

- `app`: Main app logic, navigation
- `swipe`: Handles swipe gesture & animations (separated for reusability)

---

## 📋 Assignment Requirements Covered

✅ API Integration  
✅ Swipeable Match Cards  
✅ Accept/Decline Status & Offline Sync  
✅ Room DB Persistence  
✅ RecyclerView logic ported to Compose equivalents  
✅ Clean architecture (MVVM, Repository)  
✅ Error Handling  
✅ Modular and scalable code  

---

## 📸 Screenshots

*(Add screenshots here if desired)*

---

## 📂 How to Run

1. Clone the repository:
```bash
git clone https://github.com/ronil-gwalani/MatchMate.git
