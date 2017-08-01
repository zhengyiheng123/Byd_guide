package com.example.asus.xyd_order.net;





import com.example.asus.xyd_order.net.result.ActAttractionsBean;
        import com.example.asus.xyd_order.net.result.AddressBean;
        import com.example.asus.xyd_order.net.result.AttractionsBean;
        import com.example.asus.xyd_order.net.result.AttractionsOrderSuccess;
        import com.example.asus.xyd_order.net.result.AttractionsTimeBean;
        import com.example.asus.xyd_order.net.result.Calender;
        import com.example.asus.xyd_order.net.result.CategoryBean;
        import com.example.asus.xyd_order.net.result.CityListBean;
        import com.example.asus.xyd_order.net.result.CodeBean;
        import com.example.asus.xyd_order.net.result.Collections;
        import com.example.asus.xyd_order.net.result.CommentBean;
        import com.example.asus.xyd_order.net.result.CommentRecords;
        import com.example.asus.xyd_order.net.result.ConfirmUserInfo;
        import com.example.asus.xyd_order.net.result.CountryBean;
        import com.example.asus.xyd_order.net.result.CreateDemand;
        import com.example.asus.xyd_order.net.result.DemandUpdate;
        import com.example.asus.xyd_order.net.result.Demand_Details_Bean;
        import com.example.asus.xyd_order.net.result.EmbassyBean;
        import com.example.asus.xyd_order.net.result.FirstResult;
        import com.example.asus.xyd_order.net.result.ForgetPassword;
        import com.example.asus.xyd_order.net.result.GuideDetailsBean;
        import com.example.asus.xyd_order.net.result.Guide_Circle_Bean;
        import com.example.asus.xyd_order.net.result.HeadBean;
        import com.example.asus.xyd_order.net.result.His_SampleBean;
        import com.example.asus.xyd_order.net.result.History_Mode;
import com.example.asus.xyd_order.net.result.HomePage;
import com.example.asus.xyd_order.net.result.HospitaiDetails;
import com.example.asus.xyd_order.net.result.HospitalBean;
import com.example.asus.xyd_order.net.result.HttpResult;
        import com.example.asus.xyd_order.net.result.IsCollectBean;
        import com.example.asus.xyd_order.net.result.JingDianDetails;
        import com.example.asus.xyd_order.net.result.JingdianBean;
        import com.example.asus.xyd_order.net.result.LoginResult;
        import com.example.asus.xyd_order.net.result.Msg;
        import com.example.asus.xyd_order.net.result.MutualBean;
        import com.example.asus.xyd_order.net.result.MyDemandBean;
        import com.example.asus.xyd_order.net.result.MyOrderBean;
        import com.example.asus.xyd_order.net.result.Name;
        import com.example.asus.xyd_order.net.result.NomalOrderSuccess;
import com.example.asus.xyd_order.net.result.NoticeBean;
import com.example.asus.xyd_order.net.result.Order_Info_Bean;
        import com.example.asus.xyd_order.net.result.RegisterBean;
        import com.example.asus.xyd_order.net.result.RestaurantBean;
        import com.example.asus.xyd_order.net.result.RestaurantDetailsBean;
        import com.example.asus.xyd_order.net.result.RoutDetailsBean;
        import com.example.asus.xyd_order.net.result.RouteBean;
        import com.example.asus.xyd_order.net.result.RouteDetails;
        import com.example.asus.xyd_order.net.result.ServiceBean;
        import com.example.asus.xyd_order.net.result.TakingOrderBean;
        import com.example.asus.xyd_order.net.result.TuancanBean;
        import com.example.asus.xyd_order.net.result.TuiShuiDetails;
        import com.example.asus.xyd_order.net.result.TuishuiBean;
        import com.example.asus.xyd_order.net.result.UserInfoResult;
        import com.example.asus.xyd_order.net.result.ZhongcanOrderSuccessBean;
import com.example.asus.xyd_order.utils.StringUtils;

