package regionhovedstaden.gcm;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.widget.Toast;
import android.os.Handler;

import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.logging.Level;
import java.util.logging.Logger;

import regionhovedstaden.Data.App;
import regionhovedstaden.Data.DataBehandling;
import regionhovedstaden.MainActivity;

/**
 * Created by Mathias Lyngman on 27-03-2015.
 */
public class GcmIntentService extends IntentService {

    DataBehandling dataBehandling = new DataBehandling();
    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (extras != null && !extras.isEmpty()) {  // has effect of unparcelling Bundle
            // Since we're not using two way messaging, this is all we really to check for
            if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                Logger.getLogger("GCM_RECEIVED").log(Level.INFO, extras.toString());

                respond(extras.getString("message"));

            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    protected void respond(final String message) {

        if(App.brugerType.equals("plejer"))dataBehandling.svarBehandling(message);

            /*new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
                }
            });*/

    }
}

