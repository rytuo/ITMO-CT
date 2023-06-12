#!/bin/bash

read s
result=$s

while [[ $s != "q" ]]
do
	read s
	result="$result$s"
done

echo $result
