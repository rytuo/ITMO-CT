#!/bin/bash

awk -F ':' '{print $1 " " $3}' /etc/passwd | sort -g -k 2
