package io.renren.Wutils.Utils;

import org.junit.Test;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wutils {
    /**
     * 使替换中文的零一二三变成数字的123
     * @param str
     * @return
     */
    public static String replaceStrToNum(String str) {
        str = str.replace("零", "0")
                .replace("〇", "0")
                .replace("一", "1")
                .replace("二", "2")
                .replace("三", "3")
                .replace("四", "4")
                .replace("五", "5")
                .replace("六", "6")
                .replace("七", "7")
                .replace("八", "8")
                .replace("九", "9");
        return str;
    }

    /**
     * 去掉字符串中的渣滓，
     * @param oldStr  去除渣滓的字符串
     * @param single  渣滓中的单个字符
     * @param group   渣滓中的词组，每个词组以“-”为间隔符
     * @return
     */
    public static String replaceGarbage(String oldStr,String single,String group){

        if(oldStr!=null&&oldStr!=""){
            for(int i=0;i<single.length();i++){
                   String str=single.charAt(i)+"";
                   oldStr=oldStr.replace(str,"");
            }
            //词组以-为间隔符号
            String[] groups=group.split("-");
            for (int i=0;i<groups.length;i++){
                for (int j=i;j<groups.length;j++){
                    if(groups[i].length()<groups[j].length()){
                        String strtt=groups[i];
                        groups[i]=groups[j];
                        groups[j]=strtt;
                    }
                }
            }
            for (int j=0;j<groups.length;j++){
                oldStr=oldStr.replace(groups[j],"");
            }
            oldStr=oldStr
                    .replace("\r"," ")
                    .replace("\n"," ");

            oldStr=oldStr.trim();
        }
        return oldStr;
    }
    public static String[] packedArray(String[] strs){
      String[] newStrs=null;
      for (int i=0;i<strs.length;i++){
          if(i!=strs.length){
              if(strs[i+1].length()==1){
                  strs[i]+=strs[i+1];
                 // strs[]
              }
          }
      }
      return newStrs;
    }
    @Test
    public void funck(){
        String str = "浙江浙经律师事务所（章）\n" +
                "负责人： 经办律师： 杨 杰 方怀宇\n" +
                "经办律师：\n" +
                "李诗云";
     //   System.out.println(replaceGarbage(str,"\n"));
    }


    /**
     * 遍历清空文件夹下面的文件和文件夹
     * @param file
     */
    public static void  deleteDirectory(File file){
        File[] list = file.listFiles();
        Integer i = 0;
        for (File f:list){
            if (f.isDirectory()){
                //删除子文件夹
                deleteDirectory(new File(f.getPath()));
            }else{
                //删除文件
                f.delete();
                i ++;
            }
        }
        //重新遍历一下文件夹内文件是否已删除干净，删除干净后则删除文件夹。
        if (file.listFiles().length <=0 ){
            //file.delete();
            return;
        }
    }

    public String[] removeEnterToNum(String oldStr){
         for (int i=0;i<oldStr.length();i++){
             if((oldStr.charAt(i)==' ')&&(oldStr.charAt(i-1)!=' ')){

             }
         }
         return null;
    }

    /**
     * 时间戳变成标准的时间格式  1564329600000L  To  2019-07-29 00:00:00
     * @param timestamp
     * @return
     */
    public static String timestampToStr(Long timestamp){
        String res="";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(timestamp);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     *  把多个连续的空格替换为一个空格，把一个空格去掉
     *  比如 ：王臣伟       厉 害         尼玛的那     里 斯
     *  王臣伟 厉害 尼玛的那 里斯
     * @param str 原字符串
     * @return
     */
    public static String replacePluralBlank(String str){
        if (str!=null&&str!="") {
            str=str.trim();
            Pattern p = Pattern.compile("(\\s){3,}");
            Matcher m = p.matcher(str);
            str=m.replaceAll("/");
            str=str.replace(" ","")
            .replace("/"," ");
        }
        return str;
    }
}