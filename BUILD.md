# Build Guide — BuildScapes

This document describes how to build and run the BuildScapes Android application.

---

## 📋 Requirements

- Android Studio (Hedgehog or newer)
- JDK 17
- Android SDK 34+
- Gradle (via wrapper)

---

## 🛠️ Build Using Android Studio

1. Clone the repository
2. Open **Android Studio**
3. Select **Open an existing project**
4. Choose the `BuildScapes` directory
5. Wait for Gradle sync to finish
6. Click **Run ▶** to launch the app

---

## 🖥️ Build Using Command Line

```bash
./gradlew assembleDebug
````

To install on a connected device or emulator:

```bash
./gradlew installDebug
```

---

## 🧪 Run Tests

```bash
./gradlew test
```

---

## 📦 Output

Generated APK files can be found in:

```text
app/build/outputs/apk/
```

---

## ⚠️ Notes

* Ensure SDK versions are installed correctly
* Emulator or physical device is required to run the application

````

---

# 📄 `SECURITY.md`

```md
# Security Policy

## Supported Versions

Only the latest development version of BuildScapes is actively supported.

---

## Reporting a Vulnerability

If you discover a security vulnerability, please **do not open a public issue**.

Instead, report it privately via:

📧 Email: security@buildscapes.com  
📌 Or contact the maintainers directly

---

## Scope

Security issues may include:
- Data leakage
- Unauthorized access
- Misuse of permissions
- Authentication or session issues
