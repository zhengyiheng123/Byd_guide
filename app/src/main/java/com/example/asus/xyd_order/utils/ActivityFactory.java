package com.example.asus.xyd_order.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.asus.xyd_order.activity.ActivityCustomsActivity;
import com.example.asus.xyd_order.activity.Activity_About;
import com.example.asus.xyd_order.activity.Activity_Account_Details;
import com.example.asus.xyd_order.activity.Activity_Add_News;
import com.example.asus.xyd_order.activity.Activity_Attractions_order_Successed;
import com.example.asus.xyd_order.activity.Activity_DrawBackRecord;
import com.example.asus.xyd_order.activity.Activity_Draw_Loading;
import com.example.asus.xyd_order.activity.Activity_Draw_Success;
import com.example.asus.xyd_order.activity.Activity_Draw_Waiting;
import com.example.asus.xyd_order.activity.Activity_Edit_Release;
import com.example.asus.xyd_order.activity.Activity_ForgetPassword;
import com.example.asus.xyd_order.activity.Activity_Help_News;
import com.example.asus.xyd_order.activity.Activity_Message_Setting;
import com.example.asus.xyd_order.activity.Activity_MyCircle;
import com.example.asus.xyd_order.activity.Activity_My_Demand;
import com.example.asus.xyd_order.activity.Activity_News;
import com.example.asus.xyd_order.activity.Activity_Opinion;
import com.example.asus.xyd_order.activity.Activity_Order_Nomal;
import com.example.asus.xyd_order.activity.Activity_Register_confirm;
import com.example.asus.xyd_order.activity.Activity_ReleaseDemand;
import com.example.asus.xyd_order.activity.Activity_Route_List;
import com.example.asus.xyd_order.activity.Activity_Setting;
import com.example.asus.xyd_order.activity.Activity_Suggestion;
import com.example.asus.xyd_order.activity.Activity_UserInfo;
import com.example.asus.xyd_order.activity.Activity_address_list;
import com.example.asus.xyd_order.activity.Activity_myguide_details;
import com.example.asus.xyd_order.activity.AddAddressActivity;
import com.example.asus.xyd_order.activity.AddCardActivity;
import com.example.asus.xyd_order.activity.AddDrawBackSiteActivity;
import com.example.asus.xyd_order.activity.AttractionDetailsNomal;
import com.example.asus.xyd_order.activity.AttractionsActivity;
import com.example.asus.xyd_order.activity.AttractionsDetailsActivity;
import com.example.asus.xyd_order.activity.AttractionsOrderActivity;
import com.example.asus.xyd_order.activity.AttractionsOrderConfirm;
import com.example.asus.xyd_order.activity.Attractions_TicketActivity;
import com.example.asus.xyd_order.activity.CancelOrderActivity;
import com.example.asus.xyd_order.activity.CardDetailsActivity;
import com.example.asus.xyd_order.activity.CatchLeaveMessageActivity;
import com.example.asus.xyd_order.activity.CatchOrdersActivity;
import com.example.asus.xyd_order.activity.CheckSmsActivity;
import com.example.asus.xyd_order.activity.CityActivity;
import com.example.asus.xyd_order.activity.ConfirmOrderActivity;
import com.example.asus.xyd_order.activity.DrawBackActivity;
import com.example.asus.xyd_order.activity.DrawBackIntroduceActivity;
import com.example.asus.xyd_order.activity.EmergencyActivity;
import com.example.asus.xyd_order.activity.LeaveMessage;
import com.example.asus.xyd_order.activity.LoginActivity;
import com.example.asus.xyd_order.activity.MainActivity;
import com.example.asus.xyd_order.activity.MultipleDetailsActivity;
import com.example.asus.xyd_order.activity.MyCollectActivity;
import com.example.asus.xyd_order.activity.MyOrderNews_Activity;
import com.example.asus.xyd_order.activity.OrderActivity;
import com.example.asus.xyd_order.activity.OverViewActivity;
import com.example.asus.xyd_order.activity.RegisterActivity;
import com.example.asus.xyd_order.activity.SelectRoutes;
import com.example.asus.xyd_order.activity.SendMessageActivity;
import com.example.asus.xyd_order.activity.SingleChooseActivity;
import com.example.asus.xyd_order.activity.SiteDisplayActivity;
import com.example.asus.xyd_order.activity.TicketOrder;
import com.example.asus.xyd_order.activity.Ticket_List_Activity;
import com.example.asus.xyd_order.activity.ZhongCanActivity;
import com.example.asus.xyd_order.activity.ZhongcanDetailsActivity;
import com.example.asus.xyd_order.fragment.Journy_Activity;
import com.example.asus.xyd_order.fragment.MyOrderDetails;
import com.example.asus.xyd_order.fragment.MyOrdersActivity;
import com.example.asus.xyd_order.net.Filter.ResultFilter;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.BaseTicketRouteBean;
import com.example.asus.xyd_order.net.result.HttpResult;
import com.example.asus.xyd_order.net.result.RestaurantDetailsBean;
import com.example.asus.xyd_order.net.result.RouteDetails;
import com.example.asus.xyd_order.net.result.TuancanBean;
import com.example.asus.xyd_order.net.result.ZhongcanOrderSuccessBean;

