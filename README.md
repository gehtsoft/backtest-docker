# backtest-docker

## Overview
This is a containerized version of [Console Backtester](http://fxcodebase.com/wiki/index.php/Console_Backtester) and [Optimizer](http://fxcodebase.com/wiki/index.php/Console_Optimizer) applications.

So, instead of setting up the apps locally, a user can deploy the docker image in some cloud and run backstesting/optimization via REST interface.

The repo contains an info about docker image and REST protocol to run backtesting and trading strategies optimization.

The main docker image is based on Centos-6 and Openjdk-8 and also contains [Indicore Backtest Utils](http://fxcodebase.com/wiki/index.php/Indicore_Backtest_Utils).

The image contains a webserver exposing a REST API to use the functionality of Backtest Utils.

Also there is a Windows image with the same functionality to be run under Windows 10 within native mode.

More details on [FXCodebase](http://fxcodebase.com/wiki/index.php/Indicore_Backtest_Docker)

## Do it yourself
Although we provided a ready-to-go docker image, all the components are also open and the image could be built on yout own.
The image based on:
- OpenJDK
- [Indicore Backtest Utils](http://fxcodebase.com/wiki/index.php/Indicore_Backtest_Utils).
- [REST service exposing Backtest Utils functionality](https://github.com/gehtsoft/backtest-docker/tree/master/REST/api/rest)

Dockerfiles and helper scripts are prepared for [Windows](https://github.com/gehtsoft/backtest-docker/tree/master/windows) and [Linux](https://github.com/gehtsoft/backtest-docker/tree/master/linux).
