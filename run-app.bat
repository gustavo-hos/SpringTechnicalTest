@echo off

set MAX_HEAP_SIZE=1024m

set MAX_MEMORY_SIZE=1024m


set JAR_FILE=.\app\order_manager-1.0.jar

java -Xmx%MAX_HEAP_SIZE% -Xms%MAX_MEMORY_SIZE% -jar "%JAR_FILE%"
