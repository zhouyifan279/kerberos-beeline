#!/usr/bin/env bash

THISSERVICE=kerberos_beeline
export SERVICE_LIST="${SERVICE_LIST}${THISSERVICE} "

kerberos_beeline () {
  CLASS=org.apache.hive.beeline.KerberosBeeLine;

  # include only the beeline client jar and its dependencies
  beelineJarPath=`ls ${HIVE_LIB}/hive-beeline-*.jar`
  superCsvJarPath=`ls ${HIVE_LIB}/super-csv-*.jar`
  jlineJarPath=`ls ${HIVE_LIB}/jline-*.jar`
  kerberosBeelineJarPath=`ls ${HIVE_LIB}/kerberos-beeline-*.jar`
  hadoopClasspath=""
  if [[ -n "${HADOOP_CLASSPATH}" ]]
  then
    hadoopClasspath="${HADOOP_CLASSPATH}:"
  fi
  export HADOOP_CLASSPATH="${hadoopClasspath}${HIVE_CONF_DIR}:${beelineJarPath}:${superCsvJarPath}:${jlineJarPath}:${kerberosBeelineJarPath}"
  export HADOOP_CLIENT_OPTS="$HADOOP_CLIENT_OPTS -Dlog4j.configurationFile=beeline-log4j2.properties "

  exec $HADOOP jar ${beelineJarPath} $CLASS $HIVE_OPTS "$@"
}

kerberos_beeline_help () {
  kerberos_beeline "--help"
}