import java.io.Serializable;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2016/11/29.
 */
public class ActivityFactory {
    static Intent intent;
public static void goToLogin(final Context context){
    Intent intent=new Intent(context, LoginActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
}
    public static void goToRegister(Context context) {
        Intent intent=new Intent(context, RegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void goToRegisterConfirm(Context context) {
        Intent intent=new Intent(context, Activity_Register_confirm.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
public static void gotoMain(Context context){
    Intent intent=new Intent(context, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(intent);
}
    public static void gotoLocation(Context context){
        intent=new Intent(context, CityActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void gotoZhongCan(Context context,String cate_id){
        intent=new Intent(context, ZhongCanActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("cate_id",cate_id);
        context.startActivity(intent);
    }
    public static void gotoTuancanDetails(Context context,String res_id,String logo){
        intent=new Intent(context, ZhongcanDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("logo",logo);
        intent.putExtra("res_id",res_id);
        context.startActivity(intent);
    }
    /**
     * 跳转到团餐预定界面
     */
    public static void gotoOrder(Context context, String mode, String res_name, TuancanBean.PriceListBean bean,String res_id){
        intent=new Intent(context, OrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //mode 1团餐   0单点
        intent.putExtra("mode",mode);
        intent.putExtra("res_name",res_name);
        intent.putExtra("mer_id",res_id);
        Bundle bundle=new Bundle();
        bundle.putSerializable("price_bean",bean);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 跳转到单点预定界面
     */
    public static void gotoOrderSingle(Context context, String mode, String res_name, String img_path,String mer_id,List<RestaurantDetailsBean.SingleMealBean> templist){
        intent=new Intent(context, OrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //mode 1团餐   0单点
        intent.putExtra("mode",mode);
        intent.putExtra("templist",(Serializable) templist);
        intent.putExtra("mer_id",mer_id);
        intent.putExtra("res_name",res_name);
        intent.putExtra("img_path",img_path);
        Bundle bundle=new Bundle();
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    /**
     * 跳转到团餐详情页面
     */
    public static void goToMultipleDetails(Context context,RestaurantDetailsBean.GroupMealBean groupMeal,String res_name,String res_id){
        intent=new Intent(context, MultipleDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Bundle bundle=new Bundle();
        bundle.putSerializable("groupMeal",groupMeal);
        intent.putExtra("res_name",res_name);
        intent.putExtra("res_id",res_id);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }
    /**
     * 跳转到预定成功也界面
     */
    public static void goToConfirmOrder(Context context,String order_id){
        intent=new Intent(context, ConfirmOrderActivity.class);
        intent.putExtra("ord_id",order_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 跳转到取消预订页面
     */
    public static void gotoCaccelOrder(Context context){
        intent=new Intent(context, CancelOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 跳转到单点页面
     */
    public static void gotoSingleChoice(Context context, List<RestaurantDetailsBean.SingleMealBean> singleList,String res_name,String logo,String res_id){
        intent=new Intent(context, SingleChooseActivity.class);
        intent.putExtra("singleList",(Serializable) singleList);
        intent.putExtra("res_id",res_id);
        intent.putExtra("res_name",res_name);
        intent.putExtra("logo",logo);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 景点门票
     */
    public static void gotoAttraction(Context context){
        intent=new Intent(context, AttractionsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 景点门票详情
     */
    public static void gotoAttractionDetails(Context context,String scene_id){
        intent=new Intent(context, AttractionsDetailsActivity.class);
        intent.putExtra("scene_id",scene_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void gotoOverView(Context context,String mer_id,String date){
        intent=new Intent(context, OverViewActivity.class);
        intent.putExtra("mer_id",mer_id);
        intent.putExtra("date",date);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void gotoSiteDisplay(Context context,String route_id){
        intent=new Intent(context, SiteDisplayActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("route_id",route_id);
        context.startActivity(intent);
    }
    public static void gotoLeaveMessage(Context context){
        intent=new Intent(context, LeaveMessage.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void gotoAttractionsOrder(Context context, List<BaseTicketRouteBean> groupList,
                                            List<BaseTicketRouteBean> nomalList,String totalPrice,String mer_id,String route_id){
        intent=new Intent(context, AttractionsOrderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("totalPrice",totalPrice);
        intent.putExtra("route_id",route_id);
        intent.putExtra("mer_id",mer_id);
        intent.putExtra("groupList", (Serializable) groupList);
        intent.putExtra("nomalList", (Serializable) nomalList);
        context.startActivity(intent);
    }
    /**
     * 添加地址
     */
    public static void gotoAddAddress(Context context){
        intent=new Intent(context, AddAddressActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 地址列表
     */
    public static void gotoAddAddressList(Context context){
        intent=new Intent(context, Activity_address_list.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**景点门票预定确
     * 认页面**/
    public static  void gotoAttractionsConfirm(Context context){
        intent =new Intent(context, AttractionsOrderConfirm.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**添加银行卡
     * 认页面**/
    public static  void gotoAddCard(Context context){
        intent =new Intent(context, AddCardActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 银行卡信息
     */
    public static void gotoCardDetails(Context context){
        intent=new Intent(context, CardDetailsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 银行卡验证
     */
    public static void gotoCheckCard(Context context){
        intent=new Intent(context, CheckSmsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 场景门票预定成功
     */
    public static void gotoSuccessed(Context context,String roder_id){
        intent=new Intent(context, Activity_Attractions_order_Successed.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ord_id",roder_id);
        context.startActivity(intent);
    }
    /**
     * 跳转到选择票数页面
     */
    public static  void gotoTicketOrder(Context context){
        intent=new Intent(context, TicketOrder.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 退税说明页面
     */
    public static void gotoDrawback(Context context){
        intent=new Intent(context, DrawBackActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 新增加退税点
     */
    public static void gotoAddDrawBack(Context context){
        intent=new Intent(context, AddDrawBackSiteActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 退税说明
     */
    public static void gotoDrawBackIntroduce(Context context,String coun_id){
        intent=new Intent(context, DrawBackIntroduceActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("country_id",coun_id);
        context.startActivity(intent);
    }
    /**
     * 应急通道
     */
    public static void gotoEmergencyActivity(Context context){
        intent=new Intent(context, EmergencyActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 海关手续
     */
    public static void gotoCustoms(Context context){
        intent=new Intent(context, ActivityCustomsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 抢单详情页面
     */
    public static void gotoCatchOrders(Context context,String dmd_id){
        intent=new Intent(context, CatchOrdersActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("dmd_id",dmd_id);
        context.startActivity(intent);
    }
    /**
     * 接单留言页面
     */
    public static void gotoCatchMessage(Context context,String ord_id){
        intent=new Intent(context, SendMessageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("ord_id",ord_id);
        context.startActivity(intent);
    }
    /**
     * 忘记密码
     */
    public static void gotoForgetPassword(Context context){
        intent=new Intent(context, Activity_ForgetPassword.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 忘记密码
     */
    public static void gotoSetting(Context context){
        intent=new Intent(context, Activity_Setting.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void gotoMessageSetting(Context context) {
        intent=new Intent(context, Activity_Message_Setting.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 关于我们
     */
    public static void gotoAboutUs(Context context) {
        intent=new Intent(context, Activity_About.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void gotoSuggestion(Context context) {
        intent=new Intent(context, Activity_Suggestion.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 退款记录
     * @param context
     */
    public static void gotoDrawRecord(Context context){
        intent=new Intent(context, Activity_DrawBackRecord.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 退款成功
     */
    public static void gotoDrawSuccess(Context context){
        intent=new Intent(context, Activity_Draw_Success.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void gotoDrawLoading(Context context) {
        intent=new Intent(context, Activity_Draw_Loading.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void gotoDrawWaiting(Context context) {
        intent=new Intent(context, Activity_Draw_Waiting.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 意见单记录
     */
    public static void gotoOpinion(Context context){
        intent=new Intent(context, Activity_Opinion.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 我的导游圈
     */
    public static void gotoMyGuideCircle(Context context){
        intent=new Intent(context, Activity_MyCircle.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 我的导游圈详情
     */
    public static void gotoMyGuideCircleDetails(Context context){
        intent=new Intent(context, Activity_myguide_details.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 个人中心
     * @param context
     */
    public static void gotoUserInfo(Context context) {
        intent=new Intent(context, Activity_UserInfo.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 账户明细
     */
    public static void gotoAcctountDetails(Context context){
        intent=new Intent(context, Activity_Account_Details.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 发布需求
     */
    public static void gotoReleaseDemand(Context context){
        intent=new Intent(context, Activity_ReleaseDemand.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }



    /**
     * 业内互助消息
     */
    public static void gotoHelpNews(Context context){
        intent=new Intent(context, Activity_Help_News.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 添加互助消息
     */
    public static void gotoAddHelpNews(Context context){
        intent=new Intent(context, Activity_Add_News.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 我的订单最新通知
     */
    public static void gotoNews(Context context){
        intent=new Intent(context, Activity_News.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 我的消息
     */
    public static void gotoMyDemand(Context context){
        intent=new Intent(context, Activity_My_Demand.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 景点详情普通
     */
    public static void gotoAttractionsNomal(Context context,int scene_id){
        intent=new Intent(context, AttractionDetailsNomal.class);
        intent.putExtra("scene_id",scene_id+"");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 选择参观路线
     */
    public static void selectRoutes(Context context,String mer_id){
        intent=new Intent(context, SelectRoutes.class);
        intent.putExtra("mer_id",mer_id);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 预定普通景点
     */
    public static void gotoOrderNomal(Context context,String route_id){
        intent=new Intent(context, Activity_Order_Nomal.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("route_id",route_id);
        context.startActivity(intent);
    }
    /**
     * 我的收藏
     */
    public static void gotoMyCollect(Context context){
        intent=new Intent(context, MyCollectActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 编辑我的需求
     */
    public static void gotoEditRelease(Context context){
        intent=new Intent(context, Activity_Edit_Release.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
    *我的订单最新通知
     */
    public static void gotoMyOrder_News(Context context){
        intent=new Intent(context, MyOrderNews_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 形成日历
     */
    public static void gotoJourny(Context context){
        intent=new Intent(context, Journy_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static void gotoJouney_Route(Context context){
        intent =new Intent(context, Activity_Route_List.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 选择车票
     */
    public static void gotoAttractionTicket(Context context,int scene_id){
        intent=new Intent(context, Attractions_TicketActivity.class);
        intent.putExtra("scene_id",scene_id+"");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void gotoTickList(Context context,String route_id,String start_station,String end_station,String date,String scene_id){
        intent=new Intent(context, Ticket_List_Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("route_id",route_id);
        intent.putExtra("scene_id",scene_id);
        intent.putExtra("start_station",start_station);
        intent.putExtra("end_station",end_station);
        intent.putExtra("date",date);
        context.startActivity(intent);
    }
    /**
     * 我的预定
     */
    public static void gotoMyOrder(Context context){
        intent=new Intent(context, MyOrdersActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    /**
     * 我的预定已完成详情
     */
    public static void gotoMyOrderDetails(Context context){
        intent=new Intent(context, MyOrderDetails.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
