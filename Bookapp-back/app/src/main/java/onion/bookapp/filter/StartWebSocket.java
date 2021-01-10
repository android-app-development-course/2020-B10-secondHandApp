package onion.bookapp.filter;


import onion.bookapp.websocket.WsServer;
import org.java_websocket.WebSocketImpl;

import javax.servlet.*;
import java.io.IOException;

public class StartWebSocket implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.startWebSocketInstantMsg();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {

    }

    /**
     * 启动websocket 服务
     */
    public void startWebSocketInstantMsg(){
        System.out.println("开始启动webSocket服务...");
        WebSocketImpl.DEBUG=false;
        //端口
        WsServer server = new WsServer(8888);
        server.start();
    }
}
