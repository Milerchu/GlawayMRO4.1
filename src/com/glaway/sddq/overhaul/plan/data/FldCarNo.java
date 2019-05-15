package com.glaway.sddq.overhaul.plan.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

/**
 * 
 * 月度计划选择车号字段类
 * 
 * @author  hyhe
 * @version  [版本号, 2018-1-23]
 * @since  [产品/模块版本]
 */
public class FldCarNo extends JpoField
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        this.setLookupMap(new String[] {"WAGONNUM","ANCESTOR","CMODEL","REPAIRPROCESS"}, new String[] {"CARNO","ANCESTOR","CMODEL","REPAIRPROCESS"});
    }
    
    @Override
    public IJpoSet getList()
        throws MroException
    {
        this.setListObject("ASSET");
        if(this.getJpo().getParent() != null){
        	String where = "";
        	IJpoSet jposet = this.getJpo().getParent().getJpoSet("MONTHYEARPLAN");
        	for(int index=0;index < jposet.count();index++){
        		IJpo jpo = jposet.getJpo(index);
        		String cmodel = jpo.getString("CMODEL");
        		String repairprocess = jpo.getString("REPAIRPROCESS");
        		if(index == 0){
        			where = "(assetlevel = 'ASSET' and cmodel='"+cmodel+"')"+where;
        		}else{
            		where = "(assetlevel = 'ASSET' and cmodel='"+cmodel+"') or"+where;
        		}
        	}
            this.setListWhere(where);
        }
        return super.getList();
    }
}
