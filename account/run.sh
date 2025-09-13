#!/bin/bash
# Author: QuanTuanHuy, Description: Part of Serp Project

echo "Loading environment variables from .env file..."

# Load environment variables from .env file
if [ -f .env ]; then
    while IFS='=' read -r key value || [ -n "$key" ]; do
        if [[ -n "$key" && ! "$key" =~ ^[[:space:]]*# ]]; then
            export "$key"="$(echo "$value" | sed 's/[[:space:]]*$//')"
            echo "Set $key=$value"
        fi
    done < .env
else
    echo "Warning: .env file not found!"
fi

echo ""
echo "Starting Account Service..."
echo ""

export SPRING_PROFILES_ACTIVE=prod

./mvnw spring-boot:run -Dspring-boot.run.profiles=prod