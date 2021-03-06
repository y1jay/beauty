package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kakao.auth.ApiErrorCode;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.StringSet;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.util.OptionalBoolean;
import com.yijun.beauty.api.NetworkClient;
import com.yijun.beauty.api.UserApi;
import com.yijun.beauty.model.UserCheck;
import com.yijun.beauty.model.UserReq;
import com.yijun.beauty.model.UserRes;
import com.yijun.beauty.url.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.kakao.usermgmt.StringSet.email;
import static com.kakao.usermgmt.StringSet.nickname;
import static com.kakao.usermgmt.StringSet.phone_number;

public class MyInfo extends AppCompatActivity {

    TextView txt_nick_name;
    TextView txt_phone;
    TextView txt_email;
    TextView txt_agree;
    TextView txt_created_at;
    Button btn_update;
    Button btn_end;
    Button btn_my_review;

    // 닉네임 수정하기
    private AlertDialog alertDialog;
    EditText txt_new_nick_name;
    Button btn_change;
    Button btn_no;

    // 탈퇴 (카카오 or 미인닭발)
    Boolean kakao;

    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        txt_nick_name = findViewById(R.id.txt_nick_name);
        txt_phone = findViewById(R.id.txt_phone);
        txt_email = findViewById(R.id.txt_email);
        txt_agree = findViewById(R.id.txt_agree);
        txt_created_at = findViewById(R.id.txt_created_at);

        SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
        String nick_name = sp.getString("nick_name", null);
        String phone_number = sp.getString("phone_number", null);
        Log.i("info", nick_name +  phone_number);

        // 닉네임이랑 폰넘버 가져와야함
        Retrofit retrofit = NetworkClient.getRetrofitClient(MyInfo.this);
        UserApi userApi = retrofit.create(UserApi.class);

        Call<UserCheck> call = userApi.info_User(nick_name);
        call.enqueue(new Callback<UserCheck>() {
            @Override
            public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                // response.body() ==> PostRes 클래스
                if (response.isSuccessful()){
                    String nick_name = response.body().getNick_name();
                    String phone = response.body().getPhone_number();
                    String email = response.body().getEmail();
                    int agree = response.body().getInfo_agree();
                    String created_at = response.body().getCreated_at();

                    Log.i("info", nick_name + created_at + email);

                    txt_nick_name.setText(nick_name);
                    txt_phone.setText(phone);

                    if (email == null){
                        txt_email.setVisibility(View.GONE);
                        kakao = true;
                    }else {
                        txt_email.setText(email);
                        kakao = false;
                    }

                    if (agree == 1){
                        txt_agree.setText("동의");
                    }else if (agree == 0){
                        txt_agree.setText("동의안함");
                    }

                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                    df.setTimeZone(TimeZone.getTimeZone("UTF"));    // 위의 시간을 utc로 맞추는것.(우리는 이미 서버에서 utc로 맞춰놔서 안해도 되는데 혹시몰라서 해줌)

                    try {
                        Date date = df.parse(created_at);
                        df.setTimeZone(TimeZone.getDefault());      // 내 폰의 로컬 타임존으로 바꿔줌.
                        String strDate = df.format(date).replace("T", " ");
                        txt_created_at.setText(strDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }else {
                    Log.i("tlqkf", response.toString());
                }

            }

            @Override
            public void onFailure(Call<UserCheck> call, Throwable t) {
                Log.i("tlqkf", t.toString());
            }
        });

        btn_update = findViewById(R.id.btn_update);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
                String nick_name = sp.getString("nick_name", null);
                String phone_number = sp.getString("phone_number", null);

                AlertDialog.Builder alert = new AlertDialog.Builder(MyInfo.this);
                View alertView = getLayoutInflater().inflate(R.layout.change_nick_name,null);
                txt_new_nick_name = alertView.findViewById(R.id.txt_new_nick_name);
                btn_change = alertView.findViewById(R.id.btn_change);
                btn_no = alertView.findViewById(R.id.btn_no);

                btn_change.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String new_nick_name = txt_new_nick_name.getText().toString().trim();
                        if (new_nick_name.isEmpty()){
                            Toast.makeText(MyInfo.this,"아이디를 입력해주세요",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (new_nick_name.equals(txt_nick_name.getText().toString().trim())){
                            Log.i("nick_name", txt_nick_name.getText().toString().trim() +new_nick_name);
                            Toast.makeText(MyInfo.this, "현재 닉네임과 동일합니다.", Toast.LENGTH_LONG).show();
                            return;
                        }

                        Retrofit retrofit = NetworkClient.getRetrofitClient(MyInfo.this);
                        UserApi userApi = retrofit.create(UserApi.class);

                        Call<UserCheck> call = userApi.checkId(nick_name);
                        call.enqueue(new Callback<UserCheck>() {
                            @Override
                            public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                                // 상태코드가 200 인지 확인
                                if (response.isSuccessful()==true){
                                    Toast.makeText(MyInfo.this,"닉네임이 중복되었습니다.",Toast.LENGTH_SHORT).show();
                                    return;
                                }else if(response.isSuccessful()==false){

                                    Retrofit retrofit = NetworkClient.getRetrofitClient(MyInfo.this);

                                    UserApi userApi = retrofit.create(UserApi.class);

                                    Call<UserRes> call0 = userApi.changeUser(phone_number, new_nick_name,nick_name);
                                    call0.enqueue(new Callback<UserRes>() {
                                        @Override
                                        public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                                            // response.body() ==> PostRes 클래스
                                            if (response.isSuccessful()){
                                                //"회원탈퇴에 성공했습니다."라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                                                Toast.makeText(MyInfo.this, "닉네임이 변경되었습니다.", Toast.LENGTH_SHORT).show();
                                                Log.i("nick_name", response.message());
                                                txt_nick_name.setText(new_nick_name);
                                                alertDialog.cancel();
                                            }else{
                                                Toast.makeText(MyInfo.this,"닉네임이 중복되었습니다.",Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<UserRes> call, Throwable t) {

                                        }
                                    });
                                }
                            }
                            @Override
                            public void onFailure(Call<UserCheck> call, Throwable t) {

                            }
                        });
                    }
                });

