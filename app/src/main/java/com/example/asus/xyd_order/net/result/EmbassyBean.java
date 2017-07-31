package com.example.asus.xyd_order.net.result;

import java.util.List;

/**
 * Created by Zheng on 2017/7/12.
 */

public class EmbassyBean {

    /**
     * embassies : [{"emb_address":"中国驻奥地利大使馆.......","emb_name":"中国驻奥地利大使馆","emb_phone":"4238450582"}]
     * passport_loss : {"notice":"如果最终出境申请和目前国家不同,请针对具体出境国查询","procedure":"警察局的证明,照片,老护照和签证复印件,办好回乡证后带机票过关."}
     */

    private PassportLossBean passport_loss;
    private List<EmbassiesBean> embassies;

    public PassportLossBean getPassport_loss() {
        return passport_loss;
    }

    public void setPassport_loss(PassportLossBean passport_loss) {
        this.passport_loss = passport_loss;
    }

    public List<EmbassiesBean> getEmbassies() {
        return embassies;
    }

    public void setEmbassies(List<EmbassiesBean> embassies) {
        this.embassies = embassies;
    }

    public static class PassportLossBean {
        /**
         * notice : 如果最终出境申请和目前国家不同,请针对具体出境国查询
         * procedure : 警察局的证明,照片,老护照和签证复印件,办好回乡证后带机票过关.
         */

        private String notice;
        private String procedure;

        public String getNotice() {
            return notice;
        }

        public void setNotice(String notice) {
            this.notice = notice;
        }

        public String getProcedure() {
            return procedure;
        }

        public void setProcedure(String procedure) {
            this.procedure = procedure;
        }
    }

    public static class EmbassiesBean {
        /**
         * emb_address : 中国驻奥地利大使馆.......
         * emb_name : 中国驻奥地利大使馆
         * emb_phone : 4238450582
         */

        private String emb_address;
        private String emb_name;
        private String emb_phone;

        public String getEmb_address() {
            return emb_address;
        }

        public void setEmb_address(String emb_address) {
            this.emb_address = emb_address;
        }

        public String getEmb_name() {
            return emb_name;
        }

        public void setEmb_name(String emb_name) {
            this.emb_name = emb_name;
        }

        public String getEmb_phone() {
            return emb_phone;
        }

        public void setEmb_phone(String emb_phone) {
            this.emb_phone = emb_phone;
        }
    }
}
