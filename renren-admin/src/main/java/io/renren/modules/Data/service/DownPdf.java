package io.renren.modules.Data.service;

import org.apache.commons.io.IOUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class DownPdf implements Runnable {
    private String urls;
    private String path;
    public DownPdf(String url,String path){
        this.urls=url;
        this.path=path;
        this.run();
    }
    public DownPdf(){

    }

    public String getUrls() {
        return urls;
    }

    public void setUrls(String urls) {
        this.urls = urls;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void run1(){
        InputStream inputStream=null;
        OutputStream outputStream=null;
        try{
            URL url = new URL(this.urls);
            String uuid= UUID.randomUUID().toString();
            File file = new File(this.path.replace(":","：").replaceFirst("：",":").replace("\\","//").replace(".pdf","")+"+"+uuid+".pdf");
            Boolean b = file.createNewFile();
            System.out.println(b);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(5 * 1000);
            //防止屏蔽程序抓取而返回403错误
            //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            conn.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0;WindowsNT 5.0)");
            conn.setDoInput(true);
            conn.connect();
            outputStream=new FileOutputStream(file);
            inputStream=conn.getInputStream();
            IOUtils.copy(inputStream,outputStream);
        }catch (Exception e){
            System.out.println("下载失败:"+urls);
        }finally {
            try {
                if(outputStream!=null){
                    outputStream.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void run(){
        InputStream inputStream=null;
        OutputStream outputStream=null;
        try{
            synchronized(DownPdf.class) {
                System.out.println("-----------------" + Thread.currentThread().getName() + "在执行");
                URL url = new URL(this.urls);
                String uuid = UUID.randomUUID().toString();
                File file = new File(this.path.replace(":", "：").replaceFirst("：", ":").replace("\\", "//").replace(".pdf", "") + "+" + uuid + ".pdf");
                Boolean b = file.createNewFile();
                System.out.println(b);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setConnectTimeout(5 * 1000);
                //防止屏蔽程序抓取而返回403错误
                //conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
                conn.addRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0;WindowsNT 5.0)");
                conn.setDoInput(true);
                conn.connect();
                outputStream = new FileOutputStream(file);
                inputStream = conn.getInputStream();
                IOUtils.copy(inputStream, outputStream);
            }
        }catch (Exception e){
            System.out.println("下载失败:"+urls);
        }finally {
            try {
                if(outputStream!=null){
                    outputStream.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
