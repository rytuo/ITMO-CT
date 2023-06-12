#!/bin/bash

ls /var/log | awk -vORS='\n\n\n' '{print $0 }' > files.lst
