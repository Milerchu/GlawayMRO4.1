package com.glaway.sddq.material.mprline.data;

import io.netty.util.internal.StringUtil;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;

/**
 * 
 * <领料单序列号字段绑定类>
 * 
 * @author public2795
 * @version [版本号, 2019-1-8]
 * @since [产品/模块版本]
 */
public class FldSqn extends JpoField {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 映射赋值
	 * 
	 * @throws MroException
	 */
	@Override
	public void init() throws MroException {
		super.init();
		
		if (getJpo() != null && !StringUtil.isNullOrEmpty(this.getJpo().getAppName())) {
			if (this.getJpo().getAppName().equalsIgnoreCase("JSITEMREQ")) {
				String[] thisAttrs = { this.getFieldName(), "assetnum", "parentassetnum" };
				String[] srcAttrs = { "sqn", "assetnum", "parent" };
				setLookupMap(thisAttrs, srcAttrs);
			}else{
				String[] thisAttrs = { this.getFieldName(), "assetnum" };
				String[] srcAttrs = { "sqn", "assetnum" };
				setLookupMap(thisAttrs, srcAttrs);
			}
		}
		
		
	}

	/**
	 * 获取可选择列表数据
	 * 
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		setListObject("asset");
		IJpo parent = this.getJpo().getParent();
		String MPRSTOREROOM = parent.getString("MPRSTOREROOM");
		String rktype = parent.getString("rktype");
		String itemnum = this.getJpo().getString("itemnum");
		String PARENTSQNASSETNUM = this.getJpo().getString("PARENTSQNASSETNUM");
		String appname = this.getJpo().getParent().getAppName();
		String listSql = "";
		if (appname.equalsIgnoreCase("JSITEMREQ")) {
			if(rktype.equalsIgnoreCase("拆借件入库")){
				listSql = "ASSETNUM in(select ASSETNUM from asset  start with assetnum='"+PARENTSQNASSETNUM+"' connect by parent = PRIOR assetnum) and itemnum='"+itemnum+"'";
				setListWhere(listSql);
			}else{
				listSql = "assetnum='1'";
				setListWhere(listSql);
			}
		}else if(appname.equalsIgnoreCase("QTITEMREQ")){
			listSql = "itemnum='"
					+ itemnum
					+ "' and location='"
					+ MPRSTOREROOM
					+ "' and sqn not in (select sqn from asset where sqn like 'LS%') and assetlevel='ASSET'";
			setListWhere(listSql);
		}
		else{
			listSql = "itemnum='"
					+ itemnum
					+ "' and location='"
					+ MPRSTOREROOM
					+ "' and sqn not in (select sqn from asset where sqn like 'LS%') and iserp!='0' and assetlevel='ASSET'";
			setListWhere(listSql);
		}
		
		return super.getList();
	}

	/**
	 * 校验序列号相同
	 * 
	 * @throws MroException
	 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
		String appname = this.getJpo().getParent().getAppName();
		if (appname.equalsIgnoreCase("JSITEMREQ")) {
			String itemnum = this.getJpo().getString("itemnum");
			if (itemnum.isEmpty()) {
				throw new MroException("请先选择物料编码");
			} else {
				String rktype = this.getJpo().getParent().getString("rktype");
				String sqn = this.getValue();
				if(!rktype.equalsIgnoreCase("拆借件入库")){
					this.getJpo().setValue("parentassetnum", "",
							GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
					IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",
							MroServer.getMroServer().getSystemUserServer());// --对应的周转件集合
					assetset.setQueryWhere("itemnum='" + itemnum + "' and sqn='"
							+ sqn + "'");
					assetset.reset();
					if (!assetset.isEmpty()) {
						throw new MroException("相同图号已存该序列号，请检查");
					}
				}
			}
		}
		this.getJpo().setValue("qty", 1,
				GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
		this.getJpo().setFieldFlag("qty", GWConstant.S_READONLY, true);
	}

	/**
	 * 无校验
	 * 
	 * @throws MroException
	 */
	@Override
	protected void validateList() throws MroException {// 序列号，无校验 ---- 关
		// TODO Auto-generated method stub
		String appname = this.getJpo().getParent().getAppName();
		if (appname.equalsIgnoreCase("TKITEMREQ")) {
			super.validateList();
		}else if(appname.equalsIgnoreCase("JSITEMREQ")){
			String rktype = this.getJpo().getParent().getString("rktype");
			if(rktype.equalsIgnoreCase("拆借件入库")){
				super.validateList();
			}
		}
	}
}
