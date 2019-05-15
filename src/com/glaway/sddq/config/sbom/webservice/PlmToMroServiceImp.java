package com.glaway.sddq.config.sbom.webservice;

import java.util.Iterator;
import java.util.List;

import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Attribute;

@WebService(endpointInterface = "com.glaway.sddq.config.sbom.webservice.PlmToMroService")
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class PlmToMroServiceImp implements PlmToMroService
{
    @Override
    public String toMroEbomData(String itemnum, String ebomXml)
    {
        System.out.println("itemnum:" + itemnum + "\n ebomXml:" + ebomXml);
        try
        {
            Document document = DocumentHelper.parseText(ebomXml);
            //获取根节点元素对象
            Element root = document.getRootElement();
            //遍历
            listNodes(root);
        }
        catch (DocumentException e)
        {
            e.printStackTrace();
        }
        return "测试成功";
    }
    
    @SuppressWarnings("unchecked")
    private void listNodes(Element node)
    {
        System.out.println("当前节点的名称：" + node.getName());
        //获取当前节点的所有属性节点
        List<Attribute> list = node.attributes();
        for (Attribute attribute : list)
        {
            System.out.println("属性：" + attribute.getName() + ":" + attribute.getValue());
        }
        
        //如果当前节点内容不为空，则输出
        if (node.getTextTrim().equals(""))
        {
            System.out.println(node.getName() + ":" + node.getText());
        }
        
        //迭代当前节点下面的所有子节点
        Iterator<Element> iterator = node.elementIterator();
        while (iterator.hasNext())
        {
            Element e = iterator.next();
            listNodes(e);
        }
    }
}
