#!/bin/bash

SCRIPT_HOME=${SCRIPT_HOME:-"$(cd "$(dirname "$0")"; pwd)"}
cd "${SCRIPT_HOME}" || exit

#EXECUTABLE_JAR=$(ls -1 ../build/*.jar | tail -1)
#
#if [[ -z ${EXECUTABLE_JAR} ]]; then
#  echo "Execution failed.";
#  exit 1;
#fi

exec echo "START FRONTEND"