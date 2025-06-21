#!/bin/bash
set -e

# Show usage instructions
usage() {
  echo "Usage: $0 [all|backend|frontend|postgres]"
  echo
  echo "  all       - Build and start all services (default)"
  echo "  backend   - Build and start only the backend"
  echo "  frontend  - Build and start only the frontend"
  echo "  postgres  - Build and start only the database"
}

# If no argument provided, default to 'all' after showing usage
SERVICE="${1:-all}"
[ -z "$1" ] && usage && echo && echo "No argument given, defaulting to: all"

# Start services in detached mode (-d), ignore if already running
case "$SERVICE" in
  all|backend|frontend|postgres)
    echo "Starting service(s): $SERVICE"
    docker-compose up --build -d $([ "$SERVICE" = "all" ] || echo "$SERVICE")
    ;;
  *)
    echo "Invalid option: $SERVICE"
    usage
    exit 1
    ;;
esac
