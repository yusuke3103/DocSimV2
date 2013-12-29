# -*- encoding:utf-8 -*-

import sys
import functions,kclust
import csv

###
#サイトデータ取得
###
def getSiteData(row):
    url=row[0]
    document=row[1]
    return url,document

###
#本文から検索ワード削除
###
def eraseKeys(document,keys):
    for key in keys:
        document=document.replace(key,'')
    return document

###
# 形態素解析
# 単語出現回数取得
# word=[単語]
# wc={単語,出現回数}
###
def getWordCounts(document):
    wc={}
    words=functions.extractNouns(document)
    for word in words:
        wc.setdefault(word,0)
        wc[word]+=1
    return words,wc

def output_docLabel(filepath,docLabel):
    out=file(filepath,'w')
    for url,docLabel in docLabel.items():
        out.write('%s,%s\n' % (url,docLabel))
    out.close()

def output_tfidf(filepath,vec,wordcounts):
    alltfidf={}
    for url,wc in wordcounts.items():
        tf_idf=[]
        for w in vec:
            if w in wc:
                tf_idf.append(wc[w]*vec[w])
            else:
                tf_idf.append(0)
        alltfidf[url]=tf_idf
    return alltfidf    

if __name__ == '__main__':
    debug='off'
    argv=sys.argv
    
    if debug == 'on':
        dir = '/Users/yusuke/Dropbox/workspace/JavaEE/DocSimV2/'
        sessionName='s12008'
        keyword='日本'        
    else:
        dir=argv[1]
        sessionName=argv[2]
        keyword=argv[3]
    
    print 'dir=%s' % dir
    print 'sessionName=%s' % sessionName
    print 'keyword=%s' % keyword
    
    ###
    #ファイルパス
    ###
    csv_path = '%scache/%s_result' % (dir,sessionName)
    vec_path = '%scache/%s_vec' % (dir,sessionName)
    kclust_path = '%scache/%s_kclust' % (dir,sessionName)
    hclust_path = '%scache/%s_hclust' % (dir,sessionName)
    label_path = '%scache/%s_label' % (dir,sessionName)
    
    print 'csv_path=%s' % csv_path
    print 'vec_path=%s' % vec_path
    print 'kclust_path=%s' % kclust_path
    print 'hclust_path=%s' % hclust_path
    
    doclist=[]
    wordcounts={}
    docTF={}
    docWords={}
    
    ###
    #キーワードの単語化
    ###
    keys=functions.extractNouns(keyword)
    print keys
    
    ###
    #検索結果受取り
    ###
    csvfile=open(csv_path)
    line=csv.reader(csvfile,delimiter='\t')
    
    for row in line:
        url,document = getSiteData(row)
        document=eraseKeys(document,keys)   #本文から検索ワード削除
        doc,wc=getWordCounts(document)
        docWords[url]=doc                   #URL毎に単語（重複あり）を保持
        docTF[url]=functions.calc_tf(doc)   #URL毎にTF値を保持
        wordcounts[url]=wc                  #URL毎に単語の出現回数を保持
    
    vec,idf=functions.calc_tfidf(docWords)
    docLabel=functions.makeDocLabel(docTF,idf)
    
    output_docLabel(label_path,docLabel)
    alltfidf=output_tfidf(vec_path,vec,wordcounts)
    
    kclust.main(kclust_path,alltfidf,docWords,idf)