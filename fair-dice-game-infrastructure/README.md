# Run the Full Stack Environment

This repo includes a helper script to build and launch the system using Docker Compose.

---

## 1. Run the Compose Script
---
Use the `compose.sh` script to build and start services.

### Usage:
```bash
./compose.sh [all|backend|frontend|postgres]
```

Options:

    all (default) – builds and starts everything

    backend – backend only

    frontend – frontend only

    postgres – database only

If no argument is given, it defaults to all.

## 🛠️ 2. Build Backend

---

The script automatically runs ./build_backend.sh before starting containers. Make sure this script is present and executable.
🔁 Example

Start the full stack:

./compose.sh

Start only frontend:

./compose.sh frontend