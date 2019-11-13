package io.renren.modules.Data.service;

import com.google.gson.Gson;
import com.sun.org.apache.xpath.internal.operations.Bool;
import io.renren.modules.Data.entity.DataBean;
import io.renren.modules.Data.entity.DownThreeFxFilePathEntity;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.testng.annotations.Test;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class QuanMinJousp {
    public static void main(String[] args) throws Exception {
        /*DataBean dataBean = new DataBean();
        String canshu = "股票发行";
        String url = "http://www.neeq.com.cn/disclosureInfoController/infoResult_en.do?callback=jQuery331_1571643666297";
        Connection tempConn = Jsoup.connect(url);
        //模拟浏览器的请求头
        tempConn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        tempConn.data("isNewThree", "1");
        tempConn.data("startTime", "2015-03-01");
        tempConn.data("endTime", "2019-10-22");
        tempConn.data("keyword", canshu);

        //开始连接HTTP请求。
        Connection.Response demo = tempConn.ignoreContentType(true).method(Connection.Method.POST).execute();
        Document documentDemo = demo.parse();
        //这里就是获取该页面的HTML元素。
        String str = documentDemo.toString();
        // System.out.println(str);
        int sta = str.indexOf("<body>");
        int end = str.lastIndexOf("</body>");
        // System.out.println(str+"---"+end);
        // System.out.println("========================================");
        String str1 = str.substring(sta + 6, end).trim();
        //System.out.println(str1);
        int sta1 = str1.indexOf("(");
        int end1 = str1.lastIndexOf(")");
        String str2 = str1.substring(sta1 + 2, end1 - 1);
        System.out.println(str2);

        dataBean = new Gson().fromJson(str2, DataBean.class);
        //System.out.println(jsonRootBean.toString());
        String indexUrl = "http://www.neeq.com.cn";
        //String endUrl=jsonRootBean.getListinfo().getContent().get(1).getKernels().get(1).getFilepath();
        //System.out.println(indexUrl+endUrl);
        System.out.println(dataBean.getListInfo().getTotalPages());*/
    }

    public DataBean getListDateBean(String star, String page, String keyword) throws Exception {
        DataBean dataBean = new DataBean();
        String url = "http://www.neeq.com.cn/disclosureInfoController/infoResult_en.do?callback=jQuery331_1571643666297";
        Connection tempConn = Jsoup.connect(url);
        //模拟浏览器的请求头
        tempConn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:29.0) Gecko/20100101 Firefox/29.0");
        tempConn.data("isNewThree", "1");
        tempConn.data("page", page);
        tempConn.data("startTime", star);
        tempConn.data("endTime", "2019-10-23");
        tempConn.data("keyword", keyword);
        Connection.Response demo = tempConn.ignoreContentType(true).method(Connection.Method.POST).execute();
        Document documentDemo = demo.parse();
        //这里就是获取该页面的HTML元素。
        String str = documentDemo.toString();
        // System.out.println(str);
        int sta = str.indexOf("<body>");
        int end = str.lastIndexOf("</body>");
        // System.out.println(str+"---"+end);
        // System.out.println("========================================");
        String str1 = str.substring(sta + 6, end).trim();
        //System.out.println(str1);
        int sta1 = str1.indexOf("(");
        int end1 = str1.lastIndexOf(")");
        String str2 = str1.substring(sta1 + 2, end1 - 1);
        System.out.println(str2);
        dataBean = new Gson().fromJson(str2, DataBean.class);
       // String indexUrl = "http://www.neeq.com.cn";
        return dataBean;
    }

    @Test
    public void func2() throws Exception {
        QuanMinJousp quanMinJousp = new QuanMinJousp();
        DataBean dataBean = quanMinJousp.getListDateBean("2015-03-01", "0", "股票发行方案");
        System.out.println(dataBean.getListInfo().getTotalPages());
    }

    @Test
    public void func3() {
        QuanMinJousp quanMinJousp = new QuanMinJousp();
        String page = "0";
        try {
            DataBean dataBean = quanMinJousp.getListDateBean("2019-06-03", page, "股票发行");
            int pages = dataBean.getListInfo().getTotalPages();
            for (int i = 0; i < pages; i++) {
                List<DownThreeFxFilePathEntity> list = new ArrayList<DownThreeFxFilePathEntity>();
                if (i != 0) {
                    dataBean = quanMinJousp.getListDateBean("2019-06-03", String.valueOf(i), "股票发行");
                }
                for (int j = 0; j < dataBean.getListInfo().getContent().size(); j++) {
                    DataBean.ListInfoBean.ContentBean contentBean = dataBean.getListInfo().getContent().get(j);
                    DownThreeFxFilePathEntity fxFile = new DownThreeFxFilePathEntity();
                    fxFile.setUuid(UUID.randomUUID().toString());
                    fxFile.setFileTitle(contentBean.getDisclosureTitle());
                    fxFile.setDownloadLink("http://www.neeq.com.cn" + contentBean.getDestFilePath());
                    fxFile.setCode(contentBean.getCompanyCd());
                    fxFile.setPushDate(contentBean.getPublishDate());
                    fxFile.setYear("" + contentBean.getUpDate().getYear());
                    fxFile.setFileType("pdf");
                    fxFile.setCompanyName(contentBean.getCompanyName());
                    fxFile.setFileContentType("新三板股票发行方案");
                    fxFile.setState(0);
                    fxFile.setAddTime("2019-10-22");
                    list.add(fxFile);
                }
                System.out.println(list.get(1).getFileTitle());
                list.clear();
                System.out.println(list.size());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    public void func4() throws Exception {
        URL url = new URL("http://www.neeq.com.cn/disclosure/2019/2019-06-03/1559554625_118986.pdf");
        File file = new File("F:/pdf", "w.pdf");
        Boolean b = file.createNewFile();
        System.out.println(b);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setConnectTimeout(5 * 1000);
        //防止屏蔽程序抓取而返回403错误
        //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        conn.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0;WindowsNT 5.0)");
        conn.setDoInput(true);
        conn.connect();
        OutputStream outputStream=new FileOutputStream(file);
        InputStream inputStream=conn.getInputStream();
        IOUtils.copy(inputStream,outputStream);
        //FileUtils.copyURLToFile(url,file);
        /*

        uc

            uc.setDoInput(true);//设置是否要从 URL 连接读取数据,默认为true

            uc.connect;
        */
    }

    @Test
    public void func5() {
        // StringfileName= "d96c6dcfda2559c5865db89388d28cbf.pdf";

        String fileUrl = "http://www.neeq.com.cn/disclosure/2019/2019-06-03/1559554625_118986.pdf";

        String downPath = "F:/pdf";


    }

}

