package utils;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GalleryApi {
    private static final String TAG = "GalleryApi";
    private static final String BASE_URL = "http://127.0.0.1:9999/api/gallery";

    public static final int MSG_GET_MY = 401;
    public static final int MSG_CREATE = 402;
    public static final int MSG_UPDATE = 403;
    public static final int MSG_DELETE = 404;
    public static final int MSG_DETAIL = 405;

    /**
     * 获取我的画廊
     */
    public static void getMyGallery(int userId, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(BASE_URL + "/my?user_id=" + userId)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "getMyGallery: " + data);
                Message message = Message.obtain();
                message.what = MSG_GET_MY;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                Log.e(TAG, "getMyGallery error", e);
            }
        }).start();
    }

    /**
     * 创建画作（带图片上传）
     */
    public static void createGallery(int userId, String username, String title,
                                     byte[] imageData, String fileName,
                                     String creator, String year, String material,
                                     String size, String description, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                RequestBody fileBody = RequestBody.create(
                        MediaType.parse("image/*"), imageData);
                MultipartBody requestBody = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("user_id", String.valueOf(userId))
                        .addFormDataPart("username", username)
                        .addFormDataPart("title", title)
                        .addFormDataPart("creator", creator != null ? creator : "")
                        .addFormDataPart("year", year != null ? year : "")
                        .addFormDataPart("material", material != null ? material : "")
                        .addFormDataPart("size", size != null ? size : "")
                        .addFormDataPart("description", description != null ? description : "")
                        .addFormDataPart("file", fileName, fileBody)
                        .build();

                Request request = new Request.Builder()
                        .url(BASE_URL + "/create")
                        .post(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "createGallery: " + data);
                Message message = Message.obtain();
                message.what = MSG_CREATE;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                Log.e(TAG, "createGallery error", e);
            }
        }).start();
    }

    /**
     * 更新画作（可选重新上传图片）
     */
    public static void updateGallery(int id, int userId, String title,
                                     byte[] imageData, String fileName,
                                     String creator, String year, String material,
                                     String size, String description, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                MultipartBody.Builder bodyBuilder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("id", String.valueOf(id))
                        .addFormDataPart("user_id", String.valueOf(userId))
                        .addFormDataPart("title", title != null ? title : "")
                        .addFormDataPart("creator", creator != null ? creator : "")
                        .addFormDataPart("year", year != null ? year : "")
                        .addFormDataPart("material", material != null ? material : "")
                        .addFormDataPart("size", size != null ? size : "")
                        .addFormDataPart("description", description != null ? description : "");

                if (imageData != null && fileName != null) {
                    RequestBody fileBody = RequestBody.create(
                            MediaType.parse("image/*"), imageData);
                    bodyBuilder.addFormDataPart("file", fileName, fileBody);
                }

                Request request = new Request.Builder()
                        .url(BASE_URL + "/update")
                        .put(bodyBuilder.build())
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "updateGallery: " + data);
                Message message = Message.obtain();
                message.what = MSG_UPDATE;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                Log.e(TAG, "updateGallery error", e);
            }
        }).start();
    }

    /**
     * 删除画作
     */
    public static void deleteGallery(int id, int userId, Handler handler) {
        new Thread(() -> {
            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.parse("application/json; charset=utf-8");
                String jsonBody = "{\"id\":" + id + ",\"user_id\":" + userId + "}";
                RequestBody requestBody = RequestBody.create(JSON, jsonBody);
                Request request = new Request.Builder()
                        .url(BASE_URL + "/delete")
                        .delete(requestBody)
                        .build();
                Response response = client.newCall(request).execute();
                String data = response.body().string();
                Log.d(TAG, "deleteGallery: " + data);
                Message message = Message.obtain();
                message.what = MSG_DELETE;
                message.obj = data;
                handler.sendMessage(message);
            } catch (Exception e) {
                Log.e(TAG, "deleteGallery error", e);
            }
        }).start();
    }
}