import java.util.List;

        import okhttp3.MultipartBody;
        import okhttp3.RequestBody;
        import okhttp3.ResponseBody;
        import retrofit2.Call;
        import retrofit2.http.Body;
        import retrofit2.http.DELETE;
        import retrofit2.http.Field;
        import retrofit2.http.FormUrlEncoded;
        import retrofit2.http.GET;
        import retrofit2.http.Headers;
        import retrofit2.http.Multipart;
        import retrofit2.http.POST;
        import retrofit2.http.Part;
        import retrofit2.http.Path;
        import retrofit2.http.Query;
        import rx.Observable;

/**
 * Created by xiaotongchao on 2016/8/25.
 * Function :
 */
public interface HttpService {

    @GET("recite/groups")
    Call<ResponseBody> getTest2();

    /**
     * https 测试
     * @return
     */
    @FormUrlEncoded
    @POST("v1/user/login.json")
    @Headers("Content-type: application/x-www-form-urlencoded")
    Call<ResponseBody> login(
            @Field("mobile") String mobile,
            @Field("password") String password,
            @Field("os") String os,
            @Field("imei") String imei
    );
    /**
     * https 测试
     * @return
     */
//    @FormUrlEncoded
//    @POST ("v1/user/login.json")
//    @Headers("Content-type: application/x-www-form-urlencoded")
//    Call<User> getTest3(
//            @Field("mobile") String mobile,
//            @Field("password") String password,
//            @Field("os") String os,
//            @Field("imei") String imei
//    );


    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @return 状态信息
     */
//    @POST("http://api.imysky.com:6080/v1/node/upload.json?uid=606115&timestamp=1472103420&sign=dc2369c18f5bf62770234aa392d21c6e&imei=862263031103196&")
//    Call<ResultResponse<Result>> uploadFileWithRequestBody3(
//            @Body MultipartBody multipartBody);
//@GET("index.php/Home/index/index")
//    Call<FirstBean> getInfo(@Query("page") int page);
//    @GET("whtapi/new_index.php")
//    Call<ResponseBody> getInfo();

    @Multipart
    @POST("index.php?c=Upload&m=doUpload")
    //  @POST("index.php/Upload/doUpload")
    Call<HttpResponse<Msg>> upload(
            @Part("file\"; filename=\"image.png\" ") RequestBody file,
            @Part("description") RequestBody description);

    /**
     * 获取微信支付参数
     * @return
     */
    @GET("index.php/Home/index/index")
    Call<HttpResponse<FirstResult>> getWebChat(@Query("type") String type, @Query("page") int page);


    @GET("v1/node/comment/list.json")
//    @Headers("Content-type: application/x-www-form-urlencoded")
    Call<ResponseBody> comment_list(
            @Query("page") String page,
            @Query("page_rows") String page_rows,
            @Query("nid") String nid
    );
    /**
     * 获取微信支付订单信息
     */
    @FormUrlEncoded
    @POST("pay/unifiedorder")
    Call<ResponseBody> getWeixin(@Field("appid") String appid,@Field("attach") String attach,@Field("body") String body
            ,@Field("mch_id") String mch_id,@Field("nonce_str") String nonce_str,@Field("notify_url") String notify_url,
                                 @Field("out_trade_no") String out_trade_no,@Field("spbill_create_ip") String spbill_create_ip,
                                 @Field("total_fee") String total_fee,@Field("trade_type") String trade_type,@Field("sign") String sign
    );

    /**
     * 通过 List<MultipartBody.Part> 传入多个part实现多文件上传
     * @param parts 每个part代表一个
     * @return 状态信息
     */
    @Multipart
    @POST("users/image")
    Call<ResponseBody> uploadFilesWithParts(@Part() List<MultipartBody.Part> parts);


