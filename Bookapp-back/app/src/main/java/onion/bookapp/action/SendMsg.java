package onion.bookapp.action;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import onion.bookapp.DB.Impl.MessageDaoImpl;
import onion.bookapp.mybean.data.Message;
import onion.bookapp.websocket.WsPool;
import org.java_websocket.WebSocket;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMsg extends HttpServlet {
    /*
    * 使用websocket 向客户端发送消息
    * */

    private static final long serialVersionUID = 1L;

    @Override
    public void service(ServletRequest req, ServletResponse resp) throws ServletException, IOException {
        super.service(req, resp);
        req.setCharacterEncoding("utf-8");
        MessageDaoImpl dao = new MessageDaoImpl();
        String fromName = req.getParameter("fromName");
        String toName = req.getParameter("toName");
        String msgContent = req.getParameter("msgContent");
        Message msg = new Message();
        msg.setFromName(fromName);
        msg.setToName(toName);
        msg.setMsgContent(msgContent);
        int count =dao.sendMsg(msg);
        if (count>0){
            // websocket向客户端发送消息
            // 根据用户名找到 用户和服务器连接到的websocket对象
            WebSocket wsConn = WsPool.getWsByUser("online" + toName);
            // 发送单条消息

            msg.setMsgDate(new Timestamp(System.currentTimeMillis()));
            // System.out.println(wsConn + "\t" + JSON.toJSONString(msg));
            if (wsConn!=null)
                WsPool.sendMessageToUser(wsConn, JSON.toJSONString(msg));
            else
                dao.ToNotLogin(msg);
        }

        // 新增成功 发送时间
        if (count > 0) {
            JSONObject jsonObj = new JSONObject();
            jsonObj.put("count", count);
            DateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            jsonObj.put("date", sf.format(new Date()));

            resp.getWriter().print(jsonObj.toJSONString()); // json: type -> string...
        }
    }
}
