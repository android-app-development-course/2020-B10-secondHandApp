package onion.bookapp.action;

import onion.bookapp.websocket.WsPool;
import org.java_websocket.WebSocket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class IsReadMsg extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        // String fromName = req.getParameter("fromName");
        String toName = req.getParameter("toName");

        String isRead = req.getParameter("isRead");
        if ("yes".equals(isRead)) {
            WebSocket wsConn = WsPool.getWsByUser("online" + toName);

            System.out.println(toName + "\t" + wsConn);
            WsPool.sendMessageToUser(wsConn, "yes");
        }


    }
}
