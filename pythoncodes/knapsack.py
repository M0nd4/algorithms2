## Knapsack problem
## Input: n items - each has a value:
## - value v (nonnegative)
## - size w (nonnegative and integral)
## - capacity W (nonnegative integer)

## Output:  a subset S of the initial group that maximizes the sum of vs in S
##  subject to the constraint that the sum of the sizes (ws) is less than or equal to 
##  the capacity W
import sys
import collections
import functools


lines = [line.strip() for line in open('C:/users/noah/desktop/algorithms2/knapsack1.txt')]
maxweight = int(lines[0][0])

items = [[int(num) for num in line.split()] for line in lines[1:]]


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
# lines as a tuple
items = [(line[0], line[1]) for line in lines]

def knapsack(items, maxweight):
    """
    Solve the knapsack problem by finding the most valuable
    subsequence of `items` subject that weighs no more than
    `maxweight`.
    `items` is a sequence of pairs `(value, weight)`, where `value` is
    a number and `weight` is a non-negative integer.
    `maxweight` is a non-negative integer.
    Return a pair whose first element is the sum of values in the most
    valuable subsequence, and whose second element is the subsequence.
    >>> items = [(4, 12), (2, 1), (6, 4), (1, 1), (2, 2)]
    >>> knapsack(items, 15)
    (11, [(2, 1), (6, 4), (1, 1), (2, 2)])
    """
    # Return the value of the most valuable subsequence of the first i
    # elements in items whose weights sum to no more than j.
    def bestvalue(i, j):
        if i == 0: return 0
        value, weight = items[i - 1]
        if weight > j:
            return bestvalue(i - 1, j)
        else:
            return max(bestvalue(i - 1, j),
                       bestvalue(i - 1, j - weight) + value)
    return bestvalue(len(items), maxweight)

## Recursive version to handle larger memory case
def recursiveKnapsack(items, maxweight):
    @memoized
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

sys.setrecursionlimit(8000)
ans = knapsack(items, maxweight)

class memoized(object):
   '''Decorator. Caches a function's return value each time it is called.
   If called later with the same arguments, the cached value is returned
   (not reevaluated).
   '''
   def __init__(self, func):
      self.func = func
      self.cache = {}
   def __call__(self, *args):
      if not isinstance(args, collections.Hashable):
         # uncacheable. a list, for instance.
         # better to not cache than blow up.
         return self.func(*args)
      if args in self.cache:
         return self.cache[args]
      else:
         value = self.func(*args)
         self.cache[args] = value
         return value
   def __repr__(self):
      '''Return the function's docstring.'''
      return self.func.__doc__
   def __get__(self, obj, objtype):
      '''Support instance methods.'''
      return functools.partial(self.__call__, obj)
