package io.renren.modules.gkzr.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.gkzr.entity.GkzrEntity;
import io.renren.modules.gkzr.service.GkzrService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-23 14:25:06
 */
@RestController
@RequestMapping("gkzr/gkzr")
public class GkzrController {
    @Autowired
    private GkzrService gkzrService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("GkzrEntity:gkzr:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = gkzrService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("GkzrEntity:gkzr:info")
    public R info(@PathVariable("id") Integer id){
        GkzrEntity gkzr = gkzrService.selectById(id);

        return R.ok().put("gkzr", gkzr);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("GkzrEntity:gkzr:save")
    public R save(@RequestBody GkzrEntity gkzr){
        gkzrService.insert(gkzr);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("GkzrEntity:gkzr:update")
    public R update(@RequestBody GkzrEntity gkzrEntity){
        ValidatorUtils.validateEntity(gkzrEntity);
        gkzrService.updateAllColumnById(gkzrEntity);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("GkzrEntity:gkzr:delete")
    public R delete(@RequestBody Integer[] ids){
        gkzrService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
