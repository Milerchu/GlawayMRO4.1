package com.glaway.sddq.config.zcconfig.bean;

import io.netty.util.internal.StringUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.impl.httpclient4.HttpTransportPropertiesImpl.Authenticator;
import org.apache.commons.lang.StringUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.system.MroServer;
import com.glaway.mro.system.crontask.BaseStatefulJob;
import com.glaway.mro.util.GWConstant;


/*正式接口调用*/
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.Char20;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.TableOfZfracaszppz;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.Zfracaszppz;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.ZtfunFracasCppz;
import com.glaway.sddq.back.Interface.webservice.erp.zsxt.erptomrozsxt.hqccpz.ComZzsErpZTFUN_FRACAS_CPPZStub.ZtfunFracasCppzResponse;
/*正式接口调用*/
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;
import com.glaway.sddq.tools.SddqConstant;

/**
 * @ClassName GetErpBbomCronTask
 * @Description 定时任务去ERP获取出厂配置数据
 * @author public2175
 * @Date 2018-7-30 下午3:49:20
 * @version 1.0.0
 */
public class GetErpBbomCronTask extends BaseStatefulJob {

    private static final long serialVersionUID = 1L;

    Map<String, String> parentMap = null;// 存放产品序列号与assetnum的Map
    Map<String, String> treeMap = null;// 存放aseetnum与parent的Map
    JSONArray noparentArray = null;// 存放暂时找不到parent的Map
    Set<JSONObject> noItemnumSet = null;// 存放暂时找不到item的Map
    JSONArray I_ITEM_ARRAY = null;// 存放I类耗损件数据
    JSONArray II_ITEM_ARRAY = null;// 存放II类耗损件数据
    Map<String, String> sqnItemnumMap = null;// 存放产品序列号与itemnum的MAP,可以通过产品序列号快速找到对应的itemnum
    Map<String, JSONObject> sqnItemMap = null;// 存放产品序列号与对应数据的MAP,可以通过产品序列号快速找到对应的ITE

    @Override
    public void execute() throws MroException {
        // 1、首先去ASSET表中去获取没有获取过出厂配置的数据
        IJpoSet assetJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET");
        assetJpoSet.setUserWhere("assetlevel='ASSET' and (type='0' or type='1') and iserp = '0'");
        assetJpoSet.reset();

        // 2、查找到数据之后，循环去ERP获取出厂配置数据
        if (assetJpoSet != null && assetJpoSet.count() > 0) {
            for (int index = 0; index < assetJpoSet.count(); index++) {
                IJpo asset = assetJpoSet.getJpo(index);
                if (asset != null) {
                    String sqn = asset.getString("sqn");
                    if (!StringUtil.isNullOrEmpty(sqn)) {
                        getErpBomByJpo(asset);
                    }
                }
            }
        }
    }

