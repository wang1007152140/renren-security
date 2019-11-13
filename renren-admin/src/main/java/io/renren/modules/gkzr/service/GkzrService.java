package io.renren.modules.gkzr.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.gkzr.entity.GkzrEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-23 14:25:06
 */
public interface GkzrService extends IService<GkzrEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

