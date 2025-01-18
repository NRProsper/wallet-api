#!/bin/bash

# List of required environment variables
required_vars=(
  "DB_USER"
  "DB_PASSWORD"
  "DB"
  "DATABASE_URL"
  "JWT_SECRET_KEY"
  "JWT_EXPIRATION"
  "FRONTEND_URL"
  "SPRING_MAIL_HOST"
  "SPRING_MAIL_PORT"
  "MAIL_USERNAME"
  "MAIL_PASSWORD"
)

# Check if each variable is set
for var in "${required_vars[@]}"; do
  if [ -z "${!var}" ]; then
    echo "Error: Environment variable $var is not set."
    exit 1
  fi
done

echo "All required environment variables are set."