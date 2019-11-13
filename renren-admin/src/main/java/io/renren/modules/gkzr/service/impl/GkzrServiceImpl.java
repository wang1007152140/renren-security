package io.renren.modules.gkzr.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.gkzr.dao.GkzrDao;
import io.renren.modules.gkzr.entity.GkzrEntity;
import io.renren.modules.gkzr.service.GkzrService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("gkzrService")
public class GkzrServiceImpl extends ServiceImpl<GkzrDao, GkzrEntity> implements GkzrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<GkzrEntity> page = this.selectPage(
                new Query<GkzrEntity>(params).getPage(),
                new EntityWrapper<GkzrEntity>()
        );

        return new PageUtils(page);
    }


}
