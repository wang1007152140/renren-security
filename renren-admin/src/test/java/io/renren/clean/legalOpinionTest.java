package io.renren.clean;

import io.renren.Test2;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.google.gson.Gson;
import io.renren.Entity.LegalOpinion;
import io.renren.Wutils.Utils.Wutils;
import io.renren.common.utils.PDFReader;
import io.renren.common.utils.RegularUtils;
import io.renren.modules.derive.entity.XsIssueEntity;
import io.renren.modules.flyjs.entity.LegalOpinionEntity;
import io.renren.modules.flyjs.service.LegalOpinionService;
import io.renren.modules.gkzr.Down.DownPdfUtils;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.*;

import static io.renren.common.utils.PdfAnalysisUtils.extractImages;


/**
 * 深交
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class legalOpinionTest {
    private Logger logger= LoggerFactory.getLogger(legalOpinionTest.class);
    @Autowired
    LegalOpinionService legalOpinionService;

    private String filePath = "F:\\pdf";
    private List<File> list = Arrays.asList(new File(filePath).listFiles());
    @Test
    public void funfcs(){
        List<LegalOpinionEntity> list = legalOpinionService.selectList(new EntityWrapper<LegalOpinionEntity>().isNotNull("fv_charge_person"));
        for (int i=0;i<list.size();i++){
            if(list.get(i).getFvChargePerson().equals("事务所")){
                list.get(i).setFvChargePerson(list.get(i).getFvLower1());
                list.get(i).setFvLower1(list.get(i).getFvLower2());
                list.get(i).setFvLower2(list.get(i).getFvLower3());
                list.get(i).setFvLower3(list.get(i).getFvLower4());
                list.get(i).setFvLower4(list.get(i).getFvLower5());
                list.get(i).setFvLower5("");
                System.out.println(i+"-----"+list.get(i).getId() + "" + legalOpinionService.updateById(list.get(i)));
            }
        }
    }
    @Test
    public void func() {
        LegalOpinionEntity legalOpinionEntity;
        String targerFolder = "F:\\pdf1\\img\\";
        for (int i = 1306; i < list.size() - 1000; i++) {
            try {
                legalOpinionEntity = new LegalOpinionEntity();
                System.out.println(i + "------" + list.get(i).getName());
                String pdfContent = PDFReader.paserPDFFileByPdfBox(list.get(i));
                if (pdfContent.length() < 50) {
                    pdfContent = extractImages(list.get(i), targerFolder);
                }
                setFileTitle(legalOpinionEntity, list.get(i).getName());
                setCompanyAndLawFirmName(legalOpinionEntity, pdfContent);
                setPrincipal(legalOpinionEntity, pdfContent);//没搞出来
                setYear(legalOpinionEntity, pdfContent);
                legalOpinionEntity.setFvType("深交");
                legalOpinionService.insert(legalOpinionEntity);
            } catch (Exception e) {
                File file = list.get(i);
                File file1 = new File("F:\\pdf1");
                try {
                    //  FileUtils.moveToDirectory(file, file1, true);
                } catch (Exception ex) {
                    logger.info("================");
                    System.out.println("这是文件创建失败可能是" + file.getName());
                }
                System.out.println("失败:" + file.getName());
            } finally {
                File images = new File("F:\\pdf1\\img\\");
                //Wutils.deleteDirectory(images);
            }
        }
    }
    /**
     * 第二次清洗公司名称和律师事务所
     */
    @Test
    public void func1() {
        String targerFolder = "F:\\pdf1\\img\\";
        File file = new File("F:\\pdf");
        List<LegalOpinionEntity> list = legalOpinionService.selectList(new EntityWrapper<LegalOpinionEntity>().isNull("fv_company_name"));
        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.getName().contains(list.get(finalI).getFvFileTitle()))
                        return true;
                    return false;
                }
            });
            for (int j = 0; j < files.length; j++) {
                try {
                    String pdfContent = PDFReader.paserPDFFileByPdfBox(files[j]);
                    if (pdfContent.length() < 50) {
                        pdfContent = extractImages(files[j], targerFolder);
                    }
                    setCompanyAndLawFirmName2(list.get(i), pdfContent);
                    System.out.println(list.get(i).getId() + "" + legalOpinionService.updateById(list.get(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Test
    public void func7(){
        List<LegalOpinionEntity> list = legalOpinionService.selectList(new EntityWrapper<LegalOpinionEntity>().isNotNull("fv_file_title"));
        List<String> list1 = new LinkedList<String>();
        for (int i=0;i<list.size();i++){
            if(list.get(i).getFvChargePerson()!=null && list.get(i).getFvChargePerson()!=""){
                list1.add(list.get(i).getFvChargePerson());
            }
            if(list.get(i).getFvLower1()!=null && list.get(i).getFvLower1()!=""){
                list1.add(list.get(i).getFvLower1());
            }
            if(list.get(i).getFvLower2()!=null && list.get(i).getFvLower2()!=""){
                list1.add(list.get(i).getFvLower2());
            }
            if(list.get(i).getFvLower3()!=null && list.get(i).getFvLower3()!=""){
                list1.add(list.get(i).getFvLower3());
            }
            if(list.get(i).getFvLower4()!=null && list.get(i).getFvLower4()!=""){
                list1.add(list.get(i).getFvLower4());
            }
            if(list.get(i).getFvLower5()!=null && list.get(i).getFvLower5()!=""){
                list1.add(list.get(i).getFvLower5());
            }
            for (int j=0;j<list1.size();j++){
                switch (j) {
                    case 0:
                        list.get(i).setFvChargePerson(list1.get(j));
                        break;
                    case 1:
                        list.get(i).setFvLower1(list1.get(j));
                        break;
                    case 2:
                        list.get(i).setFvLower2(list1.get(j));
                        break;
                    case 3:
                        list.get(i).setFvLower3(list1.get(j));
                        break;
                    case 4:
                        list.get(i).setFvLower4(list1.get(j));
                        break;
                    case 5:
                        list.get(i).setFvLower5(list1.get(j));
                    default:
                        list.get(i).setFvLower5(list.get(i).getFvLower5().concat(list1.get(j)));
                }
            }
            list1.clear();
            System.out.println(i+"---删除--"+list.get(i).getId() + "" + legalOpinionService.deleteById(list.get(i)));
            System.out.println(i+"--增加---"+list.get(i).getId() + "" + legalOpinionService.insert(list.get(i)));
        }
    }
    /**
     * 第二次清洗法律负责人和经办律师的
     */
    @Test
    public void func6() throws Exception{
        List<LegalOpinionEntity> list = legalOpinionService.selectList(new EntityWrapper<LegalOpinionEntity>().isNotNull("fv_lower_firm"));
        String targerFolder = "F:\\pdf1\\img\\";
       File file =new File("F:\\pdf");//图片的
        File file1 =new File("");//文字的
        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if(pathname.isDirectory()){
                        return false;
                    }

                    if (pathname.getName().contains(list.get(finalI).getFvFileTitle()))
                        return true;
                    return false;
                }
            });
            for (int j = 0; j < files.length; j++) {
                try {
                    String pdfContent = PDFReader.paserPDFFileByPdfBox(files[j]);
                    if (pdfContent.length() < 50) {
                        pdfContent = extractImages(files[j], targerFolder);
                    }
                    setFvLowerAndPrincipal(list.get(i), pdfContent);
                    System.out.println(i+"-----"+list.get(i).getId());
                    logger.info(i+"-----"+list.get(i).getId() + "" + legalOpinionService.updateById(list.get(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }
    /**
     * 第二次清洗日期
     */
    @Test
    public void func3() {
        // File file = new File("F:\\pdf");
        List<LegalOpinionEntity> list = legalOpinionService.selectList(new EntityWrapper<LegalOpinionEntity>().isNotNull("fv_file_title"));
        // String path="F:\\pdf\\";
        String url = "http://www.szse.cn/api/search/content?random=0.8094327767390301";
        Map<String, String> map = new HashMap<String, String>();
        map.put("keyword", "法律意见书");
        map.put("range", "title");
        map.put("time", "1");
        map.put("orderby", "time");
        map.put("pageSize", "20");
        map.put("channelCode", "disclosure");
        map.put("currentPage", "1");
        int i = 1;
        boolean urls = false;
        do {
            map.remove("currentPage");
            map.put("currentPage", i++ + "");
            String str = DownPdfUtils.downUrlJSonRmBody(url, map).replace("<span class=\"\\&quot;keyword\\&quot;\">", "").replace("</span>", "");
            LegalOpinion legalOpinion = new Gson().fromJson(str, LegalOpinion.class);
            for (int j = 0; j < legalOpinion.getData().size(); j++) {
                if (legalOpinion.getData().get(j).getDoctitle().endsWith("法律意见书")) {
                    String fileName = legalOpinion.getData().get(j).getDoctitle().replace(":", "").replace("*", "").replace(".pdf", "").replace("PDF", "").replace("\n", "");
                    // DownPdfUtils.orderDownPdf(legalOpinion.getData().get(j).getDocpuburl(),path+legalOpinion.getData().get(j).getDoctitle().replace(":","").replace("*","").replace(".pdf","")+"+"+UUID.randomUUID().toString()+".pdf");
                    urls = Test2.pdTime(legalOpinion.getData().get(j).getDocpuburl(), "2019-06-20");
                    String times1 = Wutils.timestampToStr(legalOpinion.getData().get(j).getDocpubtime());
                    for (int k = 0; k < list.size(); k++) {
                        if (list.get(k).getFvFileTitle().contains(fileName)) {
                            list.get(k).setFvDate(times1);
                            System.out.println(times1);
                            legalOpinionService.updateById(list.get(k));
                        }
                    }
                }
            }
        } while (urls);
    }

    /**
     * 获取文件的名称
     *
     * @param legalOpinionEntity
     * @param filepath
     * @return
     */
    public LegalOpinionEntity setFileTitle(LegalOpinionEntity legalOpinionEntity, String filepath) {
        String str = filepath.substring(0, filepath.indexOf("+"));
        legalOpinionEntity.setFvFileTitle(str);
        return legalOpinionEntity;
    }

    /**
     * 清洗律师事务所
     * @param legalOpinionEntity
     * @param pdfContent
     * @return
     */
    public LegalOpinionEntity setCompanyAndLawFirmName(LegalOpinionEntity legalOpinionEntity, String pdfContent) {
        try {
            ArrayList<String> startList = new ArrayList<String>();
            startList.add(".*律师.*事务所.*(\\s.*){1,4}.*关于.*公司");
            startList.add(".*律师.*事务所.*(\\s.*){1,4}.*关于(\\s.*){1,4}.*公司");
            startList.add(".*律师.*事务所.*(\\s.*){1,4}.*关.*公司");
            String start = RegularUtils.ifFullinterceptionFromText(pdfContent, startList).replace("\n", "").replace("\r", "");
            String companyName = start.substring(0, start.indexOf("事务所") + 3);
            String lawFirmName = start.substring(start.indexOf("关于") + 2, start.indexOf("公司") + 2);
            if (companyName == null || lawFirmName.equals("")) {
                ArrayList<String> lv = new ArrayList<String>();
                lv.add(".*律师.*事务所");
                ArrayList<String> gs = new ArrayList<String>();
                gs.add("关于.*公司");
                gs.add("致.*公司");
                String start1 = RegularUtils.ifFullinterceptionFromText(pdfContent, lv).replace("\n", "").replace("\r", "");
                String start2 = RegularUtils.ifFullinterceptionFromText(pdfContent, gs).replace("\n", "").replace("\r", "");
                if (start1 != null || start1 != "")
                    companyName = start1.trim();
                if (start2 != null || start1 != "")
                    if (start2.contains("致") || start2.contains("关于"))
                        lawFirmName = start2.replace("：", "").replace(":", "").trim().substring(2);
            }

            legalOpinionEntity.setFvCompanyName(companyName);
            legalOpinionEntity.setFvLowerFirm(lawFirmName);
        } catch (Exception e) {
            System.out.println("律师事务所这，我估计又是数组下标越界");
        }
        return legalOpinionEntity;
    }

    /**
     * 第二次清洗公司名称
     *
     * @param legalOpinionEntity
     * @param pdfContent
     * @return
     */
    public LegalOpinionEntity setCompanyAndLawFirmName2(LegalOpinionEntity legalOpinionEntity, String pdfContent) {
        String companyName = "";
        String lawFirm = "";
        try {
            ArrayList<String> gs = new ArrayList<String>();
            gs.add("关于.*公司");
            gs.add("致.*公司");
            String start2 = RegularUtils.ifFullinterceptionFromText(pdfContent, gs).replace("\n", "").replace("\r", "");

            if (start2 != null && !(start2.equals("")))
                if (start2.contains("致") || start2.contains("关于"))
                    companyName = start2.replace("：", "").replace(":", "").replace("关于", "").replace("致", "").trim();
            legalOpinionEntity.setFvCompanyName(companyName);
        } catch (Exception e) {
            System.out.println("公司名称这，我估计又是数组下标越界");
        }
        try {
            ArrayList<String> ls = new ArrayList<String>();
            ls.add(".*律师.*事务所");
            String start2 = RegularUtils.ifFullinterceptionFromText(pdfContent, ls).replace("\n", "").replace("\r", "");

            if (start2 != null && !(start2.equals("")))
                if (start2.contains("律师") || start2.contains("事务所"))
                    lawFirm = start2.replace("：", "").replace(":", "").replace("关于", "").replace("致", "").trim();
            legalOpinionEntity.setFvLowerFirm(lawFirm);
        } catch (Exception e) {
            System.out.println("律师事务所这，我估计又是数组下标越界");
        }
        return legalOpinionEntity;
    }

    /**
     * 清洗负责人
     * @param legalOpinionEntity
     * @param pdfContent
     * @return
     */
    public LegalOpinionEntity setPrincipal(LegalOpinionEntity legalOpinionEntity, String pdfContent) {
        //pdfContent=pdfContent.substring(pdfContent.length()/2);
        if (pdfContent.contains("结论意见")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("结论意见"));
        } else if (pdfContent.contains("结论性意见")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("结论性意见"));
        } else if (pdfContent.contains("意见")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("意见"));
        } else if (pdfContent.contains("结论")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("结论"));
        }

        try {
            ArrayList<String> startList = new ArrayList<String>();
            startList.add("负责人:(\\s){0,4}[\\u4E00-\\u9FA5]{2,4}");
            startList.add("负责人：(\\s){0,4}[\\u4E00-\\u9FA5]{2,4}");
            startList.add("负责人(\\s){0,4}[\\u4E00-\\u9FA5]{2,4}");
            startList.add("负责人:[\\u4E00-\\u9FA5]{2,4}");
            startList.add("负责人[\\u4E00-\\u9FA5]{2,4}");
            startList.add("负责人：[\\u4E00-\\u9FA5]{2,4}");
            startList.add("主任:[\\u4E00-\\u9FA5]{2,4}");
            startList.add("主任[\\u4E00-\\u9FA5]{2,4}");
            startList.add("主任：[\\u4E00-\\u9FA5]{2,4}");
            String start = RegularUtils.ifFullinterceptionFromText(pdfContent, startList).replace("\n", "").replace("\r", "").trim();
            if (start != null && !("".equals(start.trim()))) {
                if (start.contains(":")) {
                    start = start.substring(start.indexOf(":") + 1).trim();
                } else if (start.contains("：")) {
                    start = start.substring(start.indexOf("：") + 1).trim();
                } else if (start.contains(" ")) {
                    start = start.substring(start.indexOf(" ") + 1).trim();
                }
            }
            if (start == null || start.equals("")) {

            }
            legalOpinionEntity.setFvChargePerson(start);


            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("经办律师:[\\u4E00-\\u9FA5]{2,4}");
            arrayList.add("经办律师：[\\u4E00-\\u9FA5]{2,4}");

            arrayList.add("经办律师:(\\s){0,4}[\\u4E00-\\u9FA5]{2,4}");
            arrayList.add("经办律师：(\\s){0,4}[\\u4E00-\\u9FA5]{2,4}");

//            arrayList.add("(.){1}办律师:(\\s){0,4}[\\u4E00-\\u9FA5]{2,4}");
//            arrayList.add("(.){1}办律师：(\\s){0,4}[\\u4E00-\\u9FA5]{2,4}");

            arrayList.add("经办律师:(\\s){0,4}([\\u4E00-\\u9FA5]{1}(\\s)){2,3}");
            arrayList.add("经办律师：(\\s){0,4}([\\u4E00-\\u9FA5]{1}(\\s)){2,3}");

            String lvshi = RegularUtils.FullinterceptionFromText(pdfContent, arrayList).replace("\n", "").replace("\r", "").trim();
            String[] str = null;
            if (lvshi != null && !("".equals(lvshi.trim()))) {
                if (lvshi.contains("/")) {
                    str = lvshi.split("/");
                }
                for (int k = 0; k < str.length; k++) {
                    if (str[k].contains(":")) {
                        str[k] = str[k].substring(str[k].indexOf(":") + 1).trim();
                    } else if (str[k].contains("：")) {
                        str[k] = str[k].substring(str[k].indexOf("：") + 1).trim();
                    } else if (str[k].contains(" ")) {
                        str[k] = str[k].substring(str[k].indexOf(" ") + 1).trim();
                    }
                }

            }
            if (str != null) {
                for (int i = 0; i < str.length; i++) {
                    switch (i) {
                        case 0:
                            legalOpinionEntity.setFvLower1(str[i]);
                            break;
                        case 1:
                            legalOpinionEntity.setFvLower2(str[i]);
                            break;
                        case 2:
                            legalOpinionEntity.setFvLower3(str[i]);
                            break;
                        case 3:
                            legalOpinionEntity.setFvLower4(str[i]);
                            break;
                        case 4:
                            legalOpinionEntity.setFvLower5(str[i]);
                            break;
                        default:
                            legalOpinionEntity.setFvLower1(str[0]);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("负责人这里出了错误");
        }
        return legalOpinionEntity;
    }

    /**
     * 获取经办律师
     *
     * @param legalOpinionEntity
     * @param pdfContent
     * @return
     */
    public LegalOpinionEntity setFvLower(LegalOpinionEntity legalOpinionEntity, String pdfContent) {

        try {
            ArrayList<String> startList = new ArrayList<String>();
            startList.add("经办律师:(\\s){0,4}[\\u4E00-\\u9FA5]{2,4}");
            startList.add("经办律师：(\\s){0,4}[\\u4E00-\\u9FA5]{2,4}");
            startList.add(".*办律师(\\s){0,4}[\\u4E00-\\u9FA5]{2,4}");
            String start = RegularUtils.ifFullinterceptionFromText(pdfContent, startList).replace("\n", "").replace("\r", "").trim();
            if (start != null && !("".equals(start.trim()))) {
                if (start.equals(":")) {
                    start = start.substring(start.indexOf(":") + 1);
                } else if (start.equals("：")) {
                    start = start.substring(start.indexOf(":") + 1);
                } else if (start.equals(" ")) {
                    start = start.substring(start.indexOf(":") + 1).trim();
                }
            }
            legalOpinionEntity.setFvLower1(start);
        } catch (Exception e) {
            System.out.println("律师这这里出了错误");
        }
        return legalOpinionEntity;
    }

    /**
     * 第二次清洗经办律师和负责人
     * @param legalOpinionEntity
     * @param pdfContent
     * @return
     */
    public LegalOpinionEntity setFvLowerAndPrincipal(LegalOpinionEntity legalOpinionEntity, String pdfContent){
        pdfContent = pdfContent.substring(pdfContent.length() / 2);
        if (pdfContent.contains("结论意见")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("结论意见"));
        } else if (pdfContent.contains("结论性意见")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("结论性意见"));
        } else if (pdfContent.contains("意见")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("意见"));
        } else if (pdfContent.contains("结论")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("结论"));
        }
        if(pdfContent.lastIndexOf(legalOpinionEntity.getFvLowerFirm())!=-1){
            pdfContent=pdfContent.substring(pdfContent.lastIndexOf(legalOpinionEntity.getFvLowerFirm()));
        }
        pdfContent=pdfContent.substring(pdfContent.lastIndexOf("律师事务所"));
        ArrayList<String> arrayList=new ArrayList<String>();
        arrayList.add(".*年.*月.*日");
        String age=RegularUtils.ifFullinterceptionFromText(pdfContent,arrayList);
        if (age!=null&&age!=""){
            pdfContent=pdfContent.substring(0,pdfContent.lastIndexOf(age));
        }
        int year=pdfContent.lastIndexOf("年");
        int mouth=pdfContent.lastIndexOf("月");
        int day=pdfContent.lastIndexOf("日");
        if((year!=-1)&&(mouth!=-1)&&(day!=-1)){
            if (((day-10)<mouth)&&(mouth<(year+10))){
                pdfContent=pdfContent.substring(0,year);
            }
        }
        String single="()（）【】[]{}<>《》、：:-_•⼆〇";
        String group=legalOpinionEntity.getFvLowerFirm()+"-"+"签字-盖章-签名-公章-经办律师-负责人-单位负责人-律师-章-承办律师-主办律师-见证律师" +
                "-签字律师-执行律师-律  师-经办人-经 办 律 师-承 办 律 师-主 办 律 师-执 行 律 师-签 字 律 师-签章-签 章-盖 章-公 章" +
                "-签 字-签署-签 署-中国-中 国-日期-日 期-参加-出席-列席-指派-委派-律师事务所负责人-执业律师-主任-负-则-人";
        pdfContent=Wutils.replaceGarbage(pdfContent,single,group);
        String[] names=pdfContent.split("\\s+");
        //去除空格
        ArrayList<String> arrayList1=new ArrayList<String>();
        for (int i=0;i<names.length;i++){
            if((names[i]!=null)&&(names[i].trim()!="")&&(names[i].matches("[\\u4E00-\\u9FA5]{1,3}"))){
                arrayList1.add(names[i]);
            }
        }
        for (int j=0;j<arrayList1.size();j++){
            switch (j) {
                case 0:
                    legalOpinionEntity.setFvChargePerson(arrayList1.get(j));
                    break;
                case 1:
                    legalOpinionEntity.setFvLower1(arrayList1.get(j));
                    break;
                case 2:
                    legalOpinionEntity.setFvLower2(arrayList1.get(j));
                    break;
                case 3:
                    legalOpinionEntity.setFvLower3(arrayList1.get(j));
                    break;
                case 4:
                    legalOpinionEntity.setFvLower4(arrayList1.get(j));
                    break;
                case 5:
                    legalOpinionEntity.setFvLower5(arrayList1.get(j));
                default:
                    legalOpinionEntity.setFvLower5(legalOpinionEntity.getFvLower5().concat(arrayList1.get(j)));
            }
        }
        return  legalOpinionEntity;
    }
    /**
     * 获取签字年限
     *
     * @param legalOpinionEntity
     * @param pdfContent
     * @return
     */
    public LegalOpinionEntity setYear(LegalOpinionEntity legalOpinionEntity, String pdfContent) {
        try {
            ArrayList<String> arrayList = new ArrayList<String>();
            arrayList.add("(\\W){4}年(\\W){1,2}月(\\W){1,2}日");
            arrayList.add("(.){1,4}年(.){1,2}月(.){1,3}日");
            pdfContent = pdfContent.substring(pdfContent.length() / 2);
            if (pdfContent.contains("结论意见")) {
                pdfContent = pdfContent.substring(pdfContent.lastIndexOf("结论意见"));
            } else if (pdfContent.contains("结论性意见")) {
                pdfContent = pdfContent.substring(pdfContent.lastIndexOf("结论性意见"));
            } else if (pdfContent.contains("意见")) {
                pdfContent = pdfContent.substring(pdfContent.lastIndexOf("意见"));
            } else if (pdfContent.contains("结论")) {
                pdfContent = pdfContent.substring(pdfContent.lastIndexOf("结论"));
            }
            String start = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList).replace("\n", "").replace("\r", "");
            //start = Wutils.replaceStrToNum(start);
            legalOpinionEntity.setFvDate(start);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return legalOpinionEntity;

    }
}
