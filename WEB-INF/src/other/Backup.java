package other;

import java.io.File;

public class Backup {
	
	public void moveFile(String name, String save_Dir, String theme, String times){
		String dir = save_Dir+"cache/";
		
		
		File sourceFile = new File(dir,name+"_kclust");
		File targetFile = new File(save_Dir+"cache/result/",name+"_"+theme+"_"+times+"_kclust");
		boolean ret = sourceFile.renameTo(targetFile);
		
		sourceFile = new File(dir,name+"_label");
		targetFile = new File(save_Dir+"cache/result/",name+"_"+theme+"_"+times+"_label");
		ret = sourceFile.renameTo(targetFile);
		
		sourceFile = new File(dir,name+"_result");
		targetFile = new File(save_Dir+"cache/result/",name+"_"+theme+"_"+times+"_result");
		ret = sourceFile.renameTo(targetFile);
		
	}
}
