package com.yijun.beauty;

import androidx.appcompat.app.AppCompatActivity;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class MyInfo extends AppCompatActivity {

    Button btn_end;
    TextView txt_nick_name;
    TextView txt_email;
    TextView txt_created_at;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        txt_nick_name = findViewById(R.id.txt_nick_name);
        txt_email = findViewById(R.id.txt_email);
        txt_created_at = findViewById(R.id.txt_created_at);

        SharedPreferences sp = getSharedPreferences(Utils.PREFERENCES_NAME,MODE_PRIVATE);
        String email = sp.getString("email", null);

        Retrofit retrofit = NetworkClient.getRetrofitClient(MyInfo.this);

        UserApi userApi = retrofit.create(UserApi.class);

        Call<UserCheck> call = userApi.checkUser(email);
        call.enqueue(new Callback<UserCheck>() {
            @Override
            public void onResponse(Call<UserCheck> call, Response<UserCheck> response) {
                // response.body() ==> PostRes 클래스
                if (response.isSuccessful()){
                    String nick_name = response.body().getNick_name();
                    String created_at = response.body().getCreated_at();

                    Log.i("info", nick_name + created_at + email);

                    txt_nick_name.setText(nick_name);
                    txt_email.setText(email);
                    SimpleDateFormat df = new SimpleDateFormat("YYYY-MM-dd HH-mm-ss", Locale.getDefault());
                    df.setTimeZone(TimeZone.getTimeZone("UTC"));    // 위의 시간을 utc로 맞추는것.(우리는 이미 서버에서 utc로 맞춰놔서 안해도 되는데 혹시몰라서 해줌)
                    try {
                        Date date = df.parse(created_at);
                        df.setTimeZone(TimeZone.getDefault());      // 내 폰의 로컬 타임존으로 바꿔줌.
                        String strDate = df.format(date).replace("T", "");
                        txt_created_at.setText(strDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }else {

                }

            }

            @Override
            public void onFailure(Call<UserCheck> call, Throwable t) {

            }
        });


        btn_end = findViewById(R.id.btn_end);
        btn_end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(MyInfo.this)
                        .setMessage("탈퇴하시겠습니까?")
                        .setPositiveButton("네", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //"예" 버튼 클릭시 할 동작
                                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() { //회원탈퇴 실행
                                    @Override
                                    public void onSessionClosed(ErrorResult errorResult) { //로그인 세션이 닫혀있을 시
                                        //다시 로그인해달라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                                        Toast.makeText(getApplicationContext(), "로그인 세션이 닫혔습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MyInfo.this, MainActivity.class);
                                        intent.putExtra("key",1);
                                        startActivity(intent);
                                        finish();
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
                                        //"회원탈퇴에 성공했습니다."라는 Toast 메세지를 띄우고 로그인 창으로 이동함
                                        Toast.makeText(getApplicationContext(), "회원탈퇴에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(MyInfo.this, MainActivity.class);
                                        intent.putExtra("key",1);
                                        startActivity(intent);
                                        finish();
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

    }
}
