import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onion.bookapp.DB.DAO.GoodsDAO;
import onion.bookapp.DB.Impl.GoodsDAOProxy;
import onion.bookapp.mybean.data.Goods;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class daotest {
//    public static void main(String[] args) throws Exception {
//        Goods goods=new Goods("goodsidtest","publisheridtest","imagestest","publishTimetest","titletest","detailtest","sorttest","pricetest");
//        GoodsDAO dao= new GoodsDAOProxy();
//        dao.insert(goods);
//    }
    public static void  screensearch(String words, String city, String university, int num, int index, String sort){
    boolean tag=false;
    String sql="select goodsid,images,title,price,city,university from goods";
        if(words!=""){
            sql+=" where title like ?";
            tag=true;
        }
        if (city!=""){
            if (tag==true)
                sql+=" and";
            else{
                tag=true;
                sql+=" where";
            }

            sql+="  city=?";
        }
        if (university!=""){
            if (tag==true)
                sql+=" and";
            else{
                tag=true;
                sql+=" where";
            }
            sql+=" university=?";
        }
        if (sort!=""){
            if (tag==true)
                sql+=" and";
            else{
                tag=true;
                sql+=" where";
            }
            sql+="  sort=?";
        }
        sql+=" limit ?,?";
        System.out.println(sql);}
public static void main(String[] args) {
//    String str="123.png";
//    System.out.println(str.split("\\.")[1]);
//    screensearch("words","","",12,0,"sort");
//    screensearch("","city","",12,0,"");
    try {
        GoodsDAOProxy proxy = new GoodsDAOProxy();
        ResultSet rs = proxy.screenSearch("", "广州", "华南师范大学", 10, 0, "");
        while (rs.next()) {
            String goodsid = rs.getString("goodsid");
            String images = rs.getString("images");
            String title = rs.getString("title");
            String price = rs.getString("price");
            String city = rs.getString("city");
            String university = rs.getString("university");
            System.out.println(goodsid);
            System.out.println(images);
            System.out.println(title);
            System.out.println(price);
            System.out.println(city);
            System.out.println(university);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}
//    try {
//        GoodsDAOProxy proxy = new GoodsDAOProxy();
//
//
//        ResultSet rs=proxy.search("标题");
//
//        while (rs.next()){
//
//            String imagePath = rs.getString("images").split(";")[0];
//
//
//
//            String imagetype=imagePath.split("\\.")[1];
//            String goodsid=rs.getString("goodsid");
//            String title = rs.getString("title");
//            String price = rs.getString("price");
//
//            System.out.println(title);
//        }
//} catch (SQLException e) {
//        e.printStackTrace();
//    } catch (FileNotFoundException e) {
//        e.printStackTrace();
//    } catch (IOException e) {
//        e.printStackTrace();
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//}
}
