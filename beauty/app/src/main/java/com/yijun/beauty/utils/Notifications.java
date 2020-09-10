package com.yijun.beauty.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.widget.RemoteViews;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.yijun.beauty.R;
import com.yijun.beauty.activity.PaymentTransparentActivity;
import com.yijun.beauty.service.PaymentNotificationIntentService;


public class Notifications {

    private static final int NOTIFICATION_ID = 1;
    private static final String NOTIFICATION_CHANNEL_ID = "payments_channel";

    private static final int REQUEST_CODE_SELECT_OPTION = 239357;
    private static final int REQUEST_CODE_START_ACTIVITY = 27487;

    private static final String OPTION_1 = "option1";
    private static final String OPTION_2 = "option2";
    private static final String OPTION_3 = "option3";

    public static final String ACTION_SELECT_PREFIX = "com.google.android.gms.samples.wallet.SELECT_PRICE";
    public static final String ACTION_PAY_GOOGLE_PAY = "com.google.android.gms.samples.wallet.PAY_GOOGLE_PAY";
    public static final String ACTION_PAY_OTHER = "com.google.android.gms.samples.wallet.PAY_OTHER";

    public static final String OPTION_PRICE_EXTRA = "optionPriceExtra";

    private static final HashMap<String, String> OPTION_BUTTONS = new HashMap<String, String>() {{
        put(OPTION_1, "buttonOption1");
        put(OPTION_2, "buttonOption2");
        put(OPTION_3, "buttonOption3");
    }};

    private static final HashMap<String, Long> OPTION_PRICE_CENTS = new HashMap<String, Long>() {{
        put(OPTION_1, 1000L);
        put(OPTION_2, 2500L);
        put(OPTION_3, 5000L);
    }};

    public static void triggerPaymentNotification(Context context) {
        triggerPaymentNotification(context, OPTION_2);
    }

    /**
     * 결제 알림을 트리거 / 업데이트합니다.
     */
    public static void triggerPaymentNotification(Context context, String selectedOption) {

        final Resources res = context.getResources();
        final String packageName = context.getPackageName();

        //사용자 지정 알림 레이아웃 만들기
        RemoteViews notificationLayout = new RemoteViews(packageName, R.layout.notification_top_up_account);

        // 선택 가능한 옵션을 만듬
        final List<String> options = new ArrayList<>(OPTION_PRICE_CENTS.keySet());
        for (String option : options) {

            // 선택한 옵션에 따라 색상 조정
            int optionColor = res.getColor(R.color.price_button_grey, context.getTheme());
            int optionBg = R.drawable.price_button_background;
            if (option.equals(selectedOption)) {
                optionColor = Color.WHITE;
                optionBg = R.drawable.price_button_background_selected;
            }

            int buttonId = res.getIdentifier(OPTION_BUTTONS.get(option), "id", packageName);
            notificationLayout.setTextColor(buttonId, optionColor);
            notificationLayout.setInt(buttonId, "setBackgroundResource", optionBg);

            //click 이벤트에 응답 할 pendingIntent 만들기
            Intent selectOptionIntent = new Intent(context, PaymentNotificationIntentService.class);
            selectOptionIntent.setAction(ACTION_SELECT_PREFIX + option);
            notificationLayout.setOnClickPendingIntent(buttonId, PendingIntent.getService(
                    context, REQUEST_CODE_SELECT_OPTION, selectOptionIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT));
        }

        //Google Pay 버튼 동작 설정
        Intent payIntent = new Intent(context, PaymentTransparentActivity.class);
        payIntent.setAction(ACTION_PAY_GOOGLE_PAY);
        payIntent.putExtra(OPTION_PRICE_EXTRA, OPTION_PRICE_CENTS.get(selectedOption));
        notificationLayout.setOnClickPendingIntent(
                R.id.googlePayButton, pendingIntentForActivity(context, payIntent));

        //알림 생성 및 알림 채널 설정
        Notification notification = new NotificationCompat
                .Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_text))
                .setCustomBigContentView(notificationLayout)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(false)
                .setOnlyAlertOnce(true)
                .build();

        //알림을 트리거하거나 업데이트합니다.
        NotificationManagerCompat.from(context).notify(NOTIFICATION_ID, notification);
    }

    public static void remove(Context context) {
        NotificationManagerCompat.from(context).cancel(NOTIFICATION_ID);
    }

    /**
     * API 26+에 대한 알림 채널을 생성하여 사용자가 종합적으로 이해하고 관리 할 수있는
     * 논리적 그룹을 기반으로 시스템 그룹 알림을 지원합니다.
     *
     * @param context 채널이 생성되는 위치
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void createNotificationChannelIfNotCreated(Context context) {
        CharSequence name = context.getString(R.string.channel_name);
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                name, NotificationManager.IMPORTANCE_HIGH);

        NotificationManager notificationMgr = context.getSystemService(NotificationManager.class);
        notificationMgr.createNotificationChannel(channel);
    }

    private static PendingIntent pendingIntentForActivity(Context context, Intent intent) {
        return PendingIntent.getActivity(
                context, REQUEST_CODE_START_ACTIVITY, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}