#!/bin/bash

function CHECK_FILE()
{
	FILENAME=$1
	if [[ -e $FILENAME ]]; then
		INPUT=$(grep "Expected output:" $FILENAME)
		EXPECTED=$(echo -e $INPUT | cut -d '"' -f 2)

		COMPILED_FILENAME=${FILENAME//.lever/}
		ACTUAL=$(./lever $COMPILED_FILENAME 2>&-)
		
		if [[ -z $ACTUAL ]]; then
			((RUNTIME_ERROR_COUNTER++))
			echo "RUNTIME ERROR - $FILENAME"
			mv ${f} runtimeError/
			rm $COMPILED_FILENAME.class 2>&-
		else
			if [ "$ACTUAL" = "$EXPECTED" ]
			then
				((SUCCESS_COUNTER++))
				echo "SUCCESS - $FILENAME"
				mv ${f} success/
				rm $COMPILED_FILENAME.class &> /dev/null
			else
				((FAILURE_COUNTER++))
				echo "FAILURE - $FILENAME - Exp:$EXPECTED Act:$ACTUAL"
				mv ${f} failure/
				rm $COMPILED_FILENAME.class &> /dev/null
			fi
		fi
	fi
}

le=no
for a in "$@"
do
	if [[ $a == "-le" ]]; then
		le=yes
	fi
done

COMPILE_ERROR_COUNTER=0
RUNTIME_ERROR_COUNTER=0
SUCCESS_COUNTER=0
FAILURE_COUNTER=0

ARRAY=()

#find compile errors
if [ "$#" -lt 1 ]
then
	#bring test files back to dir
	mv success/*.lever . &>/dev/null
	mv failure/*.lever . &>/dev/null
	mv notCompiling/*.lever . &>/dev/null
	mv runtimeError/*.lever . &>/dev/null
	FILES_TO_LOOK_AT=*"Test.lever"
else
	FILES_TO_LOOK_AT="$@"
	for f in $FILES_TO_LOOK_AT
	do
		#bring test files back to dir
		mv success/$f . &>/dev/null
		mv failure/$f . &>/dev/null
		mv notCompiling/$f . &>/dev/null
		mv runtimeError/$f . &>/dev/null
	done
fi

echo "testing..."
for f in $FILES_TO_LOOK_AT
do
	if [[ $f != "-le" ]]; then
	#feedback=$(./leverc $f 2>&-)
	#feedback=$(./leverc $f 2>&1 >/dev/null)
	feedback=$(./leverc $f)
		if [[ $le == "yes" ]]; then
			echo "$feedback"
		fi
		if [[ $feedback == *"file not found"*  ]] || [[ $feedback == *"error"*  ]] || [[ $feedback == *"Sorry"*  ]]
			then
				ARRAY[$COMPILE_ERROR_COUNTER]=$f
				((COMPILE_ERROR_COUNTER++))
		fi
	fi
done

#print bad files and move them
for f in ${ARRAY[@]}
do
	echo "COMPILE ERROR - ${f}"
	mv $f notCompiling/
done

#compare outputs
for f in $FILES_TO_LOOK_AT
do
	CHECK_FILE $f
done


echo "$COMPILE_ERROR_COUNTER compile-errors, $RUNTIME_ERROR_COUNTER runtime-errors, $FAILURE_COUNTER failures and $SUCCESS_COUNTER successes"
