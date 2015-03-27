package regionhovedstaden.netvaerk;

import android.os.AsyncTask;

import com.baasbox.android.BaasDocument;

import java.io.*;
import java.net.*;

/**
 * Created by Andras Bak on 01-Mar-15.
 */
public class SendBesked {

    public BaasDocument send (BaasDocument doc){

        System.out.println("BaasDoc: " + doc);

        String hej;

        return doc;

    }

/*
    String serverName = "10.16.227.250";
    int port = 8080;


    public void send(final String message){

        final String besked = message;

        new AsyncTask()
        {
            @Override
            protected Object doInBackground(Object... params)
            {
                try {
                    System.out.println("Connecting to " + serverName
                            + " on port " + port);
                    Socket client = new Socket(serverName, port);
                    System.out.println("Just connected to "
                            + client.getRemoteSocketAddress());
                    OutputStream outToServer = client.getOutputStream();
                    DataOutputStream out
                            = new DataOutputStream(outToServer);

                    out.writeUTF(besked);

                    client.close();
                } catch (IOException e) {
                }

                return null;
            }

            @Override
            protected void onPostExecute(Object o)
            {

            }
        }.execute();
    }

*/
}