#!/bin/bash

max=-1
maxInd=0
for i in $(ps --no-header -a -N -o pid)
do
cur=$(grep VmRSS "/proc/$i/status" | awk '{print $2}')
if [[ $cur -gt $max ]]
then
max=$cur
maxInd=$i
fi
done

echo $maxInd $max
