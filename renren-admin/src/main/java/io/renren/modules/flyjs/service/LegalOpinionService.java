package io.renren.modules.flyjs.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.flyjs.entity.LegalOpinionEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-01 16:37:53
 */
public interface LegalOpinionService extends IService<LegalOpinionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

