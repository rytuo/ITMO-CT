#!/bin/bash

# echo "./script1.sh" | at now + 2 minute
sleep 2m && ./script1.sh &
tail -f ~/report
