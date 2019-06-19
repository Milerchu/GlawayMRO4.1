package com.glaway.sddq.tools;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * JDBC工具类
 * @author Miller
 * @date 2019-06-12 17:12
 */
public class JDBCUtil {

    static Properties prop = null;

    static {//加载JDBCUtil类时调用
        prop = new Properties();
        try{
            prop.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("jpo.properties"));
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("数据库配置文件读取失败！");
        }

    }

    public static void main(String[] args) {

        String sql = "merge into sys_item item " +
              "using (select ? itemnum, ? des, ? itemtype, ? orderunit, ? SPEC," +
                "? itemgroup,? producter from dual) dat " +
                "on (dat.itemnum=item.itemnum)\n " +
                "when matched then\n" +
                " update set item.description=dat.des,item.SPECIFICATION=dat.SPEC," +
                "item.ORDERUNIT=dat.orderunit,item.ITEMGROUP=dat.itemgroup,item.ITEMTYPE=dat.itemtype," +
                "item.producter=dat.producter \n" +
                "when not matched then\n" +
                " insert (SYS_ITEMID,itemnum,description,HARDRESISSUE,ISKIT,ITEMTYPE,ORDERUNIT," +
                "OUTSIDE,PRORATE,ROTATING,SPECIFICATION,STATUS,Statusdate,CAPITALIZED,IMPORTANT," +
                "ISCHECK,ISTURNOVER,ITEMGROUP,PLUSCISINHOUSECAL,PLUSCSOLUTION,PRODUCTER,SPAREPARTAUTOADD," +
                "ISNEW,TOOL,ISLOT,IMPORTANTERP,ISLOTERP,ISTURNOVERERP,ISIV) \n" +
                " values(Sys_Itemseq.Nextval,dat.itemnum,dat.des,0,0,dat.itemtype,dat.orderunit," +
                "0,0,0,dat.SPEC,'活动',sysdate,0,0,0,0,dat.itemgroup,0,0,dat.producter,0,1,0,0,0,0,0,0)";

        Connection conn = null;
        PreparedStatement pstm = null;
//        ResultSet rs = null;
        try {
            conn = getOrclConn();
            pstm = conn.prepareStatement(sql);
            for(int idx = 0; idx < 2; idx++){

                pstm.setString(1, "b"+idx);
                pstm.setString(2, "C罗");
                pstm.setString(3, "cs");
                pstm.setString(4, "dd");
                pstm.setString(5, "ff");
                pstm.setString(6, "zz");
                pstm.setString(7, "A");
                pstm.addBatch();
            }
            int[] results = pstm.executeBatch();
            for(int i : results){
                if(i < 0 && i != PreparedStatement.SUCCESS_NO_INFO){

                }
            }
            //int rs = pstm.executeUpdate();

            System.out.println(results.toString());


        }catch(Exception e){
            e.printStackTrace();
        }finally {
            close(pstm, conn);
        }

    }

    /**
     * 判断是否有数据
     * @param sql
     * @return
     */
    public static boolean isHaveResult(String sql){
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        boolean ret = false;
        try {
            conn = getOrclConn();
            pstm = conn.prepareStatement(sql);
            rs = pstm.executeQuery();
            ret = rs.next();
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            close(rs, pstm, conn);
        }
        return ret;
    }

    public static ResultSet getResult(String sql) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;


        return rs;
    }

    public static boolean update(String tableName, String[] field, Object[] value) {
        //操作结果
        boolean result = false;
        //构建sql语句
        String sql = "";


        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conn = getOrclConn();
            pstm = conn.prepareStatement(sql);

        } catch (Exception e) {

        } finally {
            close(rs, pstm, conn);
        }
        return result;

    }

    /**
     * 获取oracle数据库连接
     * @return conn
     */
    public static Connection getOrclConn() throws Exception {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            return DriverManager.getConnection(prop.getProperty("c3p0.jdbcUrl"),
                prop.getProperty("c3p0.user"),prop.getProperty("c3p0.password"));
    }

    /**
     * 关闭数据库连接
     * @param rs
     * @param st
     * @param conn
     */
    public static void close(ResultSet rs, Statement st, Connection conn){

         try {
            if (rs!=null) {
                rs.close();
            }
         } catch (SQLException e) {
             e.printStackTrace();
         }

         try {
            if (st!=null) {
                st.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }

         try {
            if (conn!=null) {
                conn.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }

    }

    public static void close(Statement st, Connection conn) {
        close(null, st, conn);
    }

}
