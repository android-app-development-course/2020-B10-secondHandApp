package onion.bookapp.websocket;

import com.alibaba.fastjson.JSON;
import onion.bookapp.DB.Impl.MessageDaoImpl;
import onion.bookapp.mybean.data.Message;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.List;

public class WsServer extends WebSocketServer {

    public WsServer(int port){super(new InetSocketAddress(port));}

    public WsServer(InetSocketAddress address){super(address);}

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        //ws连接的时候触发的代码，onOpen中我们不做任何操作
        System.out.println("onopen"+"");

    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        userLeave(conn);
        System.out.println(reason);
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        //客户端发送消息给服务器，自动执行此方法
        System.out.println(message);
        //服务器端发送消息给客户端，
        //conn.send("消息内容");
        if (null != message&&message.startsWith("online")){
            userJoin(conn,message);//用户加入
            MessageDaoImpl dao = new MessageDaoImpl();
            List<Message> list = dao.notReadMsgALL(message.substring(6));
            dao.UpdateToNotRead(message.substring(6));
            for(int i=0;i<list.size();i++){
                Message msg = list.get(i);
                conn.send( JSON.toJSONString(msg));
            }
        }else if(null!=message&&message.startsWith("offline")){
            userLeave(conn);
        }

    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        //错误时候触发的代码
        System.out.println("on error");
        e.printStackTrace();
    }

    /**
     * 去除掉失效的websocket链接
     * @param conn
     */
    private void userLeave(WebSocket conn){WsPool.removeUser(conn);}

    /**
     * 将websocket加入用户池
     * @param conn
     * @param userName
     */
    private void userJoin(WebSocket conn,String userName){WsPool.addUser(userName,conn);}



}

