package onion.bookapp.myservlet.control;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import onion.bookapp.DB.Impl.GoodsDAOProxy;
import onion.bookapp.utils.common;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.Arrays;

public class HandleScreenSearch extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        try {
            GoodsDAOProxy proxy = new GoodsDAOProxy();
            String words = req.getParameter("words");
            String city_ =req.getParameter("city");
            String university_ =req.getParameter("university");
            String sort =req.getParameter("sort");
            int num =Integer.parseInt(req.getParameter("num"));
            int index =Integer.parseInt(req.getParameter("index"));

            ResultSet rs=proxy.screenSearch(words,city_,university_,num,index,sort);
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
                String city=rs.getString("city");
                String university=rs.getString("university");

                json.put("primaryImage",common.toHexString(bytes));
                //System.out.println(Arrays.toString(bytes));
                json.put("imagetype",imagetype);
                json.put("goodsid",goodsid);
                json.put("title",title);
                json.put("price",price);
                json.put("city",city);
                json.put("university",university);
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }
}
