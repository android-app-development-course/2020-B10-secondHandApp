package onion.bookapp.myservlet.control;



import onion.bookapp.DB.Impl.UserDAOProxy;
import onion.bookapp.utils.JWTTokenUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HandleLogin extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("receive post request");
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out=resp.getWriter();
        String logname=req.getParameter("logname").trim();
        System.out.println(logname);
        String password=req.getParameter("password").trim();
        System.out.println(password);
        Map<String,String> map=new HashMap<String, String>();
        try {
            UserDAOProxy proxy=new UserDAOProxy();
            if (proxy.login(logname,password)){//登录成功
                String token=JWTTokenUtil.token(logname,password);
                System.out.println("token="+token);
                map.put("status","success");
                map.put("token",token);
            }
            else{//登录失败
                map.put("status","failure");
            }
        } catch (Exception e) {
            map.put("status","failure");
        }
        System.out.println(map.toString());
        out.write(map.toString());
        out.flush();
        out.close();

    }

}
