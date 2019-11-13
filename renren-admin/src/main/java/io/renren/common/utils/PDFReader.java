package io.renren.common.utils;


//import io.renren.modules.sys.entity.DamagedFileEntity;
//import io.renren.modules.sys.service.DamagedFileService;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
/*
import com.itextpdf.text.exceptions.InvalidPdfException;
import com.itextpdf.text.pdf.*;
*/

import java.io.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * pdf工具类
 */
public class PDFReader {

    /**
     * 读取文件
     * @param file 文件目录
     * @return 文件内容
     * @throws Exception
     */
    public  static String paserPDFFileByPdfBox(File file) throws Exception {
        PDDocument document = null;
        String result;
        try{
         document = PDDocument.load(file);
        PDFTextStripper stripper = new PDFTextStripper();
        result= stripper.getText(document);
        }catch (Exception e){
            result = "";
        }
        if (document!=null)
        document.close();

        return result;
    }

    /**
     * 获取后一半的文档
      * @param file
     * @return
     */
    public static String parsePDFFileFlashbackOut(File  file){
        PDDocument document = null;
        String result=null;
        try{
            document = PDDocument.load(file);
            int pages=document.getNumberOfPages();
            PDFTextStripper stripper = new PDFTextStripper();
            String content=null;
            try {
                stripper.setSortByPosition(true);
                stripper.setStartPage((pages/2));
                stripper.setEndPage(pages);
                content = stripper.getText(document);
            } catch (IOException e) {
                e.printStackTrace();
            }
            result= stripper.getText(document);
        }catch (Exception e){
            result = "";
        }
        if (document!=null) {
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
     }
    /**
     * 筛选损坏文件文件
     * @param path  文件目录
     *默认会将顿坏文件放入该目录下的(\\损坏的文件)文件夹下边
     *返回三种意外情况  StaticResult.NULLFILE，StaticResult.DAMAGEFILE，StaticResult.FILENOEXIST
     */
   /* public static void damagedFileFilter(DamagedFileService damagedFileService, String path) {
        DamagedFileEntity damagedFileEntity = new DamagedFileEntity();
        File file = new File(path);
        if (file.exists()) {
            String[] fileNames = file.list();
            if (null == fileNames || fileNames.length == 0) {
                System.out.println("文件夹是空的!");
            } else {
                for (String fileName : fileNames) {
                    String filePath = path + "/" + fileName;
                    File afile = new File(filePath);
                    if (afile.isFile()) {
                        try {
                            PdfReader pdfReader = new PdfReader(filePath);
                        } catch (InvalidPdfException e) {
                            //damagedFileEntity.setDamagedFile(fileName);
                            //damagedFileService.insert(damagedFileEntity);
                            System.out.println("PDF文件损坏：" + filePath);
                            File myFolderPath = new File(path + "/损坏的文件");
                            try {
                                if (!myFolderPath.exists()) {
                                    myFolderPath.mkdir();
                                }
                                try {
                                    if (afile.renameTo(new File(myFolderPath + "\\" + fileName))) {
                                        System.out.println("File is moved successful!");
                                    } else {
                                        System.out.println("File is failed to move!");
                                    }
                                } catch (Exception exx) {
                                    exx.printStackTrace();
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {

        }
    }*/


    /**
     * 筛选全是图片文件
     * @param path  文件目录
     */
    public static void descPathProcess(String path) {
        PDFReader read = new PDFReader();
        File file=new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isDirectory()) {
                continue;
            }
            if (tempList[i].isFile()) {
                try {
                    String  s = read.paserPDFFileByPdfBox(tempList[i]);
                    Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                    Matcher m = p.matcher(s);
                    String str = m.replaceAll("");
                    if (str.trim().equals("")){
                      File file1 = new File(path+"/全是图片的文件/");
                      if (!file1.exists())file1.mkdir();
                        tempList[i].renameTo(new File(file1+tempList[i].getName()));
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 读取txt文件
     * @param filePath 文件地址
     * @return 文件内容
     */
    public static String readTxtFile(File filePath) { //优化读取txt工具，防止io阻塞
        String content = null;

        try (Reader reader = new InputStreamReader(new FileInputStream(filePath), "UTF-8")) {
            StringBuffer sb = new StringBuffer();
            char[] tempchars = new char[1024];
            while (reader.read(tempchars) != -1) {
                sb.append(tempchars);
            }
            content = sb.toString();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 截取pdf页数
     * @param filename 文件名
     * @param startPage 截取的开始页数
     * @param endPage   截取的结束页数
     * @return  截取章节信息转换为String
     */
    public static String getPDFContentPageNum(String filename ,int startPage,int endPage){

        PDDocument document = null;
        try {
            document = PDDocument.load(new File(filename));
        } catch (IOException e) {
            return "";
        }
        // 获取页码
       int pages = document.getNumberOfPages();
        PDFTextStripper stripper=null;
        String content=null;
        try {
            stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(startPage);
            stripper.setEndPage(endPage);
            content = stripper.getText(document);
        } catch (Exception e) {
            return "";
        }

        if(document!= null){
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;

    }
    /**
     * 截取pdf的最后2页的内容
     * @param file 文件
     * @return  截取章节信息转换为String
     */
    public static String getPDFContentEndTwoPage(File file){

        PDDocument document = null;
        try {
            document = PDDocument.load(file);
        } catch (IOException e) {
            return "";
        }
        // 获取页码
       int pages = document.getNumberOfPages();
        PDFTextStripper stripper=null;
        String content=null;
        try {
            stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(pages-1);
            stripper.setEndPage(pages);
            content = stripper.getText(document);
        } catch (Exception e) {
            return "";
        }

        if(document!= null){
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return content;

    }
    /**
     * 截取pdf的某一页的内容,可以是1，2，3，4或者是-1，-2，-3这样的
     * @param file 文件
     * @return  截取章节信息转换为String
     */
    public static String getPDFContentEndOnePage(File file,int page){

        PDDocument document = null;
        try {
            document = PDDocument.load(file);
        } catch (IOException e) {
            return "";
        }
        // 获取页码
        int pages = document.getNumberOfPages();
        PDFTextStripper stripper=null;
        String content=null;
        try {
            stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            if(page>=0) {
                stripper.setStartPage(page);
                stripper.setEndPage(page);
            }else{
                stripper.setStartPage(pages+1+page);
                stripper.setEndPage(pages+1+page);
            }
            content = stripper.getText(document);
        } catch (Exception e) {
            return "";
        }

        if(document!= null){
            try {
                document.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return content;

    }
    /**
     * 将传入的章节数加一  例如 传入“第一章”返回“第二章”
     * @param s 章节标题 例如 第一节
     * @return
     */
    public static String zhuanshuZi(String s){
        if (s.indexOf("一")!=-1) { return s.replaceAll("一","二");
        }
        else if (s.indexOf("二")!=-1) { return s.replaceAll("二","三");
        }
        else if (s.indexOf("三")!=-1) {return s.replaceAll("三","四");
        }
        else if (s.indexOf("四")!=-1) {return s.replaceAll("四","五");
        }
        else if (s.indexOf("五")!=-1) {return s.replaceAll("五","六");
        }
        else if (s.indexOf("六")!=-1) {return s.replaceAll("六","七");
        }
        else if (s.indexOf("七")!=-1) {return s.replaceAll("七","八");
        }
        else if (s.indexOf("八")!=-1) {return s.replaceAll("八","九");
        }
        else if (s.indexOf("九")!=-1) {return s.replaceAll("九","十");
        }
        else if (s.indexOf("十")!=-1) {return s.replaceAll("十","十一");
        }
        return StaticResult.ZHUANHUANSHIBAI;
    }


    /**
     * 区分文件类型并分别存放
     * @param filepath
     */
    public static void fileClassify(String filepath){
        File file = new File(filepath);
        if (file.isFile())return;
        File[] files = file.listFiles();
        for (File oneFile:files) {
            String fileName = oneFile.getName();
            if (fileName.indexOf("年度报告")!=-1&&fileName.indexOf("摘要")==-1&&fileName.indexOf("半年度报告")==-1&&fileName.indexOf("英文")==-1&&fileName.indexOf("通知")==-1&& fileName.indexOf("公告")==-1&&fileName.indexOf("通告")==-1) {
                File filePath = new File(filepath+"/年度报告/");
                if (!filePath.exists())filePath.mkdir();
                oneFile.renameTo(new File(filepath+"/年度报告/"+fileName));
            }
            if (fileName.indexOf("半年度报告")!=-1&&fileName.indexOf("英文")==-1&&fileName.indexOf("摘要")==-1&&fileName.indexOf("通知")==-1&& fileName.indexOf("公告")==-1&&fileName.indexOf("通告")==-1) {
                File filePath = new File(filepath+"/半年度报告/");
                if (!filePath.exists())filePath.mkdir();
                oneFile.renameTo(new File(filepath+"/半年度报告/"+fileName));
            }
            if (fileName.indexOf("季度报告")!=-1&&fileName.indexOf("英文")==-1&&fileName.indexOf("通过")==-1&&fileName.indexOf("通知")==-1&& fileName.indexOf("公告")==-1&&fileName.indexOf("通告")==-1) {
                File filePath = new File(filepath+"/半年度报告/");
                if (!filePath.exists())filePath.mkdir();
                oneFile.renameTo(new File(filepath+"/季度度报告/"+fileName));
            }
        }

    }
//
//    /**
//     * 倒叙输出文档
//     * @param file
//     * @return
//     */
//    public static String paserPDFFileByPdfBoxlashback(File file){
//
//        return "";
//    }
}


