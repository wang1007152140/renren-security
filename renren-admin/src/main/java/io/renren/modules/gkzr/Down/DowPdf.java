package io.renren.modules.gkzr.Down;

import java.util.Map;

public interface DowPdf<E> {
    /**
     * 下载json串
     * @param url 地址
     * @param map 地址中匹配的参数
     * @return
     */
       public String downJson(String url, Map<String, Object> map);
}
