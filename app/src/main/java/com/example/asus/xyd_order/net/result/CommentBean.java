package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/10.
 */

public class CommentBean {

    private List<CommentsBean> comments;

    public List<CommentsBean> getComments() {
        return comments;
    }

    public void setComments(List<CommentsBean> comments) {
        this.comments = comments;
    }

    public static class CommentsBean {
        /**
         * add_time : 1519382192
         * avatar : /uploads/20170612/2b8cd6ba33750d1702f2bbbc85c24c5c.jpg
         * comment : 好棒的味道
         * rank : 4
         * rc_id : 2
         * user_name : HD609189556315
         */

        private int add_time;
        private String avatar;
        private String comment;
        private int rank;
        private int rc_id;
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

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public int getRank() {
            return rank;
        }

        public void setRank(int rank) {
            this.rank = rank;
        }

        public int getRc_id() {
            return rc_id;
        }

        public void setRc_id(int rc_id) {
            this.rc_id = rc_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
