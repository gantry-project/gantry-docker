#!/bin/bash

BUILD_DIR=${BUILD_DIR:-"$(cd "$(dirname "$0")"/../build; pwd)"}
cd "${BUILD_DIR}" || exit

EXECUTABLE_JAR=$(ls -1 *.jar | tail -1)

if [[ -z ${EXECUTABLE_JAR} ]]; then
  echo "Execution failed.";
  exit 1;
fi

#[[ -f ${CURRENT_DIR}/application.properties ]] && CONFIG_OPTIONS="--spring.config.location=${CURRENT_DIR}/application.properties"

exec java -jar "${EXECUTABLE_JAR}"