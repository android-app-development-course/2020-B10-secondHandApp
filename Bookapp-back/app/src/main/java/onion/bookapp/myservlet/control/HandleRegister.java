package onion.bookapp.myservlet.control;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import onion.bookapp.DB.Impl.UserDAOProxy;
import onion.bookapp.mybean.data.Register;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class HandleRegister extends HttpServlet {
    public void init(ServletConfig config)throws ServletException{
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("application/json");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out=resp.getWriter();

        String logname=req.getParameter("logname").trim();
        String password=req.getParameter("password").trim();
        String again_password=req.getParameter("again_password").trim();
        String city=req.getParameter("city").trim();
        String major=req.getParameter("major").trim();
        String school=req.getParameter("school").trim();
        System.out.println(logname+password+again_password+city);
        String errormessage="";
        Map<String,String> responsemap=new HashMap<String, String>();

        if (logname==null)
            logname="";
        if (password==null)
            password="";
        if (!password.equals(again_password)){
            responsemap.put("status","failure");
            responsemap.put("errormessage","两次密码不同，注册失败");
            out.write(responsemap.toString());
            out.flush();
            out.close();
            return;
        } boolean isLD=true;
        //合法logname应该只含有大小写字母和数字
        for (int i =0;i<logname.length();i++){
            char c=logname.charAt(i);
            if (!((c<='z'&&c>='a')||(c<='Z'&&c>='A')||(c<='9'&&c>='0')))
                isLD=false;
        }
        boolean boo=logname.length()>0&&password.length()>0&&isLD;
        try {
            if (boo){
                UserDAOProxy proxy=new UserDAOProxy();
                Register register=new Register(logname,password,city,school,major,"");
                proxy.insert(register);
                responsemap.put("status","success");
                out.write(responsemap.toString());
            }
            else{
                responsemap.put("status","failure");
                responsemap.put("errormessage","用户名或密码非法！");
                out.write(responsemap.toString());
            }
        }catch (MySQLIntegrityConstraintViolationException e){
            responsemap.put("status","failure");
            responsemap.put("errormessage","用户名已被使用");
            out.write(responsemap.toString()) ;
        }
        catch (Exception e) {
            responsemap.put("status","failure");
            responsemap.put("errormessage","注册失败,请稍后重试！");
            out.write(responsemap.toString());
            e.printStackTrace();
        }
        out.flush();
        out.close();
    }
}
