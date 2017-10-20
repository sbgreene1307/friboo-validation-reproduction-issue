FROM registry.opensource.zalan.do/stups/openjdk:8-51
COPY target/uberjar/exception-handling-reproduction.jar /app.jar
COPY scm-source.json /scm-source.json
CMD java $(appdynamics-agent) $JAVA_OPTS $(java-dynamic-memory-opts) -jar /app.jar