    /**
     * @Description 获取数据
     * @param jpo
     * @throws MroException
     */
    public void getErpBomByJpo(IJpo jpo) {
    	  String num = "";
    	 try {
        parentMap = new HashMap<String, String>();// 存放产品序列号与assetnum的Map
        treeMap = new HashMap<String, String>();// 存放aseetnum与parent的Map
        noparentArray = new JSONArray();// 存放暂时找不到parent的Map
        noItemnumSet = new HashSet<JSONObject>();// 存放暂时找不到item的Map
        I_ITEM_ARRAY = new JSONArray();// 存放I类耗损件数据
        II_ITEM_ARRAY = new JSONArray();// 存放II类耗损件数据
        sqnItemnumMap = new HashMap<String, String>();// 存放产品序列号与itemnum的MAP,可以通过产品序列号快速找到对应的itemnum
        sqnItemMap = new HashMap<String, JSONObject>();// 存放产品序列号与对应数据的MAP,可以通过产品序列号快速找到对应的ITEM

        // 测试数据：500081110C
        String ancestor = jpo.getString("ancestor");
        String locsqn = jpo.getString("sqn");
        // String sqn = "500081110C";
//        System.out.print(locsqn);
            ComZzsErpZTFUN_FRACAS_CPPZStub service = new ComZzsErpZTFUN_FRACAS_CPPZStub();
            // 认证代码 start
            Authenticator auth = new Authenticator();
            String user = IFUtil.getIfServiceInfo("erp.user");
            String pwd = IFUtil.getIfServiceInfo("erp.pwd");
            auth.setUsername(user);
            auth.setPassword(pwd);
            service._getServiceClient().getOptions().setProperty(HTTPConstants.AUTHENTICATE, auth);
            // 认证代码 end
            ZtfunFracasCppz params = new ZtfunFracasCppz();
            Char20 sqnparam = new Char20();
            String sqn = locsqn.toUpperCase();
            sqnparam.setChar20(sqn);
            TableOfZfracaszppz inputtable = new TableOfZfracaszppz();
            params.setTZfracaszppz(inputtable);
            params.setZbarn(sqnparam);
            num = IFUtil.addIfHistory(IFUtil.CCPZ, sqn, IFUtil.TYPE_OUTPUT);// 增加输出记录
            ZtfunFracasCppzResponse re = service.ztfunFracasCppz(params);
            TableOfZfracaszppz table = re.getTZfracaszppz();
            Zfracaszppz[] zppz = table.getItem();
            if (zppz != null && zppz.length > 0) {
                IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "获取出厂配置的序列号为:" + sqn
                        + ";ERP返回数据结果共" + zppz.length + "条;");
                JSONObject jobject = new JSONObject();
                JSONArray jArray = new JSONArray();
                for (int index = 0; index < zppz.length; index++) {
                    JSONObject rdata = new JSONObject();
                    String parentItemnum = zppz[index].getMatnr().toString();// 父项物料编码
                    String parentsqn = zppz[index].getZbarn().toString();// 父项序列号
                    String itemnum = zppz[index].getZmatnr().toString();// 组件物料编码
                    String itemsqn = zppz[index].getSbarn().toString();// 组件序列号,经确认,该字段目前不会为空
                    String itemdesc = zppz[index].getMaktx().toString();// 组件物料描述
                    String version = zppz[index].getVersion().toString();// 软件版本
                    String zplace = zppz[index].getZplace().toString();// 位置号
                    String zaufnr = zppz[index].getZaufnr().toString();// 生产订单号
                    String zdate = zppz[index].getZdate().toString();// 导入日期
                    String ztime = zppz[index].getZtime().toString();// 导入时间
                    String lifnr = zppz[index].getLifnr().toString();// 供应商帐号
                    String name = zppz[index].getName1().toString();// 供应商名称
                    String zgys = zppz[index].getZgys().toString();// 供应商序列号
                    String bname = zppz[index].getBname().toString();// 记录人信息
                    String dispo = zppz[index].getDispo().toString();// MRP控制者
                    String dsnam = zppz[index].getDsnam().toString();// 控制者名称
                    String fevor = zppz[index].getFevor().toString();// 生产调度员
                    String txt = zppz[index].getTxt().toString();// 生产调度员
                    rdata.put("parentItemnum", parentItemnum);
                    rdata.put("parentsqn", parentsqn);
                    rdata.put("itemnum", itemnum);
                    rdata.put("itemsqn", itemsqn);
                    rdata.put("itemdesc", itemdesc);
                    rdata.put("version", version);
                    rdata.put("zplace", zplace);
                    rdata.put("zaufnr", zaufnr);
                    rdata.put("zdate", zdate);
                    rdata.put("ztime", ztime);
                    rdata.put("lifnr", lifnr);
                    rdata.put("name", name);
                    rdata.put("zgys", zgys);
                    rdata.put("bname", bname);
                    rdata.put("dispo", dispo);
                    rdata.put("dsnam", dsnam);
                    rdata.put("fevor", fevor);
                    rdata.put("txt", txt);

                    jArray.put(rdata);
                    // System.out.println("父项物料编码 :"+parentItemnum+" 父项序列号:"+parentsqn+"  组件物料编码:"+itemnum+
                    // " 组件序列号:"+itemsqn+" 组件物料描述:"+itemdesc+" 软件版本:"+version+" 位置号:"+zplace);
                }
                jobject.put("sqn", sqn);
                jobject.put("ancestor", ancestor);
                jobject.put("data", jArray);
                // 处理获取的配置信息数据
                dealErpData(jobject, jpo);
            } else {
                IFUtil.addDataHistory(jpo.getString("ancestor"), "获取出厂配置的序列号为:" + sqn + ";返回数据结果为空", "2");
                IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "获取出厂配置的序列号为:" + sqn + ";返回数据结果为空");
                IJpoSet myassetJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET");
                myassetJpoSet.setUserWhere("assetnum='" + jpo.getString("assetnum") + "'  and assetlevel='ASSET' and iserp = '0'");
                myassetJpoSet.reset();
                if (myassetJpoSet != null && myassetJpoSet.count() > 0) {
                    myassetJpoSet.getJpo(0).setValue("MSGFLAG", SddqConstant.MSG_INFO_NOCHILREN);
                    myassetJpoSet.getJpo(0).setValue("ISERP", "1", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    myassetJpoSet.save();
                }
            }
        } catch (Exception e) {
            try {
				IFUtil.addDataHistory(jpo.getString("ancestor"), e.getMessage(), "2");
	            IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, e.getMessage());
			} catch (MroException e1) {
				e1.printStackTrace();
			}
        }
    }

    /**
     * 处理从ERP获取的数据
     * 
     * @param data
     * @throws Exception
     * @throws MroException
     *             [参数说明]
     * @throws Exception
     */
    public void dealErpData(JSONObject data, IJpo ancetorjpo) throws MroException {
        String inputnum = "";
        MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultOrg("CRRC");
        MroServer.getMroServer().getSystemUserServer().getUserInfo().setDefaultSite("ELEC");
        IJpoSet myassetJpoSet = MroServer.getMroServer().getSysJpoSet("ASSET");
        myassetJpoSet.setQueryWhere("assetnum='" + ancetorjpo.getString("assetnum") + "' and assetlevel='ASSET' and iserp = '0'");
        myassetJpoSet.reset();
        IJpo ancestorJpo = myassetJpoSet.getJpo(0);
        try {
            if (ancestorJpo != null) {
            	  String assettype = "0";
                  if(!StringUtil.isNullOrEmpty(ancestorJpo.getString("type"))){
                  	assettype = ancestorJpo.getString("type");
                  }
                inputnum = IFUtil.addIfHistory(IFUtil.CCPZ, data.toString(), IFUtil.TYPE_INPUT);// 增加输入记录
                String sqn = data.getString("sqn");
                String ancestor = data.getString("ancestor");
                JSONArray treedata = data.getJSONArray("data");
                parentMap.put(sqn, ancestor);
                treeMap.put(ancestor, "");
                if (treedata.length() > 0) {
                    // 处理生成每一条数据，新增每一条记录时，都要去记录父级的assetnum
                    // 还需判断是否是I类批次件，如果是I类批次件，则需要则新增数据到ASSETPART表
                    IJpoSet jpoSet = ancestorJpo.getJpoSet("$systemChildren", "ASSET", "1=2");

                    for (int i = 0; i < treedata.length(); i++) {

                        JSONObject rdata = treedata.getJSONObject(i);
                        String parentsqn = rdata.getString("parentsqn");
                        String itemnum = rdata.getString("itemnum");
                        String itemsqn = rdata.getString("itemsqn");
                        String itemdesc = rdata.getString("itemdesc");
                        String version = rdata.getString("version");
                        String zplace = rdata.getString("zplace");

                        // 根据物料编码去判断物料是否是批次件追溯的，如果是序列号追溯的，则需新增数据到ASSET表中， 如果是批次件追溯的还需判断是否是I类件，如果是I类件，则新增数据到ASSETPART表
                        String type = ItemUtil.getItemInfo(itemnum);
                        if (ItemUtil.NO_ITEM.equals(type)) {
                            noItemnumSet.add(rdata);// 忽略该数据
                        } else if (ItemUtil.SQN_ITEM.equals(type)) {

                            IJpo jpo = jpoSet.addJpo();
                            String assetnum = jpo.getString("assetnum");
                            jpo.setValue("type", assettype, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            jpo.setValue("ancestor", ancestor, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            jpo.setValue("sqn", itemsqn, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            jpo.setValue("rnum", zplace, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            jpo.setValue("softversion", version, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            jpo.setValue("description", itemdesc, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            jpo.setValue("assetlevel", "SYSTEM", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            jpo.setValue("itemnum", itemnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);//
                            jpo.setValue("ISERP", "1", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            parentMap.put(itemsqn, assetnum);// 增一条记录，记住序列号与assetnum的关系，放到Map中
                            // 根据父级序列号,如果获得的父级序列号对应的assetnum不存在或者为空,则暂时将该数据存放在noparentMap中，最后循环一遍后，要重新处理noparentMap中的数据
                            if (StringUtils.isEmpty(parentMap.get(parentsqn))) {
                                JSONObject noparentdata = new JSONObject();
                                noparentdata.put("assetnum", assetnum);
                                noparentdata.put("parentsqn", parentsqn);
                                noparentArray.put(noparentdata);
                            } 
                            if(parentsqn != null && sqn != null && sqn.equals(parentsqn)){
                            	jpo.setValue("parent", ancestor, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                           	 	treeMap.put(assetnum, ancestor);// 为新增数据到assettree表中做准备
                            }else{
                            	jpo.setValue("parent", parentMap.get(parentsqn), GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            	 treeMap.put(assetnum, parentMap.get(parentsqn));// 为新增数据到assettree表中做准备
                            }

                        } else if (ItemUtil.LOT_I_ITEM.equals(type)) {
                            I_ITEM_ARRAY.put(rdata);
                            sqnItemnumMap.put(itemsqn, itemnum);
                            sqnItemMap.put(itemsqn, rdata);
                        } else {
                            II_ITEM_ARRAY.put(rdata);
                        }
                    }
                    // 循环结束之后，为第一次循环找不到父级的数据更新父级
                    for (int j = 0; j < noparentArray.length(); j++) {
                        JSONObject object = noparentArray.getJSONObject(j);
                        IJpo jpo = jpoSet.getJpoByValue("assetnum", object.getString("assetnum"));
                        String parent = parentMap.get(object.getString("parentsqn"));// 根据父级的产品序列号找到父级的assetnum
                        jpo.setValue("parent", parent, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                        treeMap.put(object.getString("assetnum"), parent);// 为新增数据到assettree表中做准备
                    }

                    // 循环结束之后，处理I类耗损件数据，这时候获取父级的数据才准确,如果是I类耗损件,则ERP传递过来的产品序列号字段存放的是批次号
                    if (I_ITEM_ARRAY.length() > 0) {

                        IJpoSet assetPartJpoSet = ancestorJpo.getJpoSet("$ASSETPART_I", "ASSETPART", "1=2");

                        for (int m = 0; m < I_ITEM_ARRAY.length(); m++) {

                            JSONObject object = I_ITEM_ARRAY.getJSONObject(m);
                            String parentsqn = object.getString("parentsqn");
                            String itemnum = object.getString("itemnum");
                            String itemsqn = object.getString("itemsqn");
                            String parentItemnum = object.getString("parentItemnum");
                            // String itemdesc = object.getString("itemdesc");
                            // String version = object.getString("version");
                            // String zplace = object.getString("zplace");
                            // 根据父级产品序列号去找批次号的父级
                            String parentAssetnum = getParent(parentsqn);
                            IJpo jpo = isExistAssetPart(itemnum, itemsqn, parentAssetnum);
                            if (jpo != null) {
                                double qty = jpo.getDouble("QTY");
                                double newQty = qty + 1;
                                jpo.setValue("QTY", newQty, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            } else {
                                IJpo assetpart = assetPartJpoSet.addJpo();
                                assetpart.setValue("itemnum", itemnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                assetpart.setValue("lotnum", itemsqn, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                assetpart.setValue("assetnum", parentAssetnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                assetpart.setValue("parentitemnum", parentItemnum,
                                        GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                assetpart.setValue("parentsqn", parentsqn, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                assetpart.setValue("QTY", 1, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            }
                        }
                    }
                    if (II_ITEM_ARRAY.length() > 0) {

                        IJpoSet assetPartJpoSet = ancestorJpo.getJpoSet("$ASSETPART_II", "ASSETPART", "1=2");

                        for (int m = 0; m < II_ITEM_ARRAY.length(); m++) {

                            JSONObject object = II_ITEM_ARRAY.getJSONObject(m);
                            String parentsqn = object.getString("parentsqn");
                            String itemnum = object.getString("itemnum");
                            String itemsqn = object.getString("itemsqn");
                            String parentItemnum = object.getString("parentItemnum");
                            // String itemdesc = object.getString("itemdesc");
                            // String version = object.getString("version");
                            // String zplace = object.getString("zplace");
                            // 根据父级产品序列号去找批次号的父级
                            String parentAssetnum = getParent(parentsqn);
                            IJpo jpo = isExistAssetPart(itemnum, itemsqn, parentAssetnum);
                            if (jpo != null) {
                                double qty = jpo.getDouble("QTY");
                                double newQty = qty + 1;
                                jpo.setValue("QTY", newQty, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            } else {
                                IJpo assetpart = assetPartJpoSet.addJpo();
                                assetpart.setValue("itemnum", itemnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                assetpart.setValue("lotnum", itemsqn, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                assetpart.setValue("assetnum", parentAssetnum, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                assetpart.setValue("QTY", 1, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                assetpart.setValue("PARENTITEMNUM", parentItemnum,
                                        GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                                assetpart.setValue("PARENTSQN", parentsqn, GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                            }
                        }
                    }
                    // this.SAVE();
                    // ArrayList<String> sqlList = new ArrayList<String>();
                    // String deleteSql =
                    // "delete from assettree where assetnum in (select assetnum from assettree where ancestor = '"+ancestor+"')";
                    // sqlList.add(deleteSql);
                    // //遍历treeMap,生成assettree表数据
                    // Iterator iter = treeMap.entrySet().iterator();
                    // while(iter.hasNext()){
                    // Entry entry = (Entry) iter.next();
                    // String assetnum = (String) entry.getKey();
                    // String parent = (String) entry.getValue();
                    // getInsertAncestorSql("ASSETTREE",sqlList,treeMap,assetnum,assetnum,ancestor,0);
                    // }
                    StringBuffer message = new StringBuffer();

                    if (noItemnumSet != null && noItemnumSet.size() > 0) {// 输出处理的信息
                        System.out.println("在MRO系统没有找到对应的物料编码:" + noItemnumSet.size());
                        message.append("在MRO系统没有找到对应的物料编码共" + noItemnumSet.size() + "种:");
                        Iterator<JSONObject> it = noItemnumSet.iterator();
                        while (it.hasNext()) {
                            JSONObject rdata = it.next();
                            String noitemnum = rdata.getString("itemnum");
                            message.append(noitemnum + ",");
                        }
                    }
                    if (II_ITEM_ARRAY.length() > 0) {// 输出处理的信息
                        System.out.println("II类耗损件" + II_ITEM_ARRAY.length());
                    }
                    message.append("获取的出厂数据共" + treedata.length() + "条;序列号件:" + jpoSet.count() + "条;I类耗损件:"
                            + I_ITEM_ARRAY.length() + "条;II类耗损件:" + II_ITEM_ARRAY.length() + "条;");
                    ancestorJpo.setValue("ISERP", "1", GWConstant.P_NOCHECK_NOACTION_NOVALIDAT);
                    IFUtil.addDataHistory(ancestor, message.toString(), "2");
                    myassetJpoSet.save();
                    // Connection con = DBManager.getDBManager().getConnection();
                    // DBManager.getDBManager().executeBatch(con, sqlList);
                    IFUtil.updateIfHistory(inputnum, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, message.toString());
                }
            }
        } catch (Exception e) {
            IFUtil.addDataHistory(ancestorJpo.getString("ancestor"), e.getMessage(), "2");
            IFUtil.updateIfHistory(inputnum, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * @Description 判断在某个父级节点下，某个物料编码、批次号的数据是否存在,如果不存在，则新增，如果存在，则更新数量+1
     * @param itemnum
     * @param lotnum
     * @param parent
     * @return
     * @throws MroException
     */
    public IJpo isExistAssetPart(String itemnum, String lotnum, String parent) throws MroException {

        IJpoSet assetPartJpoSet = MroServer.getMroServer().getSysJpoSet("ASSETPART",
                "itemnum='" + itemnum + "' and lotnum='" + lotnum + "' and assetnum='" + parent + "'");
        if (assetPartJpoSet != null && assetPartJpoSet.count() > 0) {
            return assetPartJpoSet.getJpo(0);
        }
        return null;
    }

    /**
     * 拼装成创建assettree表的sql语句
     * 
     * @param tbname
     * @param sqlList
     * @param ancestorMap
     * @param assetnum
     * @param parent
     * @param assetancestor
     * @param i
     * @return [参数说明]
     */
    public ArrayList<String> getInsertAncestorSql(String tbname, ArrayList<String> sqlList,
            Map<String, String> ancestorMap, String assetnum, String parent, String assetancestor, int i) {
        if (ancestorMap.containsKey(parent)) {
            String ancestor = (String) ancestorMap.get(parent);
            String insertSql = "insert into " + tbname.toUpperCase() + "(";
            insertSql = insertSql + "ASSETTREEID,ASSETNUM,ANCESTOR,SITEID,ORGID,HIERARCHYLEVELS)";
            insertSql = insertSql + " values (ASSETTREESEQ.NEXTVAL,'" + assetnum + "','" + parent + "','ELEC','CRRC',"
                    + i + ")";
            sqlList.add(insertSql);
            if (ancestor != null) {
                sqlList = getInsertAncestorSql(tbname, sqlList, ancestorMap, assetnum, ancestor, assetancestor, i + 1);
            }
        } else {
            if ((parent.equals(assetancestor))) {
                String insertSql = "insert into " + tbname.toUpperCase() + "(";
                insertSql = insertSql + "ASSETTREEID,ASSETNUM,ANCESTOR,SITEID,ORGID,HIERARCHYLEVELS)";
                insertSql = insertSql + " values (ASSETTREESEQ.NEXTVAL,'" + assetnum + "','" + parent
                        + "','ELEC','CRRC'," + i + ")";
                sqlList.add(insertSql);
            }
        }
        return sqlList;
    }

    /**
     * 获取批次号件的父级
     * 
     * @param parentsqn
     * @return
     * @throws Exception
     *             [参数说明]
     */
    public String getParent(String parentsqn) throws Exception {
        String parentItem = sqnItemnumMap.get(parentsqn);
        if (StringUtils.isNotEmpty(parentItem)) {
            JSONObject object = sqnItemMap.get(parentsqn);
            return getParent(object.getString("parentsqn"));
        } else {
            return parentMap.get(parentsqn);
        }
    }
}
