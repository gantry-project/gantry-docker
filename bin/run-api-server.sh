#!/bin/bash

API_SERVER_HOME=${API_SERVER_HOME:-"$(cd "$(dirname "$0")"; pwd)"}
cd "${API_SERVER_HOME}" || exit

EXECUTABLE_JAR=$(ls -1 ../build/*.jar | tail -1)

if [[ -z ${EXECUTABLE_JAR} ]]; then
  echo "Execution failed.";
  exit 1;
fi

exec java -jar "${EXECUTABLE_JAR}"