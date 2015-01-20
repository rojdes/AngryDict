package me.rds.angrydictionary.communications.https;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

import me.rds.angrydictionary.LocalConsts;

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

    public void start(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                testIt();
            }
        }).start();
    }

    private  void testIt(){
        try {
            URL url = new URL(LocalConsts.LINK_ALT_LIST);
            HttpsURLConnection con = (HttpsURLConnection)url.openConnection();
            print_content(con);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void print_content(HttpsURLConnection con){
        if(con!=null){
            try {
                BufferedReader br =  new BufferedReader(new InputStreamReader(con.getInputStream()));
                String input;
                while ((input = br.readLine()) != null){
                    Log.e("HTTPS", "==============" + input);
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
