package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Logout")
public class Logout extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public Logout(){
		super();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		HttpSession session = req.getSession(true);
		res.setCharacterEncoding("utf-8");
		session.invalidate();
		
		PrintWriter out = res.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html>");
	    out.println("<head>");
	    out.println("<meta charset=utf-8>");
	    out.println("<title>セッションテスト</title>");
	    out.println("</head>");
	    out.println("<body>");
		out.print("ご利用ありがとうございました。<br>");
		out.print("<a href=./index.jsp>ログイン画面へ戻る</a>");
	    out.println("</body>");
	    out.println("</html>");
	}
}
