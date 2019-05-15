package com.glaway.sddq.back.Interface.webservice.erp;

import com.glaway.sddq.tools.SapUtil;
import com.sap.mw.jco.IFunctionTemplate;
import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;

public class ErpTest {
	
	public static void main(String[] args)
    {
		test2();
    }
	
	public static void test1(){
		
        JCO.Client mConnection = SapUtil.getSapConnection();
    	    try{
			// 打开连接，调用功能模块，关闭连接
    	        mConnection.connect(); 
    	        JCO.Repository myRepository = new JCO.Repository("Repository",  mConnection);
    	        String strFunc = "ZTFUN_FRACAS_CPPZ";
    	        IFunctionTemplate ft = myRepository.getFunctionTemplate(strFunc.toUpperCase());
    	        JCO.Function funGetList = ft.getFunction();
    	        JCO.ParameterList input = funGetList.getImportParameterList();
    	        input.setValue("Z005773167", "ZBARN");
    	        mConnection.execute(funGetList);
    	        JCO.Table flights = funGetList.getTableParameterList().getTable("T_ZFRACASZPPZ");

    	        for (int i = 0; i < flights.getNumRows(); i++) {
    	        	flights.setRow(i);

    	        }
    	      JCO.releaseClient(mConnection); 
	        }catch(Exception e){
	        	e.printStackTrace();
			System.err.println("打开或关闭连接失败");
	        } 
	}
	
		public static void test2(){
		
        	Client mConnection = SapUtil.getSapPoolConnection();
    	    try{
    	        JCO.Repository myRepository = new JCO.Repository("Repository",  SapUtil.POOL_NAME);
    	        String strFunc = "ZTFUN_FRACAS_CPPZ";
    	        IFunctionTemplate ft = myRepository.getFunctionTemplate(strFunc.toUpperCase());
    	        JCO.Function funGetList = ft.getFunction();
    	        JCO.ParameterList input = funGetList.getImportParameterList();
    	        input.setValue("Z005773167", "ZBARN");
    	        mConnection.execute(funGetList);
    	        JCO.Table flights = funGetList.getTableParameterList().getTable("T_ZFRACASZPPZ");
    	        for (int i = 0; i < flights.getNumRows(); i++) {
    	        	flights.setRow(i);
    	        }
    	        SapUtil.releaseClient(mConnection); 
	        }catch(Exception e){
	        	e.printStackTrace();
			System.err.println("打开或关闭连接失败");
	        } 
	}
	
}
