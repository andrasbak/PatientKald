package regionhovedstaden.netvaerk;

import android.app.Application;
import com.baasbox.android.*;

/**
 * Created by Andras on 24-03-2015.
 */
public class BaasBoxForbind extends Application {

        @Override
        public void onCreate() {
            super.onCreate();
            //todo 1.2
            BaasBox.builder(this).setAuthentication(BaasBox.Config.AuthType.SESSION_TOKEN)
                    .setApiDomain("10.16.225.224")
                    .setPort(9000)
                    .setAppCode("1234567890")
                    .init();

        }


}
