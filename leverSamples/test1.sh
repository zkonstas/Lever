#!/bin/bash

function CHECK_FILE()
{
	FILENAME=$1

	INPUT=$(grep "Expected output:" $FILENAME)
	EXPECTED=$(echo -e $INPUT | cut -d '"' -f 2)

	COMPILED_FILENAME=${FILENAME//.lever/}
	ACTUAL=$(./lever $COMPILED_FILENAME)

	if [[ "$ACTUAL" == "" ]]; then
		echo NO OUTPUT
	else
		if [ "$ACTUAL" = "$EXPECTED" ]
		then
			echo SUCCESS - $FILENAME
		else
			echo FAILURE - $FILENAME - Exp:$EXPECTED Act:$ACTUAL
		fi
	fi
}

COMPILE_ERROR_COUNTER=0

ARRAY=()

#find compile errors
if [ "$#" -ne 1 ]
then
	FILES_TO_LOOK_AT=*Test.lever
else
	FILES_TO_LOOK_AT="$@"
fi

for f in $FILES_TO_LOOK_AT
do
feedback=$(./leverc $f)
	if [[ $feedback == *"Sorry"*  ]] || [[ $feedback == *"error"* ]]
	then
		ARRAY[$COMPILE_ERROR_COUNTER]=$f
		((COMPILE_ERROR_COUNTER++))
	fi
done

echo $COMPILE_ERROR_COUNTER files did not compile...
#print bad files and move them
for f in ${ARRAY[@]}
do
	echo COMPILE ERROR - ${f}
	mv ${f} notCompiling/
done

#compare outputs
for f in $FILES_TO_LOOK_AT
do
	CHECK_FILE $f
done
