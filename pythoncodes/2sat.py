## 2-sat algorithm
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
def readSat (filename): 
    "Read in 2sat problem: returns number variables and array of expressions"
    lines = [line.strip() for line in open('c:/home/algorithms2/knapsack1.txt')]
    maxweight = int(lines[0].split()[0])
    items = int(lines[0].split()[1])
    lines = [[int(num) for num in line.split()] for line in lines[1:]]
    items = [(line[0], line[1]) for line in lines]
    


## Run unit test
## "~/algorithms2/2sat"
def runTest(filestub, numFiles):
    "Run test files and print output to console"
    for i in list(range(1, numFiles)): 
        dict_file = filestub + str(i)
        print("dict"+str(i)+":\n")
        d = readDictionary(dict_file)
        ent, leave, obj = calculateObjective(d)
        print("entering var: " + str(ent) +"\n" +
              "leaving var: " + str(leave) + "\n" + 
              "new obj value: " + str(obj) + "\n")
