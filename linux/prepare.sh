#!/bin/bash

THIS_DIR=`cd -P "$(dirname "$0")" && pwd`
cd $THIS_DIR

if [ -z "$BACKTEST_DOWNLOAD_URL" ]; then 
  BACKTEST_DOWNLOAD_URL=http://fxcodebase.com/bin/products/IndicoreSDK/3.4.0/IndicoreBacktestUtils-1.1-Linux-x86_64.tar.gz
fi
if [ -z "$BACKTEST_TOMCAT_VERSION" ]; then
  BACKTEST_TOMCAT_VERSION=8.5.42
fi

if [ -z "$BACKTEST_REST_DOWNLOAD_URL" ]; then
  BACKTEST_REST_DOWNLOAD_URL=https://github.com/gehtsoft/backtest-docker/raw/master/REST/api/rest/target/ROOT.war
fi

# Prepare BacktestUtils
if [ -d $THIS_DIR/image/IndicoreBacktestUtils ]; then rm -fr $THIS_DIR/image/IndicoreBacktestUtils || exit 1; fi
mkdir -p $THIS_DIR/image/IndicoreBacktestUtils || exit 1
curl $BACKTEST_DOWNLOAD_URL | tar -xzv -C $THIS_DIR/image/IndicoreBacktestUtils --strip-components 2 \
--exclude "*/JSIndicators" \
--exclude "*/JSStrategies" \
--exclude "*/doc" \
--exclude "*/js2lua" \
--exclude "*/samples" \
--exclude "*IndicatorRunner" \
--exclude "jsMigration.lua" || exit 1
cd $THIS_DIR/image/IndicoreBacktestUtils || exit 1

# Prepare tomcat
if [ -d $THIS_DIR/image/apache-tomcat ]; then rm -fr $THIS_DIR/image/apache-tomcat || exit 1; fi
mkdir -p $THIS_DIR/image/apache-tomcat || exit 1
curl https://archive.apache.org/dist/tomcat/tomcat-${BACKTEST_TOMCAT_VERSION:0:1}/v$BACKTEST_TOMCAT_VERSION/bin/apache-tomcat-$BACKTEST_TOMCAT_VERSION.tar.gz | tar -xzv -C $THIS_DIR/image/apache-tomcat --strip-components 1 || exit 1
sed -i 's/8080/80/g' $THIS_DIR/image/apache-tomcat/conf/server.xml || exit 1
rm -fr $THIS_DIR/image/apache-tomcat/webapps/* || exit 1

# Build and copy REST servlet into tomcat
if [ "$1" == "build-rest" ]; then
  mvn -e -f $THIS_DIR/../REST/api/rest/pom.xml install || exit 1
  cp $THIS_DIR/../REST/api/rest/target/ROOT.war $THIS_DIR/image/apache-tomcat/webapps || exit 1
else
  wget $BACKTEST_REST_DOWNLOAD_URL > $THIS_DIR/image/apache-tomcat/webapps/ROOT.war
fi
exit 0
