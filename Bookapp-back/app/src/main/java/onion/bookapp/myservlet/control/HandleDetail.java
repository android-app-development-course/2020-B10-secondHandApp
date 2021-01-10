package onion.bookapp.myservlet.control;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import onion.bookapp.DB.DAO.GoodsDAO;
import onion.bookapp.DB.Impl.GoodsDAOProxy;
import onion.bookapp.utils.common;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
public class HandleDetail extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            GoodsDAOProxy proxy=new GoodsDAOProxy();
            String goodsid=req.getParameter("goodsid");
            ResultSet rs=proxy.getdeatil(goodsid);
            JSONObject json=new JSONObject();
            if(rs.next()){
                String images=rs.getString("images");
                String publisherid=rs.getString("publisherid");
                String publishTime=rs.getString("publishTime");
                String price=rs.getString("price");
                String title=rs.getString("title");
                String detail=rs.getString("detail");
                String sort=rs.getString("sort");
                String city=rs.getString("city");
                String university=rs.getString("university");

                String[] imagePath=images.split(";");
                JSONObject ijson=new JSONObject();//存放图片
                for(int i=1;i<imagePath.length;i++){
                    int idx=imagePath[i].lastIndexOf("/");
                    String filename=imagePath[i].substring(idx+1);
                    System.out.println(filename);
                    FileInputStream fin = new FileInputStream(new File(imagePath[i]));
                    byte[] bytes = new byte[fin.available()];
                    fin.read(bytes);
                    ijson.put(filename, common.toHexString(bytes));
                }
                json.put("publisherid",publisherid);
                json.put("publishTime",publishTime);
                json.put("price",price);
                json.put("title",title);
                json.put("detail",detail);
                json.put("sort",sort);
                json.put("city",city);
                json.put("university",university);
                json.put("images",ijson.toString());
                System.out.println(json.toString());

                resp.setContentType("application/json");
                resp.setCharacterEncoding("utf-8");
                PrintWriter out=resp.getWriter();
                out.write(json.toString());
                out.flush();
                out.close();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
