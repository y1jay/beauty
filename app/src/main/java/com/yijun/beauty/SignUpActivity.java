package com.yijun.beauty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.BeautyReq;
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SignUpActivity extends AppCompatActivity {
    TextView txtphone;
    EditText edtid;
    Button btnidcheck;
    Button btnsignup;

    SharedPreferences sp;

    String my_phone_num;
    private static final int MY_PERMISSION_STORAGE = 1111;

    // 동의
    ImageButton check_box;
    CheckBox check_agree;
    ScrollView scrollView;

    Boolean agree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        checkPermission();

        check_box = findViewById(R.id.check_box);
        check_agree = findViewById(R.id.check_agree);
        scrollView = findViewById(R.id.scrollView);
        check_box.setImageResource(android.R.drawable.arrow_down_float);

        check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (scrollView.getVisibility() == View.VISIBLE){
                    scrollView.setVisibility(View.GONE);
                    check_box.setImageResource(android.R.drawable.arrow_down_float);
                }else if (scrollView.getVisibility() == View.GONE){
                    scrollView.setVisibility(View.VISIBLE);
                    check_box.setImageResource(android.R.drawable.arrow_up_float);
                }

            }
        });

        txtphone = findViewById(R.id.txtphone);
        edtid = findViewById(R.id.edtid);
        btnidcheck = findViewById(R.id.btnidcheck);
        btnsignup = findViewById(R.id.btnsignup);

        getPhone();
        txtphone.setText(my_phone_num);

        btnidcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nick_name = edtid.getText().toString().trim();
                if (nick_name.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                Retrofit retrofit = NetworkClient.getRetrofitClient(SignUpActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserCheck> call = userApi.checkId(nick_name);
                call.enqueue(new Callback<UserCheck>() {
                    @Override
                    public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()==false){
                            Toast.makeText(SignUpActivity.this,"이미있는 닉네임입니다.",Toast.LENGTH_SHORT).show();
                            edtid.setText("");
                            return;
                        }else{
                            Toast.makeText(SignUpActivity.this,"사용가능한 닉네임입니다.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    @Override
                    public void onFailure(Call<UserCheck> call, Throwable t) {

                    }
                });
            }
        });

        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check_agree.isChecked() == true){
                    agree = true;
                }else {
                    Toast.makeText(SignUpActivity.this, "동의 시 이용 가능합니다.", Toast.LENGTH_LONG).show();
                    return;
                }

                String nick_name = edtid.getText().toString().trim();
                String my_phone = txtphone.getText().toString().trim();
                if (nick_name.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"닉네임을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (my_phone.isEmpty()){
                    Toast.makeText(SignUpActivity.this,"휴대폰 번호를을 입력해주세요",Toast.LENGTH_SHORT).show();
                    return;
                }

                BeautyReq beautyReq = new BeautyReq(nick_name,my_phone_num,agree);

                Retrofit retrofit = NetworkClient.getRetrofitClient(SignUpActivity.this);
                UserApi userApi = retrofit.create(UserApi.class);

                Call<UserRes> call = userApi.beautyUser(beautyReq);
                Log.i("beautyUser",nick_name+my_phone_num+agree);

                call.enqueue(new Callback<UserRes>() {
                    @Override
                    public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                        // 상태코드가 200 인지 확인
                        if (response.isSuccessful()){
                            // response.body() 가 UserRes.이다.
                            boolean success = response.body().isSuccess();

                            // 전화번호 저장.
                            sp = getSharedPreferences(Utils.PREFERENCES_NAME, MODE_PRIVATE);
                            SharedPreferences.Editor editor = sp.edit();
                            editor.putString("phone_number", my_phone_num);
                            editor.putString("nick_name", nick_name);
                            editor.apply();

                            Intent i = new Intent(SignUpActivity.this,AfterLogin.class);
                            i.putExtra("nick_name",nick_name);
                            i.putExtra("phone_number",my_phone_num);
                            i.putExtra("info_agree",agree);
                            startActivity(i);
                            finish();

                        } else if (response.isSuccessful()==false){
                            Toast.makeText(SignUpActivity.this,"입력하신 정보가 맞지 않습니다.",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<UserRes> call, Throwable t) {

                    }
                });
            }
        });

    }

    @SuppressLint({"MissingPermission", "HardwareIds"})

    private String getPhone() {
        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(SignUpActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            return "";
        }
        my_phone_num = tm.getLine1Number();

        if (my_phone_num != null) {

            my_phone_num = my_phone_num.replace("+82", "0");

        }

        return tm.getLine1Number();
    }


    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // 다시 보지 않기 버튼을 만드려면 이 부분에 바로 요청을 하도록 하면 됨 (아래 else{..} 부분 제거)
            // ActivityCompat.requestPermissions((Activity)mContext, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSION_CAMERA);

            // 처음 호출시엔 if()안의 부분은 false로 리턴 됨 -> else{..}의 요청으로 넘어감
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_PHONE_STATE)) {
                new AlertDialog.Builder(this)
                        .setTitle("알림")
                        .setMessage("저장소 권한이 거부되었습니다. 사용을 원하시면 설정에서 해당 권한을 직접 허용하셔야 합니다.")
                        .setNeutralButton("설정", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                intent.setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            }
                        })
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                            }
                        })
                        .setCancelable(false)
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_PHONE_NUMBERS}, MY_PERMISSION_STORAGE);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_STORAGE:
                for (int i = 0; i < grantResults.length; i++) {
                    // grantResults[] : 허용된 권한은 0, 거부한 권한은 -1
                    if (grantResults[i] < 0) {
                        checkPermission();
                        Toast.makeText(SignUpActivity.this, "해당 권한을 활성화 하셔야 합니다.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                // 허용했다면 이 부분에서..
                getPhone();
                Toast.makeText(SignUpActivity.this, "해당 권한이 활성화 되었습니다.", Toast.LENGTH_SHORT).show();
                txtphone.setText(my_phone_num);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(SignUpActivity.this,MainActivity.class);
        i.putExtra("key",1);
        finish();
        startActivity(i);
    }
}
