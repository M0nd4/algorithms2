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

## with union find, doesnt quite work... comes out with a few too
##  many sets for larger tests
from unionFind import UnionFind
l = lines4[1:]
clusters = set(l) # starting clusters - removes copies from the input
uf = UnionFind()
for seq in l:
    uf.union(seq) # everyone in a singleton set
for seq in uf:
    diffs = make1bitdiff(seq)
    for i in diffs:
        if i in uf:
            uf.union(i,seq)

for seq in uf.parents.values():
    diffs = make1bitdiff(seq)
    for i in diffs:
        if i in uf:
            uf.union(i,seq)

## Brute Force O(n^2)++ time, it works but is too slow to use for
##  large output
clusters = {seq:seq for seq in uf.parents.values()}
for seq in clusters:
    onebits = make1bitdiff(seq)
    for i in onebits:
        if i in clusters:
            val = clusters[i]
            for key,value in clusters.items():
                if value == val:
                    clusters[key] = seq
## Lower bound of 5499 for number of clusters*

l = lines[1:]
