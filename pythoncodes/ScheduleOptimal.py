## Algorithm to minimize the weighted sum of the completion time of 
##  a number of jobs.  The jobs have positive and integral weights
##  and lengths, but they are NOT distinct, so ties must be dealt with.
##
## The algorithm should schedules jobs in decreasing order of the 
##  difference (weight/length). 
##
## Ties: can be dealt with arbitrarily 
##
## Return: the sum of weighted completion times of the resulting 
## schedule --- a positive integer.
## 
## Pseudocode:
## - Initialize V = {total jobs} (dict of jobs not completed)
## - Initialize X = {} (set of jobs already completed)
## - Initialize T = 0 (summed weighted completion time so far)
## - Initialize L = 0 (length so far)
## - while X != V : (one job comleted with each iteration)
##   - job = minimum job in V that is not in X, minimum job determined 
##          by ratio or weight/length
##   - T += job[weight]*job[length]
##   - add job to X
## - return T


## Read in the data, the first line has number of jobs,
## every other line has [job weight] [job length]
lines = [line.strip() for line in open('/home/noah/algorithms2/jobs.txt')]
totalJobs = int(lines[0]) # 10000 jobs
lines2 = [line.strip() for line in open('/home/noah/algorithms2/pythoncodes/test.txt')]
lines3 = [line.strip() for line in open('/home/noah/algorithms2/pythoncodes/test2.txt')]
## dict jobs contains 2-arrays with job weight followed by job length
jobs = {linenum:[int(num) for num in line.split(' ')] for linenum,line in enumerate(lines[1:])}
jobs2 = {linenum:[int(num) for num in line.split(' ')] for linenum,line in enumerate(lines2[1:])}
jobs3 = {linenum:[int(num) for num in line.split(' ')] for linenum,line in enumerate(lines3[1:])}

V = jobs
X = set()
T = 0
L = 0
while X != set(V.keys()):
    min_job = max([V[j][0]/(V[j][1]) for j in V if j not in X])
    job = [j for j in V if j not in X if V[j][0]/(V[j][1]) == min_job]
    ## Ties are arbitrary: just take first job
    job = job[0]
    L += V[job][1]
    T += V[job][0]*L
    X = X | set([job])

## T = for 67311454237 jobs
## T = 76719 for jobs3
