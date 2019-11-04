FROM mcr.microsoft.com/windows/nanoserver:1803

COPY IndicoreBacktestUtils C:/IndicoreBacktestUtils
COPY apache-tomcat  C:/apache-tomcat
COPY java C:/java

ENV CATALINA_HOME=C:\\apache-tomcat
ENV JAVA_HOME=C:\\java
ENV JRE_HOME=C:\\java\\jre

ENV SDK_DIR=C:\\IndicoreBacktestUtils
ENV WORK_DIR_OPT=c:\\opt
ENV WORK_DIR_BT=c:\\bt
ENV OUT_FILE=out.txt
ENV RUN_FILE_OPT=ConsoleOptimizer.exe
ENV RUN_FILE_BT=ConsoleBacktester.exe
ENV IN_FILE=conf.xml
ENV LOG_FILE=dbg.out
ENV STAT_FILE=stat.txt
ENV DATA_DIR=c:\\extdata


CMD [ "c:/apache-tomcat/bin/catalina.bat", "run" ]
