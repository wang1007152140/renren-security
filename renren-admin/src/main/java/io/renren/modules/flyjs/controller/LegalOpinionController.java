package io.renren.modules.flyjs.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.flyjs.entity.LegalOpinionEntity;
import io.renren.modules.flyjs.service.LegalOpinionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-01 16:37:53
 */
@RestController
@RequestMapping("derive/legalopinion")
public class LegalOpinionController {
    @Autowired
    private LegalOpinionService legalOpinionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("derive:legalopinion:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = legalOpinionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("derive:legalopinion:info")
    public R info(@PathVariable("id") Long id){
        LegalOpinionEntity legalOpinion = legalOpinionService.selectById(id);

        return R.ok().put("legalOpinion", legalOpinion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("derive:legalopinion:save")
    public R save(@RequestBody LegalOpinionEntity legalOpinion){
        legalOpinionService.insert(legalOpinion);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("derive:legalopinion:update")
    public R update(@RequestBody LegalOpinionEntity legalOpinion){
        ValidatorUtils.validateEntity(legalOpinion);
        legalOpinionService.updateAllColumnById(legalOpinion);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("derive:legalopinion:delete")
    public R delete(@RequestBody Long[] ids){
        legalOpinionService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
