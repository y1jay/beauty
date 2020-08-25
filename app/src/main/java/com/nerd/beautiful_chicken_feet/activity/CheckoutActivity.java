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
     * @param data        결제 또는 오류 데이터가 포함 된 Google Pay API의 인 텐트.
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
                        // The user cancelled the payment attempt
                        break;

                    case AutoResolveHelper.RESULT_ERROR:
                        Status status = AutoResolveHelper.getStatusFromIntent(data);
                        handleError(status.getStatusCode());
                        break;
                }

                // Re-enables the Google Pay payment button.
                googlePayButton.setClickable(true);
        }
    }

    private void initializeUi() {

        // Use view binding to access the UI elements
        layoutBinding = ActivityCheckoutBinding.inflate(getLayoutInflater());
        setContentView(layoutBinding.getRoot());

        // Dismiss the notification UI if the activity was opened from a notification
        if (Notifications.ACTION_PAY_GOOGLE_PAY.equals(getIntent().getAction())) {
            sendBroadcast(new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS));
        }

        // The Google Pay button is a layout file – take the root view
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
     * Determine the viewer's ability to pay with a payment method supported by your app and display a
     * Google Pay payment button.
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

        // The call to isReadyToPay is asynchronous and returns a Task. We need to provide an
        // OnCompleteListener to be triggered when the result of the call is known.
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
     * If isReadyToPay returned {@code true}, show the button and hide the "checking" text. Otherwise,
     * notify the user that Google Pay is not available. Please adjust to fit in with your current
     * user flow. You are not required to explicitly let the user know if isReadyToPay returns {@code
     * false}.
     *
     * @param available isReadyToPay API response.
     */
    private void setGooglePayAvailable(boolean available) {
        if (available) {
            googlePayButton.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, R.string.googlepay_status_unavailable, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * PaymentData response object contains the payment information, as well as any additional
     * requested information, such as billing and shipping address.
     *
     * @param paymentData A response object returned by Google after a payer approves payment.
     * @see <a href="https://developers.google.com/pay/api/android/reference/
     * object#PaymentData">PaymentData</a>
     */
    private void handlePaymentSuccess(PaymentData paymentData) {

        // Token will be null if PaymentDataRequest was not constructed using fromJson(String).
        final String paymentInfo = paymentData.toJson();
        if (paymentInfo == null) {
            return;
        }

        try {
            JSONObject paymentMethodData = new JSONObject(paymentInfo).getJSONObject("paymentMethodData");
            // If the gateway is set to "example", no payment information is returned - instead, the
            // token will only consist of "examplePaymentMethodToken".

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

            // Logging token string.
            Log.d("Google Pay token: ", token);

        } catch (JSONException e) {
            throw new RuntimeException("The selected garment cannot be parsed from the list of elements");
        }
    }

    /**
     * At this stage, the user has already seen a popup informing them an error occurred. Normally,
     * only logging is required.
     *
     * @param statusCode will hold the value of any constant from CommonStatusCode or one of the
     *                   WalletConstants.ERROR_CODE_* constants.
     * @see <a href="https://developers.google.com/android/reference/com/google/android/gms/wallet/
     * WalletConstants#constant-summary">Wallet Constants Library</a>
     */
    private void handleError(int statusCode) {
        Log.w("loadPaymentData failed", String.format("Error code: %d", statusCode));
    }

    public void requestPayment(View view) {

        // Disables the button to prevent multiple clicks.
        googlePayButton.setClickable(false);

        // The price provided to the API should include taxes and shipping.
        // This price is not displayed to the user.
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

            // Since loadPaymentData may show the UI asking the user to select a payment method, we use
            // AutoResolveHelper to wait for the user interacting with it. Once completed,
            // onActivityResult will be called with the result.
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

        // Only load the list of items if it has not been loaded before
        if (garmentList == null) {
            garmentList = Json.readFromResources(this, R.raw.tshirts);
        }

        // Take a random element from the list
        int randomIndex = Math.toIntExact(Math.round(Math.random() * (garmentList.length() - 1)));
        try {
            return garmentList.getJSONObject(randomIndex);
        } catch (JSONException e) {
            throw new RuntimeException("The index specified is out of bounds.");
        }
    }
}