#!/bin/bash

ps -e -o pid,cmd | awk '$2 ~ "^/sbin/" {print $1}' > out2.lst
