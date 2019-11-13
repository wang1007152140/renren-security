package io.renren.modules.derive.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.derive.dao.XsIssueDao;
import io.renren.modules.derive.entity.XsIssueEntity;
import io.renren.modules.derive.service.XsIssueService;


@Service("xsIssueService")
public class XsIssueServiceImpl extends ServiceImpl<XsIssueDao, XsIssueEntity> implements XsIssueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<XsIssueEntity> page = this.selectPage(
                new Query<XsIssueEntity>(params).getPage(),
                new EntityWrapper<XsIssueEntity>()
        );

        return new PageUtils(page);
    }

}
