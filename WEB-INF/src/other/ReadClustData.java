package other;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;

public class ReadClustData {
	
	private static ArrayList<String> PageURL;
	private static HashMap<String, ArrayList<String>> ClustResult;
	
	private static HashMap<String, String> DocLabel;
	
	public HashMap<String, String> getDocLabel(String filepath){
		DocLabel=new HashMap<String, String>();
		try{
			char sep=',';
			CSVReader reader = new CSVReader(new FileReader(filepath),sep);
			String[] nextLine;
			while ((nextLine=reader.readNext())!=null){
				DocLabel.put(nextLine[0], nextLine[1]);
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		return DocLabel;
	}
	
	public HashMap<String, ArrayList<String>> getResult(String filepath){
		ClustResult = new HashMap<String, ArrayList<String>>();
		try{
			char sep = '\t';
			CSVReader reader = new CSVReader(new FileReader(filepath),sep);
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null){
				PageURL = new ArrayList<String>();
				int i=0;
				while(i<nextLine.length){
					String a = nextLine[i];
					if (a.equals("")){
						break;
					}else{
						PageURL.add(a);
					}
					i++;
				}
				String Label = PageURL.get(0);
				System.out.println(PageURL);
				PageURL.remove(0);
				ClustResult.put(Label, PageURL);
			}
		}catch (IOException e){
			e.printStackTrace();
		}
		return ClustResult;
	}
}
