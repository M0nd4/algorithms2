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

def twoSat(n, clauses):
    "Runs 2sat to determine if sequence of clauses is satisfiable."
    i = math.floor(math.log(n, 2)) + 1
    while i >= 0:
        guess = randGuess(n)
        j = 2*n^2
        while j >= 0:
            if isSatisfiable(guess, clauses):
                return 1
            else: # Pick arbitrary unsatisfied clauses and flip value of one variable
                flipClause(guess, clauses)
            j -= 1
        print(i)
        i -= 1
    return 0

def flipClause(guess, clauses):
    "Pick arbitrary unsatisfied clause"
    for i in clauses:
        if guess[abs(i[0])-1] != i[0] and guess[abs(i[1])-1] != i[1]:
            c = choice([1,0])
            guess[abs(i[c])-1] = -1 * guess[abs(i[c])-1]

def isSatisfiable(guess, clauses):
    "Test to see if guess satisfies all clauses"
    for i in clauses:
        if guess[abs(i[0])-1] != i[0] and guess[abs(i[1])-1] != i[1]:
            return False
    return True
    
def randGuess(n):
    "Intialize a random guess at satisfying conditions"
    n = list(range(1,n+1))
    sign = (-1,1)
    cl = [choice(sign) * num for num in n]
    return cl
    
# filename = '2sat1.txt'
# n, clauses = readSat(filename)
# Run unit test
filestub = "C:/home/algorithms2/2sat"
def runTest(filestub, numFiles):
    "Run test files and print output to console"
    output = []
    for i in list(range(1, numFiles + 1)): 
        sat_file = filestub + str(i) + '.txt'
        print("sat"+str(i)+":\n")
        n, clauses = readSat(sat_file)
        ans = twoSat(n, clauses)
        print("Result: " + str(ans) +"\n")
        output + [ans]
    return output