    /**
     * 通过 MultipartBody和@body作为参数来上传
     * @param multipartBody MultipartBody包含多个Part
     * @return 状态信息
     */
    @POST("users/image")
    Call<ResponseBody> uploadFileWithRequestBody(@Body MultipartBody multipartBody);
    /**
     * 导游用户注册
     */
    @FormUrlEncoded
    @POST("v1/user/register")
    Observable<HttpResult<RegisterBean>> register(@Field("reg_type")int reg_type
            ,@Field("mobile")String mobile
            ,@Field("password")String password,
                                                  @Field("email")String email
            ,@Field("captcha")int captcha
            ,@Field("area_code")String area_code
    );
    /**
     * 注册获取验证码
     */
    @FormUrlEncoded
    @POST("v1/user/captcha")
    Observable<HttpResult<CodeBean>> getCode(@Field("reg_type")int reg_type, @Field("mobile")String mobile, @Field("area_code")String area_code,@Field("email")String email);

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("v1/user/login")
    Observable<HttpResult<LoginResult>> login(@Field("user_name")String user_name,@Field("password")String password);
    /**
     * 修改密码
     */
    @FormUrlEncoded
    @POST("v1/user/forget")
    Observable<HttpResult<ForgetPassword>> findPassword(@Field("reg_type")int reg_type,
                                                        @Field("mobile")String mobile,
                                                        @Field("area_code")String area_code,
                                                        @Field("email")String email
            ,@Field("password")String password,
                                                        @Field("captcha")String captcha
    );
    /**
     * 我的全部需求
     */
    @GET("v1/demand")
    Observable<HttpResult<MyDemandBean>> getMyDeman(@Query("ord_status")String ord_status,@Query("apitoken")String apitoken);

    /**
     * 我接的单
     */
    @GET("v1/demand/taked")
    Observable<HttpResult<MyDemandBean>> getMyDemanOther(@Query("ord_status")String ord_status,@Query("apitoken")String apitoken);
    /**
     * 需求详情
     */
    @GET("v1/demand/{dmd_id}")
    Observable<HttpResult<Demand_Details_Bean>> getDemandDetails(@Path("dmd_id")String dmd_id,@Query("apitoken")String apitoken);

    /**
     * 需求订单更新
     */
    @POST("v1/demand/{dmd_id}")
    Observable<HttpResult<DemandUpdate>> updateDemand(@Path("dmd_id")String dmd_id,@Body()RequestBody route_img);

    /**
     * 需求订单取消
     */
    @FormUrlEncoded
    @POST("v1/demand/cancel")
    Observable<HttpResult> cancelDemand(@Field("apitoken") String apitoken,@Field("dmd_id")String dmd_id);

    /**
     * 确认完成需求订单
     */
    @FormUrlEncoded
    @POST("v1/demand/finish")
    Observable<HttpResult> finishDemand(@Field("apitoken")String apitoken,@Field("dmd_id")String dmd_id);
    /**
     * 我的导游圈
     */
    @GET("v1/user/set")
    Observable<HttpResult<Guide_Circle_Bean>> guideCicle(@Query("apitoken")String apitoken);

    /**
     * 创建需求订单
     */
    @POST("v1/demand")
    Observable<HttpResult<CreateDemand>> createDemand(@Body()RequestBody body);

    /**
     * 获取国家列表
     */
    @GET("v1/demand/countrylist")
    Observable<HttpResult<CountryBean>> getCountrys(@Query("apitoken")String apitoken);
    /**
     * 注册认证
     */
    @POST("v1/user")
    Observable<HttpResult<ConfirmUserInfo>> confirmUserinfo(@Body()RequestBody body);

    /**
     * 修改用户头像
     */
    @POST("v1/user/avatar")
    Observable<HttpResult<HeadBean>> changeHead(@Body()RequestBody body);
    /**
     * 更新用户信息
     */
    @POST("v1/user/{user_id}")
    Observable<HttpResult<LoginResult>> updateUserInfo(@Path("user_id")String user_id,@Body()RequestBody body);

    /**
     * 获取用户信息
     */
    @GET("v1/user/{user_id}")
    Observable<HttpResult<UserInfoResult>> getUserInfo(@Path("user_id")String user_id,@Query("apitoken")String apitoken);

    /**
     * 导游圈详情
     */
    @GET("v1/user/set/{user_id}")
    Observable<HttpResult<GuideDetailsBean>> guideDetails(@Path("user_id")String user_id,@Query("apitoken")String apitoken);

