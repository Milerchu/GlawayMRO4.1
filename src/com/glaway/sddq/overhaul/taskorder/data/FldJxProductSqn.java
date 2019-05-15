package com.glaway.sddq.overhaul.taskorder.data;

import com.alibaba.druid.util.StringUtils;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.StringUtil;

/**
 * 
 * 检修产品列表中的序列号字段类
 * 
 * @author hyhe
 * @version [版本号, 2018-3-22]
 * @since [产品/模块版本]
 */
public class FldJxProductSqn extends JpoField {

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void init() throws MroException {
		this.setLookupMap(new String[] { "ASSETNUM", "SQN","LOC"},
				new String[] { "ASSETNUM", "SQN","LOC"});
	}

	/**
	 * 根据不同的物料获取不同的数据
	 * @return
	 * @throws MroException
	 */
	@Override
	public IJpoSet getList() throws MroException {
		this.setListObject("ASSET");
		String cmodel = this.getJpo().getParent().getString("CMODEL");
		String carno = this.getJpo().getParent().getString("CARNO");
		// String itemnum = this.getJpo().getParent().getString("ITEMNUM");
		// 判断车号数据是否为空
		if (StringUtils.isEmpty(carno)) {
			throw new AppException("jxtaskorder", "carnonull");
		}
		// 判断车型数据是否为空
		if (StringUtils.isEmpty(cmodel)) {
			throw new AppException("jxtaskorder", "cmodelnull");
		}
		String jpnum = this.getJpo().getString("JPNUM");
		if(StringUtil.isStrEmpty(jpnum)){
			throw new AppException("jxtaskorder", "selectJp");
		}
		String itemnum = this.getJpo().getString("ITEMNUM");
		if(StringUtil.isStrEmpty(itemnum)){
			throw new AppException("jxtaskorder", "selectitemnum");
		}
		/*
		 * if (StringUtils.isEmpty(itemnum)) { throw new
		 * AppException("jxtaskorder", "cannotnull"); }
		 */
		IJpoSet jposet = MroServer.getMroServer().getSysJpoSet("ASSET",
				"cmodel='" + cmodel + "' and carno='" + carno + "'");
		if (jposet != null && jposet.count() > 0) {
			IJpo jpo = jposet.getJpo(0);
			String ancestor = jpo.getString("ANCESTOR");
			String assetnum = "";
			IJpoSet jxproductSet = getJpo().getJpoSet();
			if (jxproductSet != null && jxproductSet.count() > 0) {
				for (int i = 0; i < jxproductSet.count(); i++) {
					if(jxproductSet.getJpo(i) !=getJpo()){
						String assetnums = jxproductSet.getJpo(i).getString(
								"assetnum");
						if (!StringUtil.isStrEmpty(assetnums)) {
							assetnum += "'" + assetnums + "',";
						}
					}
				}
				if (StringUtil.isStrNotEmpty(assetnum)) {
					assetnum = assetnum
							.substring(0, assetnum.length() - 1);
				}
			}
			String sql="assetlevel ='SYSTEM' and type = '2' and sqn is not null and ancestor='"
					+ ancestor +"' and itemnum = '"+itemnum+"'";
			if (StringUtil.isStrNotEmpty(assetnum)) {
				sql += " and assetnum not in(" + assetnum + ")";
			}		
			this.setListWhere(sql);

		}
		return super.getList();
	}

}
