package onion.bookapp.action;


import com.alibaba.fastjson.JSON;
import onion.bookapp.DB.Impl.MessageDaoImpl;
import onion.bookapp.mybean.data.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class WebSocketMessage extends HttpServlet {
    /*
    * message ...
    * */
    private static final long serialVersionUID = 1L;

    private MessageDaoImpl dao = new MessageDaoImpl();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        req.setCharacterEncoding("utf-8");
        String fromName = req.getParameter("fromName");
        String toName = req.getParameter("toName");
        Message msg = new Message();
        msg.setFromName(fromName);
        msg.setToName(toName);
        List<Message>list = dao.hasReadMsg(msg);
        String msgList = JSON.toJSONString(list);
        resp.setContentType("text/json;charset=utf-8");
        resp.getWriter().print(msgList);
    }
}
