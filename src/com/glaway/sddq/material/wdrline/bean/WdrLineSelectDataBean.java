package com.glaway.sddq.material.wdrline.bean;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.sddq.tools.ItemUtil;
/**
 * 
 * <处置管理待处置物料行绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-3-19]
 * @since  [产品/模块版本]
 */
public class WdrLineSelectDataBean extends DataBean {
	/**
	 * 
	 * <获取处置物料信息按钮>
	 * @throws MroException [参数说明]
	 * @throws IOException 
	 *
	 */
	public void holditem() throws MroException, IOException {
		String personid = this.getAppBean().getJpo().getUserInfo().getLoginID();
		personid = personid.toUpperCase();
		String createby=this.getAppBean().getJpo().getString("createby");
		String location = this.getAppBean().getJpo().getString("location");
		String wdrnum = this.getAppBean().getJpo().getString("wdrnum");
		String status = this.getAppBean().getJpo().getString("status");
		String DEALTYPE = this.getAppBean().getJpo().getString("DEALTYPE");
		DataBean wdrlineselect = this.page.getAppBean().getDataBean("1526969427382");//待处置物料列表标识
		IJpoSet wdrlineselectset = wdrlineselect.getJpoSet();
		if (status.equalsIgnoreCase("处置完成")) {
			throw new MroException("处置完成的单据无法获取待处置物料信息");
		} else {
			if(personid.equalsIgnoreCase(createby)){
				if (location.equalsIgnoreCase("")) {
					throw new MroException("请先选择处置库房");
				}else{
					if(!wdrlineselectset.isEmpty()){
						throw new MroException("该单据已经获取过待处置物料信息");
					}else{
						addwdrlineselect(wdrlineselectset,location,DEALTYPE,wdrnum);
						wdrlineselect.reloadPage();
					}
				}
			}else{
				throw new MroException("非创建人不能编辑待处置物料信息");
			}
		}
	}
	/**
	 * 
	 * <获取待处置物料方法>
	 * @param location
	 * @param DEALTYPE
	 * @throws MroException [参数说明]
	 *
	 */
	public void addwdrlineselect(IJpoSet wdrlineselectset,String location,String DEALTYPE,String wdrnum) throws MroException {
		if(DEALTYPE.equalsIgnoreCase("报废")){
			IJpoSet inventoryset = MroServer.getMroServer().getJpoSet("sys_inventory",MroServer.getMroServer().getSystemUserServer());
			inventoryset.setUserWhere("location='"+location+"' and curbal>0");
			if(inventoryset.isEmpty()){
				throw new MroException("该库房没有可报废的物料");
			}else{
				for(int i=0;i<inventoryset.count();i++){
					IJpo inventory=inventoryset.getJpo(i);
					String itemnum=inventory.getString("itemnum");
					double curbal=inventory.getDouble("curbal");
					double fcztqty = 0;
					IJpoSet transferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
					transferlineset.setUserWhere("itemnum='"+itemnum+"'  and ISSUESTOREROOM='"+location+"' and transfernum in (select transfernum from transfer where status='在途' and type in ('SX','ZXD'))");
					transferlineset.reset();
					if (transferlineset.count() > 0) {
						fcztqty = transferlineset.sum("ORDERQTY");
					}
					double kyqty=curbal-fcztqty;
					if(kyqty>0){
						String type = ItemUtil.getItemInfo(itemnum);
						if (ItemUtil.SQN_ITEM.equals(type)) {// --判断是周转件
							IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",MroServer.getMroServer().getSystemUserServer());
							assetset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and assetlevel='ASSET' and status!='在途' " +
									"and assetnum not in (select assetnum from wdrlineselect where assetnum is not null and wdrnum in " +
									"(select wdrnum from wdr where status='草稿')and itemnum='"+itemnum+"' and location='"+location+"')" +
									"and assetnum not in (select assetnum from WDRLINE where  wdrnum in " +
									"(select wdrnum from wdr where status not in ('草稿','处置完成') and itemnum='"+itemnum+"' and location='"+location+"'))");
							if(!assetset.isEmpty()){
								for(int j=0;j<assetset.count();j++){
									IJpo asset=assetset.getJpo(j);
									String sqn=asset.getString("sqn");
									String assetnum=asset.getString("assetnum");
									IJpo wdrlineselect=wdrlineselectset.addJpo();
									wdrlineselect.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
									wdrlineselect.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
									wdrlineselect.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									wdrlineselect.setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									wdrlineselect.setValue("qty", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									wdrlineselect.setValue("wdrnum", wdrnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									wdrlineselect.setValue("sqn", sqn,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									wdrlineselect.setValue("assetnum", assetnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									wdrlineselect.setValue("DEALTYPE", DEALTYPE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							}
						}
						else if (ItemUtil.LOT_I_ITEM.equals(type)) {// --判断是批次件
							IJpoSet invblanceset = MroServer.getMroServer().getJpoSet("invblance",MroServer.getMroServer().getSystemUserServer());
							invblanceset.setUserWhere("itemnum='"+itemnum+"' and storeroom='"+location+"' and physcntqty>0");
							if(!invblanceset.isEmpty()){
								for(int k=0;k<invblanceset.count();k++){
									IJpo invblance=invblanceset.getJpo(k);
									String lotnum=invblance.getString("lotnum");
									double physcntqty=invblance.getDouble("physcntqty");
									double zyphyscntqty=0;
									double sjzyphyscntqty=0;
									IJpoSet wdrlineselectinvblanceset = MroServer.getMroServer().getJpoSet("wdrlineselect",MroServer.getMroServer().getSystemUserServer());
									wdrlineselectinvblanceset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and lotnum='"+lotnum+"' and wdrnum in (select wdrnum from wdr where status='草稿')");
									if(!wdrlineselectinvblanceset.isEmpty()){
										zyphyscntqty=wdrlineselectinvblanceset.sum("qty");
									}
									IJpoSet wdrlineinvblanceset = MroServer.getMroServer().getJpoSet("wdrline",MroServer.getMroServer().getSystemUserServer());
									wdrlineinvblanceset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and lotnum='"+lotnum+"' and wdrnum in (select wdrnum from wdr where status not in ('草稿','处置完成'))");
									if(!wdrlineinvblanceset.isEmpty()){
										sjzyphyscntqty=wdrlineinvblanceset.sum("SCRAPQTY");
									}
									double invblancekyqty=physcntqty-zyphyscntqty-sjzyphyscntqty;
									if(invblancekyqty>0){
										IJpo wdrlineselect=wdrlineselectset.addJpo();
										wdrlineselect.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
										wdrlineselect.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
										wdrlineselect.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("qty", invblancekyqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("wdrnum", wdrnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("lotnum", lotnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("DEALTYPE", DEALTYPE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								}
							}
						}else{// --判断是非批次非序列号件
							IJpoSet wdrlineselectitemset = MroServer.getMroServer().getJpoSet("wdrlineselect",MroServer.getMroServer().getSystemUserServer());
							wdrlineselectitemset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and wdrnum in (select wdrnum from wdr where status='草稿')");
							double itemqty=0;
							double sjitemqty=0;
							if(!wdrlineselectitemset.isEmpty()){
								itemqty=wdrlineselectitemset.sum("qty");
							}
							IJpoSet wdrlineinvblanceset = MroServer.getMroServer().getJpoSet("wdrline",MroServer.getMroServer().getSystemUserServer());
							wdrlineinvblanceset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and wdrnum in (select wdrnum from wdr where status not in ('草稿','处置完成'))");
							if(!wdrlineinvblanceset.isEmpty()){
								sjitemqty=wdrlineinvblanceset.sum("SCRAPQTY");
							}
							double itemkyqty=kyqty-itemqty-sjitemqty;
							if(itemkyqty>0){
								IJpo wdrlineselect=wdrlineselectset.addJpo();
								wdrlineselect.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
								wdrlineselect.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
								wdrlineselect.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								wdrlineselect.setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								wdrlineselect.setValue("qty", itemkyqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								wdrlineselect.setValue("wdrnum", wdrnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								wdrlineselect.setValue("DEALTYPE", DEALTYPE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
							}
						}
					}
				}
			}
		}
		if(DEALTYPE.equalsIgnoreCase("再利用")){
			if(location.equalsIgnoreCase("Y1710")){//如果库房是再利用库
				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet("sys_inventory",MroServer.getMroServer().getSystemUserServer());
				inventoryset.setUserWhere("location='"+location+"' and curbal>0");
				if(inventoryset.isEmpty()){
					throw new MroException("该库房没有可再利用的物料");
				}else{
					for(int i=0;i<inventoryset.count();i++){
						IJpo inventory=inventoryset.getJpo(i);
						String itemnum=inventory.getString("itemnum");
						double curbal=inventory.getDouble("curbal");
						double fcztqty = 0;
						IJpoSet transferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
						transferlineset.setUserWhere("itemnum='"+itemnum+"'  and ISSUESTOREROOM='"+location+"' and transfernum in (select transfernum from transfer where status='在途' and type in ('SX','ZXD'))");
						transferlineset.reset();
						if (transferlineset.count() > 0) {
							fcztqty = transferlineset.sum("ORDERQTY");
						}
						double kyqty=curbal-fcztqty;
						if(kyqty>0){
							String type = ItemUtil.getItemInfo(itemnum);
							if (ItemUtil.SQN_ITEM.equals(type)) {// --判断是周转件
								IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",MroServer.getMroServer().getSystemUserServer());
								assetset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and assetlevel='ASSET' and status!='在途' " +
										"and assetnum not in (select assetnum from wdrlineselect where assetnum is not null and wdrnum in " +
										"(select wdrnum from wdr where status='草稿')and itemnum='"+itemnum+"' and location='"+location+"')" +
										"and assetnum not in (select assetnum from WDRLINE where  wdrnum in " +
										"(select wdrnum from wdr where status not in ('草稿','处置完成') and itemnum='"+itemnum+"' and location='"+location+"'))");
								if(!assetset.isEmpty()){
									for(int j=0;j<assetset.count();j++){
										IJpo asset=assetset.getJpo(j);
										String sqn=asset.getString("sqn");
										String assetnum=asset.getString("assetnum");
										IJpo wdrlineselect=wdrlineselectset.addJpo();
										wdrlineselect.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
										wdrlineselect.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
										wdrlineselect.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("qty", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("wdrnum", wdrnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("sqn", sqn,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("assetnum", assetnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										wdrlineselect.setValue("DEALTYPE", DEALTYPE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									}
								}
							}
							else if (ItemUtil.LOT_I_ITEM.equals(type)) {// --判断是批次件
								IJpoSet invblanceset = MroServer.getMroServer().getJpoSet("invblance",MroServer.getMroServer().getSystemUserServer());
								invblanceset.setUserWhere("itemnum='"+itemnum+"' and storeroom='"+location+"' and physcntqty>0");
								if(!invblanceset.isEmpty()){
									for(int k=0;k<invblanceset.count();k++){
										IJpo invblance=invblanceset.getJpo(k);
										String lotnum=invblance.getString("lotnum");
										double physcntqty=invblance.getDouble("physcntqty");
										double zyphyscntqty=0;
										double sjzyphyscntqty=0;
										IJpoSet wdrlineselectinvblanceset = MroServer.getMroServer().getJpoSet("wdrlineselect",MroServer.getMroServer().getSystemUserServer());
										wdrlineselectinvblanceset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and lotnum='"+lotnum+"' and wdrnum in (select wdrnum from wdr where status='草稿')");
										if(!wdrlineselectinvblanceset.isEmpty()){
											zyphyscntqty=wdrlineselectinvblanceset.sum("qty");
										}
										IJpoSet wdrlineinvblanceset = MroServer.getMroServer().getJpoSet("wdrline",MroServer.getMroServer().getSystemUserServer());
										wdrlineinvblanceset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and lotnum='"+lotnum+"' and wdrnum in (select wdrnum from wdr where status not in ('草稿','处置完成'))");
										if(!wdrlineinvblanceset.isEmpty()){
											sjzyphyscntqty=wdrlineinvblanceset.sum("SCRAPQTY");
										}
										double invblancekyqty=physcntqty-zyphyscntqty-sjzyphyscntqty;
										if(invblancekyqty>0){
											IJpo wdrlineselect=wdrlineselectset.addJpo();
											wdrlineselect.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
											wdrlineselect.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
											wdrlineselect.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
											wdrlineselect.setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
											wdrlineselect.setValue("qty", invblancekyqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
											wdrlineselect.setValue("wdrnum", wdrnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
											wdrlineselect.setValue("lotnum", lotnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
											wdrlineselect.setValue("DEALTYPE", DEALTYPE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
										}
									}
								}
							}else{// --判断是非批次非序列号件
								IJpoSet wdrlineselectitemset = MroServer.getMroServer().getJpoSet("wdrlineselect",MroServer.getMroServer().getSystemUserServer());
								wdrlineselectitemset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and wdrnum in (select wdrnum from wdr where status!='处置完成')");
								double itemqty=0;
								double sjitemqty=0;
								if(!wdrlineselectitemset.isEmpty()){
									itemqty=wdrlineselectitemset.sum("qty");
								}
								IJpoSet wdrlineinvblanceset = MroServer.getMroServer().getJpoSet("wdrline",MroServer.getMroServer().getSystemUserServer());
								wdrlineinvblanceset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and wdrnum in (select wdrnum from wdr where status not in ('草稿','处置完成'))");
								if(!wdrlineinvblanceset.isEmpty()){
									sjitemqty=wdrlineinvblanceset.sum("SCRAPQTY");
								}
								double itemkyqty=kyqty-itemqty-sjitemqty;
								if(itemkyqty>0){
									IJpo wdrlineselect=wdrlineselectset.addJpo();
									wdrlineselect.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
									wdrlineselect.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
									wdrlineselect.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									wdrlineselect.setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									wdrlineselect.setValue("qty", itemkyqty,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									wdrlineselect.setValue("wdrnum", wdrnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
									wdrlineselect.setValue("DEALTYPE", DEALTYPE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
								}
							}
						}
					}
				}
			}else{
				IJpoSet inventoryset = MroServer.getMroServer().getJpoSet("sys_inventory",MroServer.getMroServer().getSystemUserServer());
				inventoryset.setUserWhere("location='"+location+"' and curbal>0 and itemnum in (select itemnum from sys_item where ISTURNOVERERP='1')");
				if(inventoryset.isEmpty()){
					throw new MroException("该库房没有可再利用的物料");
				}else{
					for(int i=0;i<inventoryset.count();i++){
						IJpo inventory=inventoryset.getJpo(i);
						String itemnum=inventory.getString("itemnum");
						double curbal=inventory.getDouble("curbal");
						double fcztqty = 0;
						IJpoSet transferlineset = MroServer.getMroServer().getJpoSet("transferline",MroServer.getMroServer().getSystemUserServer());
						transferlineset.setUserWhere("itemnum='"+itemnum+"'  and ISSUESTOREROOM='"+location+"' and transfernum in (select transfernum from transfer where status='在途' and type in ('SX','ZXD'))");
						transferlineset.reset();
						if (transferlineset.count() > 0) {
							fcztqty = transferlineset.sum("ORDERQTY");
						}
						double kyqty=curbal-fcztqty;
						if(kyqty>0){
								IJpoSet assetset = MroServer.getMroServer().getJpoSet("asset",MroServer.getMroServer().getSystemUserServer());
								assetset.setUserWhere("itemnum='"+itemnum+"' and location='"+location+"' and assetlevel='ASSET' and isnew='1' and status!='在途' " +
										"and assetnum not in (select assetnum from wdrlineselect where assetnum is not null and wdrnum in " +
										"(select wdrnum from wdr where status='草稿')and itemnum='"+itemnum+"' and location='"+location+"')" +
										"and assetnum not in (select assetnum from WDRLINE where  wdrnum in " +
										"(select wdrnum from wdr where status not in ('草稿','处置完成') and itemnum='"+itemnum+"' and location='"+location+"'))");
								if(!assetset.isEmpty()){
									for(int j=0;j<assetset.count();j++){
										IJpo asset=assetset.getJpo(j);
										java.util.Date ADDDATE=asset.getDate("ADDDATE");//MES生产完成时间
										java.util.Date newdate = MroServer.getMroServer().getDate();//当前时间
										if (ADDDATE != null) {
											try {
												// 调用天数计算方法
												int inday = daysBetween(newdate, ADDDATE);
												if(inday>365){
													String sqn=asset.getString("sqn");
													String assetnum=asset.getString("assetnum");
													IJpo wdrlineselect=wdrlineselectset.addJpo();
													wdrlineselect.setValue("SITEID", this.page.getAppBean().getJpo().getString("SITEID"));
													wdrlineselect.setValue("ORGID", this.page.getAppBean().getJpo().getString("ORGID"));
													wdrlineselect.setValue("itemnum", itemnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
													wdrlineselect.setValue("location", location,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
													wdrlineselect.setValue("qty", 1,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
													wdrlineselect.setValue("wdrnum", wdrnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
													wdrlineselect.setValue("sqn", sqn,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
													wdrlineselect.setValue("assetnum", assetnum,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
													wdrlineselect.setValue("DEALTYPE", DEALTYPE,GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
												}
											} catch (ParseException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}

										}
									}
								}
						}
					}
				}
			}
		}
	}
	private int daysBetween(java.util.Date Enddate, java.util.Date Stardate) throws ParseException,MroException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	Enddate = sdf.parse(sdf.format(Enddate));
	Stardate = sdf.parse(sdf.format(Stardate));
	Calendar cal = Calendar.getInstance();
	cal.setTime(Enddate);
	long time1 = cal.getTimeInMillis();

	cal.setTime(Stardate);
	long time2 = cal.getTimeInMillis();
// 两个日期相差的天数
	long daysbtw = (time1 - time2) / (1000 * 3600 * 24);
// long j = cycle * 365;
// long i = j - daysbtw;
	return Integer.parseInt(String.valueOf(daysbtw));
	}
}
