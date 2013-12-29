<%@page import="servlet.Start"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<script type="text/javascript" src="./BubbleTree/jslib/jquery-1.5.2.min.js"></script>
<script type="text/javascript" src="./BubbleTree/jslib/jquery.history.js"></script>
<script type="text/javascript" src="./BubbleTree/jslib/raphael.js"></script>
<script type="text/javascript" src="./BubbleTree/jslib/vis4.js"></script>
<script type="text/javascript" src="./BubbleTree/jslib/Tween.js"></script>
<script type="text/javascript" src="./BubbleTree/build/bubbletree.js"></script>
<link rel="stylesheet" type="text/css" href="./BubbleTree/build/bubbletree.css" />
<script type="text/javascript" src="./BubbleTree/styles/cofog.js"></script>
<link rel="stylesheet" type="text/css" href="./right.css" />
</head>

<%
        HashMap<String,HashMap<String,String>>WebPage=(HashMap)session.getAttribute("WebPage");
        HashMap<String,ArrayList<String>>ClustResult=(HashMap)session.getAttribute("ClustResult");
        HashMap<String,String>DocLabel = (HashMap)session.getAttribute("DocLabel");
        String keyword = (String)session.getAttribute("keyword");
%>
<script type="text/javascript">
$(function(){
        var data = {
                        label: '<%=keyword%>',
                        amount: '<%=WebPage.size()%>',
                        children:[
<%
int i=0;
        for(String Label:ClustResult.keySet()){
        	System.out.println(Label);
        	System.out.println(ClustResult.get(Label).size());
        	System.out.println(Start.color.get(i));
%>
                                { label:'<%=Label%><br><%=ClustResult.get(Label).size()%>件',amount:<%=ClustResult.get(Label).size()%>,color:'<%=Start.color.get(i)%>',
                                        children:[
<%                
                for(int x=0;x<ClustResult.get(Label).size();x++){
                		String url=ClustResult.get(Label).get(x);
                        if(x+1!=ClustResult.get(Label).size()){
%>	
                                                {label:'<%=DocLabel.get(url)%>',amount:1,color:'<%=Start.color.get(i)%>'},
<%
                        }else{
%>
                                                {label:'<%=DocLabel.get(url)%>',amount:1,color:'<%=Start.color.get(i)%>'}]        
<%
                        }
                	
                }
                if(i+1!=ClustResult.size()){
%>
                },
<%
                }else{
%>
                }
<%
                }
                i++;
        }
%>
        ]};
        new BubbleTree({
                data:data,
                bubbleType:'icon',
                container: '.bubbletree'
                });
});
</script>
<body>
<form name=filter action="Filter" target="left" method="post">
<input id=co name=col type="hidden" value=#999999>
<input id=label name=lab type="hidden" value="null">
</form>
        <div class="bubbletree"></div>
        <div id="foot">
                Built with <a href="http://okfnlabs.org/">Bubble Tree</a> from the 
                <a href="http://okfn.org/">Open Knowledge Foundation</a>. <br>Design
                and implementation by <a href="http://driven-by-data.net/">Gregor
                Aisch</a> and
                <a href="http://www.informationisbeautiful.net/">David McCandless</a>
        </div>
</body>
</html>