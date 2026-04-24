package com.xoliu.module_profile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.gzuliyujiang.imagepicker.ActivityBuilder;
import com.github.gzuliyujiang.imagepicker.CropImageView;
import com.github.gzuliyujiang.imagepicker.ImagePicker;
import com.github.gzuliyujiang.imagepicker.PickCallback;

import org.json.JSONObject;

import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.MVUtil;

public class ImagePickerActivity extends AppCompatActivity {

    private static final String TAG = "ImagePickerActivity";
    private static final String BASE_URL = "http://127.0.0.1:9999/api";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_picker);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ImagePicker.getInstance().onActivityResult(this, requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        ImagePicker.getInstance().onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }

    public void onCamera(View view) {
        ImagePicker.getInstance().startCamera(this, true, new PickCallback() {
            @Override
            public void onPermissionDenied(String[] permissions, String message) {
                Toast.makeText(ImagePickerActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void cropConfig(ActivityBuilder builder) {
                builder.setMultiTouchEnabled(true)
                        .setGuidelines(CropImageView.Guidelines.ON_TOUCH)
                        .setCropShape(CropImageView.CropShape.OVAL)
                        .setRequestedSize(400, 400)
                        .setFixAspectRatio(true)
                        .setAspectRatio(1, 1);
            }

            @Override
            public void onCropImage(@Nullable Uri imageUri) {
                if (imageUri != null) {
                    uploadAvatar(imageUri);
                }
            }
        });
    }

    public void onGallery(View view) {
        ImagePicker.getInstance().startGallery(this, false, new PickCallback() {
            @Override
            public void onPermissionDenied(String[] permissions, String message) {
                Toast.makeText(ImagePickerActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPickImage(@Nullable Uri imageUri) {
                if (imageUri != null) {
                    uploadAvatar(imageUri);
                }
            }
        });
    }

    /**
     * 将选取的图片上传到后端，更新头像
     */
    private void uploadAvatar(Uri imageUri) {
        int userId = MVUtil.getInt("user_id", 0);
        if (userId == 0) {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "正在上传头像...", Toast.LENGTH_SHORT).show();

        new Thread(() -> {
            try {
                // 读取图片数据
                ContentResolver resolver = getContentResolver();
                InputStream inputStream = resolver.openInputStream(imageUri);
                if (inputStream == null) {
                    runOnUiThread(() -> Toast.makeText(this, "无法读取图片", Toast.LENGTH_SHORT).show());
                    return;
                }
                byte[] fileData = readAllBytes(inputStream);
                inputStream.close();

                // 获取文件名
                String fileName = getFileName(imageUri);
                if (fileName == null || fileName.isEmpty()) {
                    fileName = "avatar.jpg";
                }

                // 构建 multipart 请求
                OkHttpClient client = new OkHttpClient();
                RequestBody fileBody = RequestBody.create(
                        MediaType.parse("image/*"), fileData);
                MultipartBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("user_id", String.valueOf(userId))
                        .addFormDataPart("file", fileName, fileBody)
                        .build();

                Request request = new Request.Builder()
                        .url(BASE_URL + "/user/avatar")
                        .put(requestBody)
                        .build();

                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "uploadAvatar response: " + data);

                JSONObject json = new JSONObject(data);
                int code = json.optInt("Code", 0);

                runOnUiThread(() -> {
                    if (code == 200) {
                        String avatarUrl = json.optString("avatar_url", "");
                        // 保存到 MMKV
                        MVUtil.put("avatar_url", avatarUrl);
                        Toast.makeText(this, "头像更新成功", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String msg = json.optString("Message", "头像上传失败");
                        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(this, "上传失败: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }
        }).start();
    }

    private byte[] readAllBytes(InputStream inputStream) throws Exception {
        java.io.ByteArrayOutputStream buffer = new java.io.ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int nRead;
        while ((nRead = inputStream.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        return buffer.toByteArray();
    }

    private String getFileName(Uri uri) {
        String result = null;
        if ("content".equals(uri.getScheme())) {
            try (Cursor cursor = getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index >= 0) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }
}