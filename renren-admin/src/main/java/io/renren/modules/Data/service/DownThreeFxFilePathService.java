package io.renren.modules.Data.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.Data.entity.DownThreeFxFilePathEntity;

import java.util.Map;

/**
 * 文件下载链接表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-22 12:36:49
 */
public interface DownThreeFxFilePathService extends IService<DownThreeFxFilePathEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询数据，并插入到数据库中
     */
    void getListQuanMinJosp();

    /**
     * 下载pdf
     */
    void downQuanMinJospPdf();

    /**
     * 顺序下载pdf
     */
    void  downOrderQuanMinJospPdf();
}

