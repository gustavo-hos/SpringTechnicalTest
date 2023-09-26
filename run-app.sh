#!/bin/bash

MAX_HEAP_SIZE=1024m
MAX_MEMORY_SIZE=1024m
JAR_FILE="./app/order_manager-1.0.jar"

java -Xmx$MAX_HEAP_SIZE -Xms$MAX_MEMORY_SIZE -jar "$JAR_FILE"
