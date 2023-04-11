#!/bin/bash

FRONT_HOME=${FRONT_HOME:-"$(cd "$(dirname "$0")"/../front || exit; pwd)"}
cd "${FRONT_HOME}" || exit

if ! npm run test -- --watchAll=false; then
  echo "Test failed.";
  exit 1;
fi

echo "Test Passed.";