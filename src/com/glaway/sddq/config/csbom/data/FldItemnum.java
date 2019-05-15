package com.glaway.sddq.config.csbom.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;

public class FldItemnum extends JpoField
{
    private static final long serialVersionUID = 1L;
    
    @Override
    public void init()
        throws MroException
    {
        this.setLookupMap(new String[] {"PRODUCTCODE", "DESCRIPTION", "MODEL", "LINENUM", "ITEMNUM", "RNUM", "LOC","LOCDESC",
            "SOFTNAME", "SOFTVERSION", "SOFTUPDATE", "CONFIGNUM", "STATUS", "PARTCODE", "TEMPLEVEL"}, new String[] {
            "PRODUCTCODE", "DESCRIPTION", "MODEL", "LINENUM", "ITEMNUM", "RNUM", "LOC","LOCDESC","SOFTNAME", "SOFTVERSION",
            "SOFTUPDATE", "CONFIGNUM", "STATUS", "PARTCODE", "TEMPLEVEL"});
    }

	@Override
	public IJpoSet getList() throws MroException {
		
		IJpo parent = this.getJpo().getParent();
		String assetcslevel = parent.getField("assetcslevel").getValue();
		if(!StringUtil.isNullOrEmpty(assetcslevel) && assetcslevel.equals("CAR")){
			return super.getList();
		}else if(!StringUtil.isNullOrEmpty(assetcslevel) && assetcslevel.equals("SYSTEM")){
			this.setLookupMap(new String[] {"ITEMNUM"}, new String[] {"ITEMNUM"});
			IJpoSet jposet = this.getUserServer().getJpoSet("SYS_ITEM", "ROTATING = '1'");
			jposet.reset();
			return jposet;
		}
		return super.getList();
	}
    
}
