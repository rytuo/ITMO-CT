#!/bin/bash
# run from java-advanced/java-solutions

# path to java-advanced-2021
advanced=../java-advanced-2021

# create javadoc
javadoc -author -private -d javadoc info/kgeorgiy/ja/popov/implementor/Implementor.java \
 $advanced/modules/info.kgeorgiy.java.advanced.implementor/info/kgeorgiy/java/advanced/implementor/{Impler.java,JarImpler.java,ImplerException.java}