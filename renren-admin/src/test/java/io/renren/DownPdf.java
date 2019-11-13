package io.renren;

import io.renren.common.utils.RedisUtils;
import io.renren.modules.Data.service.DownThreeFxFilePathService;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DownPdf {
    @Autowired
    DownThreeFxFilePathService downThreeFxFilePathService;

    @Test
    public void down(){
        downThreeFxFilePathService.downOrderQuanMinJospPdf();
    }
    @Test
    public void insert(){
        downThreeFxFilePathService.getListQuanMinJosp();
    }
    @Test
    public void TestOne(){
//        File file=new File("F:\\pdf\\[临时公告]正昌电子：北京观韬（西安）律师事务所关于西安正昌电子股份有限公司2019年第一次股票发行的法律意见书.pdf");

        try {
            URL url = new URL("http://www.neeq.com.cn/disclosure/2019/2019-06-03/1559554625_118986.pdf");
            File file=new File("F:\\pdf\\[临时公告]正昌电子：北京观韬（西安）律师事务所关于西安正昌电子股份有限公司2019年第一次股票发行的法律意见书.pdf");
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
        }catch (Exception e){
            e.printStackTrace();
//            System.out.println("下载失败:"+url);
        }
    }
}
