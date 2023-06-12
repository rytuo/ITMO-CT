#!/bin/bash

man bash | grep -E -o '\w{4,}' | sort | uniq -i -c | sort -g -r | head -n 3 | awk '{print $2}'
