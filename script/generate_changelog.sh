 #! /bin/bash
 FROM=$1
 TO=$2
 git log release/$FROM...release/$TO --pretty="%B" --reverse