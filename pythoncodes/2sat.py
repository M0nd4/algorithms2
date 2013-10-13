## Papadimitrou's 2-sat algorithm, not optimized should run P time
## Input: txt file, line 1 = number of expressions,
##  subsequent lines contain two numbers (positive numbers: TRUE, negative numbers: FALSE)
## 6 2sat files: 2sat1.txt, .. , 2sat6.txt
## Determine which of them have solutions

## Algorithm:
## repeat log(2, n) times:
## - initialize random starting condition
## - repeat 2n^2 times:
##  - if conditions are all satisfied, 
##   -return "Satisfiable"
##   - else pick arbitrary unsatisfied clause, flip sign of one of its components
##    (random) 
## return "Unsatisfiable"
import math
from random import randint, choice, shuffle

def readSat (filename): 
    "Read in 2sat problem: returns number variables and array of tuples, negative numbers are negated literals.  In each instance the number of clauses and the number of variables is the same."
    lines = [line.strip() for line in open(filename)]
    numVars = int(lines[0])
    lines = [tuple([int(num) for num in line.split()]) for line in lines[1:]]
    return numVars, lines

def twoSat(clauses, vs):
    "Runs 2sat to determine if sequence of clauses is satisfiable."
    i = math.floor(math.log(len(vs), 2)) + 1
    while i >= 0:
        guess = randGuess(vs)
        j = 2*len(vs)^2
        while j >= 0:
            if isSatisfiable(guess, clauses):
                return 1
            else: # Pick arbitrary unsatisfied clauses and flip value of one variable
                flipClause(guess, clauses)
            j -= 1
        i -= 1
    return 0

def flipClause(guess, clauses):
    "Pick arbitrary unsatisfied clause"
    for i in clauses:
        if guess[abs(i[0])] != i[0] and guess[abs(i[1])] != i[1]:
            c = choice([1,0])
            guess[abs(i[c])] = -1 * guess[abs(i[c])]

def isSatisfiable(guess, clauses):
    "Test to see if guess satisfies all clauses"
    for i in clauses:
        if guess[abs(i[0])] != i[0] and guess[abs(i[1])] != i[1]:
            return False
    return True
    
def randGuess(vs):
    "Intialize a random guess at satisfying conditions"
    n = [v for v in vs]
    sign = (-1,1)
    cl = {num: choice(sign)*num for num in n}
    return cl
    
# Run unit test
filestub = "C:/home/algorithms2/2sat"
def runTest(filestub, numFiles):
    "Run test files and print output to console"
    output = []
    for i in list(range(1, numFiles + 1)): 
        sat_file = filestub + str(i) + '.txt'
        print("sat"+str(i)+":\n")
        n, clauses = readSat(sat_file)
        c,v = sat2dict(n, clauses)
        c,v = reduce(c,v)
        ans = twoSat(c,v)
        print("Result: " + str(ans) +"\n")
        output += [ans]
    return output

####################################################################################
##  Pre-processing
def removeFixed(n, clauses):
    "Remove fixed clauses from input.  Fixed clauses are those that contain a variable that is the same sign throughout all the input clauses."
    for i in list(range(1,n)):
        fixed = [j[0] for j in clauses if abs(j[0]) == i] + [i[1] for i in clauses if abs(i[1]) == i]
        if all(t > 0 for t in fixed) or all(t < 0 for t in fixed) or fixed == []:
            clauses = [j for j in clauses if abs(j[0]) != i and abs(j[1]) != i]
    return len(clauses), clauses

def sat2dict(n, clauses):
    "Create dictionaries with clauses and variables"
    clauseDict = {cl:1 for cl in clauses}
    varDict = {i: [] for i in list(range(1,n+1))}
    for i in clauseDict:
        varDict[abs(i[0])] += [i]
        varDict[abs(i[1])] += [i]
    return clauseDict, varDict

def reduceDict(cDict, vDict):
    "Remove fixed clauses from variable and clause dictionaries"
    for var in vDict:
        if vDict[var] == []:
            continue
        fixed = [j[0] for j in vDict[var] if abs(j[0]) ==var] + [i[1] for i in vDict[var] if abs(i[1]) == var]
        if all(t > 0 for t in fixed) or all(t < 0 for t in fixed):
            for cl in vDict[var]:
                cDict[cl] = 0
                for v in cl:
                    if abs(v) != var:
                        if cl in vDict[abs(v)]:
                            vDict[abs(v)].remove(cl)
            vDict[var] = []
    cD = {cl: l for cl, l in cDict.items() if l != 0}
    vD = {v: l for v, l in vDict.items() if l != []}
    return cD, vD

def reduce(cDict, vDict):
    "Reduce until no change"
    cD, vD = reduceDict(cDict, vDict)
    if cD == cDict:
        return cD, vD
    else:
        return reduce(cD, vD)
