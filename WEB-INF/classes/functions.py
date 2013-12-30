# -*- encoding:utf-8 -*-

import MeCab
import re
import nltk

##
# 形態素解析
# 引数=文字列
# 戻値=単語リスト
##
def extractNouns(doc):
    tagger = MeCab.Tagger()
    node=tagger.parseToNode(doc)
    re_word=re.compile("助詞|接尾|BOS|EOS|記号|名詞,数|助動詞|動詞|形容詞|サ変接続|接続詞|副詞|接頭詞|非自立| ")
    #re_word=re.compile("助詞|接尾辞|接頭辞|BOS/EOS|特殊|判定|数詞|助動詞|動詞|指示詞|形容詞|接続詞|副詞| ")
    #re_word=re.compile("BOS|EOS| ")
    
    nouns=[]
    while node:
        if not re_word.findall(node.feature) and not node.surface.isspace():
            nouns.append(node.surface)
            #print('%s:%s' % (node.surface,node.feature))
        node = node.next
    return nouns

def calc_tfidf(docWords):
    tokens=[]
    doclist=[]
    for url,doc in docWords.items():
        doclist.append(doc)
        for words in doc:
            tokens+=doc
    tf_idf={}
    idf={}
    
    A=nltk.TextCollection(doclist)
    token_types=set(tokens)
    
    for token_type in token_types:
        tf_idf[token_type]=A.tf_idf(token_type, tokens)
        idf[token_type]=A.idf(token_type)
        
    vec=getVec(tf_idf)
    return vec,idf

def calc_tf(doclist,flag=False):
    
    if flag==True:
        docs=doclist
        doclist=[]
        
        for doc in docs:
            doclist+=doc
        
    
    tf={}
    A=nltk.TextCollection(doclist)
    token_types=set(doclist)
    
    for token_type in token_types:
        tf[token_type]=A.tf(token_type, doclist)
    return tf
    
###    
# ラベル
###    
def getLabels(tf,idf,top=5):
    labels=[]
    wordlist={}
    
    for word in tf.keys():
        wordlist[word]=tf[word]*idf[word]
    
    for k,v in sorted(wordlist.items(),key=lambda x:x[1],reverse=True):
        if len(labels) < top:
            labels.append(k)
        else:
            break
    return labels

def makeDocLabel(docTF,idf):
    score={}
    docLabel={}
    
    for url,tf in docTF.items():
        #print '%s\n%s' % (url,tf)
        labels=getLabels(tf,idf,1)
        docLabel[url]=labels[0]
    return docLabel

def makeLabel(kclust,urlList,docWords,idf):
    labels=[]
    
    for clustNo in kclust:
        docs=[]
        #print clustNo
        for docNo in clustNo:
            url=urlList[docNo]
            docs.append(docWords.get(url))
        tf=calc_tf(docs,True)
        labels.append(getLabels(tf,idf))
    return labels
        

def getVec(tf_idf):
    avg=0
    vec={}
    
    for word,point in tf_idf.items():
        avg+=point
    avg=avg/len(tf_idf)
    
    for word,point in tf_idf.items():
        if point > avg:
            vec[word]=point
    return vec