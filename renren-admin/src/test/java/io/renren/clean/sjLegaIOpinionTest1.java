package io.renren.clean;

import io.renren.Wutils.Utils.Wutils;
import org.junit.Test;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class sjLegaIOpinionTest1 {
    @Test
    public void func1(){
//        File file=new File("F:\\pdf2\\安正时尚：上海市锦天城律师事务所关于安正时尚集团股份有限公司回购注销部分限制性股票的法律意见书.pdf");
//        System.out.println(file.getName());

        String str="颜华荣                         李  燕                                                                         汪  琛";
        System.out.println(Wutils.replacePluralBlank(str));

    }
    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("(\\s){2,}");
            Matcher m = p.matcher(str);
            if(m.find()){
                System.out.println(m.replaceAll("-"));
            }

        }
        return dest;
    }
}
