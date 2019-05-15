package com.glaway.sddq.overhaul.jctaskorder.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;

/**
 * 
 * 交车车厢字段类
 * 
 * @author chenbin
 * @version [版本号, 2018-7-24]
 * @since [产品/模块版本]
 */
public class FldCarRiageNum extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "CARRIAGENUM"},
				new String[] { "CARRIAGENUM"});
	}

	@Override
	public IJpoSet getList() throws MroException {
		this.setListObject("ASSET");
		String cmodel = this.getJpo().getField("CMODEL").getValue();
		String carno = this.getJpo().getField("carno").getValue();
		//如果车型车号为空，则弹出提示信息
		if(StringUtil.isNullOrEmpty(carno))
		{
			throw new AppException("jcproduct", "notcarno");
		}
		
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET", "cmodel='"+cmodel+"' and carno='"+carno+"'");
		if (jposet != null && jposet.count() > 0) {
			String ancestor = jposet.getJpo(0).getString("ANCESTOR");
//			IJpoSet assetcsSet = MroServer.getMroServer().getSysJpoSet("ASSETCS");
			this.setListWhere("ancestor='" + ancestor + "' and assetlevel='CAR'");
//			assetcsSet.setQueryWhere("ASSETCSNUM in (select CARRIAGENUM from ASSET where ancestor='" + ancestor + "' and assetlevel='CAR')");
//			String assetcsnum =assetcsSet.getJpo().getString("ASSETCSNUM");
//			this.setListWhere("CARRIAGENUM=='"+assetcsnum+"'");
			super.getList();
		} else {
			this.setListWhere("1=2");
		}
		return super.getList();		
	}
}
