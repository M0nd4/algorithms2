# Traveling salesman problem
# The first line indicates the number of cities. Each city is a point in the plane, 
# and each subsequent line indicates the x and y-coordinates of a single city.
# The distance between two cities is defined as the Euclidean distance, that is, two 
# cities at locations (x,y) and (z,w) have distance (xÅ|z)2+(yÅ|w)2 between them.
# In the box below, type in the minimum cost of a traveling salesman tour for this 
# instance, rounded down to the nearest integer. 

import math
import itertools

lines = [line.strip() for line in open('c:/home/algorithms2/tsp.txt')]
lines = [[float(num) for num in line.split(' ')] for line in lines]
numPoints = int(lines[0][0])
points = lines[1:]

def eucDist (x1, y1, x2, y2):
    "compute the euclidean distance between coordinates"
    return math.sqrt((x1 - x2)**2 + (y1 - y2)**2)

def tsp (coords):
    "Computes the shortest path between set of points, satisfying the condition that all the points must be visited once: the travelling salesman problem ( *O(n^2 * 2 ^n)* )"
    ## Initialize array with +inf except at S{1}
    A = 
    for m in range(2, numPoints):
        subsets = itertools.combinations(list(range(1,numPoints + 1)), m)
        subsWithOne = [sub for sub in subsets if any([p==1 for p in sub])]
        for sub in subsWithOne:
    pass