                btn_no.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.cancel();
                    }
                });

                alert.setView(alertView);

                alertDialog = alert.create();
                alertDialog.setCancelable(false);
                alertDialog.show();

            }
        });


        btn_end = findViewById(R.id.btn_end);
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // 카카오 탈퇴
                    new AlertDialog.Builder(MyInfo.this)
                            .setTitle("탈퇴")
                            .setMessage("탈퇴하시겠습니까?")
                            .setPositiveButton("네", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //"예" 버튼 클릭시 할 동작
                                    UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() { //회원탈퇴 실행
                                        @Override
                                        public void onSessionClosed(ErrorResult errorResult) { //로그인 세션이 닫혀있을 시 (미인닭발로그인)
                                            // 디비 탈퇴
                                            SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
                                            String nick_name = sp.getString("nick_name", null);

                                            Retrofit retrofit = NetworkClient.getRetrofitClient(MyInfo.this);
                                            UserApi userApi = retrofit.create(UserApi.class);

                                            Call<UserRes> call = userApi.delUser(nick_name);
                                            call.enqueue(new Callback<UserRes>() {
                                                @Override
                                                public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                                                    // response.body() ==> PostRes 클래스
                                                    if (response.isSuccessful()){
                                                        //"회원탈퇴에 성공했습니다."라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                                                        Toast.makeText(getApplicationContext(), "회원탈퇴", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(MyInfo.this, MainActivity.class);
                                                        intent.putExtra("key",1);
                                                        SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
                                                        SharedPreferences.Editor editor = sp.edit();
                                                        editor.putBoolean("auto_login",false);
                                                        editor.apply();
                                                        finish();
                                                        startActivity(intent);
                                                    }else {

                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<UserRes> call, Throwable t) {

                                                }
                                            });

                                            sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sp.edit();
                                            editor.putBoolean("auto_login",false);
                                            editor.apply();
                                        }

                                        @Override
                                        public void onFailure(ErrorResult errorResult) { //회원탈퇴 실패 시
                                            int result = errorResult.getErrorCode();

                                            if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                                Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getApplicationContext(), "회원탈퇴에 실패했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        @Override
                                        public void onSuccess(Long result) { //회원탈퇴에 성공하면
                                            // 디비 탈퇴
                                            SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
                                            String nick_name = sp.getString("nick_name", null);

                                            Retrofit retrofit = NetworkClient.getRetrofitClient(MyInfo.this);
                                            UserApi userApi = retrofit.create(UserApi.class);

                                            Call<UserRes> call = userApi.delUser(nick_name);
                                            call.enqueue(new Callback<UserRes>() {
                                                @Override
                                                public void onResponse(Call<UserRes> call, Response<UserRes> response) {
                                                    // response.body() ==> PostRes 클래스
                                                    if (response.isSuccessful()){
                                                        //"회원탈퇴에 성공했습니다."라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                                                        Toast.makeText(getApplicationContext(), "탈퇴 되었습니다.", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(MyInfo.this, MainActivity.class);
                                                        intent.putExtra("key",1);
                                                        finish();
                                                        startActivity(intent);
                                                    }else {

                                                    }

                                                }

                                                @Override
                                                public void onFailure(Call<UserRes> call, Throwable t) {

                                                }
                                            });
                                        }
                                    });

                                    dialog.dismiss(); //팝업창 종료
                                }
                            })
                            .setNegativeButton("아니요", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //"아니오" 버튼 클릭 시 할 동작
                                    dialog.dismiss(); //팝업창 종료
                                }
                            }).show();
                }
        });

        btn_my_review = findViewById(R.id.btn_my_review);
        btn_my_review.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MyInfo.this,Myreview.class);
                startActivity(i);
            }
        });

    }
}
