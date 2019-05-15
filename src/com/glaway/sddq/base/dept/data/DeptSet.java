package com.glaway.sddq.base.dept.data;

import com.glaway.mro.jpo.IJpo;
import com.glaway.mro.jpo.JpoSet;
/**
 * 
 * <功能描述>
 * 表SYS_DEPT的数据集处理类
 * @author  jxiang
 * @version  [版本号, 2016-4-19]
 * @since  [产品/模块版本]
 */
public class DeptSet extends JpoSet
{

    @Override
    public Dept getJpoInstance()
    {
        return new Dept();
    }
    
    
    
    

    @Override
    public IJpo getParent()
    {
        // TODO Auto-generated method stub
        return super.getParent();
    }
    
    
    
}
