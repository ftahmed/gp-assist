package ftahmed.gpassist.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by tanvir on 1/3/17.
 */
public class SmsReceiver extends BroadcastReceiver {

    private String TAG = SmsReceiver.class.getSimpleName();

    private SharedPreferences preferences;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if(intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)){
            if (Build.VERSION.SDK_INT >= 19) { //KITKAT
                SmsMessage[] msgs = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                String msgFrom = msgs[0].getOriginatingAddress();
                String msgBody = msgs[0].getMessageBody();
                String message = String.format("1) SMS From: %s. Message: %s", msgFrom, msgBody);
                Log.i(TAG, message);
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } else {
                Bundle bundle = intent.getExtras();           //---get the SMS message passed in---
                SmsMessage[] msgs = null;
                if (bundle != null) {
                    //---retrieve the SMS message received---
                    try {
                        Object[] pdus = (Object[]) bundle.get("pdus");
                        msgs = new SmsMessage[pdus.length];
                        for (int i = 0; i < msgs.length; i++) {
                            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                            String msgFrom = msgs[i].getOriginatingAddress();
                            String msgBody = msgs[i].getMessageBody();
                            String message = String.format("2) SMS From: %s. Message: %s", msgFrom, msgBody);
                            Log.i(TAG, message);
                            Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        //                            Log.d("Exception caught",e.getMessage());
                    }
                }
            }
        }
    }
}
