package com.glaway.sddq.material.transferline.data;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.jpo.JpoField;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.material.invtrans.common.CommAssetNum;
/**
 * 
 * <调拨单行序列号字段绑定类>
 * 
 * @author  public2795
 * @version  [版本号, 2019-1-9]
 * @since  [产品/模块版本]
 */
public class FldSqn extends JpoField {
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 映射赋值
	 * @throws MroException
	 */
	  @Override
	    public void init()
	        throws MroException
	    {
	        super.init();
	        String[] thisAttrs = {this.getFieldName(),"assetnum"};
	        String[] srcAttrs = {"sqn","assetnum"};
	        setLookupMap(thisAttrs, srcAttrs);
	        
	    }
/**
 * 过滤序列号数据
 * @return
 * @throws MroException
 */
	public IJpoSet getList()
			throws MroException
	{
		String tasknum = this.getJpo().getString("tasknum");
		if (tasknum.isEmpty())
		{
			setListObject("asset");
			String listSql = "";
			String itemnum = this.getJpo().getString("itemnum");
			if (!itemnum.isEmpty()) {
				if(this.getJpo().getParent().getString("type").equalsIgnoreCase("SX")){
					String assetnums=CommAssetNum.GETASSETNUM(this.getJpo().getParent().getJpoSet("transferline"));
			        IJpoSet transferlineset=MroServer.getMroServer().getJpoSet("transferline", MroServer.getMroServer().getSystemUserServer());
			        transferlineset.setUserWhere("status not in ('已接收') and transfernum in (select transfernum from transfer where type='SX') and itemnum='"+itemnum+"'");
			        transferlineset.reset();
			        String transferlineassetnums = null;
			        if (!transferlineset.isEmpty())
			        {
			            for (int i = 0; i < transferlineset.count(); i++)
			            {
			            	if(transferlineset.getJpo(i)==null){
			            		continue;
			            	}
			                String transferlineassetnum = transferlineset.getJpo(i).getString("assetnum");
			                if (!"".equals(transferlineassetnum))
			                {
			                    if (transferlineassetnums == null)
			                    	transferlineassetnums = "'" + StringUtil.getSafeSqlStr(transferlineassetnum) + "'";
			                    else
			                    	transferlineassetnums = transferlineassetnums + ",'" + StringUtil.getSafeSqlStr(transferlineassetnum) + "'";
			                }
			            }
			        }
			        if(transferlineassetnums != null && assetnums != null){
			        	listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("ISSUESTOREROOM")+"' and sqn not in (select sqn from asset where sqn like 'LS%') and assetlevel='ASSET'" +
								"and assetnum not in (" + assetnums + ") and assetnum not in (" + transferlineassetnums + ")";
					}else if(transferlineassetnums == null && assetnums != null){
						listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("ISSUESTOREROOM")+"' and sqn not in (select sqn from asset where sqn like 'LS%') and assetlevel='ASSET'" +
								"and assetnum not in (" + assetnums + ")";
					}else if(transferlineassetnums != null && assetnums == null){
						listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("ISSUESTOREROOM")+"' and sqn not in (select sqn from asset where sqn like 'LS%') and assetlevel='ASSET'" +
								"and assetnum not in (" + transferlineassetnums + ")";
					}else{
						listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("ISSUESTOREROOM")+"' and sqn not in (select sqn from asset where sqn like 'LS%') and assetlevel='ASSET'" +
								"and assetnum not in (select assetnum from transferline where assetnum is not null and status !='已接收')";
					}
				}
				if(this.getJpo().getParent().getString("type").equalsIgnoreCase("ZXD")){
//					String assetnums=CommAssetNum.GETASSETNUM(this.getJpo().getParent().getJpoSet("transferline"));
					String assetnums=null;
			        IJpoSet transferlineset=MroServer.getMroServer().getJpoSet("transferline", MroServer.getMroServer().getSystemUserServer());
			        transferlineset.setUserWhere("status not in ('已接收') and transfernum in (select transfernum from transfer where type='ZXD') and itemnum='"+itemnum+"'");
			        transferlineset.reset();
			        String transferlineassetnums = null;
			        if (!transferlineset.isEmpty())
			        {
			            for (int i = 0; i < transferlineset.count(); i++)
			            {
			            	if(transferlineset.getJpo(i)==null){
			            		continue;
			            	}
			                String transferlineassetnum = transferlineset.getJpo(i).getString("assetnum");
			                if (!"".equals(transferlineassetnum))
			                {
			                    if (transferlineassetnums == null)
			                    	transferlineassetnums = "'" + StringUtil.getSafeSqlStr(transferlineassetnum) + "'";
			                    else
			                    	transferlineassetnums = transferlineassetnums + ",'" + StringUtil.getSafeSqlStr(transferlineassetnum) + "'";
			                }
			            }
			        }
			        IJpoSet jkdtransferlineset=MroServer.getMroServer().getJpoSet("transferline", MroServer.getMroServer().getSystemUserServer());
			        jkdtransferlineset.setUserWhere("transfernum in (select transfernum from transfer where type='JKD') and itemnum='"+itemnum+"' and transferlineid not in (select jkdlineid from transferline where jkdlineid is not null)");
			        jkdtransferlineset.reset();
			        String jkdtransferlineassetnums = null;
			        if (!jkdtransferlineset.isEmpty())
			        {
			            for (int i = 0; i < jkdtransferlineset.count(); i++)
			            {
			            	if(jkdtransferlineset.getJpo(i)==null){
			            		continue;
			            	}
			                String jkdtransferlineassetnum = jkdtransferlineset.getJpo(i).getString("assetnum");
			                if (!"".equals(jkdtransferlineassetnum))
			                {
			                    if (jkdtransferlineassetnums == null)
			                    	jkdtransferlineassetnums = "'" + StringUtil.getSafeSqlStr(jkdtransferlineassetnum) + "'";
			                    else
			                    	jkdtransferlineassetnums = jkdtransferlineassetnums + ",'" + StringUtil.getSafeSqlStr(jkdtransferlineassetnum) + "'";
			                }
			            }
			        }
					String sxtype=this.getJpo().getParent().getString("sxtype");
					if(!sxtype.isEmpty()){
						if(sxtype.equalsIgnoreCase("YXX")){
							if(transferlineassetnums != null && assetnums != null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and status!='故障' and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetnum not in (" + transferlineassetnums + ") and assetnum not in (" + jkdtransferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums == null && assetnums != null  && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and status!='故障' and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetnum not in (" + jkdtransferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums == null  && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and status!='故障' and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + transferlineassetnums + ") " +
												"and assetnum not in (" + jkdtransferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums != null && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and status!='故障' and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetnum not in (" + transferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums == null && assetnums != null  && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and status!='故障' and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												" and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums == null  && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and status!='故障' and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + transferlineassetnums + ") " +
												" and assetlevel='ASSET'";
							}
							else if(transferlineassetnums == null && assetnums == null  && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and status!='故障' and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + jkdtransferlineassetnums + ") " +
												" and assetlevel='ASSET'";
							}
							else{
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and status!='故障' and sqn not in (select sqn from asset where sqn like 'LS%') and assetlevel='ASSET'";
							}
						}else{
							if(transferlineassetnums != null && assetnums != null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetnum not in (" + transferlineassetnums + ") and assetnum not in (" + jkdtransferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums == null && assetnums != null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												" and assetnum not in (" + jkdtransferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums == null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + transferlineassetnums + ") " +
												" and assetnum not in (" + jkdtransferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums != null && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												" and assetnum not in (" + transferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums == null && assetnums != null && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												" and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums == null && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + transferlineassetnums + ") " +
												" and assetlevel='ASSET'";
							}
							else if(transferlineassetnums == null && assetnums == null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + jkdtransferlineassetnums + ") " +
												" and assetlevel='ASSET'";
							}
							else{
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and sqn not in (select sqn from asset where sqn like 'LS%') and assetlevel='ASSET'";
							}
						}
					}else{
						if(this.getJpo().getString("ISSUESTOREROOM").equalsIgnoreCase("QT2054")||this.getJpo().getString("ISSUESTOREROOM").equalsIgnoreCase("Y1711")||this.getJpo().getString("RECEIVESTOREROOM").equalsIgnoreCase("QT2054")||this.getJpo().getString("RECEIVESTOREROOM").equalsIgnoreCase("Y1711")){
							if(transferlineassetnums != null && assetnums != null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"'" +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetnum not in (" + jkdtransferlineassetnums + ") and assetnum not in (" + transferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums == null && assetnums != null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"'" +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetnum not in (" + jkdtransferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums == null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"'" +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + transferlineassetnums + ") " +
												"and assetnum not in (" + jkdtransferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums != null && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"'" +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetnum not in (" + transferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums == null && assetnums != null && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"'" +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums == null && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"'" +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + transferlineassetnums + ") " +
												"and assetlevel='ASSET'";
							}
							else if(transferlineassetnums == null && assetnums == null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"'" +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + jkdtransferlineassetnums + ") " +
												"and assetlevel='ASSET'";
							}
							else{
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and sqn not in (select sqn from asset where sqn like 'LS%') and assetlevel='ASSET'";
							}
						}else{
							if(transferlineassetnums != null && assetnums != null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and status!='故障' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetnum not in (" + jkdtransferlineassetnums + ") and assetnum not in (" + transferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums == null && assetnums != null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and status!='故障' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetnum not in (" + jkdtransferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums == null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and status!='故障' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + transferlineassetnums + ") " +
												"and assetnum not in (" + jkdtransferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums != null && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and status!='故障' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetnum not in (" + transferlineassetnums + ") and assetlevel='ASSET'";
							}else if(transferlineassetnums == null && assetnums != null && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and status!='故障' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") " +
												"and assetlevel='ASSET'";
							}else if(transferlineassetnums != null && assetnums == null && jkdtransferlineassetnums==null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and status!='故障' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + transferlineassetnums + ") " +
												"and assetlevel='ASSET'";
							}
							else if(transferlineassetnums == null && assetnums == null && jkdtransferlineassetnums!=null){
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and status!='故障' " +
										"and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + jkdtransferlineassetnums + ") " +
												"and assetlevel='ASSET'";
							}
							else{
								listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getString("ISSUESTOREROOM")+"' and status!='故障' and sqn not in (select sqn from asset where sqn like 'LS%') and assetlevel='ASSET'";
							}
						}
					}
					
				}
				if(this.getJpo().getParent().getString("type").equalsIgnoreCase("GZZXD")){
					IJpoSet thisSet = this.getJpo().getJpoSet();
			        String assetnums = null;
			        if (!thisSet.isEmpty())
			        {
			            for (int i = 0; i < thisSet.count(); i++)
			            {
			            	if(thisSet.getJpo(i)==null){
			            		continue;
			            	}
			                String assetnum = thisSet.getJpo(i).getString("assetnum");
			                if (!"".equals(assetnum))
			                {
			                    if (assetnums == null)
			                    	assetnums = "'" + StringUtil.getSafeSqlStr(assetnum) + "'";
			                    else
			                    	assetnums = assetnums + ",'" + StringUtil.getSafeSqlStr(assetnum) + "'";
			                }
			            }
			        }
			        IJpoSet transferlineset=MroServer.getMroServer().getJpoSet("transferline", MroServer.getMroServer().getSystemUserServer());
			        transferlineset.setUserWhere("status not in ('已接收') and transfernum in (select transfernum from transfer where type='GZZXD')");
			        transferlineset.reset();
			        String transferlineassetnums = null;
			        if (!transferlineset.isEmpty())
			        {
			            for (int i = 0; i < transferlineset.count(); i++)
			            {
			            	if(transferlineset.getJpo(i)==null){
			            		continue;
			            	}
			                String transferlineassetnum = transferlineset.getJpo(i).getString("assetnum");
			                if (!"".equals(transferlineassetnum))
			                {
			                    if (transferlineassetnums == null)
			                    	transferlineassetnums = "'" + StringUtil.getSafeSqlStr(transferlineassetnum) + "'";
			                    else
			                    	transferlineassetnums = transferlineassetnums + ",'" + StringUtil.getSafeSqlStr(transferlineassetnum) + "'";
			                }
			            }
			        }
					
					if(transferlineassetnums != null && assetnums != null){
						listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("ISSUESTOREROOM")+"'" +
								" and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") and assetnum not in (" + transferlineassetnums + ") and assetlevel='ASSET'";
					}else if(transferlineassetnums == null && assetnums != null){
						listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("ISSUESTOREROOM")+"'" +
								" and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + assetnums + ") and assetlevel='ASSET'";
					}else if(transferlineassetnums != null && assetnums == null){
						listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("ISSUESTOREROOM")+"'" +
								" and sqn not in (select sqn from asset where sqn like 'LS%') and assetnum not in (" + transferlineassetnums + ") and assetlevel='ASSET'";
					}else{
						listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("ISSUESTOREROOM")+"' and sqn not in (select sqn from asset where sqn like 'LS%') and assetlevel='ASSET'";
					}
				}
				if(this.getJpo().getParent().getString("type").equalsIgnoreCase("JKD")){
					String assetnums=CommAssetNum.GETASSETNUM(this.getJpo().getParent().getJpoSet("transferline"));
			        IJpoSet transferlineset=MroServer.getMroServer().getJpoSet("transferline", MroServer.getMroServer().getSystemUserServer());
			        transferlineset.setUserWhere("status not in ('已接收') and transfernum in (select transfernum from transfer where type='JKD') and itemnum='"+itemnum+"'");
			        transferlineset.reset();
			        String transferlineassetnums = null;
			        if (!transferlineset.isEmpty())
			        {
			            for (int i = 0; i < transferlineset.count(); i++)
			            {
			            	if(transferlineset.getJpo(i)==null){
			            		continue;
			            	}
			                String transferlineassetnum = transferlineset.getJpo(i).getString("assetnum");
			                if (!"".equals(transferlineassetnum))
			                {
			                    if (transferlineassetnums == null)
			                    	transferlineassetnums = "'" + StringUtil.getSafeSqlStr(transferlineassetnum) + "'";
			                    else
			                    	transferlineassetnums = transferlineassetnums + ",'" + StringUtil.getSafeSqlStr(transferlineassetnum) + "'";
			                }
			            }
			        }
			        if(transferlineassetnums != null && assetnums != null){
			        	listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("receivestoreroom")+"' and assetlevel='ASSET'" +
								"and assetnum not in (" + assetnums + ") and assetnum not in (" + transferlineassetnums + ")";
					}else if(transferlineassetnums == null && assetnums != null){
						listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("receivestoreroom")+"' and assetlevel='ASSET'" +
								"and assetnum not in (" + assetnums + ")";
					}else if(transferlineassetnums != null && assetnums == null){
						listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("receivestoreroom")+"' and assetlevel='ASSET'" +
								"and assetnum not in (" + transferlineassetnums + ")";
					}else{
						listSql = " itemnum='" + itemnum + "' and location='"+this.getJpo().getParent().getString("receivestoreroom")+"' and assetlevel='ASSET'" +
								"and assetnum not in (select assetnum from transferline where assetnum is not null and status !='已接收')";
					}
				}
			} else {
				listSql = " assetnum is null ";
			}
			setListWhere(listSql);
		}

		return super.getList();
	}
/**
 * 触发赋值assetnum字段，数量字段
 * @throws MroException
 */
	@Override
	public void action() throws MroException {
		// TODO Auto-generated method stub
		super.action();
//
		String sqn = this.getValue();
		String itemnum = this.getJpo().getString("itemnum");
		String issuestoreroom = this.getJpo().getString("issuestoreroom");
		String receivestoreroom = this.getJpo().getString("receivestoreroom");
		String type=this.getJpo().getParent().getString("type");
			IJpoSet assetSet = MroServer.getMroServer().getJpoSet("asset", MroServer.getMroServer().getSystemUserServer());
			if(type.equalsIgnoreCase("JKD")){
				assetSet.setUserWhere("sqn='" + sqn + "' and itemnum='"+itemnum+"' and location='"+receivestoreroom+"'");
			}else{
				assetSet.setUserWhere("sqn='" + sqn + "' and itemnum='"+itemnum+"' and location='"+issuestoreroom+"'");
			}
			assetSet.reset();
			if (!assetSet.isEmpty()) {
				String assetnum = assetSet.getJpo(0).getString("assetnum");
				this.getJpo().setValue("assetnum", assetnum, GWConstant.P_NOVALIDATION_AND_NOACTION);

			}
			this.getJpo().setValue("ORDERQTY", 1, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
			this.getJpo().setFieldFlag("ORDERQTY", GWConstant.S_READONLY, true);
	}
/**
 * 如果是装箱单则校验序列号
 * @throws MroException
 */
	@Override
	protected void validateList() throws MroException {
		// TODO Auto-generated method stub
		if (getJpo() != null && !StringUtil.isStrEmpty(this.getJpo().getAppName())) {
			if(this.getJpo().getAppName().equalsIgnoreCase("ZXTRANSFER")){
				super.validateList();
			}
		}
		
	}



}

