package com.glaway.sddq.material.mrline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
/**
 * 
 * <配件申请行选择项目字段类>
 * 
 * @author  public2795
 * @version  [版本号, 2018-8-29]
 * @since  [产品/模块版本]
 */
public class FldProject extends JpoField {
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
        String[] srcAttrs = {"PROJECTNUM"};
        setLookupMap(thisAttrs, srcAttrs);
    }
    @Override
    public IJpoSet getList()
        throws MroException
    {
        setListObject("PROJECTINFO");
        String listSql = "";
        String model = this.getJpo().getParent().getString("model");
        if(!model.isEmpty()){
        	listSql = listSql + "PROJECTNUM in (select PROJECTNUM from asset where CMODEL='"+model+"')";
        }else{
        	listSql = listSql + "STATUS='已立项'";
        }
        
        if (!listSql.equals(""))
        {
            setListWhere(listSql);
        }
        
        return super.getList();
    }
}
