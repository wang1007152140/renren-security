package io.renren.modules.gkzr.entity;

import com.baomidou.mybatisplus.annotations.TableName;

import java.util.List;

@TableName("gkzr")
public class GkzrEntity {

    /**
     * categoryNames : null
     * pageList : {"content":[{"score":0.41833407,"publishDate":"1571932800000","siteId":null,"description":null,"id":"97f8e5896df886ed016e028f0c8b018a","title":"常州龙翔气弹簧股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-25/1572000971_109432.pdf"},{"score":0.41833407,"publishDate":"1571760000000","siteId":null,"description":null,"id":"97f8e5856dba5a49016df7321ffc2198","title":"[临时报告]柴米河:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-23/1571816000_208653.pdf"},{"score":0.41833407,"publishDate":"1571760000000","siteId":null,"description":null,"id":"97f8e5836dba5a46016df76bc1b725a2","title":"[临时报告]卡尔斯:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-23/1571816136_557866.pdf"},{"score":0.41833407,"publishDate":"1571068800000","siteId":null,"description":null,"id":"97f8e5856dba5a49016dcd16e8e10673","title":"[临时报告]雄伟科技:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-15/1571124675_833876.pdf"},{"score":0.41833407,"publishDate":"1570636800000","siteId":null,"description":null,"id":"97f8e5866d064bd5016db41fd2386477","title":"[临时报告]九龙珠:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-10/1570692832_592208.pdf"},{"score":0.41833407,"publishDate":"1570464000000","siteId":null,"description":null,"id":"97f8e5836d1dabc8016daa558b0051e8","title":"[临时报告]瑞有科技:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-08/1570520811_220177.pdf"},{"score":0.41833407,"publishDate":"1569772800000","siteId":null,"description":null,"id":"97f8e5896d244912016d7fe56c6d0ac5","title":"浙江华夏电梯股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-09-30/1569828786_163747.pdf"},{"score":0.41833407,"publishDate":"1569772800000","siteId":null,"description":null,"id":"97f8e5846d064bce016d7d16fbf35351","title":"[临时报告]天赐股份:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-09-30/1569828698_957062.pdf"},{"score":0.41833407,"publishDate":"1569513600000","siteId":null,"description":null,"id":"97f8e5896d244912016d700b05470963","title":"广东风华新能源股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-09-27/1569569471_700597.pdf"},{"score":0.41833407,"publishDate":"1567699200000","siteId":null,"description":null,"id":"97f8e5866ce5a892016d05d47cfb1c13","title":"[临时报告]金沙地理:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-09-06/1567760890_925871.pdf"},{"score":0.41833407,"publishDate":"1567094400000","siteId":null,"description":null,"id":"97f8e5896cc20771016ce19530d117ff","title":"天津卡尔斯阀门股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-08-30/1567153194_762709.pdf"},{"score":0.41833407,"publishDate":"1567094400000","siteId":null,"description":null,"id":"97f8e5896cc20771016ce1b03021182a","title":"广东井得电机股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-08-30/1567154525_776923.pdf"},{"score":0.41833407,"publishDate":"1567094400000","siteId":null,"description":null,"id":"97f8e5896cc20771016ce161ddcf178e","title":"山东得胜电力股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-08-30/1567152391_674699.pdf"},{"score":0.41833407,"publishDate":"1567094400000","siteId":null,"description":null,"id":"97f8e5896cc20771016ce16aebd5179b","title":"北京喂呦科技股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-08-30/1567152510_532973.pdf"},{"score":0.41833407,"publishDate":"1567008000000","siteId":null,"description":null,"id":"97f8e58a6cc20763016cdd2cfd5115ad","title":"大元建材科技股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-08-29/1567078825_525527.pdf"},{"score":0.41833407,"publishDate":"1564502400000","siteId":null,"description":null,"id":"97f8e5846c09d4e1016c46a1770a3c62","title":"[临时报告]威宁能源:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-07-31/1564558730_777560.pdf"},{"score":0.41833407,"publishDate":"1564502400000","siteId":null,"description":null,"id":"97f8e58a6c3ca9da016c45f959f704aa","title":"广东瑞有科技股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-07-31/1564558476_752625.pdf"},{"score":0.41833407,"publishDate":"1564416000000","siteId":null,"description":null,"id":"97f8e5846c09d4e1016c417fadef2d64","title":"[临时报告]龙门医药:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-07-30/1564472289_314685.pdf"},{"score":0.41833407,"publishDate":"1564416000000","siteId":null,"description":null,"id":"97f8e5846c09d4e1016c4225025b365c","title":"[临时报告]龙开河:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-07-30/1564477842_359531.pdf"},{"score":0.41833407,"publishDate":"1564416000000","siteId":null,"description":null,"id":"97f8e5866c09d4e6016c41b4661b3394","title":"[临时报告]天马国药:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-07-30/1564472571_259233.pdf"}],"firstPage":true,"lastPage":false,"number":0,"numberOfElements":20,"size":20,"sort":null,"totalElements":8492,"totalPages":425}
     * categories : {}
     * category : info_publish
     * keyword : 公开转让说明书
     */

