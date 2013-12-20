# -*- encoding:utf-8 -*-

import random
from math import sqrt

import functions

def readfile(filepath):
    lines=[line for line in file(filepath)]
    colnames=lines[0].strip().split('\t')[1:]
    rownames=[]
    data=[]
    for line in lines[1:]:
        p=line.strip().split('\t')
        rownames.append(p[0])
        data.append([float(x) for x in p[1:]])
    return rownames,data

def pearson(p1,p2):
    
    sum1=sum(p1)
    sum2=sum(p2)
    
    sum1Sq = sum([pow(p,2) for p in p1])
    sum2Sq = sum([pow(p,2) for p in p2])
    
    pSum = sum([p1[i]*p2[i] for i in range(len(p1))])
    
    num = pSum-(sum1*sum2/len(p1))
    den=sqrt((sum1Sq-pow(sum1,2)/len(p1))*(sum2Sq-pow(sum2,2)/len(p2)))
    
    if den==0:
        return 0
    return 1.0 - num/den

def kcluster(rows, distance=pearson, k=4):
    # Make k clusters with random
    ranges=[(min([row[i] for row in rows]), max([row[i] for row in rows])) for i in range(len(rows[0]))]
    clusters=[[random.random()*(ranges[i][1]-ranges[i][0])+ranges[i][0] for i in range(len(rows[0]))] for j in range(k)]
 
    lastmatches = None
    for t in range(100):
        print 'Iteration %d' % t
        bestmatches=[[] for i in range(k)]
 
        for j in range(len(rows)):
            row = rows[j]
            bestmatch = 0
            for i in range(k):
                d = distance(clusters[i], row)
                if d < distance(clusters[bestmatch], row):
                    bestmatch = i
            bestmatches[bestmatch].append(j)
 
        if bestmatches == lastmatches: break
        lastmatches = bestmatches
 
        for i in range(k):
            avgs = [0.0] * len(rows[0])
            if len(bestmatches[i]) > 0:
                for rowid in bestmatches[i]:
                    for m in range(len(rows[rowid])):
                        avgs[m] += rows[rowid][m]
                for j in range(len(avgs)):
                    avgs[j] /= len(bestmatches[i])
                clusters[i] = avgs
        #print bestmatches
    return bestmatches
##
# 結果の書き出し
##
def output(labels,clust,url,filepath):
    x=0
    out=file(filepath,'w')
    for r in clust:
        for label in labels[x]:
            out.write('%s|' % label)
        out.write('\t')
        for i in r:
            out.write('%s\t' % url[i])
        out.write('\n')
        x+=1
        
def main(vec_path,kclust_path,doclist,idf):
    url,data=readfile(vec_path)
    kclust=kcluster(data,k=5)
    labels=functions.makelabel(kclust,doclist,idf)
    output(labels,kclust,url,kclust_path)