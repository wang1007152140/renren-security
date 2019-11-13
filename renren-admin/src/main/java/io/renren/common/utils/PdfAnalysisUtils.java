package io.renren.common.utils;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @Auther: wdh
 * @Date: 2019/1/17 16:13
 * @Description:
 */
public class PdfAnalysisUtils {
    //    APP_ID = '15435485'
//    API_KEY = 'IogwQypY26cXXGSUMhRBbX9x'
//    SECRET_KEY = 'YYnLHVvyywegDm7PMwGkkmxw02xahi8z'
    private static final String requestUrl = "https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic";
    private static final String accessToken = "24.211b5a4f8306c5bebf62e0ea84adcf24.2592000.1575441558.282335-17687787";
    private static final BASE64Encoder encoder = new BASE64Encoder();

    private static final String[] nameArray = ("a0,a1,a2,a3,a4,a5,a6,a7,a8,a9,b1,b2,b3,b4,b5,b6,b7,b8,b9,c0,c1,c2,c3,c4,c5,c6,c7,c8,c9,d0,d1,d2,d3,d4,d5,d6,d7,d8,d9,e0,e1,e2,e3,e4,e5,e6,e7,e8,e9,f0,f1,f2,f3,f4,f5,f6,f7,f8,f9,,g0,g1,g2,g3,g4,g5,g6,g7,g8,g9,h0,h1,h2,h3,h4,h5,h6,h7,h8,h9,i0,i1,i2,i3,i4,i5,i6,i7,i8,i9,j0,j1,j2,j3,j4,j5,j6,j7,j8,j9,k0,k1,k2,k3,k4,k5,k6,k7,k8,k9,l0,l1,l2,l3,l4,l5,l6,l7,l8,l9,m0,m1,m2,m3,m4,m5,m6,m7,m8,m9,n0,n1,n2,n3,n4,n5,n6,n7,n8,n9,o0,o1,o2,o3,o4,o5,o6,o7,o8,o9,p0,p1,p2,p3,p4,p5,p6,p7,p8,p9,q0,q1,q2,q3,q4,q5,q6,q7,q8,q9,r0,r1,r2,r3,r4,r5,r6,r7,r8,r9,s0,s1,s2,s3,s4,s5,s6,s7,s8,s9,t0,t1,t2,t3,t4,t5,t6,t7,t8,t9").split(",");
    /**
     * 提取pdf图片
     * @param file			PDF文件
     * @param targetFolder 	图片存放目录
     * @return
     */
    public static String extractImages(File file, String targetFolder){
        InputStream is = null;
        PDDocument document = null;
        String path = targetFolder + getFileNameWithoutSuffix(file);
        File fileTest = new File(path);
        if(!fileTest.exists()){
            fileTest.getAbsoluteFile().mkdirs();
        }
        try {
            document = PDDocument.load(file);
            int pageSize = document.getNumberOfPages();
            // 一页一页读取
            for (int i = 0; i < pageSize; i++) {
                PDPage page = document.getPage(i);
                PDResources resources = page.getResources();
                Iterable<COSName> cosNames = resources.getXObjectNames();
                if (cosNames != null) {
                    Iterator<COSName> cosNamesIter = cosNames.iterator();
                    //第一步：提取图片
                    while (cosNamesIter.hasNext()) {
                        COSName cosName = cosNamesIter.next();
                        if (resources.isImageXObject(cosName)) {
                            PDImageXObject Ipdmage = (PDImageXObject) resources.getXObject(cosName);
                            BufferedImage image = Ipdmage.getImage();
                            FileOutputStream out = new FileOutputStream(path + "/" + nameArray[i] + ".jpg");
                            ImageIO.write(image, "JPG", out);
                            out.close();

                        }
                    }
                }
            }
            //第二步：遍历所有图片，调用接口进行解析图片
            File[] files = traverseFolder(path);
            StringBuffer strBuff = new StringBuffer();
            if(files != null && files.length > 0){
                for(int k = 0; k < files.length; k++){
                    String result = analysisImage(files[k]);
                    if(StringUtils.isNotBlank(result)){
                        strBuff.append(result);
                    }
                }
                return strBuff.toString();
            }
        } catch (InvalidPasswordException e) {
            System.out.println("异常：" + e);
        } catch (IOException e) {
            System.out.println("IO异常：" + e);
        } finally {
            try {
                if (document != null) {
                    document.close();
                }
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            }
        }
        return null;
    }
    /*public static boolean extractImages(File file, String targetFolder) {
        boolean result = true;

        try{
            PDDocument document = PDDocument.load(file);
            String path = targetFolder + getFileNameWithoutSuffix(file);
            List<PDPage> pages = document.getDocumentCatalog().getAllPages();
            Iterator<PDPage> iter = pages.iterator();
            int count = 0;
            while( iter.hasNext()){
                PDPage page = (PDPage)iter.next();
                PDResources resources = page.getResources();
                Map<String, PDXObjectImage> images = resources.getImages();
                if(images != null)
                {
                    Iterator<String> imageIter = images.keySet().iterator();
                    //第一步取出所有图片并保存
                    while(imageIter.hasNext())
                    {
                        count++;
                        String key = (String)imageIter.next();
                        PDXObjectImage image = (PDXObjectImage)images.get( key );
                        File fileTest = new File(path);
                        if(!fileTest.exists()){
                            fileTest.getAbsoluteFile().mkdirs();
                        }
                        image.write2file(path + "/" + count);		// 保存图片
                    }
                    //遍历所有图片，转为jpg格式
                    File[] files = traverseFolder(path);
                    if(files != null && files.length > 0){

                    }
                }
            }
        } catch(IOException ex){
            ex.printStackTrace();
            return false;
        }

        return result;
    }*/

