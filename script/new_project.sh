NAME=$1
if [[ $# -eq 0 ]] ; then
    echo 'New project directory name required'
    exit 0
fi

cd ..

if [ -d "$NAME" ]; then
    echo "Project $NAME already exists."
    exit
fi

echo "Copying sps-gamelib to new project directory"
mkdir $NAME
cd $NAME
git init -q
cd ..
rsync -a --exclude=".*" sgl/ $NAME/
cd $NAME
rm -rf logs
rm -rf target
git add -A .
git commit -qam "Copy latest sps-gamelib for new prototype."
echo "Git repo setup for new project."
