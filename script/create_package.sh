#! /bin/sh
#
mvn -q clean
mvn -q package
cd target
git log --max-count=1 > version.txt
ln ../assets ./assets
ln ../README.md ./README.md
zip -r sps-gamelib.zip sps-gamelib.jar assets version.txt README.md 
cd ../..
