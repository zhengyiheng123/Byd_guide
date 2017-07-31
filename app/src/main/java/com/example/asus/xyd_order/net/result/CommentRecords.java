package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/4.
 */

public class CommentRecords {

    private List<CommentsBean> comments;

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * avatar : /uploads/user/avatar/20170704/057e2f250ec8377e89fdef2dae8db65d.jpg
         * comment : 意见单有什么用???
         * comment_time : 1499156265
         * countries : ["德国"]
         * finish_time : 0
         * rank : 4
         * service_type : 1
         * user_name : 正三四郎
         */

        private String avatar;
        private String comment;
        private int comment_time;
        private int finish_time;
        private float rank;
        private int service_type;
        private String user_name;
        private List<String> countries;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getComment_time() {
            return comment_time;
        }

        public void setComment_time(int comment_time) {
            this.comment_time = comment_time;
        }

        public int getFinish_time() {
            return finish_time;
        }

        public void setFinish_time(int finish_time) {
            this.finish_time = finish_time;
        }

        public float getRank() {
            return rank;
        }

        public void setRank(float rank) {
            this.rank = rank;
        }

        public int getService_type() {
            return service_type;
        }

        public void setService_type(int service_type) {
            this.service_type = service_type;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public List<String> getCountries() {
            return countries;
        }

        public void setCountries(List<String> countries) {
            this.countries = countries;
        }
    }
}