    private Object categoryNames;
    private PageListBean pageList;
    private CategoriesBean categories;
    private String category;
    private String keyword;

    public Object getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(Object categoryNames) {
        this.categoryNames = categoryNames;
    }

    public PageListBean getPageList() {
        return pageList;
    }

    public void setPageList(PageListBean pageList) {
        this.pageList = pageList;
    }

    public CategoriesBean getCategories() {
        return categories;
    }

    public void setCategories(CategoriesBean categories) {
        this.categories = categories;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public static class PageListBean {
        /**
         * content : [{"score":0.41833407,"publishDate":"1571932800000","siteId":null,"description":null,"id":"97f8e5896df886ed016e028f0c8b018a","title":"常州龙翔气弹簧股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-25/1572000971_109432.pdf"},{"score":0.41833407,"publishDate":"1571760000000","siteId":null,"description":null,"id":"97f8e5856dba5a49016df7321ffc2198","title":"[临时报告]柴米河:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-23/1571816000_208653.pdf"},{"score":0.41833407,"publishDate":"1571760000000","siteId":null,"description":null,"id":"97f8e5836dba5a46016df76bc1b725a2","title":"[临时报告]卡尔斯:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-23/1571816136_557866.pdf"},{"score":0.41833407,"publishDate":"1571068800000","siteId":null,"description":null,"id":"97f8e5856dba5a49016dcd16e8e10673","title":"[临时报告]雄伟科技:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-15/1571124675_833876.pdf"},{"score":0.41833407,"publishDate":"1570636800000","siteId":null,"description":null,"id":"97f8e5866d064bd5016db41fd2386477","title":"[临时报告]九龙珠:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-10/1570692832_592208.pdf"},{"score":0.41833407,"publishDate":"1570464000000","siteId":null,"description":null,"id":"97f8e5836d1dabc8016daa558b0051e8","title":"[临时报告]瑞有科技:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-10-08/1570520811_220177.pdf"},{"score":0.41833407,"publishDate":"1569772800000","siteId":null,"description":null,"id":"97f8e5896d244912016d7fe56c6d0ac5","title":"浙江华夏电梯股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-09-30/1569828786_163747.pdf"},{"score":0.41833407,"publishDate":"1569772800000","siteId":null,"description":null,"id":"97f8e5846d064bce016d7d16fbf35351","title":"[临时报告]天赐股份:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-09-30/1569828698_957062.pdf"},{"score":0.41833407,"publishDate":"1569513600000","siteId":null,"description":null,"id":"97f8e5896d244912016d700b05470963","title":"广东风华新能源股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-09-27/1569569471_700597.pdf"},{"score":0.41833407,"publishDate":"1567699200000","siteId":null,"description":null,"id":"97f8e5866ce5a892016d05d47cfb1c13","title":"[临时报告]金沙地理:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-09-06/1567760890_925871.pdf"},{"score":0.41833407,"publishDate":"1567094400000","siteId":null,"description":null,"id":"97f8e5896cc20771016ce19530d117ff","title":"天津卡尔斯阀门股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-08-30/1567153194_762709.pdf"},{"score":0.41833407,"publishDate":"1567094400000","siteId":null,"description":null,"id":"97f8e5896cc20771016ce1b03021182a","title":"广东井得电机股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-08-30/1567154525_776923.pdf"},{"score":0.41833407,"publishDate":"1567094400000","siteId":null,"description":null,"id":"97f8e5896cc20771016ce161ddcf178e","title":"山东得胜电力股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-08-30/1567152391_674699.pdf"},{"score":0.41833407,"publishDate":"1567094400000","siteId":null,"description":null,"id":"97f8e5896cc20771016ce16aebd5179b","title":"北京喂呦科技股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-08-30/1567152510_532973.pdf"},{"score":0.41833407,"publishDate":"1567008000000","siteId":null,"description":null,"id":"97f8e58a6cc20763016cdd2cfd5115ad","title":"大元建材科技股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-08-29/1567078825_525527.pdf"},{"score":0.41833407,"publishDate":"1564502400000","siteId":null,"description":null,"id":"97f8e5846c09d4e1016c46a1770a3c62","title":"[临时报告]威宁能源:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-07-31/1564558730_777560.pdf"},{"score":0.41833407,"publishDate":"1564502400000","siteId":null,"description":null,"id":"97f8e58a6c3ca9da016c45f959f704aa","title":"广东瑞有科技股份有限公司公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-07-31/1564558476_752625.pdf"},{"score":0.41833407,"publishDate":"1564416000000","siteId":null,"description":null,"id":"97f8e5846c09d4e1016c417fadef2d64","title":"[临时报告]龙门医药:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-07-30/1564472289_314685.pdf"},{"score":0.41833407,"publishDate":"1564416000000","siteId":null,"description":null,"id":"97f8e5846c09d4e1016c4225025b365c","title":"[临时报告]龙开河:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-07-30/1564477842_359531.pdf"},{"score":0.41833407,"publishDate":"1564416000000","siteId":null,"description":null,"id":"97f8e5866c09d4e6016c41b4661b3394","title":"[临时报告]天马国药:公开转让说明书","nodeId":null,"url":"/disclosure/2019/2019-07-30/1564472571_259233.pdf"}]
         * firstPage : true
         * lastPage : false
         * number : 0
         * numberOfElements : 20
         * size : 20
         * sort : null
         * totalElements : 8492
         * totalPages : 425
         */

        private boolean firstPage;
        private boolean lastPage;
        private int number;
        private int numberOfElements;
        private int size;
        private Object sort;
        private int totalElements;
        private int totalPages;
        private List<ContentBean> content;

        public boolean isFirstPage() {
            return firstPage;
        }

        public void setFirstPage(boolean firstPage) {
            this.firstPage = firstPage;
        }

        public boolean isLastPage() {
            return lastPage;
        }

        public void setLastPage(boolean lastPage) {
            this.lastPage = lastPage;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumberOfElements() {
            return numberOfElements;
        }

        public void setNumberOfElements(int numberOfElements) {
            this.numberOfElements = numberOfElements;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public Object getSort() {
            return sort;
        }

        public void setSort(Object sort) {
            this.sort = sort;
        }

        public int getTotalElements() {
            return totalElements;
        }

        public void setTotalElements(int totalElements) {
            this.totalElements = totalElements;
        }

        public int getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(int totalPages) {
            this.totalPages = totalPages;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public static class ContentBean {
            /**
             * score : 0.41833407
             * publishDate : 1571932800000
             * siteId : null
             * description : null
             * id : 97f8e5896df886ed016e028f0c8b018a
             * title : 常州龙翔气弹簧股份有限公司公开转让说明书
             * nodeId : null
             * url : /disclosure/2019/2019-10-25/1572000971_109432.pdf
             */

            private double score;
            private String publishDate;
            private Object siteId;
            private Object description;
            private String id;
            private String title;
            private Object nodeId;
            private String url;

            public double getScore() {
                return score;
            }

            public void setScore(double score) {
                this.score = score;
            }

            public String getPublishDate() {
                return publishDate;
            }

            public void setPublishDate(String publishDate) {
                this.publishDate = publishDate;
            }

            public Object getSiteId() {
                return siteId;
            }

            public void setSiteId(Object siteId) {
                this.siteId = siteId;
            }

            public Object getDescription() {
                return description;
            }

            public void setDescription(Object description) {
                this.description = description;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public Object getNodeId() {
                return nodeId;
            }

            public void setNodeId(Object nodeId) {
                this.nodeId = nodeId;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }

    public static class CategoriesBean {
    }
}
