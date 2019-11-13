package io.renren.clean;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.PDFReader;
import io.renren.common.utils.RegularUtils;
import io.renren.modules.derive.entity.XsIssueEntity;
import io.renren.modules.derive.service.XsIssueService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ThreeCleanFx {
    private String filePath="F:\\pdf-股票发行方案";
    private File file =new File(filePath) ;
    @Autowired
    public XsIssueService xsIssueService;
    public  List<XsIssueEntity> list;

    @Test
    public void clean(){
      //会计事务所为空的
      list=xsIssueService.selectList(new EntityWrapper<XsIssueEntity>().lt("id",15479).gt("id",15190).isNotNull("sponsor_man2"));
        System.out.println(list.size());
    }
    @Test
    public void cleankj() throws Exception {
        list=xsIssueService.selectList(new EntityWrapper<XsIssueEntity>().lt("id",15479).gt("id",15190).isNull("sponsor_man2"));//
        for (int i=0;i<list.size();i++){
            int finalI = i;
           File[] files= file.listFiles(new FileFilter() {
                     @Override
                     public boolean accept(File pathname) {
                         if(pathname.getName().contains(list.get(finalI).getFileTitle()))
                               return true;
                         return false;
                     }
                 });
           for (int j=0;j<files.length;j++){
               String pdfContent = PDFReader.parsePDFFileFlashbackOut(files[j]);
               //getaccounting(pdfContent,list.get(i));
              // getOrderAccountMan(pdfContent,list.get(i));
               cleanacction(pdfContent,list.get(i));
           }
            System.out.println(list.get(i).getId()+""+xsIssueService.updateById(list.get(i)));

        }
       // System.out.println(list.size());
    }

    /**
     * 清洗主办券商保荐人
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
   public XsIssueEntity cleanacction(String pdfContent,XsIssueEntity xsIssueEntity){
       ArrayList<String> startList=new ArrayList<String>();
       startList.add("主办券商.*\\s.*名称：.*(\\s.*){20}");
       startList.add("主办券商.*\\s.*名称:.*(\\s.*){20}");
       startList.add("主办券商.*\\s.*名称.*(\\s.*){20}");
       startList.add("主办券商.*\\s.*名称.*证券.*(\\s.*){20}");
       startList.add("主办券商.*(\\s.*){1,4}.*名称.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商.*\\s.*\\s.*名称.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商.*\\s.*名称.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商.*\\s.*名称.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商.*\\s.*名称.*证券.*公司.*\\s.*法.*人.*(\\s.*){20}");

       startList.add("主办券商：.*\\s.*名称：.*(\\s.*){20}");
       startList.add("主办券商：.*\\s.*名称:.*(\\s.*){20}");
       startList.add("主办券商：.*\\s.*名称.*(\\s.*){20}");
       startList.add("主办券商：.*\\s.*名称.*证券.*(\\s.*){20}");
       startList.add("主办券商：.*(\\s.*){1,4}.*名称.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商：.*\\s.*\\s.*名称.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商：.*\\s.*名称.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商：.*\\s.*名称.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商：.*\\s.*名称.*证券.*公司.*\\s.*法.*人.*(\\s.*){20}");

       startList.add("主办券商：.*证券.*公司.*\\s.*住所.*(\\s.*){20}");
       startList.add("主办券商：.*证券.*公司.*\\s.*负责.*(\\s.*){20}");
       startList.add("主办券商：.*证券.*公司.*\\s.*执行.*(\\s.*){20}");
       startList.add("主办券商:.*证券.*公司.*\\s.*法定.*(\\s.*){20}");
       startList.add("主办券商:.*证券.*公司.*\\s.*住所.*(\\s.*){20}");
       startList.add("主办券商:.*证券.*公司.*\\s.*负责.*(\\s.*){20}");
       startList.add("主办券商:.*证券.*公司.*\\s.*执行.*(\\s.*){20}");
       startList.add("主办券商.*\\s.*名称.*证券.*公司.*(\\s.*){20}");


       startList.add("名称:.*证券.*公司.*\\s.*负责.*(\\s.*){20}");
       startList.add("名称:.*证券.*公司.*\\s.*执行.*(\\s.*){20}");
       startList.add("名称:.*证券.*公司.*\\s.*住所.*(\\s.*){20}");
       startList.add("名称:.*证券.*公司.*\\s.*法定.*(\\s.*){20}");
       startList.add("名称：.*证券.*公司.*\\s.*法定.*(\\s.*){20}");
       startList.add("名称：.*证券.*公司.*\\s.*住所.*(\\s.*){20}");
       startList.add("名称：.*证券.*公司.*\\s.*负责.*(\\s.*){20}");
       startList.add("名称：.*证券.*公司.*\\s.*执行.*(\\s.*){20}");
       startList.add("名称：.*证券.*(\\s.*){20}");
       startList.add("名称:.*证券.*(\\s.*){20}");
       startList.add("名称.*证券.*(\\s.*){20}");
       startList.add("名称：.*证券.公司.*(\\s.*){20}");
       startList.add("名称:.*证券.公司.*(\\s.*){20}");
       startList.add("名称.*证券.公司.*(\\s.*){20}");
       startList.add("名称.*证券.*(\\s.*){20}");
       startList.add("名称.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商 .*证券.*公司\\s.*法定.*");
       startList.add("主办券商 .*证券.*公司\\s.*住所.*");
       startList.add("主办券商 .*证券.*公司\\s.*负责.*");
       startList.add("主办券商 .*证券.*公司\\s.*执行.*");
       startList.add("主办券商:.*(?!\\.){0,}.*");
       startList.add("主办券商：.*证券.*公司.*\\s.*法定.*(\\s.*){20}");
       startList.add("主办券商:.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商：.*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商 .*证券.*公司.*(\\s.*){20}");
       startList.add("主办券商：.*(?!\\.){0,}");
       String start=RegularUtils.ifFullinterceptionFromText(pdfContent,startList);
       ArrayList<String> zhubanpeo=new ArrayList<String>();
       zhubanpeo.add("项目经办人.*");
       zhubanpeo.add("项目负责人.*");
       zhubanpeo.add("项目联系人.*");
       zhubanpeo.add("项目小组负责人.*");
       String zhupeo=RegularUtils.ifFullinterceptionFromText(start,zhubanpeo);
       if (zhupeo!=null&&zhupeo!=""){
           if(zhupeo.contains(":")){
               xsIssueEntity.setSponsorMan1(zhupeo.substring(zhupeo.indexOf(":")+1));
           }else if(zhupeo.contains("：")){
               xsIssueEntity.setSponsorMan1(zhupeo.substring(zhupeo.indexOf("：")+1));
           }else if(zhupeo.contains(" ")){
               xsIssueEntity.setSponsorMan1(zhupeo.substring(zhupeo.indexOf(" ")+1));
           }
       }
       zhubanpeo.clear();
       //获取经办人员
       zhubanpeo.add("经办人员.*");
       zhubanpeo.add(".*成员.*");
       zhubanpeo.add(".*经办.*");
       String zhupeo1=RegularUtils.ifFullinterceptionFromText(start,zhubanpeo);
       if (zhupeo1!=null&&zhupeo1!=""){
           if(zhupeo1.contains(":")){
               xsIssueEntity.setSponsorMan2(zhupeo1.substring(zhupeo1.indexOf(":")+1));
           }else if(zhupeo1.contains("：")){
               xsIssueEntity.setSponsorMan2(zhupeo1.substring(zhupeo1.indexOf("：")+1));
           }else if(zhupeo1.contains(" ")){
               xsIssueEntity.setSponsorMan2(zhupeo1.substring(zhupeo1.indexOf(" ")+1));
           }
       }



       return  xsIssueEntity;
   }



    /**
     * 第二次清洗董事会秘书-公开转让发行
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public XsIssueEntity cleanDongmi(String pdfContent,XsIssueEntity xsIssueEntity){
//        ArrayList<String>  startList=new ArrayList<String>();
//        startList.add(".*名称.*"+xsIssueEntity.getCompanyMain().trim()+".*(\\s.*){40}");
//        startList.add("基本信息.*(\\s.*)+.*名称.*(\\s.*){40}");
//        startList.add("基本信息.*(\\s.*)+.*名称.*"+xsIssueEntity.getCompanyMain().trim()+".*(\\s.*){40}");
//        startList.add("信息.*(\\s.*)+.*名称.*"+xsIssueEntity.getCompanyMain()+".*(\\s.*){40}");
//        startList.add("信息.*(\\s.*)+.*名称.*(\\s.*){40}");
//        String start=RegularUtils.ifFullinterceptionFromText(pdfContent,startList);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("董事会秘书或信息披露负责人:.*");
        arrayList.add("董事会秘书或信息披露负责人：.*");
        arrayList.add("董董事会秘书或信息披露负责人 .*");
        arrayList.add("董事会秘书或信息披露负责人.*");
        arrayList.add("董事会秘书.*信息披露事务负责人:.*");
        arrayList.add("董事会秘书.*信息披露事务负责人：.*");
        arrayList.add("董事会秘书.*信息披露事务负责人 .*");
        arrayList.add("董事会秘书.*信息披露事务负责人.*");
        arrayList.add("董事会秘书.*：.*");
        arrayList.add("露负责人.*");
        arrayList.add("披露负责人.*");
        arrayList.add("董事会秘书.*");
        arrayList.add("信息披露义务人.*");
        arrayList.add("信息披露.*\\s.*责人.*");
        String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        //str.substring()
        if(str!=null||str!=""){
            str.trim();
            if(str.contains("："))
            {
                xsIssueEntity.setSecMan(str.substring(str.indexOf("：")+1));
            }else if(str.contains(":")){
                xsIssueEntity.setSecMan(str.substring(str.indexOf(":")+1));
            }else if(str.contains(" ")){
                xsIssueEntity.setSecMan(str.substring(str.indexOf(" ")+1));
            }else{
                xsIssueEntity.setSecMan(str);
            }

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
        arrayList.add("会计师事务所:.*会计师事务所.*\\s.*公告.*(\\s.*){8}");
        arrayList.add("会计师事务所:.*会计师事务所.*\\s.*执行.*(\\s.*){8}");
        arrayList.add("会计师事务所:.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
        arrayList.add("会计师事务所:.*会计师事务所.*\\s.*负责.*(\\s.*){8}");
        arrayList.add("会计师事务所:.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
        arrayList.add("会计师事务所:.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
        arrayList.add("会计师事务所：.*会计师事务所.*\\s.*公告.*(\\s.*){8}");
        arrayList.add("会计师事务所：.*会计师事务所.*\\s.*执行.*(\\s.*){8}");
        arrayList.add("会计师事务所：.*会计师事务所.*\\s.*法定.*(\\s.*){8}");
        arrayList.add("会计师事务所：.*会计师事务所.*\\s.*负责.*(\\s.*){8}");
        arrayList.add("会计师事务所：.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
        arrayList.add("会计师事务所：.*会计师事务所.*\\s.*住所.*(\\s.*){8}");
        arrayList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*(\\s.*){8}");
        arrayList.add("会计师事务所:.*(\\s.*){8}");
        arrayList.add("会计师事务所：.*(\\s.*){8}");
        arrayList.add("会计师事务所.*\\.*名.*称:.*会计事务所.*(\\s.*){8}");
        arrayList.add("会计师事务所.*\\.*名.*称：.*会计事务所.*(\\s.*){8}");
        arrayList.add("会计师事务所：.*(\\s.*){8}");
        arrayList.add("名.*称.*会计师事务所.*");
        arrayList.add("名.*称.*会计师事务所.*(\\s.*){8}");
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
        list.add("名.*称：.*");
        list.add("名.*称:.*");
        list.add("名.*称.*");
        String accountMan = RegularUtils.ifFullinterceptionFromText(errContent, list).replace(" ","");
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
        startList.add("会计师事务所:.*会计师事务所.*\\s.*公告.*(\\s.*){20}");
        startList.add("会计师事务所:.*会计师事务所.*\\s.*执行.*(\\s.*){20}");
        startList.add("会计师事务所:.*会计师事务所.*\\s.*法定.*(\\s.*){20}");
        startList.add("会计师事务所:.*会计师事务所.*\\s.*负责.*(\\s.*){20}");
        startList.add("会计师事务所:.*会计师事务所.*\\s.*住所.*(\\s.*){20}");
        startList.add("会计师事务所:.*会计师事务所.*\\s.*住所.*(\\s.*){20}");
        startList.add("会计师事务所：.*会计师事务所.*\\s.*公告.*(\\s.*){20}");
        startList.add("会计师事务所：.*会计师事务所.*\\s.*执行.*(\\s.*){20}");
        startList.add("会计师事务所：.*会计师事务所.*\\s.*法定.*(\\s.*){20}");
        startList.add("会计师事务所：.*会计师事务所.*\\s.*负责.*(\\s.*){20}");
        startList.add("会计师事务所：.*会计师事务所.*\\s.*住所.*(\\s.*){20}");
        startList.add("会计师事务所：.*会计师事务所.*\\s.*住所.*(\\s.*){20}");
        startList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*(\\s.*){20}");
        startList.add("会计师事务所:.*(\\s.*){20}");
        startList.add("会计师事务所：.*(\\s.*){20}");
        startList.add("会计师事务所.*\\.*名.*称:.*会计事务所.*(\\s.*){20}");
        startList.add("会计师事务所.*\\.*名.*称：.*会计事务所.*(\\s.*){20}");
        startList.add("会计师事务所：.*(\\s.*){20}");
        startList.add("名.*称.*会计师事务所.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师事务所");
        startList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*");
        startList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*(\\s.*){30}");
        startList.add("会计师事务所.*\\s.*住所.*(\\s.*){30}");
        startList.add("会计师事务所.*\\s.*法定.*(\\s.*){30}");
        startList.add("会计师事务所.*\\s.*负责人.*(\\s.*){30}");
        startList.add("名称.*会计师事务所.*\\s.*法定.*(\\s.*){30}");
        startList.add("会计师事务所.*\\s.*事务所.*\\s.*住所.*(\\s.*){30}");
        startList.add("会计师事务所.*\\s.*执行.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*公告.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*执行.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*法定.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*负责.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*住所.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师事务所.*\\s.*住所.*(\\s.*){30}");
        startList.add("名.*称.*会计师事务所.*\\s.*执行.*(\\s.*){30}");
        startList.add("名.*称.*会计师事务所.*\\s.*负责.*(\\s.*){30}");
        startList.add("名.*称.*会计师事务所.*\\s.*法定.*(\\s.*){30}");
        startList.add("名.*称.*会计师事务所.*\\s.*住所.*(\\s.*){30}");
        startList.add("名.*称.*会计师事务所.*\\s.*联系.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师.*\\s.*负责.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师.*\\s.*执行.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师.*\\s.*法定.*(\\s.*){30}");
        startList.add("会计师事务所.*会计师.*\\s.*住所.*(\\s.*){30}");
        startList.add("会计师事务所:.*");
        startList.add("会计师事务所：.*");
        startList.add("会计师事务所.*\\s.*名称：");
        startList.add("会计师事务所.*\\s.*名称:");
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
        }else if(accountMan.contains("会计师 ")){
                str=accountMan.substring(accountMan.indexOf(" ")+1).split("-");
        }else{
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
     * 清洗我们的律师事务所
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public XsIssueEntity cleanLs(String pdfContent,XsIssueEntity xsIssueEntity){
        ArrayList<String> endList = new ArrayList<String>();
        endList.add("律师事务所：.*律师.*事务所.*");
        endList.add("律师事务所:.*律师.*事务所.*");
        endList.add("律师事务所：.*律师.*事务所.*\\s.*法定.*");
        endList.add("律师事务所：.*律师.*事务所.*\\s.*住所.*");
        endList.add("律师事务所：.*律师.*事务所.*\\s.*负责.*");
        endList.add("律师事务所：.*律师.*事务所.*\\s.*执行.*");
        endList.add("律师事务所:.*律师.*事务所.*\\s.*法定.*");
        endList.add("律师事务所:.*律师.*事务所.*\\s.*住所.*");
        endList.add("律师事务所:.*律师.*事务所.*\\s.*负责.*");
        endList.add("律师事务所:.*律师.*事务所.*\\s.*执行.*");
        endList.add("名称:.*律师.*事务所.*");
        endList.add("名称：.*律师.*事务所.*");
        endList.add("名称:.*律师.*事务所.*\\s.*法定.*");
        endList.add("名称:.*律师.*事务所.*\\s.*住所.*");
        endList.add("名称:.*律师.*事务所.*\\s.*执行.*");
        endList.add("名称:.*律师.*事务所.*\\s.*负责.*");
        endList.add("名称：.*律师.*事务所.*\\s.*法定.*");
        endList.add("名称：.*律师.*事务所.*\\s.*住所.*");
        endList.add("名称：.*律师.*事务所");
        endList.add("名称：.*律师.*事务所.*\\s.*执行.*");
        endList.add("名称：.*律师.*事务所.*\\s.*负责.*");
        endList.add("律师事务所 .*律师.*事务所.*\\s.*法定.*");
        endList.add("律师事务所 .*律师.*事务所.*\\s.*住所.*");
        endList.add("律师事务所 .*律师.*事务所.*\\s.*执行.*");
        endList.add("律师事务所 .*律师.*事务所.*\\s.*负责.*");
        endList.add("律师事务所.*\\s.*律师.*事务所.*\\s.*法定.*");
        endList.add("律师事务所.*\\s.*律师.*事务所.*\\s.*执行.*");
        endList.add("律师事务所.*\\s.*律师.*事务所.*\\s.*住所.*");
        endList.add("律师事务所.*\\s.*律师.*事务所.*\\s.*负责.*");
        endList.add("律师事务所.*(\\s).*名.*称：.*律师.*事务所.*(\\s.*){20}");
        endList.add("律师事务所.*(\\s).*名.*称:.*律师.*事务所.*(\\s.*){20}");
        endList.add("律师事务所.*(\\s).*律师.*事务所.*(\\s.*){20}");
        endList.add("名.*称.*律师.*事务所(\\s.*){20}");
        String end = RegularUtils.ifFullinterceptionFromText(pdfContent, endList);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("名.*称：.*律师.*事务所");
        arrayList.add("名.*称:.*律师.*事务所");
        arrayList.add("律师.*事务所：.*律师.*事务所");
        arrayList.add("律师.*事务所:.*律师.*事务所");
        String st = RegularUtils.ifFullinterceptionFromText(end, arrayList);
        if(st.contains(":")){
            st=st.trim().substring(st.indexOf(":")+1);
        }else if(st.contains("：")){
            st=st.trim().substring(st.indexOf("：")+1);
        }
        xsIssueEntity.setLawyerNew(st);
        return xsIssueEntity;
    }
}
