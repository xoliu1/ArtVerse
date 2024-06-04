package com.xoliu.module_login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.xoliu.module_login.Iview.mView;
import com.xoliu.module_login.databinding.ActivityLoginBinding;
import com.xoliu.module_login.model.reDate;
import com.xoliu.module_login.presenter.transForm;

import java.io.IOException;

import global.ProfileUser;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import utils.MVUtil;

@Route(path = "/login/main")
public class LoginActivity extends AppCompatActivity implements mView {
    private ActivityLoginBinding binding;
    LinearLayout login;
    LinearLayout register;
    LinearLayout retrieve;

    private TextInputLayout email0;

    private TextInputLayout password;
    private Button button1;
    private Button button2;
//    private Button button3;

    private Button button4;

    private Button button5;

    private Button button6;
    private Button button7;
    private Button button8;
    private Button button9;
    private TextInputLayout textInputLayout1;

    private TextInputLayout textInputLayout2;

    private TextInputLayout textInputLayout3;

    private TextInputLayout textInputLayout4;

    private TextInputLayout textInputLayout5;
    private TextInputLayout textInputLayout6;
    private TextInputLayout textInputLayout7;

    private reDate data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        email0 = findViewById(R.id.email0);
        textInputLayout1 = findViewById(R.id.name);
        textInputLayout2 = findViewById(R.id.emailR);
        textInputLayout3 = findViewById(R.id.mm);
        textInputLayout4 = findViewById(R.id.yz);
        textInputLayout5 = findViewById(R.id.emailZ);
        textInputLayout6 = findViewById(R.id.zhy);
        textInputLayout7 = findViewById(R.id.zhM);
        password = findViewById(R.id.password);
        button1 = findViewById(R.id.registerButton);
        button2 = findViewById(R.id.logAt);
//        button3 = findViewById(R.id.RetrieveButton);
        login = findViewById(R.id.longAt);
        register = findViewById(R.id.register);
        retrieve = findViewById(R.id.RetrieveL);
        transForm transform1 = new transForm();
        transform1.setView(this);
        transform1.SetText(textInputLayout1);
        transform1.SetText(textInputLayout2);
        transform1.SetText2(textInputLayout3);
        transform1.SetText2(textInputLayout4);
        transform1.SetText(textInputLayout6);
        transform1.SetText(email0);
        transform1.SetText2(password);
        button4 = findViewById(R.id.return2);
        button5 = findViewById(R.id.registerB);
        button6 = findViewById(R.id.registerB2);
        button7 = findViewById(R.id.return1);
        button8 = findViewById(R.id.send);
        button9 = findViewById(R.id.pop);
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.VISIBLE);
                retrieve.setVisibility(View.GONE);
                register.setVisibility(View.GONE);
            }
        });
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pop = textInputLayout6.getEditText().getText().toString();
                if (pop != null) {
                    transform1.model.logat(pop, handlerS);

                    new CountDownTimer(60000, 1000) {
                        @Override
                        public void onTick(long millisUntilFinished) {
                            button8.setClickable(false);
                            button8.setEnabled(false);
                            button8.setText(millisUntilFinished / 1000 + "重新发送");
                        }

                        @Override
                        public void onFinish() {
                            button8.setText("发送验证码");
                            button8.setClickable(true);
                            button8.setEnabled(true);
                            cancel();
                        }
                    }.start();
                } else {
                    Toast.makeText(getApplicationContext(), "邮箱不正确", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = textInputLayout6.getEditText().getText().toString();
                String y = textInputLayout7.getEditText().getText().toString();
                transform1.model.login(x, y, handler);
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.VISIBLE);
                retrieve.setVisibility(View.GONE);
                register.setVisibility(View.GONE);
            }
        });
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textInputLayout1.getEditText().getText().toString();
                String email = textInputLayout2.getEditText().getText().toString();
                String pass = textInputLayout3.getEditText().getText().toString();
                String word = textInputLayout4.getEditText().getText().toString();
                String horse = textInputLayout5.getEditText().getText().toString();
                if (pass.equals(word)) {
                    transform1.model.reg(name, pass, email, horse, handlerT);
                } else {
                    Toast.makeText(getApplicationContext(), "两次输入密码不一致", Toast.LENGTH_SHORT).show();
                }
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pop = textInputLayout2.getEditText().getText().toString();

                transform1.model.logat(pop, handlerS);

                new CountDownTimer(60000, 1000) {

                    @Override
                    public void onTick(long millisUntilFinished) {
                        button6.setClickable(false);
                        button6.setEnabled(false);
                        button6.setText(millisUntilFinished / 1000 + "重新发送");
                    }

                    @Override
                    public void onFinish() {
                        button6.setText("发送验证码");
                        button6.setClickable(true);
                        button6.setEnabled(true);
                        cancel();
                    }
                }.start();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.GONE);
                retrieve.setVisibility(View.GONE);
                register.setVisibility(View.VISIBLE);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = email0.getEditText().getText().toString();
                String y = password.getEditText().getText().toString();
                transform1.model.login(x, y, handler);
            }
        });
