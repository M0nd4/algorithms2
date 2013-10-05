# Traveling salesman problem

import math
import itertools

lines = [line.strip() for line in open('c:/home/algorithms2/tsp.txt')]
lines = [[float(num) for num in line.split(' ')] for line in lines]
numPoints = int(lines[0][0])
points = lines[1:]

def eucDist (x1, y1, x2, y2):
    "compute the euclidean distance between coordinates"
    return math.sqrt((x1 - x2)**2 + (y1 - y2)**2)

def initArray (numPoints):
    "Initialize array A[S][1] with +inf except at S = {1} which is 0"
    subsets = itertools.combinations(list(range(1,numPoints + 1)), 2)
    subsWithOne = [sub for sub in subsets if any([p==1 for p in sub])]
    labels = [(1,1)] + list(subsWithOne)
    A = [[float('+inf')]*numPoints for _ in range(numPoints)]
    A[0][0] = 0
    return A, labels

def addSets (m, numPoints, labels, A):
    "Add sets with new iteration to A and labels"
    subsets = itertools.combinations(list(range(1,numPoints + 1)), m)
    subsWithOne = [sub for sub in subsets if any([p==1 for p in sub])]
    labels = labels + subsWithOne
    for i in range(numPoints):
        A[i] = A[i] + [float('+inf')]*len(subsWithOne)
    return labels, A

def tsp (coords):
    "Computes the shortest path between set of points, satisfying the condition that all the points must be visited once: the travelling salesman problem ( O(n^2 * 2 ^n) )"
    numPoints = len(coords)
    A, labels = initArray(numPoints)
    for m in range(2, numPoints):
        labels, A = addSets(m, numPoints, labels, A)
        for sub in subsWithOne:
            

    return A


A[0][1] = eucDist(points[0][0], points[0][1], points[1][0], points[1][1])
p
