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

filename = '2sat1.txt'
n, clauses = readSat(filename)

def readSat (filename): 
    "Read in 2sat problem: returns number variables and array of tuples, negative numbers are negated literals.  In each instance the number of clauses and the number of variables is the same."
    lines = [line.strip() for line in open('c:/home/algorithms2/' + filename)]
    numVars = int(lines[0])
    lines = [tuple([int(num) for num in line.split()]) for line in lines[1:]]
    return numVars, lines

def twoSat(n, clauses):
    "Runs 2sat to determine if sequence of clauses is satisfiable."
    i = math.floor(math.log(n, 2)) + 1
    while i >= 0:
        guess = randGuess(n)
        j = 2*n^2
        while j >= 0:
            if isSatisfiable(guess, clauses):
                return "Satisfiable"
            else: # Pick arbitrary unsatisfied clauses and flip value of one variable
                flipClause(guess, clauses)
            j -= 1
        print(i)
        i -= 1
    return "Unsatisfiable 8===D - -- ---"

def flipClause(guess, clauses):
    "Pick arbitrary unsatisfied clause"
    for i in clauses:
        if guess[abs(i[0])-1] != i[0]:
            guess[abs(i[0])-1] = -1 * guess[abs(i[0])-1]
        if guess[abs(i[1])-1] != i[1]:
            guess[abs(i[1])-1] = -1 * guess[abs(i[1])-1]

def isSatisfiable(guess, clauses):
    "Test to see if guess satisfies all clauses"
    for i in clauses:
        if guess[abs(i[0])-1] != i[0] & guess[abs(i[1])-1] != i[1]:
            return False
    return True
    
def randGuess(n):
    "Intialize a random guess at satisfying conditions"
    n = list(range(1,n+1))
    sign = (-1,1)
    cl = [choice(sign) * num for num in n]
    return cl
    

## Run unit test
## "~/algorithms2/2sat"
# def runTest(filestub, numFiles):
#     "Run test files and print output to console"
#     for i in list(range(1, numFiles)): 
#         dict_file = filestub + str(i)
#         print("dict"+str(i)+":\n")
#         d = readDictionary(dict_file)
#         ent, leave, obj = calculateObjective(d)
#         print("entering var: " + str(ent) +"\n" +
#               "leaving var: " + str(leave) + "\n" + 
#               "new obj value: " + str(obj) + "\n")
