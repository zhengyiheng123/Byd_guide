package com.example.asus.xyd_order.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.base.BaseFragmentTemp;
import com.example.asus.xyd_order.net.HttpCallBack;
import com.example.asus.xyd_order.net.ServiceApi;
import com.example.asus.xyd_order.net.result.FirstResult;
import com.example.asus.xyd_order.net.upload.MultipartBuilder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Zheng on 2017/2/17.
 */
public class PhotoActivity extends BaseActivity {

    private Button bnt_camera,btn_picture;
    //image
    public final static int PHOTO_ZOOM = 0;
    public final static int TAKE_PHOTO = 1;
    public final static int PHOTO_RESULT = 2;
    public static final String IMAGE_UNSPECIFIED = "image/*";
    private String imageDir;
    private File picture;
    private Bitmap photo;
    private ImageView iv_head;

    @Override
    public void myOnclick(View view) {
        switch (view.getId()){
            case R.id.btn_camera:
                Intent intent;
                if (Build.VERSION.SDK_INT < 19) {
                    intent = new Intent(Intent.ACTION_GET_CONTENT);
                } else {
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                }
                intent.setType(IMAGE_UNSPECIFIED);
                Intent wrapperIntent = Intent.createChooser(intent, null);
                startActivityForResult(wrapperIntent, 0);
                break;
            case R.id.btn_picture:
                imageDir = "temp.jpg";
                Intent intent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Environment.getExternalStorageDirectory(), imageDir)));
                startActivityForResult(intent1, TAKE_PHOTO);
                break;
        }
    }

    @Override
    public void setToolbar() {

    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_photo;
    }


    @Override
    public int getData() {
        return 1;
    }

    @Override
    public void initView() {
        bnt_camera= (Button) findViewById(R.id.btn_camera);
        btn_picture= (Button) findViewById(R.id.btn_picture);
        iv_head = (ImageView) findViewById(R.id.iv_head);
    }

    @Override
    public void setEvent() {
        bnt_camera.setOnClickListener(this);
        btn_picture.setOnClickListener(this);
    }

    /**
     * 图片处理
     *
     * @param uri
     */
    public void photoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, IMAGE_UNSPECIFIED);
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 350);
        intent.putExtra("outputY", 350);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, PHOTO_RESULT);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == this.RESULT_OK) {
            if (requestCode == PHOTO_ZOOM) {
                photoZoom(data.getData());
            }

            if (requestCode == TAKE_PHOTO) {
                picture = new File(Environment.getExternalStorageDirectory() + "/" + imageDir);
                photoZoom(Uri.fromFile(picture));
            }

            if (requestCode == PHOTO_RESULT) {
                Bundle extras = data.getExtras();
                if (extras != null) {
                    photo = extras.getParcelable("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.JPEG, 75, stream);
                    byte[] bytes=stream.toByteArray();
                    Bitmap bitmap=BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    iv_head.setImageBitmap(bitmap);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

//多文件上传demo
    private void uploadFile(){
        List<File> fileList=new ArrayList<>();
        MultipartBody multipartBody=MultipartBuilder.filesToMultipartBody(fileList);
        ServiceApi.getInstance().getServiceContract().uploadFileWithRequestBody(multipartBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
