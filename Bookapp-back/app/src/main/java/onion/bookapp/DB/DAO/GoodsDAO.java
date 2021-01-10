package onion.bookapp.DB.DAO;

import onion.bookapp.mybean.data.Goods;

import java.sql.ResultSet;

public interface GoodsDAO {
    //插入商品
    boolean insert(Goods goods) throws Exception;
    ResultSet search(String keyword);
    ResultSet screenSearch(String words, String city, String university, int num, int index, String sort) throws Exception;
    ResultSet getdeatil(String goodsid)throws Exception;
}
