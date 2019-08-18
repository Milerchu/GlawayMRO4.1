package com.glaway.sddq.tools;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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

        String sql = "merge into sys_person p using (select ? personid,? displayname,? GENDER," +
                "               ? PERSONCLASSIFICATION,? NATION,? COMPUTERLV,? STATUS from dual) dat " +
                "                   on (dat.personid=p.personid)\n" +
                "when matched then\n" +
                "    update set p.displayname=dat.displayname,p.GENDER=dat.GENDER," +
                "               p.PERSONCLASSIFICATION=dat.PERSONCLASSIFICATION,p.NATION=dat.NATION," +
                "               p.COMPUTERLV=dat.COMPUTERLV,p.status=dat.STATUS\n" +
                "when not matched then\n" +
                "    insert (PERSONID,DISPLAYNAME,GENDER,PERSONCLASSIFICATION,NATION,COMPUTERLV,\n" +
                "            STATUS,SYS_PERSONID,ACCEPTINGWFMAIL,locale,LANGCODE,LOCTOSERVREQ," +
                "            statusdate,WFMAILELECTION,TRANSEMAILELECTION)\n" +
                "    values (dat.personid,dat.displayname,dat.GENDER,dat.PERSONCLASSIFICATION," +
                "            dat.NATION,dat.COMPUTERLV,dat.STATUS,sys_personseq.nextval,1,'zh_CN'," +
                "            'ZH',1,sysdate,'过程','从不')";
        String sql2 = "merge into sys_phone ph using (select personid from sys_person where personid=?) p\n" +
                "     on ( p.personid=ph.personid )\n" +
                "     when matched then\n" +
                "       update set phonenum=?\n" +
                "     when not matched then\n" +
                "      insert (ISPRIMARY,SYS_PHONEID,phonenum,personid)\n" +
                "        values(1,sys_phoneseq.nextval,?,p.personid)";

        Connection conn = null;
        PreparedStatement pstm = null;
        PreparedStatement ps = null;
        List<Statement> psList = new ArrayList<Statement>();


//        ResultSet rs = null;
        try {
            conn = getOrclConn();
            pstm = conn.prepareStatement(sql);
            for(int idx = 10317; idx < 10319; idx++){

                pstm.setString(1, "A"+idx);
                pstm.setString(2, "C罗"+idx);
                pstm.setString(3, "男");
                pstm.setString(4, "1");
                pstm.setString(5, "1");
                pstm.setString(6, "0");
                pstm.setString(7, "活动");
                pstm.addBatch();
            }
            psList.add(pstm);

            ps = conn.prepareStatement(sql2);
            for(int j = 7; j < 9; j++){
                ps.setString(1, "A1031"+j);
                ps.setString(2, "1380200256"+j);
                ps.setString(3, "1380200256"+j);
                ps.addBatch();
            }
            psList.add(ps);
            int[] results1 = pstm.executeBatch();
            System.out.println("r1:"+isBatchSuccess(results1));
            int[] results2 =ps.executeBatch();
            System.out.println("r2:"+isBatchSuccess(results2));


        }catch(Exception e){
            e.printStackTrace();
        }finally {
           close(psList, conn);
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
            if (rs != null) {
                rs.close();
                rs = null;//防止内存泄漏
            }
            if (st != null) {
                st.close();
                st = null;//防止内存泄漏
            }
            if (conn != null) {
                conn.close();
            }
         } catch (SQLException e) {
            e.printStackTrace();
         }

    }

    public static void close(Connection conn, Statement... sts ) {
        for(Statement st: sts){
            close(null, st, conn);
        }
    }

    public static void close(List<Statement> statementList, Connection conn) {

        try {
            for (Statement st : statementList) {
                if (st != null) {
                    st.close();
                    st = null;
                }
            }
            if (conn != null) {
                conn.close();
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    /**
     * 判断批处理sql是否执行成功
     * @param results 执行结果数组
     * @return true 执行成功，false 执行失败
     */
    public static boolean isBatchSuccess(int[] results) {

        boolean isSuccess = true;
        for(int i : results){
            if(i < 0 && i != Statement.SUCCESS_NO_INFO){
                isSuccess = false;
                break;
            }
        }
        return isSuccess;
    }

}
