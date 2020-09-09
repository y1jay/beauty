package com.yijun.beauty.utils;

import android.app.Activity;


import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.yijun.beauty.Constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Payments API를 처리하기위한 도우미 정적 메서드를 포함합니다.
 *
 * <p>
 * 코드에 사용 된 많은 매개 변수는 선택 사항이며 여기에서 그 존재를 알리기 위해 설정됩니다.
 * 자세한 내용은 설명서를 참조하고 구현과 관련이없는 항목을 자유롭게 제거하십시오.
 */
public class PaymentsUtil {

    public static final BigDecimal CENTS_IN_A_UNIT = new BigDecimal(100d);

    /**
     * 모든 요청에 사용되는 속성으로 Google Pay API 기본 요청 객체를 만듭니다.
     *
     * @return Google Pay API 기본 요청 객체.
     * @throws JSONException
     */
    private static JSONObject getBaseRequest() throws JSONException {
        return new JSONObject().put("apiVersion", 2).put("apiVersionMinor", 0);
    }

    /**
     * {@link Constants}에 설정된 환경 및 테마를 사용하여 {@link Activity}에서
     * 사용할 {@link PaymentsClient}의 인스턴스를 만듭니다.
     *
     * @param activity = 발신자의 활동.
     */
    public static PaymentsClient createPaymentsClient(Activity activity) {
        Wallet.WalletOptions walletOptions =
                new Wallet.WalletOptions.Builder().setEnvironment(Constants.PAYMENTS_ENVIRONMENT).build();
        return Wallet.getPaymentsClient(activity, walletOptions);
    }

    /**
     * 게이트웨이 통합 : 게이트웨이와 앱의 게이트웨이 판매자 식별자를 식별합니다.
     *
     * <p> Google Pay API 응답은 지불 인 승인 후 지원되는 게이트웨이에서 청구 할 수있는 암호화 된 결제 수단을 반환합니다.
     *
     * <p>TODO: 매개 변수에 대한 게이트웨이를 확인하여 Constants.java 에서 전달하고 수정하십시오.
     *
     * @return CARD 결제 수단에 대한 결제 데이터 토큰 화.
     * @throws JSONException
     * @see <a href=
     * "https://developers.google.com/pay/api/android/reference/object#PaymentMethodTokenizationSpecification">PaymentMethodTokenizationSpecification</a>
     */
    private static JSONObject getGatewayTokenizationSpecification() throws JSONException {
        return new JSONObject() {{
            put("type", "PAYMENT_GATEWAY");
            put("parameters", new JSONObject() {
                {
                    put("gateway", "example");
                    put("gatewayMerchantId", "exampleGatewayMerchantId");
                }
            });
        }};
    }

    /**
     *
     * {@code DIRECT} 통합 : 서버에서 직접 응답을 복호화합니다.
     * 이 구성에는 Google 의 추가 데이터 보안 요구 사항과 추가적인 PCI DSS 준수 복잡성이 있습니다.
     *
     * <p> {@code DIRECT} 통합에 대한 자세한 내용은 문서를 참조하세요. 사용하는 통합 유형은 결제 프로세서에 따라 다릅니다.
     *
     * @return CARD 결제 수단에 대한 결제 데이터 토큰 화.
     * @throws JSONException
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#PaymentMethodTokenizationSpecification">PaymentMethodTokenizationSpecification</a>
     */
    private static JSONObject getDirectTokenizationSpecification()
            throws JSONException, RuntimeException {
        if (Constants.DIRECT_TOKENIZATION_PARAMETERS.isEmpty()
                || Constants.DIRECT_TOKENIZATION_PUBLIC_KEY.isEmpty()
                || Constants.DIRECT_TOKENIZATION_PUBLIC_KEY == null
                || Constants.DIRECT_TOKENIZATION_PUBLIC_KEY == "REPLACE_ME") {
            throw new RuntimeException(
                    "Please edit the Constants.java file to add protocol version & public key.");
        }
        JSONObject tokenizationSpecification = new JSONObject();

        tokenizationSpecification.put("type", "DIRECT");
        JSONObject parameters = new JSONObject(Constants.DIRECT_TOKENIZATION_PARAMETERS);
        tokenizationSpecification.put("parameters", parameters);

        return tokenizationSpecification;
    }

    /**
     * 앱과 게이트웨이에서 지원하는 카드 네트워크.
     *
     * <p>TODO: 앱 및 게이트웨이에서 지원하는 카드 네트워크를 확인하고 Constants.java에서 업데이트합니다.
     *
     * @return 허용 된 카드 네트워크
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#CardParameters">CardParameters</a>
     */
    private static JSONArray getAllowedCardNetworks() {
        return new JSONArray(Constants.SUPPORTED_NETWORKS);
    }

    /**
     * 앱과 게이트웨이에서 지원하는 카드 인증 방법.
     *
     * <p>TODO: 프로세서가 지원되는 카드 네트워크에서 Android 장치 토큰을 지원하는지 확인하고 Constants.java에서 업데이트합니다.
     *
     * @return 허용 된 카드 인증 방법.
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#CardParameters">CardParameters</a>
     */
    private static JSONArray getAllowedCardAuthMethods() {
        return new JSONArray(Constants.SUPPORTED_METHODS);
    }

    /**
     * CARD 결제 수단에 대한 앱의 지원을 설명하세요.
     *
     * <p> 제공된 속성은 IsReadyToPayRequest 및 PaymentDataRequest 모두에 적용됩니다.
     *
     * @return 허용되는 카드를 설명하는 CARD PaymentMethod 객체입니다.
     * @throws JSONException
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#PaymentMethod">PaymentMethod</a>
     */
    private static JSONObject getBaseCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = new JSONObject();
        cardPaymentMethod.put("type", "CARD");

