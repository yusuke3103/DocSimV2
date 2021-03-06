package servlet;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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

import other.Backup;
import other.Command;
import other.ReadClustData;
import SearchAPI.BingSearch;

@WebServlet("/Start")
public class Start extends HttpServlet{
	
	public static ArrayList<String> color= new ArrayList<String>(){{add("#C71585");add("#556b2f");add("#191970");add("#dc143c");add("#9932cc");add("#C71585");add("#556b2f");add("#191970");add("#dc143c");add("#9932cc");}};
    
	private static final long serialVersionUID = 1L;
	private static HashMap<String, HashMap<String, String>> WebPage;
	private static String Save_Dir = null;
	private static HashMap<String,ArrayList<String>> LabelGroup;
	protected String Python = "/usr/bin/python";
	
	
	public Start(){
		super();
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		System.out.println(color);
		LabelGroup = new HashMap<>();
		WebPage = new HashMap<String, HashMap<String,String>>();
		Save_Dir = getServletContext().getRealPath("/");

		/**
		 * セッション処理
		 * 接続ユーザ情報取得
		 */
		System.out.println("セッション情報確認");
		HttpSession ses = req.getSession();
		String name=(String)ses.getAttribute("name");
		if(name==null){
			req.setAttribute("error", -1);
			RequestDispatcher rds = req.getRequestDispatcher("/index.jsp");
			rds.forward(req, res);
		}else{
			System.err.println("接続ユーザ名："+name);
			
			/**
			 * POSTデータ受取り
			 */
			System.out.println("POSTデータ受取り");
			req.setCharacterEncoding("utf-8");
			String keyword = req.getParameter("keyword");
			keyword.replaceAll("　", " ");					//全角スペース→半角スペース
			System.err.println(keyword);
			String theme = (String)req.getParameter("theme");
			String times = (String)req.getParameter("times");
			System.out.println(theme);
			System.out.println(times);
			
			/**
			 * Bingで検索
			 */
			System.out.println("Bingで検索開始");
			BingSearch bingsearch = new BingSearch();
			bingsearch.setKeyword(keyword);
			bingsearch.run();
			
			/**
			 * 検索結果出力
			 */
			File file = new File(Save_Dir+"cache//"+name+"_result");
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			
			for (String url:WebPage.keySet()){
				for(String title:WebPage.get(url).keySet()){
					pw.println(url+"\t"+title+WebPage.get(url).get(title));
				}
			}
			pw.close();
			
			/**
			 * Pythonプログラム起動
			 */
			String[] cmd1 = {Python,Save_Dir+"pysrc/pymain.py",Save_Dir,name,keyword};
			Command cmdRunTime = new Command();
			try{
				cmdRunTime.execCmd(cmd1);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
			
			/**
			 * クラスタ結果読み出し
			 */
			ReadClustData rcd = new ReadClustData();
			Object ClustResult=rcd.getResult(Save_Dir+"cache/"+name+"_kclust");
			HashMap<String, String> DocLabel=rcd.getDocLabel(Save_Dir+"cache/"+name+"_label");
			
			/**
			 * ラベルグループ作成
			 */
			System.out.println("ラベル登録");
			for(String url:DocLabel.keySet()){
				String label=DocLabel.get(url);
				if(LabelGroup.containsKey(label)){
					LabelGroup.get(label).add(url);
				}else{
					ArrayList<String> urls =new ArrayList<String>();
					urls.add(url);
					LabelGroup.put(label,urls);
				}
			}
			
			Backup backup = new Backup();
			backup.moveFile(name,Save_Dir,theme,times);
			/**
			 * ブラウザ表示
			 */
			ses.setAttribute("keyword", keyword);
			ses.setAttribute("ClustResult", ClustResult);
			ses.setAttribute("WebPage", WebPage);
			ses.setAttribute("LabelGroup", LabelGroup);
			ses.setAttribute("DocLabel", DocLabel);
			RequestDispatcher rds = req.getRequestDispatcher("/left.jsp");
			rds.forward(req, res);
		}
	}

	/**
	 * WebPageの登録
	 * @param title
	 * @param siteurl
	 * @param spenit
	 */
	public static void setWebPage(String title, String siteurl, String spenit) {
		
		spenit = spenit.replaceAll("[\t\n\f\r]+ ", "");
		spenit = spenit.replaceAll("\"", "'");
		
		HashMap<String,String> ti_sp= new HashMap<String, String>();
		
		ti_sp.put(title, spenit);
		WebPage.put(siteurl, ti_sp);
		
	}
}
