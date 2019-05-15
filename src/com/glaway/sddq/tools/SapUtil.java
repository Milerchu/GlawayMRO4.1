package com.glaway.sddq.tools;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.sap.mw.jco.JCO;
import com.sap.mw.jco.JCO.Client;

/**
 * 
 * SAP连接
 * 
 * @author public2175
 * @version [版本号, 2018-6-12]
 * @since [产品/模块版本]
 */
public class SapUtil {
	
	public static final String POOL_NAME = "SAP_POOL";

	private static Properties logonProperties;

	private static int sap_max_connection = 10;
	
	/**
	 * 
	 * 获取连接
	 * @return [参数说明]
	 *
	 */
	public static Client getSapConnection() {
		
		try {
			if (logonProperties == null) {
				logonProperties = PropertiesLoaderUtils
						.loadAllProperties("saplogin.properties");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return JCO.createClient(logonProperties);
	}
	
	/**
	 * 
	 * 通过连接池获取连接
	 * @return [参数说明]
	 *
	 */
	public static Client getSapPoolConnection() {

		JCO.Pool pool = JCO.getClientPoolManager().getPool(POOL_NAME);
		if (pool == null) {
			if (logonProperties == null) {
				try {
					logonProperties = PropertiesLoaderUtils
							.loadAllProperties("saplogin.properties");
					JCO.addClientPool(POOL_NAME,
							getMaxConnection(logonProperties), logonProperties);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return JCO.getClient(POOL_NAME);
	}
	
	/**
	 * 
	 * 获取最大连接数
	 * @param logonProperties
	 * @return [参数说明]
	 *
	 */
	public static int getMaxConnection(Properties logonProperties) {

		if (logonProperties != null) {
			String max_connection = logonProperties
					.getProperty("sap_max_connection");
			if (StringUtils.isNotBlank(max_connection)) {
				sap_max_connection = Integer.valueOf(max_connection);
			}
		}
		return sap_max_connection;
	}
	
	/**
	 * 
	 * 在连接使用完毕之后，使用releaseClient方法释放当前连接
	 * @param conn [参数说明]
	 *
	 */
	public static void releaseClient(Client conn){
		if(conn != null){
			JCO.releaseClient(conn);
		}
	}
	
	/**
	 * 
	 * 移除连接池，移除连接池将导致其中所有的活动连接被强行关闭，所以必须在确保连接池中所有的连接都不再被使用的时候才能执行该操作
	 * @param poolName [参数说明]
	 *
	 */
	public static void releaseClient(String poolName){
		
		if(StringUtils.isNotEmpty(poolName)){
			JCO.removeClientPool(poolName);
		}
	}
	
}
