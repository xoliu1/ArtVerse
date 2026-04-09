package com.xoliu.module_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.xoliu.module_login.Iview.mView;
import com.xoliu.module_login.databinding.ActivityLoginBinding;
import com.xoliu.module_login.model.reDate;
import com.xoliu.module_login.presenter.transForm;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import utils.MVUtil;

@Route(path = "/login/main")
public class LoginActivity extends AppCompatActivity implements mView {
    private ActivityLoginBinding binding;
    ScrollView login;
    ScrollView register;
    ScrollView retrieve;

    private TextInputLayout email0;

    private TextInputLayout password;
    private TextView button1;
    private TextView button2;
    private TextView button3;

    private TextView button4;

    private TextView button5;

    private TextView button6;
    private TextView button7;
    private TextView button8;
    private TextView button9;
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
        button3 = findViewById(R.id.RetrieveButton);
        login = findViewById(R.id.loginScrollView);
        register = findViewById(R.id.registerScrollView);
        retrieve = findViewById(R.id.retrieveScrollView);
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
                if(pop != null){
                    transform1.model.logat(pop,handlerS);

                    new CountDownTimer(60000,1000){
                        @Override
                        public void onTick(long millisUntilFinished) {
                            button8.setClickable(false);
                            button8.setEnabled(false);
                            button8.setText(millisUntilFinished / 1000 + "s Resend");
                        }

                        @Override
                        public void onFinish() {
                            button8.setText("Send Code");
                            button8.setClickable(true);
                            button8.setEnabled(true);
                            cancel();
                        }
                    }.start();
                } else {
                    Toast.makeText(getApplicationContext(),"Invalid email",Toast.LENGTH_SHORT).show();
                }
            }
        });
        button9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = textInputLayout6.getEditText().getText().toString();
                String y = textInputLayout7.getEditText().getText().toString();
                transform1.model.login(x,y,handler);
            }
        });
//        button9.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 跳过后端验证，直接登录成功
//                MVUtil.getInstance().put("Logined", true);
//                ARouter.getInstance().build("/main/shell").navigation();
//                finish();
//            }
//        });

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
                if(pass.equals(word)){
                    transform1.model.reg(name,pass,email,horse,handlerT);
                }else {
                    Toast.makeText(getApplicationContext(),"Passwords do not match",Toast.LENGTH_SHORT).show();
                }
            }
        });
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pop = textInputLayout2.getEditText().getText().toString();

                transform1.model.logat(pop,handlerS);

                new CountDownTimer(60000,1000){

                    @Override
                    public void onTick(long millisUntilFinished) {
                        button6.setClickable(false);
                        button6.setEnabled(false);
                        button6.setText(millisUntilFinished / 1000 + "s Resend");
                    }

                    @Override
                    public void onFinish() {
                        button6.setText("Send Code");
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
                transform1.model.login(x,y,handler);
            }
        });
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // 跳过后端验证，直接登录成功
//                MVUtil.getInstance().put("Logined", true);
//                ARouter.getInstance().build("/main/shell").navigation();
//                finish();
//            }
//        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login.setVisibility(View.GONE);
                retrieve.setVisibility(View.VISIBLE);
                register.setVisibility(View.GONE);
            }
        });


//        binding.btnLogin.setOnClickListener(v -> {
//            //下面一行代码用于跳转主界面，在登录验证通过后使用
//            ARouter.getInstance().build("/main/shell").navigation();
//        });

    }
    public Handler handler = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){
                String fgh = (String) msg.obj;
                Log.d("TAD", "handleMessage: " + fgh);
                Gson gson = new Gson();
                data = gson.fromJson(fgh,reDate.class);
                Log.d("10086", "handleMessage: " + data);
                if(data.getCode() == 200){
                    ARouter.getInstance().build("/main/shell").navigation();
                    MVUtil.getInstance().put("Logined", true);
                    MVUtil.getInstance().put("user_id", data.getUserId());
                    MVUtil.getInstance().put("user_email", data.getUserEmail() != null ? data.getUserEmail() : "");
                    MVUtil.getInstance().put("username", data.getUsername() != null ? data.getUsername() : "");
                    MVUtil.getInstance().put("avatar_url", data.getAvatarUrl() != null ? data.getAvatarUrl() : "");
                }else {
                    Toast.makeText(getApplicationContext(),"Incorrect password or unregistered account",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    public Handler handlerT = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 10){
                String fgh = (String) msg.obj;
                Log.d("TAD", "handleMessage: " + fgh);
                Gson gson = new Gson();
                data = gson.fromJson(fgh,reDate.class);
                Log.d("10086", "handleMessage: " + data);
                if(data.getCode() == 200){
                    // 注册成功后回到登录页面，而不是直接进入主页
                    Toast.makeText(getApplicationContext(),"Registration successful, please sign in",Toast.LENGTH_SHORT).show();
                    login.setVisibility(View.VISIBLE);
                    register.setVisibility(View.GONE);
                    retrieve.setVisibility(View.GONE);
                }else {
                    Toast.makeText(getApplicationContext(),"Incorrect verification code",Toast.LENGTH_SHORT).show();
                }
            }
        }
    };
    public Handler handlerS = new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100){
                String fgh = (String) msg.obj;
                Log.d("TAD", "handleMessage: " + fgh);
                Gson gson = new Gson();
                data = gson.fromJson(fgh,reDate.class);
                Log.d("10086", "handleMessage: " + data);
                if(data.getCode() == 200){
                    Toast.makeText(getApplicationContext(),"Verification code sent, please check your email!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Failed to send verification code",Toast.LENGTH_SHORT).show();
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