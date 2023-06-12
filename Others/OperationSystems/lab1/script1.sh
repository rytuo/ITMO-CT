#!/bin/bash

if [[ $1 -gt $2 ]]
then
    if [[ $3 -gt $1 ]]
    then
        echo $3
    else
	echo $1
    fi
else
    if [[ $3 -gt $2 ]]
    then
	echo $3
    else
	echo $2
    fi
fi
