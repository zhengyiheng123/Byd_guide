package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/6.
 */

public class MutualBean {

    private List<MutualMessagesBean> mutual_messages;

    public List<MutualMessagesBean> getMutual_messages() {
        return mutual_messages;
    }

    public void setMutual_messages(List<MutualMessagesBean> mutual_messages) {
        this.mutual_messages = mutual_messages;
    }

    public static class MutualMessagesBean {
        /**
         * user_name : Firelord_Sama
         * avatar : /uploads/user/avatar/20170629/60a3759d002fe681f7df72703605b4f8.jpg
         * content : 想去台湾
         * add_time : 1498042845
         */

        private String user_name;
        private String avatar;
        private String content;
        private int add_time;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
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

        public int getAdd_time() {
            return add_time;
        }

        public void setAdd_time(int add_time) {
            this.add_time = add_time;
        }
    }
}
