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

## big problem
lines = [line.strip() for line in open('c:/home/algorithms2/knapsack_big.txt')]
maxweight = int(lines[0].split()[0])
items = int(lines[0].split()[1])
lines = [[int(num) for num in line.split()] for line in lines[1:]]

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
    # Iterate through the values table, and check
    # to see which of the two candidates were chosen. We can do this by simply
    # checking if the value is the same as the value of the previous row. If so, then
    # we say that the item was not included in the knapsack (this is how we arbitrarily
    # break ties) and simply move the pointer to the previous row. Otherwise, we add
    # the item to the reconstruction list and subtract the item's weight from the
    # remaining capacity of the knapsack. Once we reach row 0, we're done
    reconstruction = []
    i = len(items)
    j = maxweight
    while i > 0:
        if bestvalues[i][j] != bestvalues[i - 1][j]:
            reconstruction.append(items[i - 1])
            j -= items[i - 1][1]
        i -= 1
    # Reverse the reconstruction list, so that it is presented
    # in the order that it was given
    reconstruction.reverse()
    # Return the best value, and the reconstruction list
    return bestvalues[len(items)][maxweight], reconstruction

## Recursive version to handle larger memory case
def recursiveKnapsack(items, maxweight):
    def loop(numItems, lim):
        if numItems == 0:
            return 0
        elif items[numItems-1][1] > lim:
            return loop(numItems-1,lim)
        else:
            return max(loop(numItems-1,lim), loop(numItems - 1, lim - items[numItems-1][1]) + items[numItems-1][0])
    return loop(len(items), maxweight)

sys.setrecursionlimit(6000)
ans = recursiveKnapsack(lines, maxweight)

