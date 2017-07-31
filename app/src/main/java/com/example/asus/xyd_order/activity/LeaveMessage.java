package com.example.asus.xyd_order.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.asus.xyd_order.R;
import com.example.asus.xyd_order.base.BaseActivity;
import com.example.asus.xyd_order.commonview.AddPicLayout;
import com.example.asus.xyd_order.commonview.OnPreviewListener;
import com.example.asus.xyd_order.commonview.ShowPicLayout;

import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPagerActivity;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by Zheng on 2017/3/3.
 */
public class LeaveMessage extends BaseActivity implements OnPreviewListener {

    private AddPicLayout addPicLayout;
    public final static int REQUEST_CODE = 1;
    ArrayList<String> selectedPhotos = new ArrayList<>();
    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private ShowPicLayout showPicLayout;

    @Override
    public void myOnclick(View view) {

    }

    @Override
    public void setToolbar() {
        ImageView iv_back= (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(v -> {onBackPressed();});
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_leave_message;
    }


    @Override
    public int getData() throws Exception {
        return 1;
    }

    @Override
    public void initView() {
        //申请权限
        getPermission();

    }



    private void iniPicturePicker() {
        addPicLayout = (AddPicLayout) findViewById(R.id.addPicLayout);
        addPicLayout.setOnPreviewListener(this);

        //显示网络图片
//        showPicLayout = (ShowPicLayout) findViewById(R.id.showPicLayout);
//        showPicLayout.setOnPreviewListener(this);
    }

    private void getPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST_CALL_PHONE);
        } else
        {
            //初始化图片选择
            iniPicturePicker();
        }
    }

    @Override
    public void setEvent() {

    }

    @Override
    public void onPreview(int pos, boolean delete) {
        Intent intent = new Intent(this, PhotoPagerActivity.class);
        intent.putExtra(PhotoPagerActivity.EXTRA_CURRENT_ITEM, pos);
        intent.putExtra(PhotoPagerActivity.EXTRA_SHOW_DELETE,delete);
        if (delete){
            intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, selectedPhotos);
        } else {
            //显示网络图片
//            intent.putExtra(PhotoPagerActivity.EXTRA_PHOTOS, urls);
        }
        startActivityForResult(intent, REQUEST_CODE);
    }


    //申请权限回调函数

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_CALL_PHONE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                //初始化图片选择
                iniPicturePicker();
            } else
            {
                // Permission Denied
                Toast.makeText(LeaveMessage.this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPick() {
        PhotoPickerIntent intent = new PhotoPickerIntent(LeaveMessage.this);
        intent.setPhotoCount(9);
        intent.setShowCamera(true);
        intent.setShowGif(false);
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
            }
            selectedPhotos.clear();

            if (photos != null) {
                selectedPhotos.addAll(photos);
            }
            addPicLayout.setPaths(selectedPhotos);
        }
    }


}
