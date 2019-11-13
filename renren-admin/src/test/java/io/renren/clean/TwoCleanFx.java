package io.renren.clean;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.PDFReader;
import io.renren.common.utils.RegularUtils;

import io.renren.modules.derive.entity.XsIssueEntity;
import io.renren.modules.derive.service.XsIssueService;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//对一次扫描以后还为空的数据进行再次扫描添加

@RunWith(SpringRunner.class)
@SpringBootTest
public class TwoCleanFx {

    @Autowired
    XsIssueService xsIssueService;//股票发行

    /**
     * 获取文件名
     * @param xsIssueEntity 实体
     * @param fileName  文件名称
     * @return
     */
    public XsIssueEntity getFileTitle(XsIssueEntity xsIssueEntity,String fileName){
         String str=fileName.substring(fileName.indexOf("]")+1,fileName.lastIndexOf("+")).trim();
         xsIssueEntity.setFileTitle(str);
         return xsIssueEntity;
    }

    /**
     * 获取发行人
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public XsIssueEntity getCompanyMain(String pdfContent,XsIssueEntity xsIssueEntity){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(".*有限公司");
        String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        if(str!=null||str!=""){
            str.trim();
            xsIssueEntity.setCompanyMain(str);
        }
        return xsIssueEntity;
    }
    /**
     * 获取法定代表人
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public  XsIssueEntity getrepresentativeman(String pdfContent, XsIssueEntity xsIssueEntity) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("法定代表人：.*");
        String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        if(str!=null||str!=""){
            str.trim();
            xsIssueEntity.setRepresentativeMan(str.substring(6));
        }else{
            xsIssueEntity.setRepresentativeMan("");
        }
//        arrayList.add("法定代表人:*");
//        StringBuffer stringBuffer = new StringBuffer();
//        for (int i=0;i<arrayList.size(); i++) {
//
//
//            //String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
//            Pattern pattern = Pattern.compile(arrayList.get(i));
//            Matcher matcher = pattern.matcher(pdfContent);
//            while (matcher.find()) {
//                stringBuffer.append(matcher.group() + "/");
//            }
//            if (stringBuffer != null || stringBuffer.equals("") ) {
//                String str=stringBuffer.toString().trim();
//                if (!str.contains("/")){
//                    xsIssueEntity.setCompanyMain(str);
//                }else {
//                    xsIssueEntity.setCompanyMain(str.split("/")[0]);
//                }
//            }
//        }
        return xsIssueEntity;
    }

    /**
     * 获取董事会秘书
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public  XsIssueEntity getsec_man(String pdfContent,XsIssueEntity xsIssueEntity){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("董事会秘书.*");
        arrayList.add(".*露负责人.*");
        arrayList.add("披露负责人.*");
        arrayList.add("董事会秘书 .*");
        arrayList.add("信息披露义务人.*");
        arrayList.add("信息披露.*\\s.*责人.*");
        String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        //str.substring()
        if(str!=null||str!=""){
            str.trim();
            if(str.contains("："))
            {
                xsIssueEntity.setSecMan(str.substring(str.indexOf("：")+1));
            }else {
                xsIssueEntity.setSecMan(str.substring(str.indexOf(":")+1));
            }

        }
        return xsIssueEntity;
    }

    /**
     * 获取年月和发布日期
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public  XsIssueEntity getYear(String pdfContent,XsIssueEntity xsIssueEntity){
       ArrayList<String> arrayList = new ArrayList<String>();
       arrayList.add("(\\W){4}年(\\W){1,2}月");
       String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        if(str!=null||str!=""){
            str.trim();
            xsIssueEntity.setReleaseTime(str);
            str=str.replace("零","0")
                    .replace("〇","0")
                    .replace("一","1")
                    .replace("二","2")
                    .replace("三","3")
                    .replace("四","4")
                    .replace("五","5")
                    .replace("六","6")
                    .replace("七","7")
                    .replace("八","8")
                    .replace("九","9");

            xsIssueEntity.setProjectYear(Integer.valueOf(str.substring(0,str.indexOf("年")))-1+"");
        }
        return xsIssueEntity;
    }

    /**
     * 获取律师事务所的名称
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public XsIssueEntity getaccounting(String pdfContent,XsIssueEntity xsIssueEntity){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("会计师事务所.*");
        String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        if(str!=null||str!=""){
            str.trim();
            if(!("会计师事务所".equals(str)))
            {
                xsIssueEntity.setCompanyMain(str.substring(7));
            }
            else {
                arrayList.clear();
                arrayList.add("名称");
                str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
                if(str!=null||str!=""){
                    str.trim();
                    xsIssueEntity.setCompanyMain(str.substring(3));
                }
            }

        }
        return xsIssueEntity;
    }

    /**
     * 获取经办会计师
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public  XsIssueEntity getOrderAccountMan(String pdfContent,XsIssueEntity xsIssueEntity) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(".*项目会计师.*");
        arrayList.add("经办注册会计师.*");
        arrayList.add("会\\s计\\s师.*");
        arrayList.add("签字.*会计师.*");
        arrayList.add("会计师签字.*");
        arrayList.add("经办会计师.*\\、.*");
        arrayList.add("经办会计师.*");
        arrayList.add("注册会计师.*");
        arrayList.add("经办注册会计师.*\\、.*");
        arrayList.add("经办注册会计师.*");
        arrayList.add("会.*计.*师：.*");
        arrayList.add("会.*计.*师:.*");
        arrayList.add("经办会计：.*");
        String accountMan = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList).replace("、", "-");
        String[] str=accountMan.split("-");
        for (int i=0;i<str.length;i++){
            switch (i){
                case 0:xsIssueEntity.setAccountMan1(str[i]); break;
                case 1:xsIssueEntity.setAccountMan2(str[i]); break;
                case 2:xsIssueEntity.setAccountMan3(str[i]); break;
                case 3:xsIssueEntity.setAccountMan4(str[i]); break;
                case 4:xsIssueEntity.setAccountMan5(str[i]); break;
                case 5:xsIssueEntity.setAccountMan6(str[i]); break;
                case 6:xsIssueEntity.setAccountMan7(str[i]); break;
                case 7:xsIssueEntity.setAccountMan8(str[i]); break;
                default:xsIssueEntity.setAccountMan1(str[0]);
            }
        }
        return xsIssueEntity;
    }

    /**
     *  主办卷商,主办卷商的负责人，律所名称
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public XsIssueEntity getSponsonMan(String pdfContent,XsIssueEntity xsIssueEntity) {
        ArrayList<String> startList = new ArrayList<String>();
        startList.add("主办券商：.*证券.*公司.*\\s.*法定.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*住所.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*负责.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*执行.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*法定.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*住所.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*负责.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*执行.*");
        startList.add("名称:.*证券.*公司.*\\s.*法定.*");
        startList.add("名称:.*证券.*公司.*\\s.*住所.*");
        startList.add("名称:.*证券.*公司.*\\s.*负责.*");
        startList.add("名称:.*证券.*公司.*\\s.*执行.*");
        startList.add("名称：.*证券.*公司.*\\s.*法定.*");
        startList.add("名称：.*证券.*公司.*\\s.*住所.*");
        startList.add("名称：.*证券.*公司.*\\s.*负责.*");
        startList.add("名称：.*证券.*公司.*\\s.*执行.*");
        startList.add("主办券商 .*证券.*公司\\s.*法定.*");
        startList.add("主办券商 .*证券.*公司\\s.*住所.*");
        startList.add("主办券商 .*证券.*公司\\s.*负责.*");
        startList.add("主办券商 .*证券.*公司\\s.*执行.*");
        String start = RegularUtils.ifFullinterceptionFromText(pdfContent, startList);
        if(start.contains("名称")){
            start=start.trim().replace("、","-").substring(3);
        }else if(start.contains("主办券商")){
            start=start.trim().replace("、","-").substring(5);
        }
        xsIssueEntity.setSponsorNew(start);
        ArrayList<String> endList = new ArrayList<String>();
        endList.add("律师事务所：.*律师事务所.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*法定.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*住所.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*负责.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*执行.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*法定.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*住所.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*负责.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*执行.*");
        endList.add("名称:.*律师事务所\\s.*法定.*");
        endList.add("名称:.*律师事务所\\s.*住所.*");
        endList.add("名称:.*律师事务所\\s.*执行.*");
        endList.add("名称:.*律师事务所\\s.*负责.*");
        endList.add("名称：.*律师事务所\\s.*法定.*");
        endList.add("名称：.*律师事务所\\s.*住所.*");
        endList.add("名称：.*律师事务所\\s.*执行.*");
        endList.add("名称：.*律师事务所\\s.*负责.*");
        endList.add("律师事务所 .*律师事务所\\s.*法定.*");
        endList.add("律师事务所 .*律师事务所\\s.*住所.*");
        endList.add("律师事务所 .*律师事务所\\s.*执行.*");
        endList.add("律师事务所 .*律师事务所\\s.*负责.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*法定.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*执行.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*住所.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*负责.*");
        String end = RegularUtils.ifFullinterceptionFromText(pdfContent, endList);
        if(end.contains(":")){
            end=end.trim().substring(end.indexOf(":"));
        }else if(end.contains("：")){
            end=end.trim().substring(end.indexOf("："));
        }
        xsIssueEntity.setLawyerNew(end);
        String errContent = "";
        try {
            errContent = pdfContent.substring(pdfContent.lastIndexOf(start), pdfContent.lastIndexOf(end));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("项目经办人.*");
        list2.add("项目负责人.*");
        list2.add("项目联系人.*");
        list2.add("项目小组负责人.*");
        String lawyerMan = RegularUtils.ifFullinterceptionFromText(errContent, list2);
        if(lawyerMan.contains(":")){
            lawyerMan=lawyerMan.substring(lawyerMan.indexOf(":")+1);
        }else if(lawyerMan.contains("：")){
            lawyerMan=lawyerMan.substring(lawyerMan.indexOf("：")+1);
        }
        String[] str=lawyerMan.split("、");
        for (int i=0;i<str.length;i++){
            switch (i){
                case 0:xsIssueEntity.setSponsorMan1(str[i]); break;
                case 1:xsIssueEntity.setSponsorMan2(str[i]); break;
                case 2:xsIssueEntity.setSponsorMan3(str[i]); break;
                case 3:xsIssueEntity.setSponsorMan4(str[i]); break;
                case 4:xsIssueEntity.setSponsorMan5(str[i]); break;
                case 5:xsIssueEntity.setSponsorMan6(str[i]); break;
                case 6:xsIssueEntity.setSponsorMan7(str[i]); break;
                case 7:xsIssueEntity.setSponsorMan8(str[i]); break;
                default:xsIssueEntity.setSponsorMan1(str[0]);
            }
        }
        return xsIssueEntity;
    }

    /**
     * 获取经办律师
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public  XsIssueEntity  getLawyerMan(String pdfContent,XsIssueEntity xsIssueEntity) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("经办律师.*");
        arrayList.add(".*经.*办.*律.*师.*");
        arrayList.add(".*律.*师：.*");
        arrayList.add(".*项.*目.*律.*师：.*");
        RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        String content = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList).replace("、", "-").replace("项目律师：", "")
                .replace("经办律师：", "").replace("经办律师", "");
        String[] str=content.split("-");
        for (int i=0;i<str.length;i++){
            switch (i){
                case 0:xsIssueEntity.setLawyerMan1(str[i]); break;
                case 1:xsIssueEntity.setLawyerMan2(str[i]); break;
                case 2:xsIssueEntity.setLawyerMan3(str[i]); break;
                case 3:xsIssueEntity.setLawyerMan4(str[i]); break;
                case 4:xsIssueEntity.setLawyerMan5(str[i]); break;
                case 5:xsIssueEntity.setLawyerMan6(str[i]); break;
                case 6:xsIssueEntity.setLawyerMan7(str[i]); break;
                case 7:xsIssueEntity.setLawyerMan8(str[i]); break;
                default:xsIssueEntity.setAccountMan1(str[0]);
            }
        }
        return xsIssueEntity;
    }
    public XsIssueEntity get(String pdfContent,XsIssueEntity xsIssueEntity){
        ArrayList<String> arrayList = new ArrayList<String>();
        String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        if(str!=null||str!=""){
            str.trim();
            xsIssueEntity.setCompanyMain(str);
        }
        return xsIssueEntity;
    }
    @Test
    public void func() throws Exception {
         String filePath="F:\\pdf";
         List<File> list= Arrays.asList(new File(filePath).listFiles());
        int i=10;
        String str=PDFReader.paserPDFFileByPdfBox(list.get(i));
        TwoCleanFx twoCleanFx=new TwoCleanFx();
        XsIssueEntity xsIssueEntity=new XsIssueEntity();
        twoCleanFx.getFileTitle(xsIssueEntity,list.get(i).getName());
        System.out.println(xsIssueEntity.getFileTitle());
    }

    @Test
    public void getLawyerData() throws Exception {
        List<XsIssueEntity> list = xsIssueService.selectList(new EntityWrapper<XsIssueEntity>().isNull("lawyer_man1").and().isNull("is_image"));
        for (XsIssueEntity faxingEntity : list) {
            int id = faxingEntity.getId();
            String fileName = faxingEntity.getFileTitle();
            File path = new File("Z:\\武杰\\证券会股票发行\\没匹配到律师事务所的\\" + fileName + ".PDF");
            File path1 = new File("Z:\\武杰\\证券会股票发行\\没匹配到律师事务所的");
            String pdfContent = PDFReader.paserPDFFileByPdfBox(path);
            String lawyer = getLawyer(pdfContent);
            String lawyerMan = getLawyerMan(pdfContent);
            String[] lawyerMans = null;
            if (lawyerMan != "" && !("").equals(lawyerMan)) {
                lawyerMans = lawyerMan.split("-");
            }
            String lawyerMan1 = "";
            String lawyerMan2 = "";
            String lawyerMan3 = "";
            String lawyerMan4 = "";
            if (lawyerMans != null) {
                if (lawyerMans.length == 1) {
                    lawyerMan1 = lawyerMans[0];
                }
                if (lawyerMans.length == 2) {
                    lawyerMan1 = lawyerMans[0];
                    lawyerMan2 = lawyerMans[1];
                }
                if (lawyerMans.length == 3) {
                    lawyerMan1 = lawyerMans[0];
                    lawyerMan2 = lawyerMans[1];
                    lawyerMan3 = lawyerMans[2];
                }
                if (lawyerMans.length == 4) {
                    lawyerMan1 = lawyerMans[0];
                    lawyerMan2 = lawyerMans[1];
                    lawyerMan3 = lawyerMans[2];
                    lawyerMan4 = lawyerMans[3];

                }
            }
            if (lawyerMan1 != "" && !("").equals("")) {
                faxingEntity.setLawyerMan1(lawyerMan1);
            }
            if (lawyerMan2 != "" && !("").equals("")) {
                faxingEntity.setLawyerMan2(lawyerMan2);
            }
            if (lawyerMan3 != "" && !("").equals("")) {
                faxingEntity.setLawyerMan3(lawyerMan3);
            }
            if (lawyerMan4 != "" && !("").equals("")) {
                faxingEntity.setLawyerMan4(lawyerMan4);
            }
            faxingEntity.setLawyer(lawyer);
            Boolean b = xsIssueService.updateById(faxingEntity);
            if (b) {
                System.out.println("二次数据修改成功");
            }
            if (lawyer == "") {
                File file1 = new File(path1 + "\\二次清洗律师事务所为空\\");
                if (!file1.exists()) file1.getName();
                try {
                    FileUtils.copyFileToDirectory(path, new File(path1 + "\\二次清洗律师事务所为空\\"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("移动失败");
                }
            }
            if (lawyerMan == "") {
                File file1 = new File(path1 + "\\二次清洗律师事为空\\");
                if (!file1.exists()) file1.getName();
                try {
                    FileUtils.copyFileToDirectory(path, new File(path1 + "\\二次清洗律师事务所为空\\"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("移动失败");
                }
            }
        }
    }

    private String getLawyer(String pdfContent) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("律师事务所.*\\s.*律师事务所");
        arrayList.add("律师：.*律师事务所");
        arrayList.add("机构.*律师.*事务所");
        arrayList.add("名.*称.*律师.*事务所.*");
        String content = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        return content;
    }

    /**
     * 获取经办律师
     * @param pdfContent
     * @return
     */
    private String getLawyerMan(String pdfContent) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("经办律师.*");
        arrayList.add(".*经.*办.*律.*师.*");
        arrayList.add(".*律.*师：.*");
        arrayList.add(".*项.*目.*律.*师：.*");
        System.out.println(RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList));
        String content = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList).replace("、", "-").replace("项目律师：", "")
                .replace("经办律师：", "").replace("经办律师", "");
        return content;
    }

    @Test
    public void getAccountData() throws Exception {
        List<XsIssueEntity> list = xsIssueService.selectList(new EntityWrapper<XsIssueEntity>().notLike("file_title", "半年").and().
                notLike("file_title", "摘要").and().notLike("file_title", "取消").and().notLike("file_title", "的公告").and().isNull("account_man1").and().isNull("is_image"));
        int i = 0;
        for (XsIssueEntity faxingEntity : list) {
            String fileName = faxingEntity.getFileTitle();
            int id = faxingEntity.getId();
            File path = new File("Z:\\武杰\\证券会股票发行\\" + fileName + ".PDF");
            File path1 = new File("Z:\\武杰\\证券会股票发行\\二次清洗未匹配到会计师\\");
            String pdfContent = PDFReader.paserPDFFileByPdfBox(path);
            String accountMan = getAccountMan(pdfContent);
            if (accountMan == "" && accountMan.equals("")) {
                accountMan = getAccountMan2(pdfContent);
                if (accountMan == "" && accountMan.equals("")) {
                    System.out.println("此时进入了截取段落进行匹配的阶段");
                    accountMan = subAcoount(pdfContent);
                    if (accountMan != "") {
                        System.out.println("截取段落匹配匹配到了内容");
                    } else {
                        System.out.println("截取段落未匹配到内容");
                    }
                }
            }
            String[] accountMans = null;
            String accountMan1 = "";
            String accountMan2 = "";
            String accountMan3 = "";
            String accountMan4 = "";
            try {
                accountMans = accountMan.split("-");
                if (accountMans.length == 1) {
                    accountMan1 = accountMans[0];
                }

                if (accountMans.length == 2) {
                    accountMan1 = accountMans[0];
                    accountMan2 = accountMans[1];
                }

                if (accountMans.length == 3) {
                    accountMan1 = accountMans[0];
                    accountMan2 = accountMans[1];
                    accountMan3 = accountMans[2];
                }

                if (accountMans.length == 4) {
                    accountMan1 = accountMans[0];
                    accountMan2 = accountMans[1];
                    accountMan3 = accountMans[2];
                    accountMan4 = accountMans[3];
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!accountMan1.equals("")) {
                faxingEntity.setAccountMan1(accountMan1);
            }
            if (!accountMan2.equals("")) {
                faxingEntity.setAccountMan2(accountMan2);
            }
            if (!accountMan3.equals("")) {
                faxingEntity.setAccountMan3(accountMan3);
            }
            if (!accountMan4.equals("")) {
                faxingEntity.setAccountMan4(accountMan4);
            }
            Boolean b = xsIssueService.updateById(faxingEntity);
            if (accountMan != "") {
                System.out.println("数据修改成功,这是第" + i + "条数据");
            } else {
                File file1 = new File(path1 + "\\二次清洗会计师为空\\");
                if (!file1.exists()) file1.getName();
                try {
                    FileUtils.copyFileToDirectory(path, new File(path1 + "\\二次清洗会计师为空\\"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("移动失败");
                }
                System.out.println("请检查正则，文件名字为" + fileName + "    第" + i + "条数据");
            }
            i++;
        }
    }

    private  String getAccountMan(String pdfContent) {
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(".*项目会计师.*");
        arrayList.add("经办注册会计师.*");
        arrayList.add("会\\s计\\s师.*");
        arrayList.add("签字.*会计师.*");
        arrayList.add("会计师签字.*");
        arrayList.add("经办会计师.*\\、.*");
        arrayList.add("经办会计师.*");
        arrayList.add("注册会计师.*");
        arrayList.add("经办注册会计师.*\\、.*");
        arrayList.add("经办注册会计师.*");
        arrayList.add("会.*计.*师：.*");
        arrayList.add("会.*计.*师:.*");
        arrayList.add("经办会计：.*");
        String accountMan = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList).replace("、", "-");
        return accountMan;
    }

    public static String getAccountMan2(String pdfContent) {
        ArrayList<String> arrayList = new ArrayList<String>();
//        arrayList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*\\s.*住所.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*\\s.*法定.*(\\s.*){8}");
    //        arrayList.add("会计师事务所.*\\s.*负责人.*(\\s.*){8}");
//        arrayList.add("名称.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*\\s.*事务所.*\\s.*住所.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*\\s.*执行.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*会计师事务所.*\\s.*公告.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*会计师事务所.*\\s.*执行.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*会计师事务所.*\\s.*负责.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师事务所.*\\s.*住所.*(\\s.*)");
//        arrayList.add("名.*称.*会计师事务所.*\\s.*执行.*(\\s.*){8}");
//        arrayList.add("名.*称.*会计师事务所.*\\s.*负责.*(\\s.*){8}");
//        arrayList.add("名.*称.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
//        arrayList.add("名.*称.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
//        arrayList.add("名.*称.*会计师事务所.*\\s.*联系.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*会计师.*\\s.*负责.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*会计师.*\\s.*执行.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*会计师.*\\s.*法定.*(\\s.*){8}");
//        arrayList.add("会计师事务所.*会计师.*\\s.*住所.*(\\s.*){8}");
        String errContent = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        ArrayList<String> list = new ArrayList<String>();
        list.add("会计师事务所：.*");
        list.add("名称：.*");
        list.add("会计师事务所:.*");
        list.add("名称:.*");
        String accountMan = RegularUtils.ifFullinterceptionFromText(errContent, list);
        return accountMan;
    }

    public String subAcoount(String pdfContent) {
        try {
            if (pdfContent.contains("会计师事务所")) {
                String account = pdfContent.substring(pdfContent.lastIndexOf("会计师事务所"), pdfContent.lastIndexOf("会计师事务所") + 200);
                ArrayList<String> list = new ArrayList<String>();
                list.add("经办人员.*");
                list.add("经办人.*");
                list.add("项目经办人.*");
                list.add("项目小组成员.*");
                String accountMan = RegularUtils.ifFullinterceptionFromText(account, list);
                return accountMan;
            } else {
                System.out.println("该文件没有包含会计师事务所");
                return "";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Test
    public void getSecManData() throws Exception {
        List<XsIssueEntity> list = xsIssueService.selectList(new EntityWrapper<XsIssueEntity>().notLike("file_title", "半年").and().notLike("file_title", "摘要")
                .and().notLike("file_title", "取消").and().isNull("is_image").and().notLike("file_title", "的公告").and().eq("sec_man", ""));
        int i = 0;
        for (XsIssueEntity faxingEntity : list) {
            String fileName = faxingEntity.getFileTitle();
            int id = faxingEntity.getId();
            File path = new File("Z:\\武杰\\证券会股票发行\\" + fileName + ".PDF");
            File path1 = new File("Z:\\武杰\\证券会股票发行\\二次清洗未匹配到董秘\\");
            String pdfContent = PDFReader.paserPDFFileByPdfBox(path);
            String secMan = getSecMan(pdfContent);
            System.out.println("---------------------这是匹配到的董秘数据:" + secMan + "-----------------------------------");
            if (secMan != "" && !("").equals(secMan)) {
                faxingEntity.setSecMan(secMan);
                Boolean b = xsIssueService.updateById(faxingEntity);
                System.out.println("数据修改成功,这是第" + i + "条数据");
            } else {
                File file1 = new File(path1 + "\\二次清洗未匹配到董秘\\");
                if (!file1.exists()) file1.getName();
                try {
                    FileUtils.copyFileToDirectory(path, new File(path1 + "\\二次清洗未匹配到董秘\\"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("移动失败");
                }
                System.out.println("请检查正则，文件名字为" + fileName + "    第" + i + "条数据");
            }
            i++;
        }
    }
    public static String getSecMan(String pdfContent) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("披.*\\s.*露负责人.*\\s.*");
        arrayList.add(".*露负责人.*\\s.*");
        arrayList.add("披露负责人.*\\s.*");
        arrayList.add("董事会秘书 .*");
        arrayList.add("信息披露义务人.*");
        arrayList.add("信息披露.*\\s.*责人.*");
//        arrayList.add("董秘：.*");
//        arrayList.add("董秘:.*");
        arrayList.add("董事会秘书.*\\s.*负责人.*\\s.*");
        arrayList.add("董事会秘书.*\\s.*");
        arrayList.add("信息披露负责人.*");
//        arrayList.add("信息披露.*：.*");
//        arrayList.add("信息披露.*:.*");
//        arrayList.add("公司董秘：.*");
        arrayList.add("公司董秘:.*");
//        arrayList.add("董事会秘书：.*");
//        arrayList.add("董事会秘书:.*");
//        arrayList.add("董事会秘书.*:.*");
//        arrayList.add("董事会秘书.*：.*");
        arrayList.add("信息披露负责人.*:.*");
        arrayList.add("信息披露负责人.*：.*");
        arrayList.add("信息披露人.*");
        String sec_man = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        return sec_man;
    }

    @Test
    public void getSponsorManData() throws Exception {
        List<XsIssueEntity> list = xsIssueService.selectList(new EntityWrapper<XsIssueEntity>().notLike("file_title", "半年").and()
                .notLike("file_title", "摘要").and().notLike("file_title", "取消").and().isNull("is_image").and().notLike("file_title", "的公告")
                .isNull("sponsor_man1"));
        int i = 0;
        for (XsIssueEntity faxingEntity : list) {
            String fileName = faxingEntity.getFileTitle();
            int id = faxingEntity.getId();
            File path = new File("Z:\\武杰\\证券会股票发行\\" + fileName + ".PDF");
            File path1 = new File("Z:\\武杰\\证券会股票发行\\二次清洗未匹配到保荐人\\");
            String pdfContent = PDFReader.paserPDFFileByPdfBox(path);
            String sponsorMan1 = getSponsonMan1(pdfContent);
            String sponsorMan2 = getSponsonMan2(pdfContent);
            String sponsorMan = sponsorMan1 + sponsorMan2.replace("、", "-");
            sponsorMan = sponsorMan.replace("项目负责人：", "").replace("项目经办人：", "").replace("经办人：", "")
                    .replace("项目组成员：", "").replace("项目组成员（经办人）：", "").replace("经办人员：", "")
                    .replace("项目小组成员：", "").replace("项目成员：", "").replace("、", "-");
            String[] sponsons = new String[5];
            if (sponsorMan != "" && !("").equals(sponsorMan)) {
                sponsons = sponsorMan.split("-");
            }
            String spon1 = "";
            String spon2 = "";
            String spon3 = "";
            String spon4 = "";
            String spon5 = "";
            String spon6 = "";
            if (sponsons.length != 0) {
                if (sponsons.length == 1) {
                    spon1 = sponsons[0];
                }
                if (sponsons.length == 2) {
                    spon1 = sponsons[0];
                    spon2 = sponsons[1];
                }
                if (sponsons.length == 3) {
                    spon1 = sponsons[0];
                    spon2 = sponsons[1];
                    spon3 = sponsons[2];
                }
                if (sponsons.length == 4) {
                    spon1 = sponsons[0];
                    spon2 = sponsons[1];
                    spon3 = sponsons[2];
                    spon4 = sponsons[3];
                }
                if (sponsons.length == 5) {
                    spon1 = sponsons[0];
                    spon2 = sponsons[1];
                    spon3 = sponsons[2];
                    spon4 = sponsons[3];
                    spon5 = sponsons[4];
                }
                if (sponsons.length == 4) {
                    spon1 = sponsons[0];
                    spon2 = sponsons[1];
                    spon3 = sponsons[2];
                    spon4 = sponsons[3];
                    spon5 = sponsons[4];
                    spon6 = sponsons[5];
                }
            }
            if (spon1 != "" && !("").equals(spon1)) {
                faxingEntity.setSponsorMan1(spon1);
            }
            if (spon2 != "" && !("").equals(spon2)) {
                faxingEntity.setSponsorMan2(spon2);
            }
            if (spon3 != "" && !("").equals(spon3)) {
                faxingEntity.setSponsorMan3(spon3);
            }
            if (spon4 != "" && !("").equals(spon4)) {
                faxingEntity.setSponsorMan4(spon4);
            }
            if (spon5 != "" && !("").equals(spon5)) {
                faxingEntity.setSponsorMan6(spon5);
            }
            if (spon6 != "" && !("").equals(spon6)) {
                faxingEntity.setSponsorMan6(spon6);
            }
            if (sponsorMan != "") {
                Boolean b = xsIssueService.updateById(faxingEntity);
                if (b) {
                    System.out.println("数据修改成功,这是第" + i + "条数据");
                }
            } else {
                File file1 = new File(path1 + "\\二次清洗未匹配到董秘\\");
                if (!file1.exists()) file1.getName();
                try {
                    FileUtils.copyFileToDirectory(path, new File(path1 + "\\二次清洗未匹配到保荐人\\"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("移动失败");
                }
                System.out.println("请检查正则，文件名字为" + fileName + "    第" + i + "条数据");
            }
            i++;
        }
    }
    public String getSponsonMan1(String pdfContent) {
        ArrayList<String> startList = new ArrayList<String>();
        startList.add("主办券商：.*证券.*公司.*\\s.*法定.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*住所.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*负责.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*执行.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*法定.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*住所.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*负责.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*执行.*");
        startList.add("名称:.*证券.*公司.*\\s.*法定.*");
        startList.add("名称:.*证券.*公司.*\\s.*住所.*");
        startList.add("名称:.*证券.*公司.*\\s.*负责.*");
        startList.add("名称:.*证券.*公司.*\\s.*执行.*");
        startList.add("名称：.*证券.*公司.*\\s.*法定.*");
        startList.add("名称：.*证券.*公司.*\\s.*住所.*");
        startList.add("名称：.*证券.*公司.*\\s.*负责.*");
        startList.add("名称：.*证券.*公司.*\\s.*执行.*");
        startList.add("主办券商 .*证券.*公司\\s.*法定.*");
        startList.add("主办券商 .*证券.*公司\\s.*住所.*");
        startList.add("主办券商 .*证券.*公司\\s.*负责.*");
        startList.add("主办券商 .*证券.*公司\\s.*执行.*");
        String start = RegularUtils.ifFullinterceptionFromText(pdfContent, startList);
        ArrayList<String> endList = new ArrayList<String>();
        endList.add("律师事务所：.*律师事务所.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*法定.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*住所.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*负责.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*执行.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*法定.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*住所.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*负责.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*执行.*");
        endList.add("名称:.*律师事务所\\s.*法定.*");
        endList.add("名称:.*律师事务所\\s.*住所.*");
        endList.add("名称:.*律师事务所\\s.*执行.*");
        endList.add("名称:.*律师事务所\\s.*负责.*");
        endList.add("名称：.*律师事务所\\s.*法定.*");
        endList.add("名称：.*律师事务所\\s.*住所.*");
        endList.add("名称：.*律师事务所\\s.*执行.*");
        endList.add("名称：.*律师事务所\\s.*负责.*");
        endList.add("律师事务所 .*律师事务所\\s.*法定.*");
        endList.add("律师事务所 .*律师事务所\\s.*住所.*");
        endList.add("律师事务所 .*律师事务所\\s.*执行.*");
        endList.add("律师事务所 .*律师事务所\\s.*负责.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*法定.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*执行.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*住所.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*负责.*");
        String end = RegularUtils.ifFullinterceptionFromText(pdfContent, endList);
        String errContent = "";
        try {
            errContent = pdfContent.substring(pdfContent.lastIndexOf(start), pdfContent.lastIndexOf(end));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("项目经办人.*");
        list2.add("项目负责人.*");
        list2.add("项目联系人.*");
        list2.add("项目小组负责人.*");
        String lawyerMan = RegularUtils.ifFullinterceptionFromText(errContent, list2);
        System.out.println(start);
        System.out.println(end);
        System.out.println(lawyerMan);
        return lawyerMan;
    }
    public String getSponsonMan2(String pdfContent) {
        ArrayList<String> startList = new ArrayList<String>();
        startList.add("主办券商：.*证券.*公司.*\\s.*法定.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*住所.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*负责.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*执行.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*法定.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*住所.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*负责.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*执行.*");
        startList.add("名称:.*证券.*公司.*\\s.*法定.*");
        startList.add("名称:.*证券.*公司.*\\s.*住所.*");
        startList.add("名称:.*证券.*公司.*\\s.*负责.*");
        startList.add("名称:.*证券.*公司.*\\s.*执行.*");
        startList.add("名称：.*证券.*公司.*\\s.*法定.*");
        startList.add("名称：.*证券.*公司.*\\s.*住所.*");
        startList.add("名称：.*证券.*公司.*\\s.*负责.*");
        startList.add("名称：.*证券.*公司.*\\s.*执行.*");
        startList.add("主办券商 .*证券.*公司\\s.*法定.*");
        startList.add("主办券商 .*证券.*公司\\s.*住所.*");
        startList.add("主办券商 .*证券.*公司\\s.*负责.*");
        startList.add("主办券商 .*证券.*公司\\s.*执行.*");
        String start = RegularUtils.ifFullinterceptionFromText(pdfContent, startList);
        ArrayList<String> endList = new ArrayList<String>();
        endList.add("律师事务所：.*律师事务所.*\\s.*法定.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*住所.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*负责.*");
        endList.add("律师事务所：.*律师事务所.*\\s.*执行.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*法定.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*住所.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*负责.*");
        endList.add("律师事务所:.*律师事务所.*\\s.*执行.*");
        endList.add("名称:.*律师事务所\\s.*法定.*");
        endList.add("名称:.*律师事务所\\s.*住所.*");
        endList.add("名称:.*律师事务所\\s.*执行.*");
        endList.add("名称:.*律师事务所\\s.*负责.*");
        endList.add("名称：.*律师事务所\\s.*法定.*");
        endList.add("名称：.*律师事务所\\s.*住所.*");
        endList.add("名称：.*律师事务所\\s.*执行.*");
        endList.add("名称：.*律师事务所\\s.*负责.*");
        endList.add("律师事务所 .*律师事务所\\s.*法定.*");
        endList.add("律师事务所 .*律师事务所\\s.*住所.*");
        endList.add("律师事务所 .*律师事务所\\s.*执行.*");
        endList.add("律师事务所 .*律师事务所\\s.*负责.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*法定.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*执行.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*住所.*");
        endList.add("律师事务所.*\\s.*律师事务所\\s.*负责.*");
        String end = RegularUtils.ifFullinterceptionFromText(pdfContent, endList);
        String errContent = "";
        try {
            errContent = pdfContent.substring(pdfContent.lastIndexOf(start), pdfContent.lastIndexOf(end));
        } catch (Exception e) {
            e.printStackTrace();
        }
        ArrayList<String> list2 = new ArrayList<String>();
        list2.add("项目组员.*");
        list2.add("项目组成员.*");
        list2.add(".*项目.*成员.*");
        list2.add("项目小组成员.*");
        String lawyerMan = RegularUtils.ifFullinterceptionFromText(errContent, list2);
        return lawyerMan;
    }

    @Test
    public void getFarenData() throws Exception {
        List<XsIssueEntity> list = xsIssueService.selectList(new EntityWrapper<XsIssueEntity>().notLike("file_title", "半年").and().notLike("file_title", "摘要")
                .and().notLike("file_title", "取消").and().isNull("is_image").and().notLike("file_title", "的公告").and().eq("representative_man", ""));
        int i = 0;
        for (XsIssueEntity faxingEntity : list) {
            String fileName = faxingEntity.getFileTitle();
            int id = faxingEntity.getId();
            File path = new File("Z:\\武杰\\证券会股票发行\\" + fileName + ".PDF");
            File path1 = new File("Z:\\武杰\\证券会股票发行\\二次清洗未匹配到董秘\\");
            String pdfContent = PDFReader.paserPDFFileByPdfBox(path);
            String faren = getFaRen(pdfContent);
            if(faren!=""){
                try {
                    faxingEntity.setRepresentativeMan(faren);
                    Boolean b = xsIssueService.updateById(faxingEntity);
                    System.out.println("数据修改成功,这是第" + i + "条数据");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else {
                File file1 = new File(path1 + "\\二次清洗未匹配到法人\\");
                if (!file1.exists()) file1.getName();
                try {
                    FileUtils.copyFileToDirectory(path, new File(path1 + "\\二次清洗未匹配到法人\\"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("移动失败");
                }
                System.out.println("请检查正则，文件名字为" + fileName + "    第" + i + "条数据");
            }
            i++;
        }

    }
    public String getFaRen(String pdfContent){
        ArrayList arrayList=new ArrayList();
        arrayList.add("法定代表人.*");
        arrayList.add("法定代表人.*\\s.*");
        String faRen= RegularUtils.ifFullinterceptionFromText(pdfContent,arrayList).replace("：","");
        if(faRen.contains("法定代表人： ")){
            faRen=faRen.replace("法定代表人： ","");
        }else if(faRen.contains("法定代表人")){
            faRen=faRen.replace("法定代表人","");
        }
        return faRen;
    }
}