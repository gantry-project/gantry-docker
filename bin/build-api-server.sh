#!/bin/bash

API_SERVER_HOME=${API_SERVER_HOME:-"$(cd "$(dirname "$0")"/../api-server || exit; pwd)"}
cd "${API_SERVER_HOME}" || exit

if ! ./gradlew build; then
  echo "Build failed.";
  exit 1;
fi

echo "Build Passed.";