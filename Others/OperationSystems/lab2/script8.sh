#!/bin/bash

sleep 1m

ps --pid $$ --ppid $$ -N -o pid,etime,comm --no-header | awk '{ if (length($2) == 5 && substr($2, 0, 2) == "00") { print $0 } }'