package onion.bookapp.DB.Impl;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import onion.bookapp.DB.DAO.UserDAO;
import onion.bookapp.mybean.data.Goods;
import onion.bookapp.mybean.data.Register;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersDAOImpl implements UserDAO {
    private Connection conn=null;
    private PreparedStatement pstmt=null;
    public UsersDAOImpl(Connection conn){this.conn=conn;}
    public boolean insert(Register register) throws Exception {
        boolean flag=false;
        String sql="Insert into user(userid,psw,city,school,major)Values(?,?,?,?,?)";
        try {
            this.pstmt=this.conn.prepareStatement(sql);
            this.pstmt.setString(1,register.getUserid());
            this.pstmt.setString(2,register.getPsw());
            this.pstmt.setString(3,register.getCity());
            this.pstmt.setString(4,register.getSchool());
            this.pstmt.setString(5,register.getMajor());
            if (this.pstmt.executeUpdate()>0){
                flag=true;
            }
            this.pstmt.close();
        }
        catch (MySQLIntegrityConstraintViolationException e){
            e.printStackTrace();
            throw new MySQLIntegrityConstraintViolationException();
        }
        catch (Exception e){
            e.printStackTrace();
            throw new Exception();
        }
        return flag;
    }

    @Override
    public boolean login(String logname, String psw)   {
        String sql="select * from user where userid='"+logname+"'and psw='"+psw+"'";
        try{
            this.pstmt=this.conn.prepareStatement(sql);

            ResultSet rs=this.pstmt.executeQuery();

            boolean m=rs.next();
            System.out.println(rs.getString(1));
            this.pstmt.close();
            return m;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
