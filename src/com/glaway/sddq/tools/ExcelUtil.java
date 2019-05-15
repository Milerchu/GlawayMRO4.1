package com.glaway.sddq.tools;

import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.ss.usermodel.Cell;

/**
 * 
 * Excel操作封装类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-2]
 * @since  [产品/模块版本]
 */
public class ExcelUtil
{
    public  static String getStringValue(Cell cell){
        String value = "";
        if(cell != null){
            
            int ty = cell.getCellType();
            if(ty == 0){
                value =  NumberToTextConverter.toText(cell.getNumericCellValue());
            }else{
                value = cell.getStringCellValue();
            }
            if(value == null){
                return "";
            }else{
                return value.trim();
            }
        }
        return value;
    }
}
