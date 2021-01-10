package onion.bookapp.DB.Impl;


import onion.bookapp.DB.DBUtils;
import onion.bookapp.mybean.data.Message;
import sun.security.pkcs11.Secmod;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

public class MessageDaoImpl {

    //发送消息写入数据库

    public int sendMsg(Message msg){
        int count =-1;
        long id =System.currentTimeMillis();
        msg.setMsgId(id);
        Date sysdate = new Date(System.currentTimeMillis());
        SimpleDateFormat format =new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(sysdate));
        String sql = "insert into ws_message values(?,?,?,?,?,0)";
        Connection connection = DBUtils.getConnection();
        PreparedStatement ps = null;
        try{
            ps=connection.prepareStatement(sql);
            ps.setLong(1,id);
            ps.setString(2,msg.getMsgContent());
            ps.setString(3,msg.getFromName());
            ps.setString(4,msg.getToName());
            ps.setDate(5, sysdate);
            count = ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtils.closeConnection();
        }
        return count;
    }

    //查找历史消息
    public List<Message> hasReadMsg(Message msg){
        List<Message>msgList = new ArrayList<Message>();
        StringBuffer sql = new StringBuffer();
        sql.append("select * from ws_message where 1=1");
        sql.append("and ((fromName = ? and toName = ?) or ");
        sql.append("(toName = ? and fromName =? and msgStatus = 1");
        sql.append("order by msgdate asc");
        Connection connection = DBUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try{
            ps=connection.prepareStatement(sql.toString());
            ps.setString(1,msg.getFromName());
            ps.setString(2,msg.getToName());
            ps.setString(3,msg.getFromName());
            ps.setString(4,msg.getToName());
            rs=ps.executeQuery();
            while (rs.next()){
                Message m = new Message();
                m.setMsgId(rs.getLong("msgId"));
                m.setFromName(rs.getString("fromName"));
                m.setToName(rs.getString("toName"));
                m.setMsgContent(rs.getString("msgContent"));
                m.setMsgStatus(rs.getInt("msgStatus"));
                m.setMsgDate(rs.getTimestamp("msgDate"));
                msgList.add(m);
            }
            rs.close();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtils.closeConnection();
        }
        return msgList;
    }


    //获取未读消息
    public List<Message> notReadMsg(Message msg){
        List<Message>msgList = new ArrayList<Message>();
        String sql = "select * from ws_message where toName = ? and fromName = ? and msgStatus = 0";

        Connection conn = DBUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,msg.getFromName());
            ps.setString(2,msg.getToName());

            rs=ps.executeQuery();
            while (rs.next()){
                Message m = new Message();
                m.setMsgId(rs.getLong("msgId"));
                m.setFromName(rs.getString("fromName"));
                m.setToName(rs.getString("toName"));
                m.setMsgContent(rs.getString("msgContent"));
                m.setMsgStatus(rs.getInt("msgStatus"));
                m.setMsgDate(rs.getTimestamp("msgDate"));
                msgList.add(m);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtils.closeConnection();
        }
        return msgList;
    }

    //更新未读信息为已读
    public int updMsgStatus(Message msg){
        int count=-1;

        String sql ="update ws_message set msgStatus = 1 where fromName = ? and toName = ? and msgStatus= 0";
        Connection connection = DBUtils.getConnection();
        PreparedStatement ps = null;
        try{
            ps = connection.prepareStatement(sql);
            ps.setString(1,msg.getFromName());
            ps.setString(2,msg.getFromName());
            count=ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtils.getConnection();
        }
        return count;
    }

    //更新所有未读消息未已读
    public int updMsgStatusInLoad(Message msg){
        int count = -1;
        StringBuffer sql = new StringBuffer("update ws_message set msgstatus = 1");
        sql.append("where fromname = ? and toname = ?");
        sql.append("and msgstatus = 0");
        Connection conn = DBUtils.getConnection();
        PreparedStatement ps = null;
        try {
            ps=conn.prepareStatement(sql.toString());
            ps.setString(1,msg.getToName());
            ps.setString(2,msg.getFromName());
            count = ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    //获取未读消息
    public List<Message> notReadMsgALL(String name){
        List<Message>msgList = new ArrayList<Message>();
        String sql = "select * from ws_message where toName = ? and msgStatus = 2";

        Connection conn = DBUtils.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try{
            ps=conn.prepareStatement(sql);
            ps.setString(1,name);

            rs=ps.executeQuery();
            while (rs.next()){
                Message m = new Message();
                m.setMsgId(rs.getLong("msgId"));
                m.setFromName(rs.getString("fromName"));
                m.setToName(rs.getString("toName"));
                m.setMsgContent(rs.getString("msgContent"));
                m.setMsgStatus(rs.getInt("msgStatus"));
                m.setMsgDate(rs.getTimestamp("msgDate"));
                msgList.add(m);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }finally {
            DBUtils.closeConnection();
        }
        return msgList;
    }

    //发送给未登录用户信息
    public int ToNotLogin(Message msg){
        int count = -1;
        StringBuffer sql = new StringBuffer("update ws_message set msgstatus = 2");
        sql.append("where msgid = ?");
        Connection conn = DBUtils.getConnection();
        PreparedStatement ps = null;
        try {
            ps=conn.prepareStatement(sql.toString());
            ps.setLong(1,msg.getMsgId());
            count = ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

    //将离线消息设置为未读
    public int UpdateToNotRead(String name){
        int count = -1;
        StringBuffer sql = new StringBuffer("update ws_message set msgstatus = 0");
        sql.append(" where toName=?");
        Connection conn = DBUtils.getConnection();
        PreparedStatement ps = null;
        try {
            ps=conn.prepareStatement(sql.toString());
            ps.setString(1,name);
            count = ps.executeUpdate();
            ps.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return count;
    }

}
