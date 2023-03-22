#!/bin/bash

API_SERVER_HOME=${API_SERVER_HOME:-"$(cd "$(dirname "$0")"/../api-server || exit; pwd)"}
cd "${API_SERVER_HOME}" || exit

EXECUTABLE_JAR=$(find ../build/*.jar | tail -1)

if [[ -z ${EXECUTABLE_JAR} ]]; then
  echo "Execution failed.";
  exit 1;
fi

echo "Execution Passed.";
java -jar "${EXECUTABLE_JAR}"