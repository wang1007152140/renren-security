package io.renren.modules.sjflyjs.controller;

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

import io.renren.modules.sjflyjs.entity.ShLegalOpinionEntity;
import io.renren.modules.sjflyjs.service.ShLegalOpinionService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-13 11:45:38
 */
@RestController
@RequestMapping("sjflyjs/shlegalopinion")
public class ShLegalOpinionController {
    @Autowired
    private ShLegalOpinionService shLegalOpinionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sjflyjs:shlegalopinion:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = shLegalOpinionService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sjflyjs:shlegalopinion:info")
    public R info(@PathVariable("id") Long id){
        ShLegalOpinionEntity shLegalOpinion = shLegalOpinionService.selectById(id);

        return R.ok().put("shLegalOpinion", shLegalOpinion);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sjflyjs:shlegalopinion:save")
    public R save(@RequestBody ShLegalOpinionEntity shLegalOpinion){
        shLegalOpinionService.insert(shLegalOpinion);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sjflyjs:shlegalopinion:update")
    public R update(@RequestBody ShLegalOpinionEntity shLegalOpinion){
        ValidatorUtils.validateEntity(shLegalOpinion);
        shLegalOpinionService.updateAllColumnById(shLegalOpinion);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("derive:shlegalopinion:delete")
    public R delete(@RequestBody Long[] ids){
        shLegalOpinionService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
