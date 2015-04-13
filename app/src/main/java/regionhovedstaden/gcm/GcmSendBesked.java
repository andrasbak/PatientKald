package regionhovedstaden.gcm;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.widget.Toast;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import java.io.IOException;
import backendpatientkald.kaldApi.KaldApi;
import regionhovedstaden.Data.App;

/**
 * Created by Andras on 06-04-2015.
 */
public class GcmSendBesked extends AsyncTask<Pair<Context, String>, Void, String> {
    private static KaldApi kaldApi = null;
    private Context context;
    private String rootUrl = App.rootUrl;
    private String regId = App.regId;

    @Override
    protected String doInBackground(Pair<Context, String>... params) {
        if(kaldApi == null) {  // Only do this once
            KaldApi.Builder builder = new KaldApi.Builder(AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(), null)
                    // options for running against local devappserver
                    // - 10.0.2.2 is localhost's IP address in Android emulator
                    // - turn off compression when running against local devappserver
                    .setRootUrl(rootUrl)
                    .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                        @Override
                        public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                            abstractGoogleClientRequest.setDisableGZipContent(true);
                        }
                    });
            // end options for devappserver

            kaldApi = builder.build();
        }

        context = params[0].first;
        String patientKald = params[0].second;

        try {
            kaldApi.kald(patientKald + "!" + regId).execute();
        } catch (IOException e) {
            return e.getMessage();
        }
        return patientKald;
    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context, result, Toast.LENGTH_LONG).show();

    }
}