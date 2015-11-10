#! /bin/bash
root_log=/work/projects/intellij/db_logviewer
main=build/classes/main

cd $root_log/$main
#echo $PWD
export CLASSPATH=$root_log/$main/.:$root_log/lib/*:$root_log/lib_gradle/*

unset DISPLAY
java com.rockvole.logback.ShowLogBackViewPort "$@" 2>&1 | tee /work/tmp/rockvole.log
