package io.renren.modules.flyjs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.flyjs.dao.LegalOpinionDao;
import io.renren.modules.flyjs.entity.LegalOpinionEntity;
import io.renren.modules.flyjs.service.LegalOpinionService;


@Service("legalOpinionService")
public class LegalOpinionServiceImpl extends ServiceImpl<LegalOpinionDao, LegalOpinionEntity> implements LegalOpinionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<LegalOpinionEntity> page = this.selectPage(
                new Query<LegalOpinionEntity>(params).getPage(),
                new EntityWrapper<LegalOpinionEntity>()
        );

        return new PageUtils(page);
    }

}
