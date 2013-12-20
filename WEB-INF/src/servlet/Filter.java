package servlet;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/Filter")
public class Filter extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Filter() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String color = request.getParameter("col");
		request.setAttribute("postColor", color);
	 	RequestDispatcher rds = request.getRequestDispatcher("/left.jsp");
 		rds.forward(request, response);
	}

}
