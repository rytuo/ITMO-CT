#!/bin/bash

awk '{if ($3 == "(WW)") {$3 = "(Warning)"; print $0}}' /var/log/Xorg.0.log > full.log
awk '{if ($3 == "(II)") {$3 = "(Information)"; print $0}}' /var/log/Xorg.0.log >> full.log
