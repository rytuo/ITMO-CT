#!/bin/bash

mkfifo pipe

./proc &
./gen

rm pipe
