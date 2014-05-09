#! /bin/sh

echo "== Preparing a working directory for the new package: pkg"
rm -rf pkg
rm -rf core/assets/graphics/sprites/.directory
mkdir pkg

echo "== Compiling the game"
cd project
gradle dist
cd ..

echo "== Copying resources to pkg/"
cp -r project/core/assets pkg/assets
cp -r dist/* pkg
cp project/desktop/build/libs/desktop-1.0.jar pkg/game.jar

git log --max-count=1 > pkg/assets/data/git.txt

