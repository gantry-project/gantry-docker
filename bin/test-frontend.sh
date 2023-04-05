#!/bin/bash

API_SERVER_HOME=${API_SERVER_HOME:-"$(cd "$(dirname "$0")"/../api-server || exit; pwd)"}
cd "${API_SERVER_HOME}" || exit


if ! ./gradlew test; then
  echo "Test failed.";
  exit 1;
fi

echo "Test Passed.";