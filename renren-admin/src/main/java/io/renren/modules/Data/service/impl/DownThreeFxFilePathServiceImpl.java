package io.renren.modules.Data.service.impl;

import io.renren.modules.Data.entity.DataBean;
import io.renren.modules.Data.service.DownPdf;
import io.renren.modules.Data.service.QuanMinJousp;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.Data.dao.DownThreeFxFilePathDao;
import io.renren.modules.Data.entity.DownThreeFxFilePathEntity;
import io.renren.modules.Data.service.DownThreeFxFilePathService;


@Service("downThreeFxFilePathService")
public class DownThreeFxFilePathServiceImpl extends ServiceImpl<DownThreeFxFilePathDao, DownThreeFxFilePathEntity> implements DownThreeFxFilePathService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<DownThreeFxFilePathEntity> page = this.selectPage(
                new Query<DownThreeFxFilePathEntity>(params).getPage(),
                new EntityWrapper<DownThreeFxFilePathEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查数据并且插入到数据
     */
    public void getListQuanMinJosp() {
        //含有取数据的方法
        QuanMinJousp quanMinJousp = new QuanMinJousp();
        String page = "0";
        try {
            DataBean dataBean = quanMinJousp.getListDateBean("2019-06-03", page, "股票发行方案");
            int pages = dataBean.getListInfo().getTotalPages();
            for (int i = 0; i < pages; i++) {
                List<DownThreeFxFilePathEntity> list = new ArrayList<DownThreeFxFilePathEntity>();
                if (i != 0) {
                    dataBean = quanMinJousp.getListDateBean("2019-06-03", String.valueOf(i), "股票发行方案");
                }
                for (int j = 0; j < dataBean.getListInfo().getContent().size(); j++) {
                    DataBean.ListInfoBean.ContentBean contentBean = dataBean.getListInfo().getContent().get(j);
                    DownThreeFxFilePathEntity fxFile = new DownThreeFxFilePathEntity();
                    fxFile.setUuid(UUID.randomUUID().toString());
                    fxFile.setFileTitle(contentBean.getDisclosureTitle());
                    fxFile.setDownloadLink("http://www.neeq.com.cn" + contentBean.getDestFilePath());
                    fxFile.setCode(contentBean.getCompanyCd());
                    fxFile.setPushDate(contentBean.getPublishDate());
                    fxFile.setYear("" + contentBean.getUpDate().getYear());
                    fxFile.setFileType("pdf");
                    fxFile.setCompanyName(contentBean.getCompanyName());
                    fxFile.setFileContentType("新三板股票发行方案");
                    fxFile.setState(0);
                    fxFile.setAddTime("2019-10-23");
                    list.add(fxFile);
                }
                this.insertBatch(list);
                list.clear();
            }
        }catch (Exception e){
            System.out.println("失败");
        }
    }

    @Override
    public void downQuanMinJospPdf() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("add_time","2019-10-23");
        List<DownThreeFxFilePathEntity> list = this.selectByMap(map);
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
         for(int i=0;i<list.size();){
             String url;
             String path;
//            synchronized (DownPdf.class){
                 url=list.get(0).getDownloadLink();
                 path="F:/pdf/"+list.get(0).getFileTitle()+".pdf";


//            }
             fixedThreadPool.execute(new DownPdf(url,path));
             list.remove(0);



         }
    }

    @Override
    public void downOrderQuanMinJospPdf() {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("add_time","2019-10-23");
        List<DownThreeFxFilePathEntity> list = this.selectByMap(map);
        DownPdf downPdf=new DownPdf();
        for (int i=0;i<list.size();){
            System.out.println("----"+list.size()+"----");
            String url=list.get(0).getDownloadLink();
            String path="F:/pdf/"+list.get(0).getFileTitle()+".pdf";
            list.remove(0);
            downPdf.setUrls(url);
            downPdf.setPath(path);
            downPdf.run1();
        }
    }


}
