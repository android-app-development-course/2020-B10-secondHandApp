package onion.bookapp.DB.Impl;

import onion.bookapp.DB.DAO.UserDAO;
import onion.bookapp.DB.DBUtils;
import onion.bookapp.mybean.data.Register;

public class UserDAOProxy implements UserDAO {
    private DBUtils dbUtils;
    private UserDAO dao=null;

    public UserDAOProxy() throws Exception {
        dbUtils=new DBUtils();
        dao=new UsersDAOImpl(dbUtils.getConnection());
    }

    @Override
    public boolean insert(Register register) throws Exception {
        if (dao.insert(register)){
            DBUtils.closeConnection();
            return true;
        }
        DBUtils.closeConnection();
        return false;
    }

    @Override
    public boolean login(String logname, String psw)   {
        if (dao.login(logname,psw)){
            DBUtils.closeConnection();
            return true;
        }
        DBUtils.closeConnection();
        return false;
    }
}
