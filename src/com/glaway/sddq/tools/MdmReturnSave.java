package com.glaway.sddq.tools;

public class MdmReturnSave {
	
	 public final static String strReturnMsg = "<?xml version='1.0' encoding='UTF-8'?><return><msg>";
	 public final static String trueMsg = "</msg><flag>TRUE</flag></return>";
	 public final static String falseMsg = "</msg><flag>FALSE</flag></return>";
	
	 //处理返回成功信息
	 public static String getTrueMsg(){
		 
		 String t = strReturnMsg+trueMsg;
		 System.out.println(t.replace("'", "\""));
		 return t.replace("'", "\"");
	 }
	 
	 //处理返回失败信息
	 public static String getFalseMsg(String errorMsg){
		 String f = strReturnMsg+errorMsg+falseMsg;
		 System.out.println(f.replace("'", "\""));
		 return f.replace("'", "\"");
	 }
	 

}
