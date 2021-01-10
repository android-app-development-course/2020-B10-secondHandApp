package onion.bookapp.DB.Impl;

import onion.bookapp.DB.DAO.GoodsDAO;
import onion.bookapp.mybean.data.Goods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GoodsDAOImpl implements GoodsDAO {
    private Connection conn=null;
    private PreparedStatement pstmt=null;
    public GoodsDAOImpl(Connection conn){this.conn=conn;}
    public boolean insert(Goods goods) throws Exception {
        boolean flag=false;
        String sql="Insert into goods(goodsid,publisherid,images,publishTime,price,title,detail,sort)Values(?,?,?,?,?,?,?,?)";
        try {
            this.pstmt=this.conn.prepareStatement(sql);
            this.pstmt.setString(1,goods.getGoodsid());
            this.pstmt.setString(2,goods.getPublisherid());
            this.pstmt.setString(3,goods.getImages());
            this.pstmt.setString(4,goods.getPublishTime());
            this.pstmt.setString(5,goods.getPrice());
            this.pstmt.setString(6,goods.getTitle());
            this.pstmt.setString(7,goods.getDetail());
            this.pstmt.setString(8,goods.getSort());
            if (this.pstmt.executeUpdate()>0){
                flag=true;
            }
            this.pstmt.close();
        }catch (SQLException e){
            e.printStackTrace();
            throw new Exception();
        }
        return flag;
    }
    public ResultSet search(String keyword) {
        try{
            String sql = "select goodsid,images,title,price from goods where title like ? or detail like ?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,"%"+keyword+"%");
            pstmt.setString(2,"%"+keyword+"%");
            return pstmt.executeQuery();
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResultSet screenSearch(String words, String city, String university, int num, int index, String sort) throws Exception {
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

            sql+=" city=?";
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
            sql+=" sort=?";
        }
        sql+=" limit ?,?";
        System.out.println(sql);
        System.out.println(city);
        try{
            pstmt=conn.prepareStatement(sql);
            int i=1;
            if(words!="")pstmt.setString(i++,"%"+words+"%");

            if(city!="")pstmt.setString(i++,city);
            if (university!="")pstmt.setString(i++,university);
            if (sort!="")pstmt.setString(i++,sort);
            pstmt.setInt(i++,index);
            pstmt.setInt(i++,num);

            System.out.println(pstmt.toString());
            return pstmt.executeQuery();
        }
        catch (Exception e){
            throw e;
        }

    }

    @Override
    public ResultSet getdeatil(String goodsid) throws Exception {
        String sql="select * from goods where goodsid=?";
        try{
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,goodsid);
            return pstmt.executeQuery();
        }catch(Exception e){
            throw e;
        }
    }
}
