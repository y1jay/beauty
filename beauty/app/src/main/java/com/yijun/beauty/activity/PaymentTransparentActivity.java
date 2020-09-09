package com.yijun.beauty.activity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;


import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.yijun.beauty.R;
import com.yijun.beauty.utils.Notifications;
import com.yijun.beauty.utils.PaymentsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Optional;

import androidx.appcompat.app.AppCompatActivity;

/**
 * notification(알림)에서 trigger(자동으로 뜨는)되는 투명한 액티비티
 */
public class PaymentTransparentActivity extends AppCompatActivity {

    //지불 데이터 활동에 대한 요청을 추적하기 위해 사용자가 정의하는 임의로 선택한 상수 정수.
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 991;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //활동이 알림에서 열린 경우 알림 UI 닫기
        if (Notifications.ACTION_PAY_GOOGLE_PAY.equals(getIntent().getAction())) {
            sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }

        showPaymentsSheet();
    }

    /**
     * Google Pay 결제 명세서에서 해결 된 활동을 처리함
     *
     * @param requestCode requestPayment ()에서 AutoResolveHelper 에 원래 제공된 요청 코드.
     * @param resultCode  Google Pay API 에서 반환 한 결과 코드.
     * @param data        결제 또는 오류 데이터가 포함 된 Google Pay API 의 인텐트.
     * @see <a href="https://developer.android.com/training/basics/intents/result">Getting a result
     * from an Activity</a>
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case LOAD_PAYMENT_DATA_REQUEST_CODE:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        PaymentData paymentData = PaymentData.getFromIntent(data);
                        handlePaymentSuccess(paymentData);
                        break;

                    case Activity.RESULT_CANCELED:
                        // 사용자가 결제 방법을 선택하지 않고 simple 하게 취소했을 때
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        // 저거 써서 오류에 대해 자세히 알아봐 – AutoResolveHelper.getStatusFromIntent(data);
                        break;
                }

                // Close the activity
                finishAndRemoveTask();
        }
    }

    private void showPaymentsSheet() {

        // 사용자 선택에 따라 가격을 가져옴.
        long priceCents = getIntent().getLongExtra(Notifications.OPTION_PRICE_EXTRA, 2500L);

        // TransactionInfo transaction = PaymentsUtil.createTransaction(price);
        Optional<JSONObject> paymentDataRequestJson = PaymentsUtil.getPaymentDataRequest(priceCents);
        if (!paymentDataRequestJson.isPresent()) {
            return;
        }

        PaymentDataRequest request =
                PaymentDataRequest.fromJson(paymentDataRequestJson.get().toString());

        if (request != null) {
            final PaymentsClient paymentsClient = PaymentsUtil.createPaymentsClient(this);
            AutoResolveHelper.resolveTask(
                    paymentsClient.loadPaymentData(request),
                    this, LOAD_PAYMENT_DATA_REQUEST_CODE);
        }
    }

    /**
     *
     * PaymentData 응답 객체에는 결제 정보는 물론 청구 및 배송 주소와 같은 추가 요청 정보가 포함됨.
     *
     * @param paymentData 지급인이 결제를 승인 한 후 Google 에서 반환하는 응답 객체
     * @see <a href="https://developers.google.com/pay/api/android/reference/
     * object#PaymentData">PaymentData</a>
     */
    private void handlePaymentSuccess(PaymentData paymentData) {

        // PaymentDataRequest 가 fromJson(String)을 사용하여 생성되지 않은 경우 토큰은 null.
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }

        // 결제 알림 제거.
        Notifications.remove(this);

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");

            final JSONObject info = paymentMethodData.getJSONObject("info");
            final String billingName = info.getJSONObject("billingAddress").getString("name");
            Toast.makeText(
                    this, getString(R.string.payments_show_name, billingName),
                    Toast.LENGTH_LONG).show();

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }
}