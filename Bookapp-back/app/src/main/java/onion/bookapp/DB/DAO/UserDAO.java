package onion.bookapp.DB.DAO;

import onion.bookapp.mybean.data.Register;

public interface UserDAO {
    public boolean insert(Register register) throws Exception;

    public boolean login(String logname, String psw)  ;
}