    /**
     * 填写意见单
     */
    @FormUrlEncoded
    @POST("v1/demand/comment")
    Observable<HttpResult> comment(@Field("apitoken") String apitoken,@Field("dmd_id")String dmd_id,
                                   @Field("rank")String rank,@Field("comment")String comment
    );

    /**
     * 餐厅评论
     */
    @FormUrlEncoded
    @POST("v1/res_comment")
    Observable<HttpResult> commentRestaurant(@Field("apitoken") String apitoken,@Field("dmd_id")String dmd_id,
                                             @Field("rank")String rank,@Field("comment")String comment,@Field("ord_id")String ord_id);
    /**
     * 景点评论
     */
    @FormUrlEncoded
    @POST("v1/scene_comment")
    Observable<HttpResult> commentJingdian(@Field("apitoken") String apitoken,@Field("dmd_id")String dmd_id,
                                             @Field("rank")String rank,@Field("comment")String comment,@Field("ord_id")String ord_id);

    /**
     * 意见单记录
     */
    @GET("v1/demand/comment")
    Observable<HttpResult<CommentRecords>> commentRecords(@Query("apitoken")String apitoken,@Query("index_type")int index_type);
    /**
     * 提交反馈信息
     */
    @FormUrlEncoded
    @POST("v1/user/suggestion")
    Observable<HttpResult> submitSuggestion(@Field("apitoken")String apitoken,@Field("content")String content);

    /**
     * 地址列表
     */
    @GET("v1/address")
    Observable<HttpResult<AddressBean>> addressData(@Query("apitoken")String apitoken);

    /**
     * 设置默认地址
     */
    @FormUrlEncoded
    @POST("v1/address/default")
    Observable<HttpResult> setDefault(@Field("apitoken")String apitoken,@Field("ua_id")String ua_id);

    /**
     * 删除收货地址
     */
    @DELETE("v1/address/{ua_id}")
    Observable<HttpResult> deleteAddress(@Path("ua_id")String ua_id,@Query("apitoken")String apitoken);

    /**
     * 保存用户收货地址
     */
    @FormUrlEncoded
    @POST("v1/address")
    Observable<HttpResult> saveAddress(@Field("apitoken")String apitoken,@Field("user_name")String user_name,@Field("post_code")String post_code,@Field("mobile")String mobile,@Field("address")String address,
                                       @Field("region_name_list")String region_name_list,@Field("is_default")String is_default
    );

    /**
     * 获取单个地址信息
     * @param ua_id
     * @param apitoken
     * @return
     */
    @GET("v1/address/{ua_id}/edit")
    Observable<HttpResult<AddressBean.AddressesBean>> addressInfo(@Path("ua_id")String ua_id,@Query("apitoken")String apitoken);

    /**
     * 更新收货地址信息
     */
    @FormUrlEncoded
    @POST("v1/address/{ua_id}")
    Observable<HttpResult> updateAddress(@Path("ua_id")String ua_id,@Field("apitoken")String apitoken,@Field("user_name")String user_name,@Field("post_code")String post_code,@Field("mobile")String mobile,@Field("address")String address,
                                         @Field("region_name_list")String region_name_list,@Field("is_default")String is_default,@Field("_method")String _method);

    /**
     * 样本列表
     */
    @GET("v1/sample")
    Observable<HttpResult<History_Mode>> historyList(@Query("apitoken")String apitoken);

    /**
     *删除需求样本
     */
    @DELETE("v1/sample/{ds_id}")
    Observable<HttpResult> deleteHistory(@Path("ds_id")String ds_id,@Query("apitoken")String apitoken);

    /**
     * 需求样本详情
     */
    @GET("v1/sample/{ds_id}")
    Observable<HttpResult<His_SampleBean>> sampleDetails(@Path("ds_id")String ds_id, @Query("apitoken")String apitoken);

    /**
     * 存为样本
     */
    @FormUrlEncoded
    @POST("v1/sample")
    Observable<HttpResult> saveToSample(@Field("apitoken")String apitoken,@Field("dmd_id")String dmd_id,@Field("ds_name")String ds_name);

    /**
     * 获取接单者用户名
     */
    @FormUrlEncoded
    @POST("v1/demand/username")
    Observable<HttpResult<Name>> getName(@Field("apitoken")String apitoken,@Field("dmd_id")String dmd_id);

