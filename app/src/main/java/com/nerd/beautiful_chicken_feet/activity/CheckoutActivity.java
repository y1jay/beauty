package com.nerd.beautiful_chicken_feet.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.nerd.beautiful_chicken_feet.R;
import com.nerd.beautiful_chicken_feet.utils.Json;
import com.nerd.beautiful_chicken_feet.utils.Notifications;
import com.nerd.beautiful_chicken_feet.utils.PaymentsUtil;
import com.nerd.beautiful_chicken_feet.databinding.ActivityCheckoutBinding;

import java.util.Locale;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

/**
 * 앱에 대한 결제 구현
 */
public class CheckoutActivity extends AppCompatActivity {

    //지불 데이터 활동에 대한 요청을 추적하기 위해 사용자가 정의하는 임의로 선택한 상수 정수.
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    private static final long SHIPPING_COST_CENTS = 90 * PaymentsUtil.CENTS_IN_A_UNIT.longValue();

    //Google Pay API 와 상호 작용하기위한 클라이언트.
    private PaymentsClient paymentsClient;

    private ActivityCheckoutBinding layoutBinding;
    private View googlePayButton;

    private JSONArray garmentList;
    private JSONObject selectedGarment;

    /**
     *
     * 활동 생성시 Google Pay API 초기화
     *
     * @see Activity#onCreate(android.os.Bundle)
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initializeUi();

        //Android O+ 가이드 라인에 따라 알림((utils) Notifications) 채널 만들기
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Notifications.createNotificationChannelIfNotCreated(this);
        }

        // UI(user interface)에서 항목(Item)에 대한 모의 정보 설정.
        try {
            selectedGarment = fetchRandomGarment();
            displayGarment(selectedGarment);
        } catch (JSONException e) {
            throw new RuntimeException("The list of garments cannot be loaded");
        }

        // 테스트에 적합한 환경을 위해 Google Pay API 클라이언트 초기화.
        // onCreate 메서드 내부에 PaymentsClient 개체를 만드는 게 좋음.
        paymentsClient = PaymentsUtil.createPaymentsClient(this);
        possiblyShowGooglePayButton();
    }

    /**
     * 알림을 트리거(= 어느 특정한 동작에 반응해 자동으로 필요한 동작을 실행하는 것을 뜻한다)하는 메뉴 옵션 추가
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menus, menu);
        return true;
    }

    /**
     * 옵션 메뉴에서 선택 처리 (메뉴에 있는 send_notification 누르면 바로 알람옴)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send_notification:
                Notifications.triggerPaymentNotification(this);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Google Pay 결제 명세서에서 해결 된 활동을 처리함.
     *
     * @param requestCode requestPayment ()에서 AutoResolveHelper 에 원래 제공된 요청 코드.
     * @param resultCode  Google Pay API 에서 반환 한 결과 코드.
     * @param data        결제 또는 오류 데이터가 포함 된 Google Pay API 의 인텐트.
     * @see <a href="https://developer.android.com/training/basics/intents/result">여기에서 결과 얻기</a>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // AutoResolveHelper 에 전달된 값
            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;

                    case Activity.RESULT_CANCELED:
                        // 사용자가 결제 시도를 취소했을 때
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }

                // Google Pay 결제 버튼을 다시 활성화시킴.
                googlePayButton.setClickable(true);
        }
    }

    private void initializeUi() {

        // view binding(뷰 결합) 을 사용해 UI 요소에 접속(access), build.gradle(app) 25~27
        layoutBinding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        // 활동이 알림에서 열린 경우 알림 UI 닫기
        if (Notifications.ACTION_PAY_GOOGLE_PAY.equals(getIntent().getAction())) {
            sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }

        //Google Pay 버튼은 레이아웃 파일 – root view 사용해
        googlePayButton = layoutBinding.googlePayButton.getRoot();
        googlePayButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        requestPayment(view);
                    }
                });
    }

    private void displayGarment(JSONObject garment) throws JSONException {
        layoutBinding.detailTitle.setText(garment.getString("title"));
        layoutBinding.detailPrice.setText(
                String.format(Locale.getDefault(), "$%.2f", garment.getDouble("price")));

        final String escapedHtmlText = Html.fromHtml(
                garment.getString("description"), Html.FROM_HTML_MODE_COMPACT).toString();
        layoutBinding.detailDescription.setText(Html.fromHtml(
                escapedHtmlText, Html.FROM_HTML_MODE_COMPACT));

        final String imageUri = String.format("@drawable/%s", garment.getString("image"));
        final int imageResource = getResources().getIdentifier(imageUri, null, getPackageName());
        layoutBinding.detailImage.setImageResource(imageResource);
    }

    /**
     * 앱에서 지원하는 결제 수단으로 시청자가 결제 할 수 있는지 확인하고
     * Google Pay 결제 버튼 표시
     *
     * @see <a href="https://developers.google.com/android/reference/com/google/android/gms/wallet/
     * PaymentsClient.html#isReadyToPay(com.google.android.gms.wallet.
     * IsReadyToPayRequest)">PaymentsClient#IsReadyToPay</a>
     */
    private void possiblyShowGooglePayButton() {

        final Optional<JSONObject> isReadyToPayJson = PaymentsUtil.getIsReadyToPayRequest();
        if (!isReadyToPayJson.isPresent()) {
            return;
        }


        // isReadyToPay 에 대한 호출은 비동기(Asynchronous, 요청과 결과가 동시에 일어나지 않을거라는 약속)이며 Task 를 반환한다.
        // 호출 결과를 알 때 트리거 할 OnCompleteListener 를 제공해야합니다
        IsReadyToPayRequest request = IsReadyToPayRequest.fromJson(isReadyToPayJson.get().toString());
        Task<Boolean> task = paymentsClient.isReadyToPay(request);
        task.addOnCompleteListener(this,
                new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            setGooglePayAvailable(task.getResult());
                        } else {
                            Log.w("isReadyToPay failed", task.getException());
                        }
                    }
                });
    }

    /**
     * isReadyToPay 가 {@code true}를 반환 한 경우 버튼을 표시하고 '확인 중'텍스트를 숨김.
     * 그렇지 않으면 사용자에게 Google Pay 를 사용할 수 없다고 알림. 현재 사용자 흐름에 맞게 조정하시오.
     * isReadyToPay 가 {@code false}를 반환하는지 사용자에게 명시 적으로 알릴 필요가 없음.
     *
     * @param available isReadyToPay API response(응답).
     */
    private void setGooglePayAvailable(boolean available) {
        if (available) {
            googlePayButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, R.string.googlepay_status_unavailable, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * PaymentData response object(응답 객체)에는 결제 정보는 물론 청구 및 배송 주소와 같은 추가 요청 정보가 포함됨.
     *
     * @param paymentData 지급인이 결제를 승인 한 후 Google 에서 반환하는 응답 객체.
     * @see <a href="https://developers.google.com/pay/api/android/reference/
     * object#PaymentData">PaymentData</a>
     */
    private void handlePaymentSuccess(PaymentData paymentData) {

        // PaymentDataRequest 가 fromJson(String) 을 사용하여 생성되지 않은 경우 토큰은 null.
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // gateway 가 "example"로 설정되면, 결제 정보가 반환되지 않음 -
            // 대신, 토큰은 "examplePaymentMethodToken"으로만 구성됨.

            final JSONObject tokenizationData = paymentMethodData.getJSONObject("tokenizationData");
            final String tokenizationType = tokenizationData.getString("type");
            final String token = tokenizationData.getString("token");

            if ("PAYMENT_GATEWAY".equals(tokenizationType) && "examplePaymentMethodToken".equals(token)) {
                new AlertDialog.Builder(this)
                        .setTitle("Warning")
                        .setMessage(getString(R.string.gateway_replace_name_example))
                        .setPositiveButton("OK", null)
                        .create()
                        .show();
            }

            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    this, getString(R.string.payments_show_name, billingName),
                    Toast.LENGTH_LONG).show();

            // Logging token string.(토큰 찍어보기)
            Log.d("Google Pay token: ", token);

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }

    /**
     * 이 단계에서 사용자는 이미 오류가 발생했음을 알리는 팝업을 봤음.
     * 일반적으로, 로깅(= 로그)만 필요함.
     *
     * @param statusCode CommonStatusCode 이나 WalletConstants.ERROR_CODE_* constant(상수) 중 하나의 상수값 보유.
     * @see <a href="https://developers.google.com/android/reference/com/google/android/gms/wallet/WalletConstants#constant-summary">Wallet Constants Library</a>
     */
    private void handleError(int statusCode) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    public void requestPayment(View view) {

        // 버튼을 비활성화하여 다중클릭을 방지함.
        googlePayButton.setClickable(false);

        // API 에 제공되는 가격헤는 세금 및 배송비가 포함되어야 함.
        // 이 가격은 사용자에게 표시되지 않음.
        try {
            double garmentPrice = selectedGarment.getDouble("price");
            long garmentPriceCents = Math.round(garmentPrice * PaymentsUtil.CENTS_IN_A_UNIT.longValue());
            long priceCents = garmentPriceCents + SHIPPING_COST_CENTS;

            Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents);
            if (!paymentDataRequestJson.isPresent()) {
                return;
            }

            PaymentDataRequest request =
                    PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());

            // loadPaymentData 는 사용자에게 결제 방법을 선택하도록 요청하는 UI를 표시 할 수 있으므로
            // AutoResolveHelper 를 사용하여 사용자가 상호 작용할 때까지 기다린다.
            // 완료되면 onActivityResult 가 결과와 함께 호출됨.
            if (request != null) {
                AutoResolveHelper.resolveTask(
                        paymentsClient.loadPaymentData(request),
                        this, LOAD_PAYMENT_DATA_REQUEST_CODE);
            }

        } catch (JSONException e) {
            throw new RuntimeException("The price cannot be deserialized from the JSON object.");
        }
    }

    private JSONObject fetchRandomGarment() {

        // 이전에 로드되지 않은 경우에만 항목 목록을로드합니다
        if (garmentList == null) {
            garmentList = Json.readFromResources(this, R.raw.tshirts);
        }

        //목록에서 임의의 요소를 가져옵니다
        int randomIndex = Math.toIntExact(Math.round(Math.random() * (garmentList.length() - 1)));
        try {
            return garmentList.getJSONObject(randomIndex);
        } catch (JSONException e) {
            throw new RuntimeException("The index specified is out of bounds.");
        }
    }
}