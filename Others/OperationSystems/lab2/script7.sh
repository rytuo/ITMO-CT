#!/bin/bash

> temp1

# write starting number of bytes
for i in $(ps -a -N --no-header -o pid)
do
echo $i $(sudo grep read_bytes "/proc/$i/io" | awk '{print $2}') >> temp1
done

# wait 1 minute
sleep 15s

> temp2

# count diff
for i in $(ps -a -N --no-header -o pid)
do
prev=$(grep "^$i " temp1 | awk '{print $2}')
cur=$(sudo grep "read-bytes" "/proc/$i/io" | awk '{print $2}')
diff=$(echo $cur $prev | awk '{print $1-$2}')
comm=$(cat "/proc/$i/cmdline")
echo "$i $diff $comm" >> temp2
done

cat temp2 | sort -r -g -k 2 | head -n 3 | awk '{print $1 ":" $3 ":" $2}'

# remove temporary files
rm temp1
rm temp2
