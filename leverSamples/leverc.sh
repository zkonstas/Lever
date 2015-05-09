#!/bin/bash

if [ -z "$1" ]
	then
    echo "usage: lever <program_name>"
    exit 1
fi

echo Compiling Lever into intermediate code... 
java -cp "../translator:/usr/local/lib/antlr-4.5-complete.jar" Leverc ../leverSamples/$1

echo Compiling and JARing API...
javac -cp ".:../lib/*" -d ../translator/LeverAPIPackageSourceCode/  ../translator/LeverAPIPackage/*.java
jar cvf ../lib/LeverAPI-package.jar -C ../translator/LeverAPIPackage  LeverAPIPackage

echo Compiling program...
javac -cp ".:../lib/*" ${1//.lever/.java}
