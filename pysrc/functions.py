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
    nouns=[]
    while node:
        if not re_word.findall(node.feature) and not node.surface.isspace():
            nouns.append(node.surface)
        node = node.next
    return nouns

##
# TF-IDF計算
##
def calc_tfidf(doclist):
    tokens=[]
    for doc in doclist:
        tokens+=doc
    
    tf_idf={}
    idf={}
    
    A=nltk.TextCollection(doclist)
    token_types=set(tokens)
    for token_type in token_types:
        tf_idf[token_type]=A.tf_idf(token_type, tokens)
        idf[token_type]=A.idf(token_type)
    
    vec=getVec(tf_idf)
    return tokens,vec,idf
##
# ラベル作成用・TF値の計算
##
def calc_tf(doclist):
    tokens=[]
    for doc in doclist:
        tokens+=doc
    tf={}
    A=nltk.TextCollection(doclist)
    token_types=set(tokens)
    for token_type in token_types:
        tf[token_type]=A.tf(token_type, tokens)
    return tf

##
# ラベル作成
# TFIDF値上位5件
##
def getLabels(tf,idf):
    labels=[]
    wordlist={}
    
    #TFIDF値の計算
    for word in tf.keys():
        wordlist[word]=tf[word]*idf[word]
    
    for k,v in sorted(wordlist.items(),key=lambda x:x[1],reverse=True):
        if len(labels) < 5:
            labels.append(k)
    return labels
##
# ラベル作成
##
def makelabel(kclust,doclist,idf):
    labels=[]
    for clustNo in kclust:
        doc=[]
        for docNo in clustNo:
            doc.append(doclist[docNo])
        tf=calc_tf(doc)
        labels.append(getLabels(tf,idf))
    return labels
##
# TFIDF平均算出
# 平均以上をベクトルに登録
##
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