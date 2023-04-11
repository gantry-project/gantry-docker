#!/bin/bash

FRONT_HOME=${FRONT_HOME:-"$(cd "$(dirname "$0")"/../front || exit; pwd)"}
cd "${FRONT_HOME}" || exit

TARGET_WEBAPP_DIR=${FRONT_HOME}/../build/front
SOURCE_WEBAPP_DIR=${FRONT_HOME}/build
if [[ -z ${SOURCE_WEBAPP_DIR} ]]; then
  echo "Deploy failed.";
  exit 1;
fi

cp -r "${SOURCE_WEBAPP_DIR}" "${TARGET_WEBAPP_DIR}" && \
echo "Deploy Passed.";