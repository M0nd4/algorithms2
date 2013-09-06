## Prim's algorithm to find minimum spanning tree (MST)
## The data is an undirected graph with integer edge costs that 
##  are indistinct and can be negative

## Pseudocode:
## - Initialize V = Total Vertices
## - Initialize X = [s] (vertices spanned so far)
## - T = 0 (T is our tree, +1 edge each iteration)
## - while X != V : (one edge claimed each iteration)
##  -let e = (u, v) be the cheapest edge of G (the graph) such that u ∈ X, v not ∈ X.
##  - e = minimum(
##  (b) add e to T
##  (c) add v to X

## Data in format:
## [number_of_nodes] [number_of_edges]
## [one_node_of_edge_1] [other_node_of_edge_1] [edge_1_cost]
## [one_node_of_edge_2] [other_node_of_edge_2] [edge_2_cost]
lines = [line.strip() for line in open('/home/noah/algorithms2/edges.txt')]
lines2 = [line.strip() for line in open('/home/noah/algorithms2/pythoncodes/testPrim.txt')]
lines3 = [line.strip() for line in open('/home/noah/algorithms2/pythoncodes/testPrim2.txt')]
numNodes, numEdges = int(lines[0].split(' ')[0]), int(lines[0].split(' ')[1])

## dict edges contains 3-arrays with edge weight followed by edge length
edges = {linenum:[int(num) for num in line.split(' ')] for linenum,line in enumerate(lines[1:])}
edges2 = {linenum:[int(num) for num in line.split(' ')] for linenum,line in enumerate(lines2[1:])}
edges3 = {linenum:[int(num) for num in line.split(' ')] for linenum,line in enumerate(lines3[1:])}

edges = edges

Node1 = set([edges[j][0] for j in edges])
Node2 = set([edges[j][1] for j in edges])
set(edges[0]) == set(edges[1])
totalNodes = Node1 | Node2
V = totalNodes
X = set([1])
T = 0
count = 0
path = []
while X != V:
    min_edge = min([edges[e][2] for e in edges if len(set(edges[e][:2]) | X) == len(X)+1])
    next_edge = [e for e in edges if edges[e][2] == min_edge if len(set(edges[e][:2]) | X) == len(X)+1]
    next_edge = next_edge[0]
    T += edges[next_edge][2]
    X = X | set(edges[next_edge][:2])
    path = path + [(edges[next_edge][0],edges[next_edge][1])]
    count += 1
    if count % 50 == 0:
        print(X)

## T = -3612829 for edges
## T =  7 for edges2
## T = -18683 for edges3
