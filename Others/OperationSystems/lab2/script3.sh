#!/bin/bash

ps --pid $$ --ppid $$ -N -o pid,stime,comm --sort=start_time --no-header | tail -n 1
