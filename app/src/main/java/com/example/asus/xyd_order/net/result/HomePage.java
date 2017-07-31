package com.example.asus.xyd_order.net.result;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Zheng on 2017/7/26.
 */

public class HomePage {

    /**
     * banners : [{"img_path":"/uploads/20170720/02c57921d42ffaba98211c35f7b2b76f.jpg","title":"BBB","url":"http://aaa.com"},{"img_path":"/uploads/20170720/d7d23a58a04799bef629aa46c168c6ae.png","title":"淘宝","url":"http://aaa.com"},{"img_path":"/uploads/20170720/6e3f43afe45d8b7f22d1d251af1e38ae.jpg","title":"aaa","url":"http://aaa.com"}]
     * dmd_notice : {"add_time":1500966495,"msg":"您团号为 1492849283 的需求订单已被 郑郑一横 接单，请及时查看","opm_id":78,"ord_id":152,"ord_type":-1,"title":""}
     * mutual_message : {"add_time":1500022261,"avatar":"/uploads/user/avatar/20170721/5b85a8a549bfa80e6fe1fd3a2f0c8d78.png","content":"Ufuibfui3f","user_name":"lemon"}
     */

    private DmdNoticeBean dmd_notice;
    private MutualMessageBean mutual_message;
    private List<BannersBean> banners;
    private MerNoticeBean mer_notice;

    public MerNoticeBean getMer_notice() {
        return mer_notice;
    }

    public void setMer_notice(MerNoticeBean mer_notice) {
        this.mer_notice = mer_notice;
    }

    public class  MerNoticeBean{
        private int add_time;
        private String msg;
        private int opm_id;
        private int ord_id;
        private int ord_type;
        private String title;

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getOpm_id() {
            return opm_id;
        }

        public void setOpm_id(int opm_id) {
            this.opm_id = opm_id;
        }

        public int getOrd_id() {
            return ord_id;
        }

        public void setOrd_id(int ord_id) {
            this.ord_id = ord_id;
        }

        public int getOrd_type() {
            return ord_type;
        }

        public void setOrd_type(int ord_type) {
            this.ord_type = ord_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
    public DmdNoticeBean getDmd_notice() {
        return dmd_notice;
    }

    public void setDmd_notice(DmdNoticeBean dmd_notice) {
        this.dmd_notice = dmd_notice;
    }

    public MutualMessageBean getMutual_message() {
        return mutual_message;
    }

    public void setMutual_message(MutualMessageBean mutual_message) {
        this.mutual_message = mutual_message;
    }

    public List<BannersBean> getBanners() {
        return banners;
    }

    public void setBanners(List<BannersBean> banners) {
        this.banners = banners;
    }

    public static class DmdNoticeBean {
        /**
         * add_time : 1500966495
         * msg : 您团号为 1492849283 的需求订单已被 郑郑一横 接单，请及时查看
         * opm_id : 78
         * ord_id : 152
         * ord_type : -1
         * title :
         */

        private int add_time;
        private String msg;
        private int opm_id;
        private int ord_id;
        private int ord_type;
        private String title;

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public int getOpm_id() {
            return opm_id;
        }

        public void setOpm_id(int opm_id) {
            this.opm_id = opm_id;
        }

        public int getOrd_id() {
            return ord_id;
        }

        public void setOrd_id(int ord_id) {
            this.ord_id = ord_id;
        }

        public int getOrd_type() {
            return ord_type;
        }

        public void setOrd_type(int ord_type) {
            this.ord_type = ord_type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class MutualMessageBean {
        /**
         * add_time : 1500022261
         * avatar : /uploads/user/avatar/20170721/5b85a8a549bfa80e6fe1fd3a2f0c8d78.png
         * content : Ufuibfui3f
         * user_name : lemon
         */

        private int add_time;
        private String avatar;
        private String content;
        private String user_name;

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }

    public static class BannersBean implements Serializable{
        /**
         * img_path : /uploads/20170720/02c57921d42ffaba98211c35f7b2b76f.jpg
         * title : BBB
         * url : http://aaa.com
         */

        private String img_path;
        private String title;
        private String url;

        public String getImg_path() {
            return img_path;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
