NAME=$1
if [[ $# -eq 0 ]] ; then
    echo 'New project directory name required'
    exit 0
fi

GIT_VERSION=$(git log -n 1 --pretty=format:"%H")

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
cp sgl/.gitignore $NAME/.gitignore
cd $NAME
git add -A .
git commit -qam "Copy sps-gamelib [$GIT_VERSION] for new project."
echo "Git repo setup for new project."
