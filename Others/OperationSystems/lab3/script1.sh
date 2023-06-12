#!/bin/bash

mkdir $HOME/test && {
echo "catalog test was created successfully" >> ~/report 
mkdir $HOME/test/$(date +%d_%m_%y_%T)
}

ping www.net_nikogo.ru || {
echo $(date +%d_%m_%y_%T) " ERROR : INVALID_SERVER" >> ~/report
}
