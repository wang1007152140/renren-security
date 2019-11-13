package io.renren.Wutils.Wdown;

import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

public class WdownPdfUtils {
    /**
     * 移除body后剩下的数据
     *
     * @param url
     * @param dataMap
     * @return
     * @throws IOException
     */
    public static String downUrlJSonRmBody(String url, Map<String, String> dataMap) {
        Connection tempConn = Jsoup.connect(url);
        //模拟浏览器的请求头
        tempConn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        //tempConn.data("isNewThree", "1");
        tempConn.data(dataMap);
        Connection.Response demo = null;
        try {
            demo = tempConn.ignoreContentType(true).method(Connection.Method.POST).execute();
            //这里就是获取该页面的HTML元素。
            Document documentDemo = demo.parse();
            String str = documentDemo.toString();
            if (str != null && str != "") {
                if (str.contains("<body>")) {
                    str = str.substring(str.indexOf("<body>") + 6, str.lastIndexOf("</body>")).trim();
                }
            }
            return str;
        } catch (IOException e) {
            return downUrlJSonRmBody(url, dataMap);
        }
    }

    /**
     * 通过url下载存放文档地址的json串，这个是包括整个页面
     *
     * @param url
     * @param dataMap
     * @return
     * @throws IOException
     */
    public static String downUrlJSon(String url, Map<String, String> dataMap) throws IOException {
        Connection tempConn = Jsoup.connect(url);
        //模拟浏览器的请求头
        tempConn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        //tempConn.data("isNewThree", "1");
        tempConn.data(dataMap);
        Connection.Response demo = tempConn.ignoreContentType(true).method(Connection.Method.POST).execute();
        Document documentDemo = demo.parse();
        //这里就是获取该页面的HTML元素。
        return documentDemo.toString();
    }

    /**
     * 顺序下载pdf文件
     *
     * @param urls 文件的下载地址
     * @param path 文件下载到哪里
     */
    public static void orderDownPdf(String urls, String path) {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(urls);
            String uuid = UUID.randomUUID().toString();
            File file = new File(path.replace(":", "：").replaceFirst("：", ":").replace("*","").replace("\\", "//").replace("\n","") );
            Boolean b = file.createNewFile();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0;WindowsNT 5.0)");
            conn.setDoInput(true);
            conn.connect();
            outputStream = new FileOutputStream(file);
            inputStream = conn.getInputStream();
            IOUtils.copy(inputStream, outputStream);
            System.out.println(b+urls);
        } catch (Exception e) {
            System.out.println("下载失败:" + urls);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 多线程下载文件
     * @param urls
     * @param path
     */
    public static void multiDownPdf(String urls,String path){

    }
}

/**
 * 下载的实体类
 */
class Downloader implements Runnable{
    String urls;
    String files;
    Downloader( String url, String file){
            this.urls=url;
            this.files=file;
    }

    @Override
    public void run(){
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            URL url = new URL(urls);
            String uuid = UUID.randomUUID().toString();
            File file = new File(files.replace(":", "：").replaceFirst("：", ":").replace("*","").replace("\\", "//").replace("\n","") );
            Boolean b = file.createNewFile();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5 * 1000);
            //防止屏蔽程序抓取而返回403错误
            conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0;WindowsNT 5.0)");
            conn.setDoInput(true);
            conn.connect();
            outputStream = new FileOutputStream(file);
            inputStream = conn.getInputStream();
            IOUtils.copy(inputStream, outputStream);
            System.out.println(b+urls);
        } catch (Exception e) {
            System.out.println("下载失败:" + urls);
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}