    /**
     * 保存新成员进导游圈
     */
    @FormUrlEncoded
    @POST("v1/user/set")
    Observable<HttpResult> addtoFriend(@Field("apitoken")String apitoken,@Field("user_id")String user_id);

    /**
     * 我接的单列表
     */
    @GET("v1/demand")
    Observable<HttpResult<MyDemandBean>> myOrdersGeted(@Query("apitoken")String apitoken,@Query("ord_status")int ord_status);

    /**
     * 接单者取消订单
     */
    @FormUrlEncoded
    @POST("v1/demand/taked/cancel")
    Observable<HttpResult> getedCancel(@Field("apitoken")String apitoken,@Field("dmd_id")String dmd_id);

    /**
     * 我接的单详情
     */
    @GET("v1/demand/taked/{dmd_id}")
    Observable<HttpResult<Demand_Details_Bean>> getOrderDetails(@Path("dmd_id")String dmd_id,@Query("apitoken")String apitoken);

    /**
     * 业内互助消息
     */
    @GET("v1/mutual")
    Observable<HttpResult<MutualBean>> getMutualData(@Query("apitoken")String apitoken);

    /**
     * 发表互助消息
     */
    @POST("v1/mutual")
    Observable<HttpResult> sendMutualData(@Field("apitoken")String apitoken,@Field("content")String content);

    /**
     * 去接单列表
     */
    @GET("v1/demand/taking")
    Observable<HttpResult<TakingOrderBean>> takingOrderList(@Query("apitoken")String apitoken);

    /**
     * 接单。。
     */
    @POST("v1/demand/taking")
    Observable<HttpResult> taking(@Query("apitoken")String apitoken,@Query("dmd_id")String dmd_id);

    /**
     * 餐厅列表
     */
    @GET("v1/restaurant")
    Observable<HttpResult<RestaurantBean>> restaurantList(@Query("apitoken")String apitoken,@Query("p")int p,@Query("longitude")String longitude,@Query("latitude")String latitude,@Query("cate_id")String cate_id,
                                                          @Query("sort_type")String sort_type,@Query("park")String park,@Query("price_start")String price_start,@Query("price_end")String price_end,
                                                          @Query("seat_start")String seat_start,@Query("seat_end")String seat_end,
                                                          @Query("sub_cate_id")String sub_cate_id
                                                          );

    /**
     * 餐厅详情页
     */
    @GET("v1/restaurant/{mer_id}")
    Observable<HttpResult<RestaurantDetailsBean>> restaurantDetails(@Path("mer_id")String mer_id,@Query("apitoken")String apitoken);

    /**
     * 获取中餐子级分类列表
     */
    @GET("v1/restaurant/subcategory")
    Observable<HttpResult<CategoryBean>> categoryBean(@Query("apitoken")String apitoken,@Query("cate_id")String cate_id);

    /**
     * 餐厅评价列表
     */
    @GET("v1/res_comment")
    Observable<HttpResult<CommentBean>> commentList(@Query("apitoken")String apitoken, @Query("mer_id")String mer_id);
    /**
     * 景区评价列表
     */
    @GET("v1/scene_comment")
    Observable<HttpResult<CommentBean>> commentJingquList(@Query("apitoken")String apitoken, @Query("mer_id")String mer_id);
    /**
     * 团餐详情
     */
    @GET("v1/restaurant/meal/{tuan_id}")
    Observable<HttpResult<TuancanBean>> tuancanDetails(@Path("tuan_id")String tuan_id,@Query("apitoken")String apitoken);

    /**
     *团餐预定
     */
    @FormUrlEncoded
    @POST("v1/res_order")
    Observable<HttpResult<ZhongcanOrderSuccessBean>> orderSuccess(@Field("apitoken")String apitoken,
           @Field("group_num")String group_num,@Field("seat_cost")String seat_cost,@Field("book_time")String book_time,
            @Field("pay_type")String pay_type,@Field("mer_id")String mer_id,@Field("message")String message,@Field("price")String price,
             @Field("user_name")String user_name,@Field("mobile")String mobile,@Field("meal_type")String meal_type,@Field("mp_id")String mp_id,
             @Field("nums")String nums,@Field("single_meal")String single_meal
                                                                  );
    /**
     * 预定详情
     */
    @GET("v1/res_order/{id}")
    Observable<HttpResult<Order_Info_Bean>> orderInfo(@Path("id")String ord_id,@Query("apitoken")String apitoken);

