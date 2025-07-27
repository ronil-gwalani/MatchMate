# 📱 MatchMate – Matrimonial App MVP

**Developed by Ronil Gwalani as an assignment for Shadi.com**

MatchMate is a modern MVP-stage matrimonial app designed to simulate the functionality of apps like Shaadi.com. It provides an intuitive, swipe-based experience to browse and engage with user profiles. The app is fully functional offline, ensuring uninterrupted usability with powerful local persistence and seamless sync logic.

---

## 🚀 Features

- 🔥 Swipe-based matchmaking interface (Accept/Decline)
- 📦 Offline-first approach using Room database
- 📡 Retrofit integration with error handling & retry
- 🎨 Jetpack Compose UI with modern animations and bottom sheets
- 🔀 MVVM architecture with Repository layer and modular code
- ⚙️ Koin for dependency injection
- 🧠 Kotlin Flows & Coroutines for reactive and efficient state handling
- 🧾 Screenshots and user-friendly design aligned with modern UX standards
- 🗂️ Multi-module structure (swipe functionality separated cleanly)

---

## 🧭 App Flow

1. **Splash Screen**
2. **Registration Screen** (skippable, info stored in preferences)
3. **Home Screen** – Contains 3 tabs:
   - **Explore Tab**: Swipe left/right through profiles, view details in bottom sheet
   - **Matches Tab**: View swiped (Accepted/Declined) profiles and update status
   - **Profile Tab**: Add or edit your own profile

---

## 📦 Offline Support Strategy

- Profiles are **fetched from API** and **saved in Room**
- App **always displays profiles from Room**
- **Swiping works offline**, and updates are persisted locally
- If the profile list is exhausted, it will **fetch more profiles** (only when online)
- **Pending requests are queued** and synced automatically when the internet is available again

---

## ⚙️ Tech Stack

| Layer          | Technology                      |
|----------------|----------------------------------|
| UI             | Jetpack Compose                 |
| Architecture   | MVVM + Repository Pattern       |
| Local DB       | Room                            |
| Network Layer  | Retrofit                        |
| Observability  | Kotlin Flow                     |
| DI             | Koin                            |
| Async Ops      | Kotlin Coroutines               |
| Structure      | Multi-module                    |

---

## 🧪 Error Handling

- Network errors gracefully handled
- Retry logic for failed API requests
- Internet connectivity checks with real-time updates
- Queued operations sync automatically

---

## 🖼️ Screenshots

*(Include your screenshots here, like this)*



| Splash Screen | Register(Optional)|  
|-------------|------------------|
| ![Splash Screen](https://github.com/ronil-gwalani/MatchMate/blob/main/screenshots/s1.jpeg) | ![Register](https://github.com/ronil-gwalani/MatchMate/blob/main/screenshots/register.jpeg) |


---

| Explore | Matches | Edit Profile |
|-------------|------------------|---------|
| ![Explore](https://github.com/ronil-gwalani/MatchMate/blob/main/screenshots/s2.jpeg) | ![Matches](https://github.com/ronil-gwalani/MatchMate/blob/main/screenshots/s4.jpeg) | ![Edit](https://github.com/ronil-gwalani/MatchMate/blob/main/screenshots/s6.jpeg) |

---

---

|  |  |  |
|-------------|------------------|---------|
| ![Explore](https://github.com/ronil-gwalani/MatchMate/blob/main/screenshots/s3.jpeg) | ![Matches](https://github.com/ronil-gwalani/MatchMate/blob/main/screenshots/s5.jpeg) | ![Edit](https://github.com/ronil-gwalani/MatchMate/blob/main/screenshots/s7.jpeg) |

---




## 🗂️ Project Modules

- `app`: Base UI logic, DI, Navigation
- `domain`: Model definitions and interfaces
- `data`: Local (Room) and remote (Retrofit) data sources
- `swipe`: Custom swipe UI logic and animations

---

## 📋 Assignment Requirements Checklist

✅ API Integration  
✅ Swipeable Match Cards  
✅ Accept/Decline + Status Handling  
✅ Room DB & Offline Support  
✅ MVVM + Repository + Multi-Module  
✅ Retrofit, Koin, Kotlin Flow  
✅ Clean Compose UI  
✅ Error Handling + Retry Logic  
✅ State Management via Flows & Coroutines  
✅ Screenshots Included  
✅ GitHub Integration Ready  

---

## ▶️ Run Locally

1. Clone the repository:
```bash
git clone https://github.com/ronil-gwalani/MatchMate.git
```

---

## 📲 Download

👉 [Download Android APK](https://ronildeveloper.in/files/MatchMate.apk)

---

## 🙋 About Me

- 🌐 [ronildeveloper.in](https://ronildeveloper.in)  
- 💼 [LinkedIn – Ronil Gwalani](https://www.linkedin.com/in/ronil-gwalani)  
- 📄 [Download My Resume](https://ronildeveloper.in/files/Ronil-CV.pdf)

---

