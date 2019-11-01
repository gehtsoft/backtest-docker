#!/bin/bash

THIS_DIR=`cd -P "$(dirname "$0")" && pwd`
cd $THIS_DIR

# Prepare docker build context
$THIS_DIR/prepare.sh || exit 1

# Build fat docker image
docker rmi indicore-backtest --force
docker build -t indicore-backtest:latest -f $THIS_DIR/Dockerfile $THIS_DIR/image || exit 1

exit 0
