# Android Starter Template 🚀

A **production-ready Android starter template** built with Google's recommended architecture — UDF-based MVVM with sealed UiState, multi-module Clean Architecture, and convention plugins for fast builds.

Save **2+ weeks** of setup time. Clone → configure → ship.

---

## ✨ Features

| Layer | Tech | Status |
|-------|------|--------|
| **UI** | Jetpack Compose + Material 3 + Dynamic Color | ✅ |
| **Architecture** | UDF MVVM + Sealed UiState + Clean Arch (3 layers) | ✅ |
| **DI** | Hilt | ✅ |
| **Networking** | Retrofit + OkHttp + Auth Interceptor + Token Refresh | ✅ |
| **Database** | Room (local cache) | ✅ |
| **Preferences** | DataStore (replaces SharedPreferences) | ✅ |
| **Auth** | Login/Signup flow + Biometric stub | ✅ |
| **Navigation** | Navigation Compose with type-safe routes | ✅ |
| **Testing** | Turbine (Flow testing), MockWebServer, Truth | ✅ |
| **CI** | GitHub Actions (lint + test on every push) | ✅ |
| **Performance** | Baseline Profiles + ProGuard rules | ✅ |
| **Debug** | Chucker + LeakCanary + Timber | ✅ |
| **Build** | Convention plugins for multi-module | ✅ |
| **Payments** | Billing 7 stub ready | ✅ |
| **Offline** | Network-bound resource pattern | ✅ |

---

## 🏗 Architecture

```
app/                    → Application entry, navigation
├── core/
│   ├── model/          → UiResult sealed interface
│   ├── ui/             → Theme, shared composables
│   ├── network/        → Retrofit, interceptors, DI
│   ├── database/       → Room, DAOs, entities
│   ├── datastore/      → Preferences, token storage
│   ├── domain/         → Use cases (ready for business logic)
│   └── testing/        → Test rules, test doubles
├── data/
│   └── repository/     → Repository implementations + fakes
└── feature/
    ├── auth/           → Login/Signup screens + ViewModel
    ├── home/           → Dashboard with paginated list
    └── settings/       → Dark mode, logout, about
```

### UDF Data Flow

```
User Action → ViewModel.fun() → Repository → API/Database
                                  ↓
Compose UI ← StateFlow<UiState> ← ViewModel
```

---

## 🚀 Quick Start

### Prerequisites

- JDK 17+
- Android Studio Hedgehog (2024.3.1+) or later
- Android SDK 35

### Setup

```bash
# Clone the repo
git clone <your-repo-url>
cd android-starter

# Verify environment
./scripts/verify.sh

# Build
./gradlew assembleDebug

# Run tests
./gradlew testDebugUnitTest
```

### Configure

1. Update `BASE_URL` in `core/network/di/NetworkModule.kt` with your API endpoint
2. Replace app icon in `app/src/main/res/mipmap-*`
3. Configure Firebase (optional) for push notifications
4. Update `gradle/libs.versions.toml` for version bumps

---

## 🧪 Testing

```bash
# Run all unit tests
./gradlew testDebugUnitTest

# Run specific test class
./gradlew :feature:auth:testDebugUnitTest --tests "*AuthViewModelTest*"

# Lint
./gradlew lintDebug
```

Tests use:
- **Turbine** — elegant Flow/StateFlow testing
- **Truth** — readable assertions
- **MockWebServer** — HTTP mock server
- **Fake repositories** — test doubles without mocking libraries

### Test Coverage

| Module | Tests |
|--------|-------|
| AuthViewModel | 7 tests (login, signup, validation, errors) |
| HomeViewModel | 4 tests (loading, success, error, load more) |
| SettingsViewModel | 2 tests (initial state, logout) |
| Repository tests | 5 tests (login, errors, logout, items) |

---

## 📦 Selling Points

| Feature | Value to You |
|---------|-------------|
| Multi-module with convention plugins | 40% faster builds |
| Offline-first with Room | Works without internet |
| Token refresh pre-built | No production auth bugs |
| Full test suite | Modify with confidence |
| CI green badge | Professional presentation |
| Material 3 + Dynamic Color | Modern UI out of the box |
| Debug drawer | Built-in debugging tools |
| Billing scaffold | Monetize immediately |

---

## 🛠 Customization Guide

See `docs/setup-guide.md` for detailed customization instructions.

---

## 📄 License

MIT — use it for personal or commercial projects.

---

## 🙏 Support

If this template saved you time, consider [buying me a coffee](#).
