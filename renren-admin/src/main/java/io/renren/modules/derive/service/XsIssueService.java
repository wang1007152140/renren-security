package io.renren.modules.derive.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.derive.entity.XsIssueEntity;

import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-23 14:25:06
 */
public interface XsIssueService extends IService<XsIssueEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