    /**
     * 获取退税说明城市列表
     */
    @GET("v1/drawback/city_list")
    Observable<HttpResult<CityListBean>> cityList(@Query("apitoken")String apitoken);
    /**
     * 获取退税说明城市列表
     */
    @GET("v1/drawback/country_list")
    Observable<HttpResult<CityListBean>> countyrList(@Query("apitoken")String apitoken);
    /**
     * 退税详情，退税点说明
     */
    @GET("v1/drawback")
    Observable<HttpResult<TuishuiBean>> tuishui(@Query("apitoken")String apitoken,@Query("region_id")String region_id);

    //退税点详情
    @GET("v1/drawback/{id}")
    Observable<HttpResult<TuiShuiDetails>> tuishuiDetails(@Path("id")String id,@Query("apitoken")String apitoken);

    /**
     * 添加退税点
     */
    @POST("v1/drawback")
    Observable<HttpResult> drawBack(@Body()RequestBody body);

    /**
     * 应急通道接口
     */
    @GET("v1/embassy")
    Observable<HttpResult<EmbassyBean>> embassy(@Query("apitoken")String apitoken,@Query("region_id")String region_id);

    /**
     * 景点列表
     */
    @GET("v1/scene")
    Observable<HttpResult<JingdianBean>> jingdianList(@Query("apitoken")String apitoken,@Query("longitude")String longitude,@Query("latitude")String latitude,@Query("sort_type")String sort_type,@Query("p")String p);

    /**
     * 景区详情
     */
    @GET("v1/scene/{id}")
    Observable<HttpResult<JingDianDetails>> jingdianDetails(@Path("id")String id,@Query("apitoken")String apitoken);

    /**
     * 普通景区路线
     */
    @GET("v1/scene/normal/route")
    Observable<HttpResult<RouteBean>> routeList(@Query("apitoken")String apitoken,@Query("mer_id")String mer_id);

    /**
     * 普通景区路线详情
     */
    @GET("v1/scene/normal/route/{id}")
    Observable<HttpResult<RoutDetailsBean>>  routeDeTails(@Path("id")String id, @Query("apitoken")String apitoken);

    /**
     * 普通景区保存
     */
    @FormUrlEncoded
    @POST("v1/scene/normal/order")
    Observable<HttpResult<ZhongcanOrderSuccessBean>> saveOrderNomal(@Field("apitoken")String apitoken,@Field("group_num")String group_num,@Field("pay_type")String pay_type,
                                          @Field("post_type")String post_type,@Field("mer_id")String mer_id,@Field("route_id")String route_id,
                                          @Field("price")String price,@Field("ua_id")String ua_id,@Field("user_name")String user_name,@Field("mobile")String mobile
                                          ,@Field("group_ticket")String group_ticket,@Field("normal_ticket")String normal_ticket
                                          );

    /**
     * 普通景点预定成功
     */
    @GET("v1/scene/normal/order/{id}")
    Observable<HttpResult<NomalOrderSuccess>> nomalOrderSuccess(@Path("id")String id,@Query("apitoken")String apitoken);

    /**
     * 景点取消
     */
    @POST("v1/scene/order/cancel")
    Observable<HttpResult> cancelNomal(@Query("ord_id")String ord_id,@Query("apitoken")String apitoken,@Query("cancel_content")String cancel_content);

    /**
     * 景区商户，景区详情
     */
    @GET("v1/scene/{id}")
    Observable<HttpResult<AttractionsBean>> attractiosDetails(@Path("id")String id,@Query("apitoken")String apitoken);

