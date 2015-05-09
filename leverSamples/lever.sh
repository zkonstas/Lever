#!/bin/bash

if [ -z "$1" ]
	then
    echo "usage: lever <program_name>"
    exit 1
fi

java LeverTranslator < $1
