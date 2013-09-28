## Knapsack problem
## Input: n items - each has a value:
## - value v (nonnegative)
## - size w (nonnegative and integral)
## - capacity W (nonnegative integer)

## Output:  a subset S of the initial group that maximizes the sum of vs in S
##  subject to the constraint that the sum of the sizes (ws) is less than or equal to 
##  the capacity W
import sys

## small problem
lines = [line.strip() for line in open('c:/home/algorithms2/knapsack1.txt')]
maxweight = int(lines[0].split()[0])
items = int(lines[0].split()[1])
lines = [[int(num) for num in line.split()] for line in lines[1:]]
items = [(line[0], line[1]) for line in lines]

## big problem
lines = [line.strip() for line in open('c:/home/algorithms2/knapsack_big.txt')]
maxweight = int(lines[0].split()[0])
items = int(lines[0].split()[1])
lines = [[int(num) for num in line.split()] for line in lines[1:]]
# lines as a tuple
items = [(line[0], line[1]) for line in lines]

## Recursive version to handle larger memory case, will store each recursive call in 
##  python dictionary for lookup later
def recursiveKnapsack(items, maxweight):
    P = {}
    def loop(numItems, lim):
        if (numItems, lim) not in P:
            if numItems == 0:
                P[(numItems, lim)] = 0
            elif items[numItems-1][1] > lim:
                P[(numItems, lim)] = loop(numItems-1,lim)
            else:
                P[(numItems, lim)] = max(loop(numItems-1,lim), loop(numItems - 1, lim - items[numItems-1][1]) + items[numItems-1][0])
        return P[(numItems, lim)]
    return loop(len(items), maxweight)

sys.setrecursionlimit(6000)
ans = recursiveKnapsack(items, maxweight)

## store values in 2-D array, takes too much memory for larger cases
def knapsack(items, maxweight):
    bestvalues = [[0] * (maxweight + 1) for i in range(len(items) + 1)]
    # Enumerate through the items and fill in the best-value table
    for i, (value, weight) in enumerate(items):
        # row 0 is full of 0s
        i += 1
        for capacity in range(maxweight + 1):
            # Edge case: weight bigger than sub-capacity
            if weight > capacity:
                bestvalues[i][capacity] = bestvalues[i - 1][capacity]
            else: 
                # Choose between case where i is in and i is out of optimal soln
                case1 = bestvalues[i - 1][capacity]
                case2 = bestvalues[i - 1][capacity - weight] + value
                bestvalues[i][capacity] = max(case1, case2)
    # Reconstruction
    reconstruction = []
    i = len(items)
    j = maxweight
    while i > 0:
        if bestvalues[i][j] != bestvalues[i - 1][j]:
            reconstruction.append(items[i - 1])
            j -= items[i - 1][1]
        i -= 1
    reconstruction.reverse()
    return bestvalues[len(items)][maxweight], reconstruction

