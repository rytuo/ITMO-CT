#!/bin/bash

sudo awk '{if ($6 == "<info>") print $0}' /var/log/syslog > info.log
