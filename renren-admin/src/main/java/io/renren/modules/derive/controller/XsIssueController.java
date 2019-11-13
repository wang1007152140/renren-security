package io.renren.modules.derive.controller;

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

import io.renren.modules.derive.entity.XsIssueEntity;
import io.renren.modules.derive.service.XsIssueService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-23 14:25:06
 */
@RestController
@RequestMapping("derive/xsissue")
public class XsIssueController {
    @Autowired
    private XsIssueService xsIssueService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("gkzr:xsissue:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = xsIssueService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("gkzr:xsissue:info")
    public R info(@PathVariable("id") Integer id){
        XsIssueEntity xsIssue = xsIssueService.selectById(id);

        return R.ok().put("xsIssue", xsIssue);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("gkzr:xsissue:save")
    public R save(@RequestBody XsIssueEntity xsIssue){
        xsIssueService.insert(xsIssue);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("gkzr:xsissue:update")
    public R update(@RequestBody XsIssueEntity xsIssue){
        ValidatorUtils.validateEntity(xsIssue);
        xsIssueService.updateAllColumnById(xsIssue);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("gkzr:xsissue:delete")
    public R delete(@RequestBody Integer[] ids){
        xsIssueService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

}
