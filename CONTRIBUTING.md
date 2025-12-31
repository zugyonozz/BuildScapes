# Contributing to BuildScapes

Thank you for your interest in contributing!

---

## 📌 Contribution Guidelines

1. Fork the repository
2. Create a new branch:
   ```bash
   git checkout -b feat/your-feature-name
   ```

3. Commit changes following the commit message convention
4. Push to your fork
5. Open a Pull Request

---

## 🧠 Coding Standards

### Android Development

* Follow MVVM architecture
* Avoid business logic inside Activities or Fragments
* Use ViewModel for state handling
* Prevent Context leaks

### Kotlin Guidelines

* Prefer immutable data
* Use meaningful naming
* Keep functions small and readable

---

## 🧹 Commit Message Rules

Use Conventional Commits:

```text
feat(ui): add discover screen
fix(core): resolve memory leak
docs(readme): update project overview
```

---

## 🚫 What Not to Do

* Commit generated build files
* Push unfinished or broken code
* Break existing features without discussion
