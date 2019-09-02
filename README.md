# backtest-docker

## Overview
This repo contains an info about docker image to run backtesting and trading strategies optimization.
The docker image is based on centos and openjdk-8 and also contains [Indicore Backtest Utils](http://fxcodebase.com/wiki/index.php/Indicore_Backtest_Utils).
The image contains a webserver exposing a REST API to use the functionality of Backtest Utils.

## Installation
The image is available at [Docker Hub](https://hub.docker.com/r/gehtsoft/indicore-backtest).

To get the image locally:

*docker pull gehtsoft/indicore-backtest*

The simpliest way to run the container on a local machine is:

*docker run -p 4000:80 gehtsoft/indicore-backtest*

This command runs the container with http port mapped to local 4000.

The REST API will be available at:

*http://<HOST_ADDRESS>:4000*

There are options to run the image in public clouds:

[Amazon](https://github.com/gehtsoft/backtest-docker/tree/master/AWS)

[Google](https://github.com/gehtsoft/backtest-docker/tree/master/GoogleCloud)

[Microsoft](https://github.com/gehtsoft/backtest-docker/tree/master/MicrosoftAzure)

## REST API
### Indicators

#### Add a custom indicator
POST: /indicator/add

##### Example:
curl -X POST -F "src=@data/ATR_pips_Indicator.lua" $BACKTEST_DOCKER_ADDRESS/indicator/add

#### Delete a custom indicator
DELETE: /indicator/delete

##### Example:
curl -X DELETE -H "Content-type: application/json" -d '{"name":"ATR_pips_Indicator.lua"}' $BACKTEST_DOCKER_ADDRESS/indicator/delete

#### List  custom indicators
GET: /indicator/list

##### Example:
curl -X GET $BACKTEST_DOCKER_ADDRESS/indicator/list

### Strategies

#### Add a custom strategy
/strategy/add

##### Example:
curl -X POST -F "src=@data/MA_Crossover_Strategy.lua" $BACKTEST_DOCKER_ADDRESS/strategy/add

#### Delete a custom strategy
/strategy/delete

##### Example:
curl -X DELETE -H "Content-type: application/json" -d '{"name":"MA_Crossover_Strategy.lua"}' $BACKTEST_DOCKER_ADDRESS/strategy/delete

#### List  custom strategy
/strategy/list

##### Example:
curl -X GET $BACKTEST_DOCKER_ADDRESS/strategy/list

### Data files

#### Add a custom file
/data/add

##### Example:
curl -X POST -F "src=@data/test_data.csv" $BACKTEST_DOCKER_ADDRESS/data/add

#### Delete a custom file
/data/delete

##### Example:
curl -X DELETE -H "Content-type: application/json" -d '{"name":"test_data.csv"}' $BACKTEST_DOCKER_ADDRESS/data/delete

#### List  custom files
/data/list

##### Example:
curl -X GET $BACKTEST_DOCKER_ADDRESS/data/list

### Backtester

#### Run backtesting
/bt/run

##### Example:
curl -F "name=bt001" -F "src=@data/bt_ma.bpj" $BACKTEST_DOCKER_ADDRESS/bt/run

#### Delete backtesting and all related data
/bt/<bt_id>/delete

##### Example:
curl -X DELETE $BACKTEST_DOCKER_ADDRESS/bt/bt001/delete

#### Get backtesting project file
/bt/<bt_id>/input

##### Example:
curl -X GET $BACKTEST_DOCKER_ADDRESS/bt/bt001/input/

#### Get backtesting output
/bt/<bt_id>/output

##### Example:
curl -X GET $BACKTEST_DOCKER_ADDRESS/bt/bt001/output/

#### Get backtesting final statistics
/bt/<bt_id>/stat

##### Example:
curl -X GET $BACKTEST_DOCKER_ADDRESS/bt/bt001/stat/

#### Get backtesting log
/bt/<bt_id>/log

##### Example:
curl -X GET $BACKTEST_DOCKER_ADDRESS/bt/bt001/log/

### Optimizer

#### Run optimizer
/opt/run

##### Example:
curl -F "name=opt001" -F "src=@data/opt_ma.opj" $BACKTEST_DOCKER_ADDRESS/opt/run

#### Delete optimizer run and all related data
/opt/<opt_id>/delete

##### Example:
curl -X DELETE $BACKTEST_DOCKER_ADDRESS/opt/opt001/delete

#### Get optimizer project file
/opt/<opt_id>/input

##### Example:
curl -X GET $BACKTEST_DOCKER_ADDRESS/opt/opt001/input/

#### Get optimizer output
/opt/<opt_id>/output

##### Example:
curl -X GET $BACKTEST_DOCKER_ADDRESS/opt/opt001/output/

#### Get optimizer log
/opt/<opt_id>/log

##### Example:
curl -X GET $BACKTEST_DOCKER_ADDRESS/opt/opt001/log/

