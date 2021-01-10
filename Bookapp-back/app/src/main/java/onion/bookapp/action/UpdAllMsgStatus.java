package onion.bookapp.action;

import onion.bookapp.DB.Impl.MessageDaoImpl;
import onion.bookapp.mybean.data.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdAllMsgStatus extends HttpServlet {
    /*
    * 修改发送给我的消息为已读
    * */
    private static final long serialVersionUID = 1L;

    protected void service(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException{
        req.setCharacterEncoding("utf-8");
        MessageDaoImpl dao = new MessageDaoImpl();
        Message msg = new Message();
        msg.setFromName(req.getParameter("fromName"));
        msg.setToName(req.getParameter("toName"));
        dao.updMsgStatusInLoad(msg);
    }
}
