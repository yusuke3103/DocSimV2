
<%
	try {
		String login = (String) session.getAttribute("login");
		if (login.equals("false")) {
			pageContext.forward("index.jsp");
		}
	} catch (Exception e1) {
		try {
			pageContext.forward("top.jsp");
		} catch (Exception e2) {

		}
	}
%>