#!/bin/bash

usage="Usage: start.sh [front|api-server]"

if [ $# -ne 1 ]; then
  echo $usage
  exit 1
fi

FRONT_HOME=${FRONT_HOME:-"$(cd "$(dirname "$0")"/../front || exit; pwd)"}


change_directory() {
  SCRIPT_HOME=${SCRIPT_HOME:-"$(cd "$(dirname "$0")"; pwd)"}
  cd "${SCRIPT_HOME}" || exit
}

if [[ "$1" == "front" ]]; then
  echo Starting front;
  change_directory;
  env | grep REACT_APP_ >> "${FRONT_HOME}"/.env
  ./build-front.sh
  ./deploy-front.sh
  exec ./run-front.sh
elif [[ "$1" == "api-server" ]]; then
  echo Startnig api-server;
  change_directory;
  exec ./run-api-server.sh
else
  echo "Unknown module:" "$1";
  echo $usage;
fi