//        button3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                login.setVisibility(View.GONE);
//                retrieve.setVisibility(View.VISIBLE);
//                register.setVisibility(View.GONE);
//            }
//        });


//        binding.btnLogin.setOnClickListener(v -> {
//            //下面一行代码用于跳转主界面，在登录验证通过后使用
//            ARouter.getInstance().build("/main/shell").navigation();
//        });

    }

    public Handler handler = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String fgh = (String) msg.obj;
                Log.d("TAD", "handleMessage: " + fgh);
                Gson gson = new Gson();
                data = gson.fromJson(fgh, reDate.class);
                Log.d("10086", "handleMessage: " + data);
                if (data.getCode() == 200) {
                    ARouter.getInstance().build("/main/shell").navigation();
                    MVUtil.getInstance().put("Logined", true);
                    //登陆成功逻辑
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("http://1.92.123.214:16666/api/user/userinfo?username=" + email0.getEditText().getText().toString())
                            .build();
                    client.newCall(request).enqueue(new okhttp3.Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Log.d("TAG", "onFailure: " + e);
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String body = response.body().string();
                            if (response!=null){
                                Log.d("TAG", "onResponse: != null" + response);
                            }
                            //Log.d("TAG", "onResponse: " + response.body().string());
                            Log.d("TAG", "onResponse: " + response.body());
                            ProfileUser profileUser = new Gson().fromJson(body, ProfileUser.class);
                            MVUtil.getInstance().put("profileName", profileUser.getData().getUsername());
                        }
                    });
                }
            } else {
                Toast.makeText(getApplicationContext(), "密码不正确或账号未注册", Toast.LENGTH_SHORT).show();
            }
        }


    };
    public Handler handlerT = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10) {
                String fgh = (String) msg.obj;
                Log.d("TAD", "handleMessage: " + fgh);
                Gson gson = new Gson();
                data = gson.fromJson(fgh, reDate.class);
                Log.d("10086", "handleMessage: " + data);
                if (data.getCode() == 200) {
                    ARouter.getInstance().build("/main/shell").navigation();
                } else {
                    Toast.makeText(getApplicationContext(), "验证码不正确", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    public Handler handlerS = new Handler(Looper.myLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                String fgh = (String) msg.obj;
                Log.d("TAD", "handleMessage: " + fgh);
                Gson gson = new Gson();
                data = gson.fromJson(fgh, reDate.class);
                Log.d("10086", "handleMessage: " + data);
                if (data.getCode() == 200) {
                    Toast.makeText(getApplicationContext(), "验证码已发送，请注意查收!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "验证码发送失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    @Override
    public void logSuccess() {

    }

    @Override
    public void logFail() {

    }
}