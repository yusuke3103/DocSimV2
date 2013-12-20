# -*- encoding:utf-8 -*-

import csv
import sys
import functions,kclust

##
# サイトデータ取得
# 戻値：URL＋文書
##
def getSiteData(row):
    title=row[0]
    document=row[1]
    return title,document

##
# 文書からキーワード削除
# 戻値：document
##
def eraseKey(document,keys):
    for key in keys:
        document = document.replace(key,'')
    return document

##
# 単語の出現回数取得
##
def getWordCounts(document):
    wc={}
    words=functions.extractNouns(document)
    for word in words:
        wc.setdefault(word,0)
        wc[word]+=1
    return words,wc
##
# TFIDF結果書き出し
##
def output_tfidf(filepath,vec,wordcounts):
    out=file(filepath,'w')
    out.write('SITE')
    for word in vec:
        out.write('\t'+word)
    out.write('\n')
    for url,wc in wordcounts.items():
        out.write(url)
        for w in vec:
            if(w in wc):
                out.write('\t%f'%(wc[w]*vec[w]))
            else:
                out.write('\t0')
        out.write('\n')
##
#引数受取り
##
argv=sys.argv
dir=argv[1]
sessionName=argv[2]
keyword=argv[3]

print "DIR=%s" % dir
print "sessionName=%s" % sessionName
print "keyword=%s" % keyword

##
#ファイルパス作成
##
csv_path = "%scache/%s_result" % (dir,sessionName)
vec_path = "%scache/%s_vec" % (dir,sessionName)
kclust_path = "%scache/%s_kclust" % (dir,sessionName)
hclust_path = "%scache/%s_hclust" % (dir,sessionName)

print "csv_path=%s" % csv_path
print "vec_path=%s" % vec_path
print "kclust_path=%s" % kclust_path
print "hclust_path=%s" % hclust_path

##
#キーワードの単語化
##
keys=functions.extractNouns(keyword)
print keys

##
# CSV読み込み
##
csvfile=open(csv_path)
line=csv.reader(csvfile,delimiter='\t')

doclist=[]
wordcounts={}

for row in line:
    url,document = getSiteData(row)
    document=eraseKey(document,keys)
    doc,wc=getWordCounts(document)
    doclist.append(doc)
    wordcounts[url]=wc

allwords,vec,idf=functions.calc_tfidf(doclist)
output_tfidf(vec_path,vec,wordcounts)
kclust.main(vec_path,kclust_path,doclist,idf)

