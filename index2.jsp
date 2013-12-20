<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:include page="checklogin.jsp" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>DocSimV2</title>
</head>
<frameset border=1 rows=10%,*>
	<frame src=top.jsp name=top>
	<frameset border=1 cols=40%,60%>
		<frame src=first.html name=left>
		<frame src=first.html name=right>
	</frameset>
</frameset>
</html>