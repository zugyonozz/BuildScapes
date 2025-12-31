# API Reference — BuildScapes

This document describes the core components of the application.

---

## Core Components

### DiscoverViewModel
Handles fetching and presenting architectural inspirations.

**Functions:**
- loadDiscoverFeed()
- refreshContent()

---

### BookmarkManager
Manages saved design references.

**Functions:**
- saveItem(id)
- removeItem(id)
- getAllBookmarks()

---

## Data Models

### DesignItem
Represents an architectural reference item.

Properties:
- id
- title
- imageUrl
- category
