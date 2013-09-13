## Algorithm to compute max-spacing k-clustering
## Input: A distance function, each line of input file:
## Note: the distances are positive, but not necessarily unique
## Goal: Cluster the data such that 4 groups remain with the 
##  maximum possible distance separating them (i.e k=4)
##
## Pseudocode:
# 1. Initialize N = number of clusters (initially, each point is in a 
#    separate cluster)
# 2. Repeat until only k clusters remain:
# 3. - let p,q = the closest pair of separated points 
#      (determines the current spacing)
# 4. - merge the clusters containing p,q into a single cluster
# 5. Return the max distance between the k remaining clusters

## Data in format:
# [number_of_nodes]
# [edge 1 node 1] [edge 1 node 2] [edge 1 cost]
# [edge 2 node 1] [edge 2 node 2] [edge 2 cost]
lines = [[int(num) for num in line.strip().split(' ')] for line in open('/home/noah/algorithms2/clustering1.txt')]
lines1 = [[int(num) for num in line.strip().split(' ')] for line in open('/home/noah/algorithms2/clust1.txt')]
lines2 = [[int(num) for num in line.strip().split(' ')] for line in open('/home/noah/algorithms2/primcase.txt')]
lines = lines


## Algorithm
clusters = set([line[0] for line in lines[1:]]) | set([line[1] for line in lines[1:]])
clusters = {node:node for node in clusters}
S = sorted(lines[1:], key = lambda line: line[2])
for i in range(len(S)):
    a = clusters[S[i][0]]
    b = clusters[S[i][1]]
    if a != b:
        # if we reached k, break off
        if len(set(clusters.values())) == 4:
            break
        # merge b into a (change b values to a)
        for key,value in clusters.items():
            if value == b:
                clusters[key] = a
ans = S[i][2]

## For clust1: Result should be 7. Clusters: {1: [1, 2], 4: [4], 5: [5], 7: [7, 8, 6, 9, 3]}
## For primcase: 2277
## For clustering1: 106
