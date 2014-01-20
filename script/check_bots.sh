target=${1-"logs/"}
grep -r -I Exception $target | wc -l | xargs -I % echo "% exceptions"
grep -r -I WIN $target | wc -l | xargs -I % echo "% wins"
grep -r -I LOSE $target | wc -l | xargs -I % echo "% losses"
python script/stats.py $target
ls game | grep hs_ | wc -l | xargs -I % echo "Java crashed % times. If it happened at then, it's probably because the JVM ran out of memory."
