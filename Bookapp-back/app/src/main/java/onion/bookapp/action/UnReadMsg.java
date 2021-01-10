package onion.bookapp.action;

import onion.bookapp.DB.Impl.MessageDaoImpl;
import onion.bookapp.mybean.data.Message;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import java.io.IOException;

public class UnReadMsg extends HttpServlet {
    /*
    * 在线未读消息 修改为已读
    * */
    private static final long serialVersionUID = 1L;
    private MessageDaoImpl dao = new MessageDaoImpl();

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        req.setCharacterEncoding("utf-8");
        String fromName = req.getParameter("fromName");
        String toName = req.getParameter("toName");
        Message msg = new Message();
        msg.setFromName(fromName);
        msg.setToName(toName);

        //修改消息已读 对方发给我
        int count = dao.updMsgStatus(msg);
        resp.getWriter().print(count);
    }
}
