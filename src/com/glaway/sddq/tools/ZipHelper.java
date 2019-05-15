package com.glaway.sddq.tools;

import java.util.List;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.FileHeader;

public class ZipHelper {
	
	@SuppressWarnings("unchecked")
	public static String getEncoding( String path ) throws Exception
	{
	    String encoding = "GBK" ;
	    ZipFile zipFile = new ZipFile( path ) ;
	    zipFile.setFileNameCharset( encoding ) ;
	    List<FileHeader> list = zipFile.getFileHeaders() ;
	    for( int i = 0; i < list.size(); i++ )
	    {
	        FileHeader fileHeader = list.get( i ) ;
	        String fileName = fileHeader.getFileName();
	        if( isMessyCode( fileName ) )
	        {
	            encoding = "UTF-8" ;
	            break ;
	        }
	    }
	    return encoding ;
	}

	private static boolean isMessyCode( String str )
	{
	    for( int i = 0; i < str.length(); i++ )
	    {
	        char c = str.charAt( i ) ;
	        // 当从Unicode编码向某个字符集转换时，如果在该字符集中没有对应的编码，则得到0x3f（即问号字符?）
	        // 从其他字符集向Unicode编码转换时，如果这个二进制数在该字符集中没有标识任何的字符，则得到的结果是0xfffd
	        if( (int)c == 0xfffd )
	        {
	            // 存在乱码
	            return true ;
	        }
	    }
	    return false ;
	}
}
