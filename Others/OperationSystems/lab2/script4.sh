#!/bin/bash

> out

for i in $(ps -a -N --no-header -o pid)
do
ppid=$(grep PPid "/proc/$i/status" | awk '{print $2}')
ser=$(grep "sum_exec_runtime" "/proc/$i/sched" | awk '{print $3}')
ns=$(grep "nr_switches" "/proc/$i/sched" | awk '{print $3}')
art=$(echo $ser $ns | awk '{print $1/$2}')
echo "ProcessID= $i : Parent_ProcessID= $ppid : Average_Running_Time= $art" >> out
done

cat out | sort -g -k 5 > out4.lst
rm out
