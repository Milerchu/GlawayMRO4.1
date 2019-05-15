package com.glaway.sddq.back.Interface.webservice.erp;

import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.IRepository;
import com.sap.mw.jco.JCO;

public class JcoCppzTest {
	public static void main(String[] args){
    	IRepository repository; 
		// 定义连接
    	        JCO.Client mConnection ; 
		// 建立连接对象
    	        mConnection = 
    	        	/*JCO.createClient(   "200",         // SAP client 
    	                    "itfsap",            // userid 
    	                    "12392008",           // password 
    	                    "ZH",                 // language (null for the default language) 
    	                    "172.16.9.80",     // application server host name 
    	                    "00");*/                // system number 
    	        	
    	        	JCO.createClient("800",         // SAP client 
    	                    "itfsap",            // userid 
    	                    "12392008",           // password 
    	                    "ZH",                 // language (null for the default language) 
    	                    //"sappro4.teg.cn",     // application server host name //172.16.9.90
    	                    "10.96.12.63",
    	                    "01");
    	    try{
			// 打开连接，调用功能模块，关闭连接
    	        mConnection.connect(); 
    	        
    	        JCO.Repository myRepository = new JCO.Repository("Repository",  mConnection);
    	        
    	        String strFunc = "ZTFUN_FRACAS_CPPZ";
    	        IFunctionTemplate ft = myRepository.getFunctionTemplate(strFunc.toUpperCase());
    	        JCO.Function funGetList = ft.getFunction();
    	        
    	        JCO.ParameterList input = funGetList.getImportParameterList();
//    	        input.setValue("500039610C", "ZBARN");
    	        //input.setValue("5003121145", "ZBARN");
    	        //input.setValue("Z073204155", "ZBARN");
    	        //input.setValue("Z072566158", "ZBARN");//Z023061166
    	        input.setValue("Z005773167", "ZBARN");
    	        
    	        mConnection.execute(funGetList);
    	        JCO.Table flights = funGetList.getTableParameterList().getTable("T_ZFRACASZPPZ");
//MATNR, ZBARN, ZMATNR, SBARN, ZPLACE, M	AKTX
    	        for (int i = 0; i < flights.getNumRows(); i++) {
    	        	flights.setRow(i);
				// if(flights.getString("MAKTX").indexOf("变流器模块") != -1){
    	        }

    	        mConnection.disconnect(); 
	        }catch(Exception e){
	        	e.printStackTrace();
			System.err.println("打开或关闭连接失败");
	        } 

    }
	
	public boolean checkMos(String xlh,String cpxh){
		boolean mf = false;
		IRepository repository; 
		// 定义连接
    	        JCO.Client mConnection ; 
		// 建立连接对象
    	        mConnection = 
    	        	
    	        	JCO.createClient(   "800",         // SAP client 
    	                    "itfsap",            // userid 
    	                    "12392008",           // password 
    	                    "ZH",                 // language (null for the default language) 
    	                    //"sappro4.teg.cn",     // application server host name //172.16.9.90
    	                    "10.96.12.63",
    	                    "01");
    	        
    	    try{
			// 打开连接，调用功能模块，关闭连接
    	        mConnection.connect(); 
    	        JCO.Repository myRepository = new JCO.Repository("Repository",  mConnection);
    	        
    	        String strFunc = "ZTFUN_FRACAS_CPPZ";
    	        IFunctionTemplate ft = myRepository.getFunctionTemplate(strFunc.toUpperCase());
    	        JCO.Function funGetList = ft.getFunction();
    	        
    	        JCO.ParameterList input = funGetList.getImportParameterList();
    	        input.setValue(xlh, "ZBARN");
    	        
    	        mConnection.execute(funGetList);
    	        JCO.Table flights = funGetList.getTableParameterList().getTable("T_ZFRACASZPPZ");
//MATNR, ZBARN, ZMATNR, SBARN, ZPLACE, M	AKTX
    	        for (int i = 0; i < flights.getNumRows(); i++) {
    	        	
    	        	flights.setRow(i);
    	        	String th = flights.getString("ZMATNR");
    	        	String bh = flights.getString("SBARN");
    	        	if(cpxh.equals(th) && !"".equals(bh) && bh != null){
    	        		mf = true;
    	        	}
    	        }

    	        mConnection.disconnect(); 
	        }catch(Exception e){
	        	e.printStackTrace();
			System.err.println("打开或关闭连接失败");
	        } 
	        return mf;
	}
}
