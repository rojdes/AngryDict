package me.rds.angrydictionary.communications.https;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import me.rds.angrydictionary.LocalConsts;
import me.rds.angrydictionary.communications.https.listeners.OnGetContentListener;

/**
 * Created by D1m11n on 20.01.2015.
 */
public class HttpsClient {

    private void print_https_cert(HttpsURLConnection con) throws IOException {

        if(con!=null){

            try {
                System.out.println("Response Code : " + con.getResponseCode());
                System.out.println("Cipher Suite : " + con.getCipherSuite());
                System.out.println("\n");

                Certificate[] certs = con.getServerCertificates();
                for(Certificate cert : certs){
                    System.out.println("Cert Type : " + cert.getType());
                    System.out.println("Cert Hash Code : " + cert.hashCode());
                    System.out.println("Cert Public Key Algorithm : "
                            + cert.getPublicKey().getAlgorithm());
                    System.out.println("Cert Public Key Format : "
                            + cert.getPublicKey().getFormat());
                    System.out.println("\n");
                }

            } catch (SSLPeerUnverifiedException e) {
                e.printStackTrace();
            } catch (IOException e){
                e.printStackTrace();
            }

        }
    }

    public void start(final OnGetContentListener listener){
        if (listener==null) return;
        new Thread(new Runnable() {
            @Override
            public void run() {
                testIt(listener);
            }
        }).start();
    }

    private  void testIt(final OnGetContentListener listener){
        try {
            URL url = new URL(LocalConsts.LINK_DB_LIST);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            print_content(con, listener);
        } catch (MalformedURLException e) {
            listener.onGetContent(null, e);
        } catch (IOException e) {
            listener.onGetContent(null, e);
        }
    }

    private void print_content(HttpsURLConnection con, final OnGetContentListener listener) {
        if (con == null) {
            listener.onGetContent(null, new NullPointerException("Https connection is null; "));
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder bb = new StringBuilder();
            String input;
            while ((input = br.readLine()) != null)
                bb.append(input);
            br.close();
            listener.onGetContent(bb.toString(), null);
        } catch (IOException e) {
            listener.onGetContent(null, e);
        }
    }

}
