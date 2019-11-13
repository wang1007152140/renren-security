package io.renren.modules.Data.controller;

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

import io.renren.modules.Data.entity.DownThreeFxFilePathEntity;
import io.renren.modules.Data.service.DownThreeFxFilePathService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 文件下载链接表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-22 12:36:49
 */
@RestController
@RequestMapping("Data/downthreefxfilepath")
public class DownThreeFxFilePathController {
    @Autowired
    private DownThreeFxFilePathService downThreeFxFilePathService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("Data:downthreefxfilepath:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = downThreeFxFilePathService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("Data:downthreefxfilepath:info")
    public R info(@PathVariable("id") Integer id){
        DownThreeFxFilePathEntity downThreeFxFilePath = downThreeFxFilePathService.selectById(id);

        return R.ok().put("downThreeFxFilePath", downThreeFxFilePath);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("Data:downthreefxfilepath:save")
    public R save(@RequestBody DownThreeFxFilePathEntity downThreeFxFilePath){
        downThreeFxFilePathService.insert(downThreeFxFilePath);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("Data:downthreefxfilepath:update")
    public R update(@RequestBody DownThreeFxFilePathEntity downThreeFxFilePath){
        ValidatorUtils.validateEntity(downThreeFxFilePath);
        downThreeFxFilePathService.updateAllColumnById(downThreeFxFilePath);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("Data:downthreefxfilepath:delete")
    public R delete(@RequestBody Integer[] ids){
        downThreeFxFilePathService.deleteBatchIds(Arrays.asList(ids));

        return R.ok();
    }

    @RequestMapping("/test")
    public R test(){
        downThreeFxFilePathService.downQuanMinJospPdf();
        return R.ok();
    }
}
