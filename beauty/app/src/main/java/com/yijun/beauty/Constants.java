package com.yijun.beauty;



import com.google.android.gms.wallet.WalletConstants;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * 이 파일에는 계속하기 전에 편집해야 하는 여러 상수가 포함되어있음.
 * PaymentsUtil.java 에서 상수가 사용되는 위치를 확인하고 통합과 관련이없는 항목을 잠재적으로 제거해야함.
 *
 * <p> 필수 변경 사항:
 * <ol>
 * <li> 필요한 경우 SUPPORTED_NETWORKS 와 SUPPORTED_METHODS 를 업데이트(확실하지않은 경우 프로세서에 문의하셈)
 * <li> 사용하는 통화로 CURRENCY_CODE 업데이트.
 * <li> 현재 배송중인 국가를 나열하려면 SHIPPING_SUPPORTED_COUNTRIES 를 업데이트하세요.
 *      이것이 앱에 적용되지 않는 경우 PaymentsUtil.java 에서 관련 비트를 제거해.
 * <li>
 *  {@code PAYMENT_GATEWAY}와 통합하는 경우 제공된
 *  안내에 따라 PAYMENT_GATEWAY_TOKENIZATION_NAME 및 PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS 을 업데이트하세요.
 *  DIRECT_TOKENIZATION_PUBLIC_KEY 를 업데이트 할 필요가 없습니다.
 * <li> {@code DIRECT} 통합을 사용하는 경우 지침에 따라 프로토콜 버전과 공개 키를 수정하세요.
 */
public class Constants {

    /**
     * ENVIRONMENT_PRODUCTION 으로 변경하면 API에서 청구 가능한 카드 정보를 반환합니다.
     * ENVIRONMENT_PRODUCTION 을 활성화하는 데 필요한 단계를 읽으려면 설명서를 참조.
     *
     * @value #PAYMENTS_ENVIRONMENT
     */
    public static final int PAYMENTS_ENVIRONMENT = WalletConstants.ENVIRONMENT_TEST;

    /**
     * API 에서 요청할 수 있는 허용 도니 네트워크.
     * 사용자의 계정에 여기에 지정되지 않은 네트워크의 카드가있는 경우 팝업 선택항목에 제공되지 않음.
     *
     * @value #SUPPORTED_NETWORKS
     */
    public static final List<String> SUPPORTED_NETWORKS = Arrays.asList(
            "AMEX",
            "DISCOVER",
            "JCB",
            "MASTERCARD",
            "VISA");

    /**
     * Google Pay API 는 Google 에 등록 된 카드를 반환 할 수 있습니다.
     * com (PAN_ONLY) 및 / 또는 3D 보안 암호화 (CRYPTOGRAM_3DS)로 인증 된 Android 기기의 기기 토큰
     *
     * @value #SUPPORTED_METHODS
     */
    public static final List<String> SUPPORTED_METHODS = Arrays.asList(
            "PAN_ONLY",
            "CRYPTOGRAM_3DS");

    /**
     * Required by the API, but not visible to the user.
     *
     * @value #COUNTRY_CODE Your local country
     */
    public static final String COUNTRY_CODE = "KR";

    /**
     * Required by the API, but not visible to the user.
     *
     * @value #CURRENCY_CODE Your local currency
     */
    public static final String CURRENCY_CODE = "USD";

    /**
     * Supported countries for shipping (use ISO 3166-1 alpha-2 country codes). Relevant only when
     * requesting a shipping address.
     *
     * @value #SHIPPING_SUPPORTED_COUNTRIES
     */
    public static final List<String> SHIPPING_SUPPORTED_COUNTRIES = Arrays.asList("US", "GB");

    /**
     * The name of your payment processor/gateway. Please refer to their documentation for more
     * information.
     *
     * @value #PAYMENT_GATEWAY_TOKENIZATION_NAME
     */
    public static final String PAYMENT_GATEWAY_TOKENIZATION_NAME = "example";

    /**
     * Custom parameters required by the processor/gateway.
     * In many cases, your processor / gateway will only require a gatewayMerchantId.
     * Please refer to your processor's documentation for more information. The number of parameters
     * required and their names vary depending on the processor.
     *
     * @value #PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS
     */
    public static final HashMap<String, String> PAYMENT_GATEWAY_TOKENIZATION_PARAMETERS =
            new HashMap<String, String>() {{
                put("gateway", PAYMENT_GATEWAY_TOKENIZATION_NAME);
                put("gatewayMerchantId", "exampleGatewayMerchantId");
                // Your processor may require additional parameters.
            }};

    /**
     * Only used for {@code DIRECT} tokenization. Can be removed when using {@code PAYMENT_GATEWAY}
     * tokenization.
     *
     * @value #DIRECT_TOKENIZATION_PUBLIC_KEY
     */
    public static final String DIRECT_TOKENIZATION_PUBLIC_KEY = "REPLACE_ME";

    /**
     * Parameters required for {@code DIRECT} tokenization.
     * Only used for {@code DIRECT} tokenization. Can be removed when using {@code PAYMENT_GATEWAY}
     * tokenization.
     *
     * @value #DIRECT_TOKENIZATION_PARAMETERS
     */
    public static final HashMap<String, String> DIRECT_TOKENIZATION_PARAMETERS =
            new HashMap<String, String>() {{
                put("protocolVersion", "ECv2");
                put("publicKey", DIRECT_TOKENIZATION_PUBLIC_KEY);
            }};
}