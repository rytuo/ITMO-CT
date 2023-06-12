#!/bin/bash

awk 'BEGIN {last=0; N=0; M=0;} {if ($5==last) {N++; M+=$8; print $0} else {print "Average_Sleeping_Children_of_ParentID= " last " is " M/N; last=$5; N=1; M=$8; print $0}} END {print "Average_Sleeping_Children_of_ParentID= " last " is " M/N;}' "out4.lst" > out5.lst
