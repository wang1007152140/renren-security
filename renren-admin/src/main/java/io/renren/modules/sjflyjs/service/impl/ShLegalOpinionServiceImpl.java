package io.renren.modules.sjflyjs.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.sjflyjs.dao.ShLegalOpinionDao;
import io.renren.modules.sjflyjs.entity.ShLegalOpinionEntity;
import io.renren.modules.sjflyjs.service.ShLegalOpinionService;


@Service("shLegalOpinionService")
public class ShLegalOpinionServiceImpl extends ServiceImpl<ShLegalOpinionDao, ShLegalOpinionEntity> implements ShLegalOpinionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<ShLegalOpinionEntity> page = this.selectPage(
                new Query<ShLegalOpinionEntity>(params).getPage(),
                new EntityWrapper<ShLegalOpinionEntity>()
        );

        return new PageUtils(page);
    }

}
