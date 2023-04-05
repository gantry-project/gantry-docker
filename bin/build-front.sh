#!/bin/bash

FRONT_HOME=${FRONT_HOME:-"$(cd "$(dirname "$0")"/../front || exit; pwd)"}
cd "${FRONT_HOME}" || exit

if ! npm install; then
  echo "Build failed. : while npm install";
  exit 1;
fi

if ! npm run build; then
  echo "Build failed.";
  exit 1;
fi

echo "Build Passed.";