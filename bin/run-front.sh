#!/bin/bash

SCRIPT_HOME=${SCRIPT_HOME:-"$(cd "$(dirname "$0")"; pwd)"}
cd "${SCRIPT_HOME}" || exit

TARGET_WEBAPP_DIR=${SCRIPT_HOME}/../build/front

if [[ -z ${TARGET_WEBAPP_DIR} ]]; then
  echo "Execution failed.";
  exit 1;
fi

sed -i 's|\"REACT_APP_GANTRY_API_SERVER_HOST\"\s*:.*|\"REACT_APP_GANTRY_API_SERVER_HOST\":\"https://api-server:9090\"|g'

exec serve -s "${TARGET_WEBAPP_DIR}"

