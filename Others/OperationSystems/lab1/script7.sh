#!/bin/bash

grep --binary-files=text -E -h -s -o "\w+@\w+\.\w+" /etc/* | sort | uniq | awk -vORS=", " '{print $0}' > emails.lst
