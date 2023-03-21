#!/bin/bash

API_SERVER_HOME=${API_SERVER_HOME:-"$(cd "$(dirname "$0")"/../api-server || exit; pwd)"}
cd "${API_SERVER_HOME}" || exit

DESTINATION_DIR=${API_SERVER_HOME}/../build/
SOURCE_JAR=$(find build/libs/*.jar | tail -1)
if [[ -z ${SOURCE_JAR} ]]; then
  echo "Deploy failed.";
  exit 1;
fi

mkdir -p "${DESTINATION_DIR}"
cp "${SOURCE_JAR}" "${DESTINATION_DIR}"
echo "Deploy Passed.";