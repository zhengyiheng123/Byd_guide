package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/6/27.
 */

public class Guide_Circle_Bean {

    private List<UsersBean> users;

    public List<UsersBean> getUsers() {
        return users;
    }

    public void setUsers(List<UsersBean> users) {
        this.users = users;
    }

    public static class UsersBean {
        /**
         * avatar : /uploads/20170612/2b8cd6ba33750d1702f2bbbc85c24c5c.jpg
         * user_id : 1
         * user_name : HD609189556315
         */

        private String avatar;
        private int user_id;
        private String user_name;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }
    }
}
