package onion.bookapp.myservlet.control;

import com.google.gson.internal.$Gson$Preconditions;
import onion.bookapp.DB.DAO.GoodsDAO;
import onion.bookapp.DB.DBUtils;
import onion.bookapp.DB.Impl.GoodsDAOImpl;
import onion.bookapp.DB.Impl.GoodsDAOProxy;
import onion.bookapp.mybean.data.Goods;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;
import org.apache.tomcat.util.http.fileupload.servlet.ServletRequestContext;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class HandlePublish extends HttpServlet {
    final String imagePath="/home/tomcat/appimages/";
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
//        int goodsid=Integer.parseInt(req.getParameter("goodsid"));
//        int publisherid=Integer.parseInt(req.getParameter("publisherid"));
//        double price=Double.parseDouble(req.getParameter("price"));
//        Goods goods=new Goods();
//        GoodsDAOImpl impl=new GoodsDAOImpl(DBUtils.getConnection());
//        resp.setContentType("application/json");
//        PrintWriter writer=resp.getWriter();
//        Map<String,String> map=new HashMap<String, String>();
//        if (impl.insert(goods)){
//            map.put("status","success");
//        }
//        else{
//            map.put("status","fail");
//        }
//        writer.write(map.toString());
        request.setCharacterEncoding("utf-8");
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        PrintWriter out=response.getWriter();
        // 创建文件项目工厂对象
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // 设置文件上传路径
        //String upload = "f:";
        // 获取系统默认的临时文件保存路径，该路径为Tomcat根目录下的temp文件夹
        //String temp = System.getProperty("java.io.tmpdir");
        // 设置缓冲区大小为 5M
        factory.setSizeThreshold(1024 * 1024 * 5);
        // 设置临时文件夹
        factory.setRepository(new File(imagePath));
        // 用工厂实例化上传组件,ServletFileUpload 用来解析文件上传请求
        ServletFileUpload servletFileUpload = new ServletFileUpload(factory);
        servletFileUpload.setHeaderEncoding("UTF-8");
        // 解析结果放在List中

        Map<String,String> responsemap=new HashMap<String, String>();
        Map<String,String> map = new HashMap<String, String>() ;//存储字段参数
        List<String> Image_Local_Path=new Vector<String>();//存储图片在服务器上的存储路径
        try {
            List<FileItem> list = servletFileUpload.parseRequest(new ServletRequestContext(request));
            for (FileItem item : list) {
                String name = item.getFieldName();
                InputStream is = item.getInputStream();
                System.out.println("the current name is " + name);

                if (name.contains("photo") ||name.contains("file")) {
                    try {
                        //用时间戳加图片名作为图片的文件名
                        String savePath=imagePath + System.currentTimeMillis()
                                +item.getName();
                        log("path:"+savePath);
                        inputStream2File(is,savePath);
                        Image_Local_Path.add(savePath);
                    } catch (Exception e) {
                        log(e.toString());
                        e.printStackTrace();
                    }
                } else {
                    String key = item.getName();
                    String value = item.getString("UTF-8");
                    System.out.println(value );
                    System.out.println(key + "---" + value);
                    map.put(name,value);
                }
            }
            String publisherid=map.get("publisherid");
            String goodsid=Long.toString(System.currentTimeMillis());
            String publishTime=Long.toString(System.currentTimeMillis());
            String price=map.get("price");
            String title=map.get("title");
            String detail=map.get("detail");
            String sort=map.get("sort");
            String images= StringUtils.join(Image_Local_Path,';');
            Goods goods=new Goods(goodsid, publisherid, images, publishTime, title, detail, sort, price);
            GoodsDAO dao=new GoodsDAOProxy();
            dao.insert(goods);
            responsemap.put("status","success");
            out.write(responsemap.toString());
        }  catch (Exception e) {
            e.printStackTrace();
            responsemap.put("status","failure");
            out.write(responsemap.toString());
        }

        out.flush();
        out.close();
    }
    // 流转化成字符串
    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    // 流转化成文件
    public static void inputStream2File(InputStream is, String savePath)
            throws Exception {
        System.out.println("the file path is  :" + savePath);
        File file = new File(savePath);
        InputStream inputSteam = is;
        BufferedInputStream fis = new BufferedInputStream(inputSteam);
        FileOutputStream fos = new FileOutputStream(file);
        int f;
        while ((f = fis.read()) != -1) {
            fos.write(f);
        }
        fos.flush();
        fos.close();
        fis.close();
        inputSteam.close();
    }

}
