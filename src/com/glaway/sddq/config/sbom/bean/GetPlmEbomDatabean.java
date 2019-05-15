/*
 * 文 件 名:  GetPlmEbomAppbean.java
 * 版    权:  Glaway Technologies Co., Ltd. Copyright 2010-2016,  All rights reserved
 * 描    述:  MRO传递ITEMNUM参数，获取完整EBOM数据，在MRO系统解析自动构建SBOM数据
 * 修 改 人:  hyhe
 * 修改时间:  2018-3-13
 */
package com.glaway.sddq.config.sbom.bean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.glaway.mro.controller.DataBean;
import com.glaway.mro.exception.AppException;
import com.glaway.mro.exception.MroException;
import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.IJpoSet;
import com.glaway.mro.util.GWConstant;
import com.glaway.mro.util.StringUtil;
import com.glaway.sddq.config.sbom.data.PlmNode;
import com.glaway.sddq.tools.IFUtil;
import com.glaway.sddq.tools.ItemUtil;

/**
 * MRO传递ITEMNUM参数，获取完整EBOM数据，在MRO系统解析自动构建SBOM数据
 * 
 * @author  hyhe
 * @version  [版本号, 2018-3-13]
 * @since  [产品/模块版本]
 */
public class GetPlmEbomDatabean extends DataBean
{
    @Override
    public int execute()
        throws MroException, IOException
    {
        String itemnum = this.getJpo().getString("NEWITEMNUM");
        //首先校验在MRO系统中是否存在该ITEMNUM的产品SBOM,如果已经存在，则弹出提示信息，如果不存在，则调用PLM的接口获取该物料的EBOM数据，然后解析构建SBOM
        if (ItemUtil.isExistItemNum(itemnum))
        {
            //弹出已经存在的提示消息
            throw new AppException("assettmp", "isExist");
        }
        else
        {
            //调用PLM系统接口,获取XML,并保留到中间表中
            
            String data = "";
            String num = IFUtil.addIfHistory("", data, IFUtil.TYPE_INPUT);
            try
            {
                //                File file = new File("E:/xml/TE6395000000-001.xml");
                //                SAXReader reader = new SAXReader();
                //                Document doc = reader.read(file);
                Document doc = DocumentHelper.parseText(data);
                Element root = doc.getRootElement();
                listRootChildNodes(root);
                IFUtil.updateIfHistory(num, IFUtil.STATUS_SUCCESS, IFUtil.FLAG_YES, "");
            }
            catch (Exception e)
            {
                IFUtil.updateIfHistory(num, IFUtil.STATUS_FAILURE, IFUtil.FLAG_YES, e.getMessage());
                e.printStackTrace();
            }
        }
        return GWConstant.NOACCESS_SAMEMETHOD;
    }
    
    @SuppressWarnings("unchecked")
    public void listRootChildNodes(Element root)
        throws MroException
    {
        //所有的节点
        List<PlmNode> plmNodeList = new ArrayList<PlmNode>();
        Map<String, PlmNode> dataMap = new HashMap<String, PlmNode>();
        String rootItemnum = root.attributeValue("itemnum");
        
        IJpoSet roatJposet = this.getMroSession().getUserServer().getJpoSet("ASSETTMP", "1=2");
        IJpo ancestorJpo = roatJposet.addJpo();
        String ancestor = ancestorJpo.getString("ASSETTMPNUM");
        ancestorJpo.setValue("ASSETTMPLEVEL", "ASSET");
        ancestorJpo.setValue("ANCESTOR", ancestor);
        ancestorJpo.setValue("ITEMNUM", rootItemnum);
        
        //根节点下第一层节点
        List<Element> childElemnets = root.elements();
        int num = 0;
        for (Element e : childElemnets)
        {
            listChildNodes(e, ancestor, ancestor, plmNodeList, dataMap, num);
        }
        
        //批次号追溯
        IJpoSet lotJposet = this.getMroSession().getUserServer().getJpoSet("ASSETMODELPART", "1=2");
        
        toMroJpoSet(plmNodeList, roatJposet, lotJposet);
        roatJposet.save();
        lotJposet.save();
    }
    
    public void toMroJpoSet(List<PlmNode> plmNodeList, IJpoSet roatJposet, IJpoSet lotJposet)
        throws MroException
    {
        if (plmNodeList.size() > 0)
        {
            for (int index = 0; index < plmNodeList.size(); index++)
            {
                PlmNode node = plmNodeList.get(index);
                //在这里判断是否是序列号追溯或者批次号追溯
                String itemnum = node.getItemnum();
                if (getItemRoat(itemnum))
                {
                    String orderunit = node.getOrderunit();
                    if ("个".equals(orderunit))
                    {
                        int amount = Integer.valueOf(node.getAmount());
                        if (amount > 1)
                        {
                            for (int i = 0; i < amount; i++)
                            {
                                addRoatJpo(roatJposet, node);
                            }
                        }
                    }
                    else
                    {
                        addRoatJpo(roatJposet, node);
                    }
                }
                else
                {
                    //待确认下是否除了序列号追溯的都要放入备件中，还是只放入批次号追溯的
                    if (getItemLotType(itemnum))
                    {
                        addLotJpo(lotJposet, node);
                    }
                }
            }
        }
    }
    
