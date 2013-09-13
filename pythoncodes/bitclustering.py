# The idea here is to cluster 24-bit sequences by their hamming
#  distance (number of bitwise differences between sequences).  So,
#  the min distance is 0 if two sequences are identical and the max
#  difference is 24 if one string is all 0s and another is all 1s for
#  example.  The file is two large to use brute force comparison,
#  i.e. sort them by their differences and then cluster.
#
# The question is: what is the largest value of k such that there is a k-clustering with spacing at least 3? That is, how many clusters are needed to ensure that no pair of nodes with all but 2 bits in common get split into different clusters? 

# Idea: hash them out, 
#  loop through the sequence,
#  for each one: look for 0,1,2 neighbor differences and merge them all
#  Should then be left with only 3-distance separated sequences

# Data input:
# [# of nodes] [# of bits for each node's label]
# [first bit of node 1] ... [last bit of node 1]
# [first bit of node 2] ... [last bit of node 2]
lines = [line.strip() for line in open('/home/noah/algorithms2/clustering_big.txt')]
lines1 = [line.strip() for line in open('/home/noah/algorithms2/pythoncodes/short1.txt')]
lines2 = [line.strip() for line in open('/home/noah/algorithms2/pythoncodes/short2.txt')]
lines3 = [line.strip() for line in open('/home/noah/algorithms2/pythoncodes/short3.txt')]
lines4 = [line.strip() for line in open('/home/noah/algorithms2/pythoncodes/example1')]

## clean up 4th test to be the same
for i in range(1,len(lines4)):
    newline = list(lines4[i])
    newline = " ".join(newline)
    lines4[i] = newline

# lines2 = [[int(num) for num in line.strip().split(' ')] for line in open('/home/noah/algorithms2/primcase.txt')]

# helper function to make list of all 1 or 2-bit different sequences
def make1bitdiff(seq):
    "given a sequence, make a set of sequences with 1 bit difference"
    orig = [int(num) for num in seq.split(' ')]
    output = []
    # make 1 and 2 bit diffs
    for i in range(len(orig)):
        for j in range(i,len(orig)):
            newseq = list(orig)
            if i != j:
                newseq[i] = (newseq[i]+1)%2
                newseq[j] = (newseq[j]+1)%2
            if i == j:
                newseq[i] = (newseq[i]+1)%2
            newseq = [str(num) for num in newseq]
            newseq = " ".join(newseq)
            output = output + [newseq]
    return output

# crappy union thingy -- super slow
# def union(a,b):
#     for key,value in clusters.items():
#         if value == b:
#             clusters[key] = a

## with union find
from unionFind import UnionFind
l = lines[1:]
clusters = set(l) # starting clusters - removes copies from the input
uf = UnionFind()
for seq in l:
    uf.union(seq) # everyone in a singleton set

## Make a set containing all possible 0,1,2 distances
seqs = set()
for seq in set(lines4[1:]):
    diffs = make1bitdiff(seq)
    seqs = seqs | set(diffs)

start = len(set(lines4[1:]))
for seq in set(lines4[1:]):
    if seq in seqs:
        start -= 1

done = []
count = len(clusters)
for seq in clusters:
    onebits = make1bitdiff(seq)
    for i in onebits:
        if i not in done and i in clusters:
            done = done + [i]
            count -= 1
            if seq not in done:
                done = done + [seq]
print(count)

## try opposite, counting only when we find a seq that has no hits
##  faster b/c no list to build
count = 0
for seq in clusters:
    onebits = make1bitdiff(seq)
    for i in onebits:
        if i in clusters:
            break;
count -= 1
print(count)


## answer is the number of clusters remaining, so the cardinality
##  of the clusters 
ans = len(set(uf.parents.values()))
print(ans)

## len(clusters) = 198788
## len(uf.weights) = 200000 - 193289
## ans = 1508 (wrong)

## Algorithm
lines = lines4

clusters = {seq:seq for seq in lines}
for seq in clusters:
    onebits = make1bitdiff(seq)
    for i in onebits:
        if i in clusters:
            clusters[i] = clusters[seq]

len(set([value for key,value in clusters.items()]))


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


