@echo off
start /B java -server -Xms16M -Xmx32M -XX:+UseParallelGC -jar void-ddns-fat-jar-with-dependencies.jar true simple-circle-dns.playbook >> ddns_info.log 2>&1
