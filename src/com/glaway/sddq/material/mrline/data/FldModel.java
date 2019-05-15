package com.glaway.sddq.material.mrline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * <配件申请行选择车型字段类>
 * 
 * @author  public2795
 * @version  [版本号, 2018-8-29]
 * @since  [产品/模块版本]
 */
public class FldModel extends JpoField {
	 /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        super.init();
        String[] thisAttrs = {this.getFieldName()};
        String[] srcAttrs = {"modelnum"};
        setLookupMap(thisAttrs, srcAttrs);
    }
    @Override
    public IJpoSet getList()
        throws MroException
    {
        setListObject("MODELS");
        String listSql = "";
        String project = this.getJpo().getParent().getString("PROJECT");
        if(!project.isEmpty()){
        	listSql = listSql + "modelnum in (select CMODEL from asset where PROJECTNUM='"+project+"')";
        }else{
        	listSql = listSql + "1=1";
        }
        if (!listSql.equals(""))
        {
            setListWhere(listSql);
        }
        
        return super.getList();
    }  
}