    /**
     * 表演景区类，获取时间列表
     */
    @POST("v1/scene/show/date")
    Observable<HttpResult<AttractionsTimeBean>> attractionsTimesBean(@Query("apitoken")String apitoken,@Query("mer_id")String mer_id);

    /**
     * 演出类景区演出列表
     */
    @GET("v1/scene/show/route")
    Observable<HttpResult<ActAttractionsBean>> actAttraction(@Query("apitoken")String apitoken,@Query("mer_id")String mer_id,@Query("date")String date,@Query("month")String month);

    /**
     * 表演类景区预定详情（演出详情）
     */
    @GET("v1/scene/show/route/{id}")
    Observable<HttpResult<RouteDetails>> routeDetails(@Path("id")String id,@Query("apitoken")String apitoken);

    /**
     * 保存新增表演类景区订单
     */
    @POST("v1/scene/show/order")
    Observable<HttpResult<ZhongcanOrderSuccessBean>> showOrder(@Query("apitoken")String apitoken,@Query("group_num")String group_num,@Query("pay_type")String pay_type,
        @Query("post_type")String post_type,@Query("mer_id")String mer_id,@Query("route_id")String route_id,@Query("price")String price,@Query("ua_id")String ua_id,@Query("user_name")String user_name
         ,@Query("mobile")String mobile,@Query("group_ticket")String group_ticket,@Query("normal_ticket")String normal_ticket
    );

    /**
     * 表演类景区订单详情
     */
    @GET("v1/scene/show/order/{id}")
    Observable<HttpResult<AttractionsOrderSuccess>> orderSuccess(@Path("id")String id,@Query("apitoken")String apitoken);

    /**
     *游船景区类站点列表
     */
    /**
     * 客服接口
     */
    @GET("v1/system/customer_service")
    Observable<HttpResult<ServiceBean>> service(@Query("apitoken")String apitoken);

    /**
     * 收藏取消收藏
     */
    @FormUrlEncoded
    @POST("v1/user/collection")
    Observable<HttpResult<IsCollectBean>> collect(@Field("apitoken")String apitoken,@Field("mer_id")String mer_id);

    /**
     * 我的收藏列表
     */
    @GET("v1/user/collection")
    Observable<HttpResult<Collections>> collectionsList(@Query("apitoken") String apitoken);

    /**
     * 我的预定列表
     */
    @GET("v1/orders")
    Observable<HttpResult<MyOrderBean>> myOrderList(@Query("apitoken")String apitoken, @Query("ord_status")String ord_status, @Query("stamp")String stamp);

    /**
     * 取消中餐订单
     */
    @FormUrlEncoded
    @POST("v1/res_order/cancel")
    Observable<HttpResult> cancelZhongcan(@Field("apitoken")String apitoken,@Field("ord_id")String ord_id,@Field("cancel_content")String cancel_content);

    /**
     * 行程日历列表
     */
    @GET("v1/orders/calendar")
    Observable<HttpResult<Calender>> calenderList(@Query("apitoken")String apitoken);

    /**
     * 首页
     */
    @GET("v1")
    Observable<HttpResult<HomePage>> homePage(@Query("apitoken")String apitoken);

    /**
     * 推送消息列表
     */
    @GET("v1/notice")
    Observable<HttpResult<NoticeBean>> notice(@Query("apitoken")String apitoken,@Query("ord_type")String ord_type,@Query("p")String p);

    /**
     * 联系操作员
     */
    @FormUrlEncoded
    @POST("v1/demand/taking/msg")
    Observable<HttpResult> msg(@Field("apitoken")String apitoken,@Field("msg")String msg,@Field("ord_id")String ord_id);

    /**
     * 医院列表
     */
    @GET("v1/hospital")
    Observable<HttpResult<HospitalBean>> hospitalList(@Query("apitoken")String apitoken,@Query("region_id")String region_id);

    /**
     *医院详情
     */
    @GET("v1/hospital/{id}")
    Observable<HttpResult<HospitaiDetails>> hospitalDetails(@Path("id")String id,@Query("apitoken")String apitoken);

    /**
     * 添加医院
     */
    @POST("v1/hospital")
    Observable<HttpResult> addHospital(@Body()RequestBody body);
}
