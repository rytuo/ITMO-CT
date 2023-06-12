#!/bin/bash

./run

cpulimit -l 10 -p $(cat .pid | head -n 1) &
kill $(cat .pid | tail -n 1)
