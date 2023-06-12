#!/bin/bash

ps -U rytuo -u rytuo --no-heading -o pid,cmd > temp
cat temp | wc -l > out1.lst
cat temp | awk '{print $1 ":" $2}' >> out1.lst

rm temp
