#!/bin/bash

echo -e "Choose an app:\n  1 - nano\n  2 - vi\n  3 - links\n  Any other input to quit\n"

read s
case $s in
	"1")
		nano
		;;
	"2")
		vi
		;;
	"3")
		links
		;;
	*)
		exit
esac