        JSONObject parameters = new JSONObject();
        parameters.put("allowedAuthMethods", getAllowedCardAuthMethods());
        parameters.put("allowedCardNetworks", getAllowedCardNetworks());
        //선택적으로 CARD 결제 방법과 관련된 청구 주소 / 전화 번호를 추가 할 수 있습니다.
        parameters.put("billingAddressRequired", true);

        JSONObject billingAddressParameters = new JSONObject();
        billingAddressParameters.put("format", "FULL");

        parameters.put("billingAddressParameters", billingAddressParameters);

        cardPaymentMethod.put("parameters", parameters);

        return cardPaymentMethod;
    }

    /**
     * CARD 결제 수단에 대해 예상되는 반환 결제 데이터를 설명하세요.
     *
     * @return 허용되는 카드 및 선택적 필드를 설명하는 CARD PaymentMethod 입니다.
     * @throws JSONException
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#PaymentMethod">PaymentMethod</a>
     */
    private static JSONObject getCardPaymentMethod() throws JSONException {
        JSONObject cardPaymentMethod = getBaseCardPaymentMethod();
        cardPaymentMethod.put("tokenizationSpecification", getGatewayTokenizationSpecification());

        return cardPaymentMethod;
    }

    /**
     * 앱에서 허용되는 결제 방법을 설명하는 개체로, 시청자의 결제 준비 여부를 결정하는 데 사용됩니다.
     *
     * @return 앱에서 지원하는 API 버전 및 결제 수단
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#IsReadyToPayRequest">IsReadyToPayRequest</a>
     */
    public static Optional<JSONObject> getIsReadyToPayRequest() {
        try {
            JSONObject isReadyToPayRequest = getBaseRequest();
            isReadyToPayRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(getBaseCardPaymentMethod()));

            return Optional.of(isReadyToPayRequest);

        } catch (JSONException e) {
            return Optional.empty();
        }
    }

    /**
     * 결제 금액, 통화 및 금액 상태를 Google Pay API에 제공합니다.
     *
     * @return 요청 된 지불에 대한 정보.
     * @throws JSONException
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#TransactionInfo">TransactionInfo</a>
     */
    private static JSONObject getTransactionInfo(String price) throws JSONException {
        JSONObject transactionInfo = new JSONObject();
        transactionInfo.put("totalPrice", price);
        transactionInfo.put("totalPriceStatus", "FINAL");
        transactionInfo.put("countryCode", Constants.COUNTRY_CODE);
        transactionInfo.put("currencyCode", Constants.CURRENCY_CODE);
        //지급인이 선택사항을 확인한 직후 선택한 결제 수단으로 청구됩니다. 이 옵션은 totalPriceStatus 가 FINAL 로 설정된 경우에만 사용할 수 있습니다.
        transactionInfo.put("checkoutOption", "COMPLETE_IMMEDIATE_PURCHASE");

        return transactionInfo;
    }

    /**
     * 결제 정보를 요청하는 가맹점에 대한 정보
     *
     * @return 판매자에 대한 정보.
     * @throws JSONException
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#MerchantInfo">MerchantInfo</a>
     */
    private static JSONObject getMerchantInfo() throws JSONException {
        return new JSONObject().put("merchantName", "Example Merchant");
    }

    /**
     * Google Pay 결제 명세서에서 요청 된 정보를 설명하는 객체
     *
     * @return 앱에서 예상하는 결제 데이터입니다.
     * @see <a
     * href="https://developers.google.com/pay/api/android/reference/object#PaymentDataRequest">PaymentDataRequest</a>
     */
    public static Optional<JSONObject> getPaymentDataRequest(long priceCents) {

        final String price = PaymentsUtil.centsToString(priceCents);

        try {
            JSONObject paymentDataRequest = PaymentsUtil.getBaseRequest();
            paymentDataRequest.put(
                    "allowedPaymentMethods", new JSONArray().put(PaymentsUtil.getCardPaymentMethod()));
            paymentDataRequest.put("transactionInfo", PaymentsUtil.getTransactionInfo(price));
            paymentDataRequest.put("merchantInfo", PaymentsUtil.getMerchantInfo());

            /* 선택적 배송 주소 요구 사항은 PaymentDataRequest JSON 개체의 최상위 속성입니다. */
            paymentDataRequest.put("shippingAddressRequired", true);

            JSONObject shippingAddressParameters = new JSONObject();
            shippingAddressParameters.put("phoneNumberRequired", false);

            JSONArray allowedCountryCodes = new JSONArray(Constants.SHIPPING_SUPPORTED_COUNTRIES);

            shippingAddressParameters.put("allowedCountryCodes", allowedCountryCodes);
            paymentDataRequest.put("shippingAddressParameters", shippingAddressParameters);
            return Optional.of(paymentDataRequest);

        } catch (JSONException e) {
            return Optional.empty();
        }
    }

    /**
     * cents 를 {@link PaymentsUtil # getPaymentDataRequest}에서 허용하는 문자열 형식으로 변환합니다.
     *
     * @param cents 센트 단위의 가격 가치.
     */
    public static String centsToString(long cents) {
        return new BigDecimal(cents)
                .divide(CENTS_IN_A_UNIT, RoundingMode.HALF_EVEN)
                .setScale(2, RoundingMode.HALF_EVEN)
                .toString();
    }
}