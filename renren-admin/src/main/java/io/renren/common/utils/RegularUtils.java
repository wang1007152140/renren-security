package io.renren.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 正则工具类
 */
public class RegularUtils {


    /**
     * 传入正则返回所有匹配的内容，每个匹配的内容以"/"分割，返回多个符合的字符串
     * @param context  优先匹配的正文（例如只传第三节和第四节之间的信息）
     * @param fullText  匹配优先级低的正文（全文）
     * @param arrayList 正则表达式list
     * 会先匹配优先级高的，如果优先级高的匹配不到一个字符串，就去匹配全文
     * @return 所有匹配到的内容,以"/"分割
     */
    public static String interceptionFromText(String context, String fullText, ArrayList<String> arrayList){
            StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i <arrayList.size() ; i++) {

                Pattern pattern = Pattern.compile(arrayList.get(i));
                Matcher matcher = pattern.matcher(context);
                while (matcher.find()){
                    stringBuffer.append(matcher.group()+"/");
                }
                if (!stringBuffer.toString().equals("")){
                    Pattern pattern1 = Pattern.compile(arrayList.get(i));
                    Matcher matcher1 = pattern1.matcher(fullText);
                    while (matcher1.find()){
                        stringBuffer.append(matcher1.group()+"/");
                    }
                    if (stringBuffer.toString().equals("")){
                        return "";
                    }
                }
        }
        return stringBuffer.toString();
    }

    /**传入文章全文，返回多个匹配到的内容，以“/”分割
     * @param context 文章全文
     * @param arrayList 正则list
     * @return 能够匹配到的所有结果
     */
    public static String FullinterceptionFromText(String context, ArrayList<String> arrayList){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i <arrayList.size() ; i++) {

            Pattern pattern = Pattern.compile(arrayList.get(i));
            Matcher matcher = pattern.matcher(context);
            while (matcher.find()){
                stringBuffer.append(matcher.group()+"/");
            }

        }
        if(stringBuffer.toString().equals("")){
            return "";
        }
        return stringBuffer.toString();
    }

    /**
     *   筛选符合的正则集合的字符串，全文截取，这个是返回的符合一个正则的字符串
     * @param context  需要匹配的字符串
     * @param arrayList  字符串对应的正则表达式集合
     * @return  返回匹配符合的字符串
     */
    public static String ifFullinterceptionFromText(String context, ArrayList<String> arrayList){
        String s = "";
        for (int i = 0; i <arrayList.size() ; i++) {

            Pattern pattern = Pattern.compile(arrayList.get(i));
            Matcher matcher = pattern.matcher(context);
           if (matcher.find()){
                return  matcher.group();
            }
        }
        return  s;
    }


}
