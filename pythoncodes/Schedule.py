## Algorithm to minimize the weighted sum of the completion time of 
##  a number of jobs.  The jobs have positive and integral weights
##  and lengths, but they are NOT distinct, so ties must be dealt with.
##
## The algorithm should schedules jobs in decreasing order of the 
##  difference (weight - length). Note: this algorithm is not 
##  always optimal. 
##
## Ties: if two jobs have equal difference (weight - length), 
##  schedule the job with higher weight first. 
##
## Return: the sum of weighted completion times of the resulting 
## schedule --- a positive integer.
## 
## Pseudocode:
## - Initialize V = {total jobs} (set of jobs not completed)
## - Initialize X = {} (set of jobs already completed)
## - Initialize T (summed weighted completion time so far)
## - while X != V : (one job comleted with each iteration)
##   - job = minimum job in V that is not in X, minimum job determined 
##          by weight - length
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

V = jobs2
X = set()
T = 0
L = 0
while X != set(V.keys()):
    min_job = max([V[j][0]-V[j][1] for j in V if j not in X])
    job = [j for j in V if j not in X if V[j][0]-V[j][1] == min_job]
    ## Need to deal with ties here
    if len(job) > 1:
        next_job = job[0]
        max_weight = V[job[0]][0] 
        for j in job:
            if V[j][0] > max_weight:
                max_weight = V[j][0]
                next_job = j
        job = next_job
    else:
        job = job[0]
    L += V[job][1]
    T += V[job][0]*L
    X = X | set([job])

## T = 69119377652 for jobs
## T = 40135 for jobs2
## T = 79805 for jobs3
