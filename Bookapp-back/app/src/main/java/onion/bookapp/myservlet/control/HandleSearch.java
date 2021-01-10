package onion.bookapp.myservlet.control;


import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onion.bookapp.DB.DBUtils;
import onion.bookapp.DB.Impl.GoodsDAOImpl;
import onion.bookapp.DB.Impl.GoodsDAOProxy;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;




@WebServlet(name ="SearchServlet",urlPatterns="/SearchServlet")
public class HandleSearch extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try {
        GoodsDAOProxy proxy = new GoodsDAOProxy();
        String keyword = req.getParameter("words");
            System.out.println( "doPost: "+keyword);
        ResultSet rs=proxy.search(keyword);
        JSONArray array = new JSONArray();
           while (rs.next()){
               JSONObject json = new JSONObject();
               String imagePath = rs.getString("images").split(";")[0];
               FileInputStream fin = new FileInputStream(new File(imagePath));
               byte[] bytes = new byte[fin.available()];
               fin.read(bytes);
               String imagetype=imagePath.split("\\.")[1];
               String goodsid=rs.getString("goodsid");
               String title = rs.getString("title");
               String price = rs.getString("price");
               json.put("primaryImage",toHexString(bytes));
               //System.out.println(Arrays.toString(bytes));
               json.put("imagetype",imagetype);
               json.put("goodsid",goodsid);
               json.put("title",title);
               json.put("price",price);
               array.add(json);
           }
           resp.setContentType("application/json");
           resp.setCharacterEncoding("utf-8");
           PrintWriter out = resp.getWriter();
           out.write(array.toString());
           out.flush();
           out.close();
       }catch (Exception e){
           e.printStackTrace();
       }
    }

    protected void doGet(HttpServletRequest request,HttpServletResponse response){
        doPost(request,response);
    }
    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length < 1)
            throw new IllegalArgumentException("this byteArray must not be null or empty");

        final StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < byteArray.length; i++) {
            if ((byteArray[i] & 0xff) < 0x10)//0~F前面不零
                hexString.append("0");
            hexString.append(Integer.toHexString(0xFF & byteArray[i]));
        }
        return hexString.toString().toLowerCase();
    }
}
