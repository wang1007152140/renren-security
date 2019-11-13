package io.renren.clean;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.Wutils.Utils.Wutils;
import io.renren.common.utils.PDFReader;
import io.renren.common.utils.RegularUtils;
import io.renren.modules.flyjs.entity.LegalOpinionEntity;
import io.renren.modules.sjflyjs.entity.ShLegalOpinionEntity;
import io.renren.modules.sjflyjs.service.ShLegalOpinionService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

import static io.renren.common.utils.PdfAnalysisUtils.extractImages;

/**
 * 上交
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class sjLegaIOpinionTest {
    private String filePath = "F:\\pdf2";
    private List<File> fileList = Arrays.asList(new File(filePath).listFiles());
    private Logger logger= LoggerFactory.getLogger(legalOpinionTest.class);
    @Autowired
    private ShLegalOpinionService shLegalOpinionService;

    @Test
    public void func1(){
       List<ShLegalOpinionEntity> list= shLegalOpinionService.selectList(new EntityWrapper<>());
       Set<String> fileName=new HashSet<String>();
       for (int i=0;i<fileList.size();i++){
           fileName.add(fileList.get(i).getName());
       }
       Set<String> listName=new HashSet<String>();
       for (int j=0;j<list.size();j++){
           listName.add(list.get(j).getFvFileName()+".pdf");
       }
       listName.remove(fileName);
        System.out.println(listName.size());
    }

    @Test
    public void func2(){

        List<ShLegalOpinionEntity> list=shLegalOpinionService.selectList(new EntityWrapper<ShLegalOpinionEntity>());
        String targerFolder = "F:\\pdf1\\img\\";
        File file = new File("F:\\pdf2");
        for (int i = 0; i < list.size(); i++) {
            int finalI = i;
            File[] files = file.listFiles(new FileFilter() {
                @Override
                public boolean accept(File pathname) {
                    if (pathname.getName().contains(list.get(finalI).getFvFileName()))
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
                    setCompanyAndLawFirmName(list.get(i),pdfContent);
                    if((list.get(i).getFvLowerFirm()==null||list.get(i).getFvLowerFirm().equals(""))
                            &&(list.get(i).getFvCompanyName()==null||list.get(i).getFvCompanyName().equals(""))){
                        setCompanyAndLawFirmName2(list.get(i),pdfContent);
                    }
                    setFvLowerAndPrincipal(list.get(i),pdfContent);
                    System.out.println(list.get(i).getId() + "" + shLegalOpinionService.updateById(list.get(i)));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /**
     * 清洗律师事务所 和公司
     * @param shLegalOpinionEntity
     * @param pdfContent
     * @return
     */
    public ShLegalOpinionEntity setCompanyAndLawFirmName(ShLegalOpinionEntity shLegalOpinionEntity, String pdfContent) {
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

            shLegalOpinionEntity.setFvCompanyName(lawFirmName);
            shLegalOpinionEntity.setFvLowerFirm(companyName);
        } catch (Exception e) {
            System.out.println("律师事务所这，我估计又是数组下标越界");
        }
        return shLegalOpinionEntity;
    }
    /**
     * 第二次清洗公司名称和律师事务所
     *
     * @param shLegalOpinionEntity
     * @param pdfContent
     * @return
     */
    public ShLegalOpinionEntity setCompanyAndLawFirmName2(ShLegalOpinionEntity shLegalOpinionEntity, String pdfContent) {
        String companyName = "";
        String lawFirm = "";
        try {
            ArrayList<String> gs = new ArrayList<String>();
            gs.add("关于.*公司");
            gs.add("致.*公司");
            String start2 = RegularUtils.ifFullinterceptionFromText(pdfContent, gs).replace("\n", "").replace("\r", "");

            if (start2 != null && !(start2.equals("")))
                if (start2.contains("致") || start2.contains("关于"))
                    companyName = start2.replace("：", "").replace(":", "").replace("关于", "").replace("致", "")
                     .replace("“","").replace("”","")
                            .trim();
            shLegalOpinionEntity.setFvCompanyName(companyName);
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
            shLegalOpinionEntity.setFvLowerFirm(lawFirm);
        } catch (Exception e) {
            System.out.println("律师事务所这，我估计又是数组下标越界");
        }
        return shLegalOpinionEntity;
    }
    /**
     * 第二次清洗经办律师和负责人
     * @param legalOpinionEntity
     * @param pdfContent
     * @return
     */
    public ShLegalOpinionEntity setFvLowerAndPrincipal(ShLegalOpinionEntity legalOpinionEntity, String pdfContent){
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
        if (pdfContent.contains("律师事务所")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("律师事务所"));
        }else if (pdfContent.contains("事务所")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("事务所"));
        }else if (pdfContent.contains("律师")) {
            pdfContent = pdfContent.substring(pdfContent.lastIndexOf("律师"));
        }
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
                "-签 字-签署-签 署-中国-中 国-日期-日 期-参加-出席-列席-指派-委派-律师事务所负责人-执业律师-主任-负-则-人-事务所-事-务-所";
        pdfContent= Wutils.replaceGarbage(pdfContent,single,group);
        pdfContent=Wutils.replacePluralBlank(pdfContent);
        String[] names=pdfContent.split("\\s+");
        //去除空格
        List<String> arrayList1=  Arrays.asList(names);
//        for (int i=0;i<names.length;i++){
//            if((names[i]!=null)&&(names[i].trim()!="")&&(names[i].matches("[\\u4E00-\\u9FA5]{1,3}"))){
//                arrayList1.add(names[i]);
//            }
//        }
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

}
