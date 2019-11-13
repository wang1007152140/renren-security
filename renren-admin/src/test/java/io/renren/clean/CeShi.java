package io.renren.clean;

import io.renren.common.utils.PDFReader;
import io.renren.common.utils.RegularUtils;
import io.renren.modules.derive.entity.XsIssueEntity;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;

public class CeShi {
    @Test
    public void func2()throws Exception{
        File file=new File("F:/pdf",
                "[临时报告]雄伟科技：公开转让说明书-2019-10-15+34782fc8-abd8-4616-891a-01029c3aed96+d40d019c-b4a3-40ac-a2f1-a1eb6250fb1a.pdf");
        String str=PDFReader.paserPDFFileByPdfBox(file);
        System.out.println(str);
    }
    @Test
    public void func1()throws Exception{
        File file=new File("F:\\pdf","[临时公告]ST威奥：股票发行方案（修订）+649ddf0c-e0c9-4cb4-9e8f-c64e829a57de.pdf");
        File file1=new File("F:\\gg");

        FileUtils.moveToDirectory(file,file1,true);
    }
    @Test
    public void func()throws Exception{
        XsIssueEntity xsIssueEntity ;
            File file=new File("F:/pdf",
                    "[临时公告]东杨新材：2019年股票发行方案（第二次修订稿）+be861e17-8e57-4127-8097-4087d4b6fb7e.pdf");
            xsIssueEntity=new XsIssueEntity();
            System.out.println(file.getName());
        System.out.println(file.exists());
            String pdfContent = PDFReader.paserPDFFileByPdfBox(file);
           // getFileTitle(xsIssueEntity,file.getName());//文件名
           // getCompanyMain(pdfContent,xsIssueEntity);//发行人
           // getrepresentativeman(pdfContent,xsIssueEntity);//法定代表人
           // getsec_man( pdfContent, xsIssueEntity);//董事会秘书
            getYear( pdfContent, xsIssueEntity);
//            getaccounting( pdfContent, xsIssueEntity);
//        getOrderAccountMan( pdfContent, xsIssueEntity);
//        getSponsonMan( pdfContent, xsIssueEntity);
//        getLawyerMan( pdfContent, xsIssueEntity);

    }
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
        arrayList.add("法定代表人:.*");
        String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        if(str!=null||str!=""){
            str.trim();
            if(str.contains("法定代表人：")){
                xsIssueEntity.setRepresentativeMan(str.substring(str.indexOf("：")+1));
            }else if(str.contains("法定代表人:")){
                xsIssueEntity.setRepresentativeMan(str.substring(str.indexOf(":")+1));
            }else {
                xsIssueEntity.setRepresentativeMan(str);
            }
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
        arrayList.add("董事会秘书.*:");
        arrayList.add("董事会秘书.*：");
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
    public static XsIssueEntity getYear(String pdfContent,XsIssueEntity xsIssueEntity){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("(\\W){4}年(\\W){1,2}月");
        arrayList.add("(\\d){4}年(\\d){1,2}月");
        String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        if(str!=null&&str!=""){
            str.trim();
            if(!str.contains("2")){
                str=str.replace("零","0")
                        .replace("〇","0")
                        .replace("o","0")
                        .replace("一","1")
                        .replace("二","2")
                        .replace("三","3")
                        .replace("四","4")
                        .replace("五","5")
                        .replace("六","6")
                        .replace("七","7")
                        .replace("八","8")
                        .replace("○","0")
                        .replace("九","9");
                xsIssueEntity.setReleaseTime(str);
                xsIssueEntity.setProjectYear(Integer.valueOf(str.substring(0,str.indexOf("年")))-1+"");
            }
            System.out.println(str+"什么鬼啊");

        }
        return xsIssueEntity;
    }
    /**
     * 获取会计事务所的名称
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public XsIssueEntity getaccounting(String pdfContent,XsIssueEntity xsIssueEntity){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("名.*称.*会计师事务所.*");
        arrayList.add("会计师事务所.*会计师事务所");
        arrayList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*");
        arrayList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*(\\s.*){8}");
        arrayList.add("会计师事务所.*\\s.*住所.*(\\s.*){8}");
        arrayList.add("会计师事务所.*\\s.*法定.*(\\s.*){8}");
        arrayList.add("会计师事务所.*\\s.*负责人.*(\\s.*){8}");
        arrayList.add("名称.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
        arrayList.add("会计师事务所.*\\s.*事务所.*\\s.*住所.*(\\s.*){8}");
        arrayList.add("会计师事务所.*\\s.*执行.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师事务所.*\\s.*公告.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师事务所.*\\s.*执行.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师事务所.*\\s.*负责.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
        arrayList.add("名.*称.*会计师事务所.*\\s.*执行.*(\\s.*){8}");
        arrayList.add("名.*称.*会计师事务所.*\\s.*负责.*(\\s.*){8}");
        arrayList.add("名.*称.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
        arrayList.add("名.*称.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
        arrayList.add("名.*称.*会计师事务所.*\\s.*联系.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师.*\\s.*负责.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师.*\\s.*执行.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师.*\\s.*法定.*(\\s.*){8}");
        arrayList.add("会计师事务所.*会计师.*\\s.*住所.*(\\s.*){8}");
        arrayList.add("会计师事务所:.*");
        arrayList.add("会计师事务所：.*");
        arrayList.add("会计师事务所.*\\s.*名称：");
        arrayList.add("会计师事务所.*\\s.*名称:");
        String errContent = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        ArrayList<String> list = new ArrayList<String>();
        list.add("会计师事务所：.*");
        list.add("名称：.*");
        list.add("会计师事务所:.*");
        list.add("名称:.*");
        String accountMan = RegularUtils.ifFullinterceptionFromText(errContent, list);
        if(accountMan!=null||accountMan!=""){
            accountMan.trim();
//            if(!("会计师事务所".equals(accountMan)))
//            {
//                xsIssueEntity.setAccounting(accountMan.substring(7));
//            }
//            else {
//                arrayList.clear();
//                arrayList.add("名称");
//                accountMan = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
//                if(accountMan!=null||accountMan!=""){
//                    accountMan.trim();
//                    xsIssueEntity.setAccounting(accountMan.substring(3));
//                }
//            }
            if (accountMan.contains("名称")){
                accountMan.trim();
                xsIssueEntity.setAccounting(accountMan.substring(3));
            }else {
                accountMan.trim();
                if(accountMan.contains("：")){
                    xsIssueEntity.setAccounting(accountMan.substring(accountMan.indexOf("：")+1));
                }else{
                    xsIssueEntity.setAccounting(accountMan.substring(accountMan.indexOf(":")+1));
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
        ArrayList<String> startList=new ArrayList<String>();
        startList.add("名.*称.*会计师事务所.*(\\s.*){10}");
        startList.add("会计师事务所.*会计师事务所.*(\\s.*){8}");
        startList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*(\\s.*){8}");
        startList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*(\\s.*){8}");
        startList.add("会计师事务所.*\\s.*住所.*(\\s.*){8}");
        startList.add("会计师事务所.*\\s.*法定.*(\\s.*){8}");
        startList.add("会计师事务所.*\\s.*负责人.*(\\s.*){8}");
        startList.add("名称.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
        startList.add("会计师事务所.*\\s.*事务所.*\\s.*住所.*(\\s.*){8}");
        startList.add("会计师事务所.*\\s.*执行.*(\\s.*){8}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*公告.*(\\s.*){8}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*执行.*(\\s.*){8}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*负责.*(\\s.*){8}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
        startList.add("名.*称.*会计师事务所.*\\s.*执行.*(\\s.*){8}");
        startList.add("名.*称.*会计师事务所.*\\s.*负责.*(\\s.*){8}");
        startList.add("名.*称.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
        startList.add("名.*称.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
        startList.add("名.*称.*会计师事务所.*\\s.*联系.*(\\s.*){8}");
        startList.add("会计师事务所.*会计师.*\\s.*负责.*(\\s.*){8}");
        startList.add("会计师事务所.*会计师.*\\s.*执行.*(\\s.*){8}");
        startList.add("会计师事务所.*会计师.*\\s.*法定.*(\\s.*){8}");
        startList.add("会计师事务所.*会计师.*\\s.*住所.*(\\s.*){8}");
        String str1 = RegularUtils.ifFullinterceptionFromText(pdfContent, startList);

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(".*项目会计师.*");
        arrayList.add("经办注册会计师.*");
        arrayList.add("会\\s计\\s师.*");
        arrayList.add("签字.*会计师.*");
        arrayList.add("会计师签字.*");
        arrayList.add("经办会计师.*\\、.*");
        arrayList.add("经办会计师.*");
        arrayList.add("注册会计师.*");
        arrayList.add("经办注册会计师.*\\s.*");
        arrayList.add("经办注册会计师.*");
        arrayList.add("经办注册会计师：.*");
        arrayList.add("经办注册会计师:.*");
        arrayList.add("会.*计.*师：.*");
        arrayList.add("会.*计.*师:.*");
        arrayList.add("经办会计：.*");
        arrayList.add(".*经办.*人员.*\\s");
        arrayList.add("负责人.*\\s");
        String accountMan = RegularUtils.ifFullinterceptionFromText(str1, arrayList).replace("、", "-");
        String[] str=null;
        if(accountMan.contains(":")){
            str=accountMan.substring(accountMan.indexOf(":")+1).split("-");
        }else if(accountMan.contains("：")){
            str=accountMan.substring(accountMan.indexOf("：")+1).split("-");
        }else {
            str=new String[0];
        }
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
        startList.add("主办券商:.*");
        startList.add("主办券商：.*");
        startList.add("主办券商.*\\s.*名称：");
        startList.add("主办券商.*\\s.*名称:");
        String start = RegularUtils.ifFullinterceptionFromText(pdfContent, startList);
        if(start.contains("名称")){
            if(start.contains(":")){
                start=start.trim().substring(start.indexOf(":")+1);
            }else if(start.contains("：")){
                start=start.trim().substring(start.indexOf("：")+1);
            }

        }else if(start.contains("主办券商")){
            if(start.contains(":")){
                start=start.trim().substring(start.indexOf(":")+1);
            }else if(start.contains("：")){
                start=start.trim().substring(start.indexOf("：")+1);
            }
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
        endList.add("名称:.*律师事务所.*\\s.*法定.*");
        endList.add("名称:.*律师事务所.*\\s.*住所.*");
        endList.add("名称:.*律师事务所.*\\s.*执行.*");
        endList.add("名称:.*律师事务所.*\\s.*负责.*");
        endList.add("名称：.*律师事务所.*\\s.*法定.*");
        endList.add("名称：.*律师事务所.*\\s.*住所.*");
        endList.add("名称：.*律师事务所");
        endList.add("名称：.*律师事务所.*\\s.*执行.*");
        endList.add("名称：.*律师事务所.*\\s.*负责.*");
        endList.add("律师事务所 .*律师事务所.*\\s.*法定.*");
        endList.add("律师事务所 .*律师事务所.*\\s.*住所.*");
        endList.add("律师事务所 .*律师事务所.*\\s.*执行.*");
        endList.add("律师事务所 .*律师事务所.*\\s.*负责.*");
        endList.add("律师事务所.*\\s.*律师事务所.*\\s.*法定.*");
        endList.add("律师事务所.*\\s.*律师事务所.*\\s.*执行.*");
        endList.add("律师事务所.*\\s.*律师事务所.*\\s.*住所.*");
        endList.add("律师事务所.*\\s.*律师事务所.*\\s.*负责.*");
        String end = RegularUtils.ifFullinterceptionFromText(pdfContent, endList);
        if(end.contains(":")){
            end=end.trim().substring(end.indexOf(":")+1);
        }else if(end.contains("：")){
            end=end.trim().substring(end.indexOf("：")+1);
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
        list2.add("经办人员.*");
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
        arrayList.add("律师事务所.*事务所.*经办人");
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
}
