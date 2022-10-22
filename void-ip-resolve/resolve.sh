#!/bin/bash
nohup java -server -Xms16M -Xmx32M -XX:+UseParallelGC -jar void_ip_resolve-jar-with-dependencies.jar true > resolve.log &