    //图片文字识别
    public static String analysisImage(File imageFile){
        //传输参数
        Map<String, String> paramList = new HashMap<>();
        byte[] imgData = getFileToByte(imageFile);
        String result = null;
        //参数根据ASCII码排序并加密得到签名
        try {
            String image = URLEncoder.encode(encoder.encode(imgData), "UTF-8");
            paramList.put("image",image);
            paramList.put("detect_direction","true");
            String params = proData(paramList);
            //开始推送数据
            result = sendPost(params);
            System.out.println("result:" + result);
            JSONObject obj = JSONObject.parseObject(result);
            JSONArray words_result = obj.getJSONArray("words_result");
            if(words_result.size() > 0){
                StringBuffer strBuff = new StringBuffer();
                for(int i = 0; i < words_result.size(); i++){
                    JSONObject word = words_result.getJSONObject(i);
                    strBuff.append(word.getString("words"));
                    strBuff.append("\n");
                }
                return strBuff.toString();
            }
        } catch (Exception e) {
            System.out.println("网络故障");
            return "";
        }
        return "";
    }

    /**
     * 封装请求参数
     */
    public static String proData(Map<String,String> parameters){
        StringBuffer sb = new StringBuffer();
        //所有参与传参的参数按照accsii排序（升序）
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            sb.append(k + "=" + v + "&");
        }
        sb.deleteCharAt(sb.length()-1);
        System.out.println("推送数据：" + sb.toString());
        return sb.toString();
    }

    /**
     * 发送请求的 URL
     * @param param
     * 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String param) {
        String contentType = "application/x-www-form-urlencoded";
        String urlStr = requestUrl + "?access_token=" + accessToken;
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(urlStr);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("Content-Type", contentType);
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            //使用finally块来关闭输出流、输入流
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 文件转为二进制数组
     * 等价于fileToBin
     * @param file
     * @return
     */
    public static byte[] getFileToByte(File file) {
        byte[] by = new byte[(int) file.length()];
        try {
            InputStream is = new FileInputStream(file);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            byte[] bb = new byte[2048];
            int ch;
            ch = is.read(bb);
            while (ch != -1) {
                bytestream.write(bb, 0, ch);
                ch = is.read(bb);
            }
            by = bytestream.toByteArray();
        } catch (Exception ex) {
            throw new RuntimeException("transform file into bin Array 出错",ex);
        }
        return by;
    }

    //获取不带后缀名的文件名
    public static String getFileNameWithoutSuffix(File file){
        String file_name = file.getName();
        return file_name.substring(0, file_name.lastIndexOf("."));
    }

    //获取不带后缀名的绝对文件名
    public static String getAbsoluteFileNameWithoutSuffix(File file){
        String file_name = file.getAbsolutePath();
        return file_name.substring(0, file_name.lastIndexOf("."));
    }

    /**
     * 遍历文件夹
     */
    public static File[] traverseFolder(String path) {
        File file = new File(path);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (null == files || files.length == 0) {
                System.out.println("文件夹是空的!");
                return null;
            } else {
                return files;
                /*for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        traverseFolder(file2.getAbsolutePath());
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                    }
                }*/
            }
        } else {
            System.out.println("文件不存在!");
            return null;
        }
    }

    public static void main(String[] args) {
        File file = new File("D:/pdf/11富阳债-2011年公司债券2017年第一次债券持有人会议的法律意见书.PDF");
        String targerFolder = "D:/pdf/img/";

        String result = extractImages(file, targerFolder);
        System.out.println("result:" + result);

    }
}