    /**
     * 
     * 新增序列号追溯节点
     * @param jposet
     * @param node
     * @throws MroException [参数说明]
     *
     */
    public void addRoatJpo(IJpoSet jposet, PlmNode node)
        throws MroException
    {
        IJpo jpo = jposet.addJpo();
        jpo.setValue("ASSETTMPLEVEL", "SYSTEM");
        jpo.setValue("ANCESTOR", node.getAncestor());
        jpo.setValue("assettmpnum", node.getAssettmpnum());
        jpo.setValue("parent", node.getParent());
        jpo.setValue("ITEMNUM", node.getItemnum());
        jpo.setValue("orderunit", node.getOrderunit());
        jpo.setValue("linenum", node.getLinenum());
        jpo.setValue("rownum", node.getRownum());
        jpo.setValue("version", node.getVersion());
    }
    
    /**
     * 
     * 新增批次号追溯节点
     * @param lotjposet
     * @param node
     * @throws MroException [参数说明]
     *
     */
    public void addLotJpo(IJpoSet lotjposet, PlmNode node)
        throws MroException
    {
        IJpo jpo = lotjposet.addJpo();
        jpo.setValue("assetnum", node.getParent());
        jpo.setValue("itemnum", node.getItemnum());
        jpo.setValue("QTY", node.getAmount());
        jpo.setValue("datatype", "1");
    }
    
    @SuppressWarnings("unchecked")
    public void listChildNodes(Element element, String parent, String ancestor, List<PlmNode> plmNodeList,
        Map<String, PlmNode> dataMap, int num)
        throws MroException
    {
        String itemnum = element.attributeValue("itemnum");
        PlmNode node = new PlmNode();
        node.setItemnum(itemnum);
        String assettmpnum = getAssettmpnum(ancestor, num);
        node.setAssettmpnum(assettmpnum);
        node.setParent(parent);
        
        //获取节点的元素信息内容
        node.setAmount(element.elementText("amount"));
        dataMap.put("assettmpnum", node);
        plmNodeList.add(node);
        Element nodeElemnets = element.element("nodes");
        if (nodeElemnets != null)
        {
            //含有子节点
            List<Element> childElemnets = nodeElemnets.elements();
            if(childElemnets.size() > 0){
                for (Element e : childElemnets)
                {
                    listChildNodes(e, assettmpnum, ancestor, plmNodeList, dataMap, num);
                }
            }
        }
        else
        {
            return;
        }
    }
    
    public String getAssettmpnum(String ancestor, int num)
    {
        num = num + 1;
        return ancestor + "." + num;
    }
    
    /**
     * 
     * 获取物料的SRU/LRU标记
     * @param itemnum
     * @return
     * @throws MroException [参数说明]
     *
     */
    public int getItemSruLru(String itemnum)
        throws MroException
    {
        int flag = 0;
        if (itemnum != null)
        {
            IJpoSet jposet = this.getMroSession().getUserServer().getJpoSet("SYS_ITEM", "itemnum='" + itemnum + "'");
            
            if (jposet.count() > 0)
            {
                IJpo item = jposet.getJpo(0);
                //判断是不是LRU/SRU
                if (StringUtil.isStrNotEmpty(item.getString("")))
                {
                    if (item.getString("").equals("SRU"))
                    {
                        flag = 2;
                    }
                    else
                    {
                        flag = 3;
                    }
                }
                else
                {
                    flag = 1;
                }
            }
        }
        return flag;
    }
    
    /**
     * 
     * 获取序列号跟踪属性标记
     * @param itemnum
     * @return
     * @throws MroException [参数说明]
     *
     */
    public boolean getItemRoat(String itemnum)
        throws MroException
    {
        boolean flag = false;
        IJpoSet jposet = this.getMroSession().getUserServer().getJpoSet("SYS_ITEM", "itemnum='" + itemnum + "'");
        
        if (jposet.count() > 0)
        {
            IJpo item = jposet.getJpo(0);
            flag = item.getBoolean("ROTATING");
        }
        return flag;
    }
    
    /**
     * 
     * 获取批次类型
     * @param itemnum
     * @return
     * @throws MroException [参数说明]
     *
     */
    public boolean getItemLotType(String itemnum)
        throws MroException
    {
        boolean flag = false;
        IJpoSet jposet = this.getMroSession().getUserServer().getJpoSet("SYS_ITEM", "itemnum='" + itemnum + "'");
        
        if (jposet.count() > 0)
        {
            IJpo item = jposet.getJpo(0);
            String lottype = item.getString("LOTTYPE");
            if ("批次".equals(lottype))
            {
                flag = true;
            }
        }
        return flag;
    }
}
