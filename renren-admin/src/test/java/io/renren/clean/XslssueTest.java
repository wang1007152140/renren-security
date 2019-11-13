package io.renren.clean;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 第一次清洗类
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class XslssueTest {
    @Autowired
    XsIssueService xsIssueService;

    private String filePath="F:\\pdf";
    private List<File> list= Arrays.asList(new File(filePath).listFiles());

    @Test
    public void func(){
        XsIssueEntity xsIssueEntity ;
        for (int i=0;i<list.size();i++){
            try {
                xsIssueEntity=new XsIssueEntity();
                System.out.println(list.get(i).getName());
                String pdfContent = PDFReader.paserPDFFileByPdfBox(list.get(i));
                getFileTitle1(xsIssueEntity,list.get(i).getName());//文件名
                getCompanyMain(pdfContent,xsIssueEntity);//发行人
                getrepresentativeman(pdfContent,xsIssueEntity);//法定代表人
                getsec_man( pdfContent, xsIssueEntity);//董事会秘书
                /**
                 * 这个不用了
                 *getYear( pdfContent, xsIssueEntity);
                 */
                getaccounting( pdfContent, xsIssueEntity);
                getOrderAccountMan( pdfContent, xsIssueEntity);

                getSponsonMan( pdfContent, xsIssueEntity);

                /**
                 * 这个在那个前面已经用了
                 * getLawyerMan( pdfContent, xsIssueEntity);
                 */

                getApperaisal_new(pdfContent,xsIssueEntity);
                xsIssueService.insert(xsIssueEntity);
            }catch (Exception e){
                File file =list.get(i);
                File file1 =new File("F:\\pdf1");
                try {
                    FileUtils.moveToDirectory(file,file1,true);
                } catch (IOException ex) {
                    System.out.println("这是文件创建失败可能是"+file.getName());
                }
                System.out.println("失败:"+file.getName());
            }
        }
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
     * 获取文件名和时间
     * @param xsIssueEntity
     * @param fileName
     * @return
     */
    public XsIssueEntity getFileTitle1(XsIssueEntity xsIssueEntity,String fileName){
        String str=fileName.substring(fileName.indexOf("]")+1,fileName.indexOf("-")).trim();
        xsIssueEntity.setFileTitle(str);
        String timeStr=fileName.substring(fileName.indexOf("-")+1,fileName.indexOf("+"));
        int year=Integer.valueOf(timeStr.substring(0,timeStr.indexOf("-")))-1;
        xsIssueEntity.setProjectYear(year+"");
        xsIssueEntity.setReleaseTime(timeStr);
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
        if(!("".equals(xsIssueEntity.getCompanyMain()))){
            arrayList.add(".*名称.*"+xsIssueEntity.getCompanyMain().trim()+"(\\s.*){30}");
            String str=RegularUtils.ifFullinterceptionFromText(pdfContent,arrayList);
            arrayList.clear();
            arrayList.add("法定代表人：.*");
            arrayList.add("法定代表人:.*");
            arrayList.add("法定代表人.*");
            String end=RegularUtils.ifFullinterceptionFromText(str,arrayList);
            if(end!=null||end!=""){
                end.trim();
                if(end.contains("：")){
                    xsIssueEntity.setRepresentativeMan(end.substring(end.indexOf("：")+1));
                }else if(end.contains(":")){
                    xsIssueEntity.setRepresentativeMan(end.substring(end.indexOf(":")+1));
                }else if(end.contains(" ")){
                    xsIssueEntity.setRepresentativeMan(end.substring(end.indexOf(" ")+1));
                } else {
                    xsIssueEntity.setRepresentativeMan(end);
                }
            }
            return xsIssueEntity;

        }
        arrayList.add("法定代表人：.*");
        arrayList.add("法定代表人:.*");
        arrayList.add("法定代表人.*");
        String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        if(str!=null||str!=""){
            str.trim();
            if(str.contains("：")){
                xsIssueEntity.setRepresentativeMan(str.substring(str.indexOf("：")+1));
            }else if(str.contains(":")){
                xsIssueEntity.setRepresentativeMan(str.substring(str.indexOf(":")+1));
            }else if(str.contains(" ")){
                xsIssueEntity.setRepresentativeMan(str.substring(str.indexOf(" ")+1));
            } else {
                xsIssueEntity.setRepresentativeMan(str);
            }
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
        arrayList.add("董事会秘书.*:.*");
        arrayList.add("董事会秘书.*：.*");
        arrayList.add(".*露负责人.*");
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
     * 获取年月和发布日期
     * @param pdfContent
     * @param xsIssueEntity
     * @return
     */
    public  XsIssueEntity getYear(String pdfContent,XsIssueEntity xsIssueEntity){
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("(\\W){4}年(\\W){1,2}月");
        arrayList.add("(\\d){4}年(\\d){1,2}月");
        String str = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        if(str!=null||str!=""){
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
            }
            xsIssueEntity.setReleaseTime(str);

            xsIssueEntity.setProjectYear(Integer.valueOf(str.substring(0,str.indexOf("年")))-1+"");

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
        arrayList.add("名.*称.*会计师事务所.*(\\s.*){20}");
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
        arrayList.add("名.*称.*会计师事务所.*\\s.*执行.*(\\s.*){20}");
        arrayList.add("名.*称.*会计师事务所.*\\s.*负责.*(\\s.*){20}");
        arrayList.add("名.*称.*会计师事务所.*\\s.*法定.*(\\s.*){20}");
        arrayList.add("名.*称.*会计师事务所.*\\s.*住所.*(\\s.*){20}");
        arrayList.add("名.*称.*会计师事务所.*\\s.*联系.*(\\s.*){20}");
        arrayList.add("会计师事务所.*会计师.*\\s.*负责.*(\\s.*){20}");
        arrayList.add("会计师事务所.*会计师.*\\s.*执行.*(\\s.*){20}");
        arrayList.add("会计师事务所.*会计师.*\\s.*法定.*(\\s.*){20}");
        arrayList.add("会计师事务所.*会计师.*\\s.*住所.*(\\s.*){20}");
        arrayList.add("会计师事务所:.*");
        arrayList.add("会计师事务所：.*");
        arrayList.add("会计师事务所.*\\s.*名称：");
        arrayList.add("会计师事务所.*\\s.*名称:");
        String errContent = RegularUtils.ifFullinterceptionFromText(pdfContent, arrayList);
        ArrayList<String> list = new ArrayList<String>();
        list.add("会计师.*事务所：.*");
        list.add("会计师.*事务所:.*");
        list.add(".*会计师.*事务所.*");
        list.add("名称:.*会计.*事务所.*");
        list.add("名称：.*会计.*事务所.*");
        list.add("名称.*会计.*事务所.*");
        list.add("机构名称.*会计.*事务所");
        String accountMan = RegularUtils.ifFullinterceptionFromText(errContent, list);
        if(accountMan!=null&& accountMan!=""){
            accountMan.trim();
              if (accountMan.contains(":")){
                  xsIssueEntity.setAccounting(accountMan.substring(accountMan.indexOf(":")+1));
              }else if (accountMan.contains("：")){
                  xsIssueEntity.setAccounting(accountMan.substring(accountMan.indexOf("：")+1));
              }else if (accountMan.contains(" ")){
                  xsIssueEntity.setAccounting(accountMan.substring(accountMan.indexOf(" ")+1));
              }else {
                  xsIssueEntity.setAccounting(accountMan);
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
        startList.add("名.*称.*会计师事务所.*(\\s.*){20}");
        startList.add("会计师事务所.*会计师事务所.*(\\s.*){20}");
        startList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*(\\s.*){20}");
        startList.add("会计师事务所.*\\s.*名.*称.*会计师事务所.*(\\s.*){20}");
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
        arrayList.add("经办会计师.*\\s.*");
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
        }else if(accountMan.contains(" ")){
            str=accountMan.substring(accountMan.indexOf(" ")+1).split("-");
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
        startList.add("主办券商：.*证券.*\\s.*法定.*");
        startList.add("主办券商：.*证券.*\\s.*住所.*");
        startList.add("主办券商：.*证券.*\\s.*负责.*");
        startList.add("主办券商：.*证券.*\\s.*执行.*");
        startList.add("主办券商:.*证券.*\\s.*法定.*");
        startList.add("主办券商:.*证券.*\\s.*住所.*");
        startList.add("主办券商:.*证券.*\\s.*负责.*");
        startList.add("主办券商:.*证券.*\\s.*执行.*");
        startList.add("主办券商.*\\s.*名称.*证券.*(\\s.*){20}");

        startList.add("主办券商：.*证券.*公司.*\\s.*法定.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*住所.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*负责.*");
        startList.add("主办券商：.*证券.*公司.*\\s.*执行.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*法定.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*住所.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*负责.*");
        startList.add("主办券商:.*证券.*公司.*\\s.*执行.*");
        startList.add("主办券商.*\\s.*名称.*证券.*公司.*(\\s.*){20}");


        startList.add("名称:.*证券.*公司.*\\s.*法定.*");
        startList.add("名称:.*证券.*公司.*\\s.*住所.*");
        startList.add("名称:.*证券.*公司.*\\s.*负责.*");
        startList.add("名称:.*证券.*公司.*\\s.*执行.*");
        startList.add("名称：.*证券.*公司.*\\s.*法定.*");
        startList.add("名称：.*证券.*公司.*\\s.*住所.*");
        startList.add("名称：.*证券.*公司.*\\s.*负责.*");
        startList.add("名称：.*证券.*公司.*\\s.*执行.*");
        startList.add("名称：.*证券.*(\\s.*){20}");
        startList.add("名称:.*证券.*(\\s.*){20}");
        startList.add("名称.*证券.*(\\s.*){20}");
        startList.add("名称：.*证券.公司.*(\\s.*){20}");
        startList.add("名称:.*证券.公司.*(\\s.*){20}");
        startList.add("名称.*证券.公司.*(\\s.*){20}");
        startList.add("主办券商 .*证券.*公司\\s.*法定.*");
        startList.add("主办券商 .*证券.*公司\\s.*住所.*");
        startList.add("主办券商 .*证券.*公司\\s.*负责.*");
        startList.add("主办券商 .*证券.*公司\\s.*执行.*");
        startList.add("主办券商:.*");
        startList.add("主办券商：.*");
        startList.add("主办券商.*\\s.*名称：.*");
        startList.add("主办券商.*\\s.*名称:.*");
        startList.add("主办券商.*\\s.*名称.*");
        startList.add("主办券商.*(\\s.*){1,4}.*名称.*证券.*公司.*(\\s.*){20}");
        startList.add("主办券商.*\\s.*\\s.*名称.*证券.*公司.*(\\s.*){20}");
        startList.add("主办券商.*\\s.*名称.*证券.*公司");
        startList.add("主办券商.*\\s.*名称.*证券.*公司.*");
        startList.add("主办券商.*\\s.*名称.*证券.*公司.*\\s.*法.*人.*");
        startList.add("名称.*证券.*(\\s.*){20}");
        startList.add("名称.*证券.*公司.*(\\s.*){20}");
        String start = RegularUtils.ifFullinterceptionFromText(pdfContent, startList);
        ArrayList<String> zhubanList=new ArrayList<String>();
        zhubanList.add("名称.*证券.*");
        zhubanList.add("名称.*证券.*公司");
        zhubanList.add(".*证券.*公司");
        String zhuend=RegularUtils.ifFullinterceptionFromText(start,zhubanList);
        if(zhuend.contains("名称")){
            if(zhuend.contains(":")){
                zhuend=zhuend.trim().substring(zhuend.indexOf(":")+1);
            }else if(zhuend.contains("：")){
                zhuend=zhuend.trim().substring(zhuend.indexOf("：")+1);
            }else if(zhuend.contains(" ")){
                zhuend=zhuend.trim().substring(zhuend.indexOf(" ")+1);
            }

        }else if(zhuend.contains("主办券商")){
            if(zhuend.contains(":")){
                zhuend=zhuend.trim().substring(zhuend.indexOf(":")+1);
            }else if(zhuend.contains("：")){
                zhuend=zhuend.trim().substring(zhuend.indexOf("：")+1);
            }else if(zhuend.contains(" ")){
                zhuend=zhuend.trim().substring(zhuend.indexOf(" ")+1);
            }
        }
        xsIssueEntity.setSponsorNew(zhuend);
        //获取经办负责人
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
        //律师事务所
        ArrayList<String> endList = new ArrayList<String>();
        endList.add("律师事务所.*(\\s.*){1,4}.*名称.*律师.*事务所.*(\\s.*){20}");
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
        endList.add("名称.*律师事务所.*(\\s.*){20}");
        String end6 = RegularUtils.ifFullinterceptionFromText(pdfContent, endList);
        getLawyerMan(end6,xsIssueEntity);
        endList.clear();
        endList.add("名称.*律师.*事务所.*");
        String end=RegularUtils.ifFullinterceptionFromText(end6,endList);
        if(end.contains(":")){
            end=end.trim().substring(end.indexOf(":")+1);
        }else if(end.contains("：")){
            end=end.trim().substring(end.indexOf("：")+1);
        }else if(end.contains(" ")){
            end=end.trim().substring(end.indexOf(" ")+1);
        }
        xsIssueEntity.setLawyerNew(end);

//        String errContent = "";
//        try {
//            errContent = pdfContent.substring(pdfContent.lastIndexOf(start), pdfContent.lastIndexOf(end));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        ArrayList<String> list2 = new ArrayList<String>();
//        list2.add("项目经办人.*");
//        list2.add("项目负责人.*");
//        list2.add("项目联系人.*");
//        list2.add("项目小组负责人.*");
//        list2.add("经办人员.*");
//        String lawyerMan = RegularUtils.ifFullinterceptionFromText(errContent, list2).replace("、","-");
//        if(lawyerMan.contains(":")){
//            lawyerMan=lawyerMan.substring(lawyerMan.indexOf(":")+1);
//        }else if(lawyerMan.contains("：")){
//            lawyerMan=lawyerMan.substring(lawyerMan.indexOf("：")+1);
//        }else if(lawyerMan.contains(" ")){
//            lawyerMan=lawyerMan.substring(lawyerMan.indexOf(" ")+1);
//        }
//        String[] str=lawyerMan.split("-");
//        for (int i=0;i<str.length;i++){
//            switch (i){
//                case 0:xsIssueEntity.setSponsorMan1(str[i]); break;
//                case 1:xsIssueEntity.setSponsorMan2(str[i]); break;
//                case 2:xsIssueEntity.setSponsorMan3(str[i]); break;
//                case 3:xsIssueEntity.setSponsorMan4(str[i]); break;
//                case 4:xsIssueEntity.setSponsorMan5(str[i]); break;
//                case 5:xsIssueEntity.setSponsorMan6(str[i]); break;
//                case 6:xsIssueEntity.setSponsorMan7(str[i]); break;
//                case 7:xsIssueEntity.setSponsorMan8(str[i]); break;
//                default:xsIssueEntity.setSponsorMan1(str[0]);
//            }
//        }
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
                .replace("经办律师：", "").replace("经办律师", "").trim();
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

    /**
     * 资产评估机构名称和评估师傅
     * @param pdfContent
     * @param xsIssueEntity
     */
    public XsIssueEntity getApperaisal_new(String pdfContent,XsIssueEntity xsIssueEntity){
        ArrayList<String> startList = new ArrayList<String>();
        startList.add("资产评估机构.*(\\s.*){1,4}.*名称.*(\\s.*){30}");
        String start = RegularUtils.ifFullinterceptionFromText(pdfContent, startList);
        ArrayList<String> endList=new ArrayList<String>();
        endList.add("名称.*资产.*评估.*有限公司");
        endList.add("名称.*资产，有限公司");
        endList.add("名称.*评估.*公司");
        String end=RegularUtils.ifFullinterceptionFromText(start,endList);
        if(end.contains(":")){
            end=end.trim().substring(end.indexOf(":")+1);
        }else if(end.contains("：")){
            end=end.trim().substring(end.indexOf("：")+1);
        }else if(end.contains(" ")){
            end=end.trim().substring(end.indexOf(" ")+1);
        }
        xsIssueEntity.setApperaisalNew(end);
        ArrayList<String> ent=new ArrayList<String>();
        ent.add("经办注册评估师.*");
        ent.add("注册.*资产.*评估师.*");
        ent.add("注册.*评估师.*");
        ent.add("资产.*评估师.*");
        String ent1=RegularUtils.ifFullinterceptionFromText(start,ent);
        if(ent1.contains(":")){
            ent1=ent1.trim().substring(ent1.indexOf(":")+1).replace("、","-");
        }else if(ent1.contains("：")){
            ent1=ent1.trim().substring(ent1.indexOf("：")+1).replace("、","-");
        }else if(ent1.contains(" ")){
            ent1=ent1.trim().substring(ent1.indexOf(" ")+1).replace("、","-");
        }
        String[] str=ent1.split("-");
        for (int i=0;i<str.length;i++){
            switch (i){
                case 0:xsIssueEntity.setApperaisalMan1(str[i]); break;
                case 1:xsIssueEntity.setApperaisalMan2(str[i]); break;
                case 2:xsIssueEntity.setApperaisalMan3(str[i]); break;
                case 3:xsIssueEntity.setApperaisalMan4(str[i]); break;
                case 4:xsIssueEntity.setApperaisalMan5(str[i]); break;
                case 5:xsIssueEntity.setApperaisalMan6(str[i]); break;
                case 6:xsIssueEntity.setApperaisalMan7(str[i]); break;
                case 7:xsIssueEntity.setApperaisalMan8(str[i]); break;
                default:xsIssueEntity.setApperaisalMan1(str[0]);
            }
        }
        return xsIssueEntity;
    }

}
