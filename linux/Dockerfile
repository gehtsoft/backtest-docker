FROM centos:7.6.1810

COPY ./IndicoreBacktestUtils /IndicoreBacktestUtils
COPY ./apache-tomcat  /apache-tomcat

RUN yum -y install java-1.8.0-openjdk && yum clean all

ENV LD_LIBRARY_PATH=/IndicoreBacktestUtils/
ENV SDK_DIR=/IndicoreBacktestUtils/
ENV WORK_DIR_OPT=/opt
ENV WORK_DIR_BT=/bt
ENV OUT_FILE=out.txt
ENV RUN_FILE_OPT=ConsoleOptimizer
ENV RUN_FILE_BT=ConsoleBacktester
ENV IN_FILE=conf.xml
ENV LOG_FILE=dbg.out
ENV STAT_FILE=stat.txt
ENV DATA_DIR=/extdata


CMD ["sh", "/apache-tomcat/bin/catalina.sh", "run"]
