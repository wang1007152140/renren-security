package io.renren.modules.sjflyjs.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.sjflyjs.entity.ShLegalOpinionEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-13 11:45:38
 */
public interface ShLegalOpinionService extends IService<ShLegalOpinionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

