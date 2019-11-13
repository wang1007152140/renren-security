package io.renren;

import com.google.gson.Gson;
import io.renren.Entity.LegalOpinion;
import io.renren.common.utils.PDFReader;
import io.renren.modules.gkzr.Down.DownPdfUtils;
import io.renren.modules.gkzr.entity.GkzrEntity;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 通过链接中的文件
 */
public class Test2 {
    @Test
    public void funcddd(){
        String res="";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(""+1564329600000L);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        System.out.println(res);
    }

    @Test
    public void funcd(){
        File file =new File("F:\\pdf","16银亿04：上海市上正  律师事务所关于银亿房地产股份有限公司2015年面向合格投资者公开发行公司债券（第一期）2019年第三次债券持有人会议之  法律意见书+1fce4869-3326-4ef3-9b24-84ff11f563c3.pdf");
        System.out.println(file.exists());
        String str=PDFReader.getPDFContentEndOnePage(file,-1);
        System.out.println(str);
    }

    /**
     * 通过字符串的时间变成yyyy-MM-dd的字符串
     * @param s
     * @return
     */
    public String getTimes(String s){
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        long lt=new Long(s);
        Date date =new Date(lt);
        return simpleDateFormat.format(date);
    }

    /**
     * 下载公开转让说明书
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void func() throws IOException, InterruptedException {
        int k=1;
        String path="F:\\pdf\\";
        for (int i=0;i<k;i++){
            Map<String,String> map=new HashMap<String,String>();
            map.put("keyword","公开转让说明书");
            map.put("pageSize","20");
            map.put("startTime","2019-06-18");
            map.put("endTime","2019-10-29");
            map.put("type","0");
            map.put("category","info_publish");
            map.put("page",i+"");
            String url="http://www.neeq.com.cn/index/searchByCate.do?callback=jQuery331_1572311801772";
            String str= DownPdfUtils.downUrlJSonRmBody(url,map);
            str=str.replace("<font style=\"color:#347EC9;font-weight: bold;\">","").replace("&lt;\\/font&gt;","").replace("</font>","");
            str=str.substring(str.indexOf("[")+1,str.lastIndexOf("]"));
            GkzrEntity gkzrEntity=new Gson().fromJson(str, GkzrEntity.class);
            k=Integer.valueOf(gkzrEntity.getPageList().getTotalPages());
            for (int j=0;j<gkzrEntity.getPageList().getContent().size();j++){
                  if(gkzrEntity.getPageList().getContent().get(j).getTitle().contains("公开转让说明书")){
                      DownPdfUtils.orderDownPdf(
                      "http://www.neeq.com.cn"+gkzrEntity.getPageList().getContent().get(j).getUrl(),
                      path+gkzrEntity.getPageList().getContent().get(j).getTitle().replace("\n","").replaceAll("(\\s)","")+"-"+ getTimes(gkzrEntity.getPageList().getContent().get(j).getPublishDate()) +"+"+ UUID.randomUUID().toString()+".pdf");
                  }
            }
            System.out.println("等待30s---");
            Thread.sleep(5*1000);
            System.out.println("开始---");
        }
    }

    /**
     * 判断url中的时间符合我们的额截至时间不，时间格式为年-月-日,
     * 判断是不是我们发的日期之后呢，要是之后的话，就返回true，如果时间一样的话，
     * 也返回false
     * @param url
     * @param overTime
     * @return
     */
    public static boolean pdTime(String url,String overTime){
        String newTime=url.substring(url.indexOf("finalpage")+10,url.indexOf("finalpage")+20);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date newTimeDate= null;
        try {
            newTimeDate = simpleDateFormat.parse(newTime);
            Date overTimeDate=simpleDateFormat.parse(overTime);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(newTimeDate);
            Calendar calendar1=Calendar.getInstance();
            calendar1.setTime(overTimeDate);
            return calendar.after(calendar1);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    //拿出时间
    public static String  getTime(String url){
        String newTime=url.substring(url.indexOf("finalpage")+10,url.indexOf("finalpage")+20);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date newTimeDate= null;
        try {
            newTimeDate = simpleDateFormat.parse(newTime);
            Calendar calendar=Calendar.getInstance();
            calendar.setTime(newTimeDate);
            return calendar.get(Calendar.YEAR)+"-"+calendar.get(Calendar.MONTH)+"-"+calendar.get(Calendar.DATE);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 下载深交的法律意见书
     */
    @Test
    public void funcfalvyijianshu() throws IOException {
        String path="F:\\pdf\\";
        String url="http://www.szse.cn/api/search/content?random=0.8094327767390301";
        Map<String,String> map=new HashMap<String,String>();
        map.put("keyword","法律意见书");
        map.put("range","title");
        map.put("time","1");
        map.put("orderby","time");
        map.put("pageSize","20");
        map.put("channelCode","disclosure");
        map.put("currentPage","1");
        int i=1;
        boolean urls=false;
        do{
            map.remove("currentPage");
            map.put("currentPage",i+++"");
            String str=DownPdfUtils.downUrlJSonRmBody(url,map).replace("<span class=\"\\&quot;keyword\\&quot;\">","").replace("</span>","");
            LegalOpinion legalOpinion=new Gson().fromJson(str, LegalOpinion.class);
            for (int j=0;j<legalOpinion.getData().size();j++){
                if(legalOpinion.getData().get(j).getDoctitle().endsWith("法律意见书"))
                {
                    String fileName=legalOpinion.getData().get(j).getDoctitle().replace(":","").replace("*","").replace(".pdf","");
                    // DownPdfUtils.orderDownPdf(legalOpinion.getData().get(j).getDocpuburl(),path+legalOpinion.getData().get(j).getDoctitle().replace(":","").replace("*","").replace(".pdf","")+"+"+UUID.randomUUID().toString()+".pdf");
                    urls=pdTime(legalOpinion.getData().get(j).getDocpuburl(),"2019-06-20");
                    String times=getTime(legalOpinion.getData().get(j).getDocpuburl());
                    System.out.println(times);
                }
            }
        }while(urls);

    }
}
