@echo off
start /B java -server -Xms16M -Xmx32M -XX:+UseParallelGC -jar void-dynamic-dns-fat.jar true > resolve.log
