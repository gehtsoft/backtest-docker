# backtest-docker

## Overview
This repo contains an info about docker image to run backtesting and trading strategies optimization.
The docker image is based on centos and openjdk-8 and also contains [Indicore Backtest Utils](http://fxcodebase.com/wiki/index.php/Indicore_Backtest_Utils).
The image contains a webserver exposing a REST API to use the functionality of Backtest Utils.

## REST API
### Indicators

#### Add a custom indicator
/indicator/add

#### Delete a custom indicator
/indicator/delete

#### List  custom indicators
/indicator/list

### Strategies

#### Add a custom strategy
/strategy/add

#### Delete a custom strategy
/strategy/delete

#### List  custom strategy
/strategy/list

### Data files

#### Add a custom file
/data/add

#### Delete a custom file
/data/delete

#### List  custom files
/data/list

### Backtester

#### Run backtesting
/bt/run

#### Delete backtesting and all related data
/bt/<bt_id>/delete

#### Get backtesting project file
/bt/<bt_id>/input

#### Get backtesting output
/bt/<bt_id>/output

#### Get backtesting final statistics
/bt/<bt_id>/stat

#### Get backtesting log
/bt/<bt_id>/log

### Optimizer

#### Run optimizer
/opt/run

#### Delete optimizer run and all related data
/opt/<opt_id>/delete

#### Get optimizer project file
/opt/<opt_id>/input

#### Get optimizer output
/opt/<opt_id>/output

#### Get optimizer log
/opt/<opt_id>/log
