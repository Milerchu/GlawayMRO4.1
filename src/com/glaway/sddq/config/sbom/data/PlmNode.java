package com.glaway.sddq.config.sbom.data;
/**
 * 
 * PLM传递节点信息
 * 
 * @author  hyhe
 * @version  [版本号, 2018-3-15]
 * @since  [产品/模块版本]
 */
public class PlmNode
{
    /**
     * 唯一编码
     */
    public String assettmpnum;
    
    /**
     * 祖先编码
     */
    public String ancestor;
    
    /**
     * 物料编码
     */
    public String itemnum;
    
    /**
     * 计量单位
     */
    public String orderunit;
    
    /**
     * 数量
     */
    public String amount;
    
    /**
     * 行号
     */
    public String linenum;
    
    /**
     * 位号
     */
    public String rownum;
    
    /**
     * 父级节点
     */
    public String parent;
    
    /**
     * 版本号
     */
    public String version;
    
    /**
     * 类型：系统、独立立项部件、部件、独立立项单板、单板、独立立项软件、软件（根节点属性）
     */
    public String type;
    
    /**
     * SRU/LRU
     */
    public String sltype;
    
    public String memo;
    
    public String getItemnum()
    {
        return itemnum;
    }
    
    public void setItemnum(String itemnum)
    {
        this.itemnum = itemnum;
    }
    
    public String getLinenum()
    {
        return linenum;
    }
    
    public void setLinenum(String linenum)
    {
        this.linenum = linenum;
    }
    
    public String getRownum()
    {
        return rownum;
    }
    
    public void setRownum(String rownum)
    {
        this.rownum = rownum;
    }
    
    public String getParent()
    {
        return parent;
    }
    
    public void setParent(String parent)
    {
        this.parent = parent;
    }
    
    public String getVersion()
    {
        return version;
    }
    
    public void setVersion(String version)
    {
        this.version = version;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getSltype()
    {
        return sltype;
    }
    
    public void setSltype(String sltype)
    {
        this.sltype = sltype;
    }
    
    public String getMemo()
    {
        return memo;
    }
    
    public void setMemo(String memo)
    {
        this.memo = memo;
    }
    
    public String getAmount()
    {
        return amount;
    }
    
    public void setAmount(String amount)
    {
        this.amount = amount;
    }
    
    public String getAssettmpnum()
    {
        return assettmpnum;
    }
    
    public void setAssettmpnum(String assettmpnum)
    {
        this.assettmpnum = assettmpnum;
    }
    
    public String getOrderunit()
    {
        return orderunit;
    }
    
    public void setOrderunit(String orderunit)
    {
        this.orderunit = orderunit;
    }

    public String getAncestor()
    {
        return ancestor;
    }

    public void setAncestor(String ancestor)
    {
        this.ancestor = ancestor;
    }
    
}
