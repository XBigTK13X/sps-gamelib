#! /bin/sh
#
mvn -q clean
mvn -q package
cd target
git log --max-count=1 > version.txt
zip -r sps-gamelib.zip sps-gamelib.jar ../assets version.txt ../README.md 
cd ../..
