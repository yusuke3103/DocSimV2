package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		
		ArrayList<String> ViewUrl= new ArrayList();
		
		request.setCharacterEncoding("utf-8");
		String color = request.getParameter("col");
		String lab = request.getParameter("lab");

		HttpSession ses = request.getSession();
		HashMap<String, HashMap<String, String>>WebPage = (HashMap)ses.getAttribute("WebPage");
		HashMap<String,ArrayList<String>>ClustResult = (HashMap)ses.getAttribute("ClustResult");
		HashMap<String,ArrayList<String>>LabelGroup= (HashMap)ses.getAttribute("LabelGroup");
		
		
		response.setContentType("text/html;charset=utf-8");
		PrintWriter out = response.getWriter();
		String html = "";
		html+="<!DOCTYPE html>";
		html+="<HTML><HEAD><meta charset=utf-8></head>";
		html+="<body><font size='2px'>";
		html+="<table>";
		System.out.println(lab);
		if (color.matches("#999999")){
			for (String url:WebPage.keySet()){
				for (String title:WebPage.get(url).keySet()){
					html+="<tr><td>";
					html+="<a href="+url+">"+title+"</a><br>";
					html+=WebPage.get(url).get(title);
					html+="</td></tr>";
				}
			}
		}else{
			
			int num=Start.color.indexOf(color);
			int x=0;
			if(LabelGroup.containsKey(lab)){
				ViewUrl=LabelGroup.get(lab);
			}else{
				for (String label:ClustResult.keySet()){
					if(x==num){
						ViewUrl=new ArrayList<String>(ClustResult.get(label));
					}
					x++;
				}
			}
			for (int i=0;i<ViewUrl.size();i++){
				String url = ViewUrl.get(i);
				for (String title:WebPage.get(url).keySet()){		
					html+="<tr><td>";
					html+="<a href=\""+url+"\"target=_blank>"+title+"</a><br>";
					html+=WebPage.get(url).get(title);
					html+="</td></tr>";
				}
			}
		}
		html+="</table>";
		html+="</font></body>";
		html+="</html>";
		//System.out.println(html);
		out.print(html);
		//RequestDispatcher rds = request.getRequestDispatcher("/left.jsp");
 		//rds.forward(request, response);

	}

}
