package com.jweb.dao.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author 邓超
 *
 * 2022/6/4 12:24
 */
public abstract class AbstractProducer {
	
	public TableInfo info;
	public Context context;
	
	public static final String NEW_LINE = "\r\n";
	public static final String TABLE = "\t";
	public static final String BLANK = " ";
	
	public AbstractProducer(TableInfo info, Context context){
		this.context = context;
		this.info = info;
	}
	
	public void run(){
		
		String producer = producer();
		if(producer == null || "".equals(producer)){
			throw new RuntimeException("未知Producer");
		}
		File file = createFile(producer);
		String filePath = file.getPath();
		if(producer.equalsIgnoreCase("dao") || producer.equalsIgnoreCase("daoimpl") 
				|| producer.equalsIgnoreCase("service") || producer.equalsIgnoreCase("serviceimpl")
				|| producer.equalsIgnoreCase("mapper") || producer.equalsIgnoreCase("queryxml")){
			if(file.exists()){//dao、query中有自定义的方法，不能覆盖
				System.out.println("====>"+filePath+" exist，skiped");
				return;
			}
		}
		StringBuilder builder = coding();
		if(builder == null){
			return;
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file, false);
			fos.write(builder.toString().getBytes());
			fos.flush();
			System.out.println("====>"+filePath+" created");
		} catch (FileNotFoundException e) {
			throw new RuntimeException("", e);
		} catch (IOException e) {
			throw new RuntimeException("", e);
		}finally{
			if(fos != null){
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	protected String packageName() {
		String className = this.getClass().getName();
		return className.substring(0, className.indexOf("generator")-1);
	}
	private File createFile(String producer){
		String[] packageSegments = packageName().split("\\.");
		String packageName = "";
		for(String packageSegment : packageSegments) {
			packageName += packageSegment+"/";
		}
		packageName = packageName.substring(0, packageName.length()-1);
		String path = this.getClass().getClassLoader().getResource("").getPath();
		path = path.replaceAll("/target/classes/", "/src/main/");
		if(producer.equalsIgnoreCase("entity")){
			path += "java/"+packageName+"/entity";
		}else if(producer.equalsIgnoreCase("dao")){
			path += "java/"+packageName;
		}else if(producer.equalsIgnoreCase("daoimpl")){
			path += "java/"+packageName+"/impl";
		}else if(producer.equalsIgnoreCase("mapper")){
			path += "java/"+packageName+"/mapper";
		}else if(producer.equalsIgnoreCase("mapperxml")){
			path += "resources/mapper";
		}else if(producer.equalsIgnoreCase("queryxml")){
			path += "resources/mapper/query";
		}else if(producer.equalsIgnoreCase("service")){
			path = path.replaceAll("/src/main/", "");
			path = path.substring(0, path.lastIndexOf("-")+1)+"service/src/main/java/"+packageName.substring(0, packageName.lastIndexOf("/")+1)+"service";
		}else if(producer.equalsIgnoreCase("serviceimpl")){
			path = path.replaceAll("/src/main/", "");
			path = path.substring(0, path.lastIndexOf("-")+1)+"service/src/main/java/"+packageName.substring(0, packageName.lastIndexOf("/")+1)+"service/impl";
		}else if(producer.equalsIgnoreCase("apivo")){
			path = path.replaceAll("/src/main/", "");
			path = path.substring(0, path.lastIndexOf("-")+1)+"adminweb/src/main/java/"+packageName.substring(0, packageName.lastIndexOf("/")+1)+"adminweb/generatecode/vo";
		}else if(producer.equalsIgnoreCase("apidoc")){
			path = path.replaceAll("/src/main/", "");
			path = path.substring(0, path.lastIndexOf("-")+1)+"adminweb/src/main/java/"+packageName.substring(0, packageName.lastIndexOf("/")+1)+"adminweb/generatecode";
		}else if(producer.equalsIgnoreCase("apicontroller")){
			path = path.replaceAll("/src/main/", "");
			path = path.substring(0, path.lastIndexOf("-")+1)+"adminweb/src/main/java/"+packageName.substring(0, packageName.lastIndexOf("/")+1)+"adminweb/generatecode";
		}
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		String fileName = getFileName(producer);
		file = new File(path+"/"+fileName);
		return file;
	}
	
	public void createAuthor(StringBuilder builder){
		builder.append("/**").append(NEW_LINE);
		builder.append(" *").append(NEW_LINE);
		builder.append(" * @author ").append("邓超").append(NEW_LINE);
		builder.append(" *").append(NEW_LINE);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime ldt = LocalDateTime.now();
		builder.append(" * ").append(ldt.format(dtf)).append(NEW_LINE);
		builder.append("*/").append(NEW_LINE);
	}
	
	private String getFileName(String producer){
		String fileName = info.getTableName();
		fileName = formatName(fileName);
		fileName = fileName.substring(0, 1).toUpperCase()+fileName.substring(1, fileName.length());
		if(producer.equalsIgnoreCase("entity")){
			fileName += ".java";
		}else if(producer.equalsIgnoreCase("dao")){
			fileName += "Dao.java";
		}else if(producer.equalsIgnoreCase("daoimpl")){
			fileName += "DaoImpl.java";
		}else if(producer.equalsIgnoreCase("mapper")){
			fileName += "Mapper.java";
		}else if(producer.equalsIgnoreCase("mapperxml")){
			fileName += "Mapper.xml";
		}else if(producer.equalsIgnoreCase("queryxml")){
			fileName += "Query.xml";
		}else if(producer.equalsIgnoreCase("indexdoc")){
			fileName += "Doc.java";
		}else if(producer.equalsIgnoreCase("service")){
			fileName += "Service.java";
		}else if(producer.equalsIgnoreCase("serviceimpl")){
			fileName += "ServiceImpl.java";
		}else if(producer.equalsIgnoreCase("apivo")){
			fileName += "Result.java";
		}else if(producer.equalsIgnoreCase("apidoc")){
			fileName += "ApiDoc.java";
		}else if(producer.equalsIgnoreCase("apicontroller")){
			fileName += "Controller.java";
		}
		return fileName;
	}
	public String formatName(String str){
		if(!str.contains("_")){
			return str;
		}
		String name = null;
		String[] strs = str.split("_");
		for(String s : strs){
			if(name == null){
				name = s;
			}else{
				name += s.substring(0, 1).toUpperCase()+s.substring(1, s.length());
			}
		}
		return name;
	}
	
	abstract StringBuilder coding();
	
	abstract String producer();
}
