# Architecture Overview — BuildScapes

BuildScapes follows a **modern Android MVVM architecture** to ensure scalability, maintainability, and clean separation of concerns.

---

## 🧱 High-Level Architecture

```mermaid
flowchart TD
    UI[Activity / Fragment]
    VM[ViewModel]
    REPO[Repository]
    DS[Data Source]
    
    UI --> VM
    VM --> REPO
    REPO --> DS
````

---

## 🧩 Layer Description

### UI Layer

* Activities and Fragments
* Responsible for rendering UI and handling user interactions

### ViewModel Layer

* Holds UI state
* Handles business logic
* Lifecycle-aware

### Repository Layer

* Acts as a single source of truth
* Manages data flow

### Data Source

* Remote API
* Local storage (future extension)

---

## 🎯 Design Goals

* Clear separation of concerns
* Lifecycle safety
* Testability
* Scalability for future features
