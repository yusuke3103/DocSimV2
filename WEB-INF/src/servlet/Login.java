package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public Login(){
		super();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		req.setCharacterEncoding("utf-8");
		String name=req.getParameter("userName");
		String pass=req.getParameter("password");
		
		HttpSession ses = req.getSession();
		
		if(name.equals(pass)){
			ses.setAttribute("login", "true");
			ses.setAttribute("name", name);
		}else{
			ses.setAttribute("login", "false");
		}
		
		res.sendRedirect("index2.jsp");
	}
}
