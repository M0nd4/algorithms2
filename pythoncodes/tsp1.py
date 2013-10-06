# Traveling salesman problem
import numpy as np
import math
import itertools

lines1 = [line.strip() for line in open('c:/home/algorithms2/pythoncodes/tsp-test1.txt')]
lines = [line.strip() for line in open('c:/home/algorithms2/tsp.txt')]
lines = [[float(num) for num in line.split(' ')] for line in lines]
numPoints = int(lines[0][0])
points = lines[1:]

def eucDist (x1, y1, x2, y2):
    "compute the euclidean distance between coordinates"
    return math.sqrt((x1 - x2)**2 + (y1 - y2)**2)

def initArray (numPoints):
    "Initialize array A[S][1] with +inf except at S = {1} which is 0"
    subsets = itertools.combinations(list(range(1,numPoints + 1)), 1)
    subsWithOne = [sub for sub in subsets if any([p==1 for p in sub])]
    labels = list(subsWithOne)
    A = [[float('+inf')] for _ in range(numPoints)]
    A[0][0] = 0
    return A, labels

def addSets (m, numPoints, labels, A):
    "Add sets with new iteration to A and labels"
    subsets = itertools.combinations(list(range(2,numPoints + 1)), m-1)
    subsWithOne = [((1,) + sub) for sub in subsets]
    labels = labels + subsWithOne
    for i in range(numPoints):
        A[i] = A[i] + [float('+inf')]*len(subsWithOne)
    return A, labels, subsWithOne

def minPath (sub, j, A, labels, coords):
    "calculate the min path ending at vertex j and passing through all vertices in sub"
    preVerts = [i for i in sub]
    minP = float('+inf')
    for k in preVerts:
        if k != j:
            preEdge = labels.index(tuple([i for i in preVerts if i != j]))
            C = eucDist(coords[j-1][0], coords[j-1][1], coords[k-1][0], coords[k-1][1])
            if A[k-1][preEdge] + C < minP:
                minP = A[k-1][preEdge] + C
    return minP

def finalPass (A, labels, coords):
    "Get minimum of previous path distances + final trip back to starting point."
    finalSet = tuple(range(1, numPoints+1))
    print(labels)
    print(A)
    minP = float('+inf')
    preEdge = labels.index(finalSet)
    for i in range(2, numPoints):
        C = eucDist(coords[0][0], coords[0][1], coords[i-1][0], coords[i-1][1])
        if A[i-1][preEdge] + C < minP:
            minP = A[i-1][preEdge] + C
    return minP

def tsp (coords):
    "Computes the shortest path between set of points, satisfying the condition that all the points must be visited once: the travelling salesman problem ( O(n^2 * 2 ^n) )"
    numPoints = len(coords)
    A, labels = initArray(numPoints)
    for m in range(2, numPoints+1):
        A, labels, subsWithOne = addSets(m, numPoints, labels, A)
        for sub in subsWithOne:
            for j in sub:
                if j != 1: ## ignore j == 1
                    subInd = labels.index(sub)
                    A[j-1][subInd] = minPath(sub, j, A, labels, coords)
    ## Get minimum of dist back to starting point
    return finalPass (A, labels, coords)
