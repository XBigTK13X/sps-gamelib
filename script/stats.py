import os,json,sys

def dirToJson(dir):
    res = []
    for root,dirs,files in os.walk(dir):
        for f in files:
            path = os.path.join(root,f)
            for line in open(path,'r').readlines():
                if '"version"' in line:
                    j = json.loads(line)
                    res.append(j)
    return res

target = sys.argv[1]
data = dirToJson(target)

def genCSV():
    print data[0]['stats']

    csv = open("output.csv","w")
    csv.write('End Result,Score,Victories,Chomps,Abrasive,Slice,Vaporize,Expansion,Explosive,Contraction\n')
    for d in data:
        line = d['endResult']+','
        line += d['score']['total']+','
        line += d['score']['victories']+','
        line += d['score']['chomps']+','
        line += d['stats']['Abrasive']['strength']+','
        line += d['stats']['Slice']['strength']+','
        line += d['stats']['Vaporize']['strength']+','
        line += d['stats']['Expansion']['strength']+','
        line += d['stats']['Explosive']['strength']+','
        line += d['stats']['Contraction']['strength']
        line += "\n"
        csv.write(line)
    csv.close()

def genStats(data):
    if len(data) > 0:
        forces = ['Abrasive','Slice','Vaporize','Expansion','Explosive','Contraction']
    
        wins = {}
        loses = {}

        for f in forces:
            wins[f] = 0
            loses[f] = 0

        tWins = 0
        tLoses = 0
        for d in data:
            m = 0
            mf = ''
            for f in forces:
                if d['stats'][f]['strength'] > m:
                    m = d['stats'][f]['strength']
                    mf = f
            if 'WIN' in d['endResult']:
                wins[mf] = wins[mf] + 1
                tWins += 1
            if 'LOSE' in d['endResult']:
                loses[mf] = loses[mf] + 1
                tLoses += 1
            
        print "Total W/L: "+str(tWins)+"/"+str(tLoses)
        print "Total W %: "+"{0:.2f}".format(100*float(tWins)/len(data))+"%"
        print "WIN: "+str(wins)
        print "LOSE: "+str(loses)
        ratio = ''
        for f in forces:
            ratio += f + ': ' + "{0:.2f}".format(100*float(wins[f])/len(data)) + '%/' +"{0:.2f}".format(100*float(loses[f])/len(data))+'%, ' 
        print ratio
    else:
        print "No data was found"
      

genStats(data)
