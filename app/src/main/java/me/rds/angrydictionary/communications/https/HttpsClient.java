package me.rds.angrydictionary.communications.https;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Writer;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLPeerUnverifiedException;

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

    public void start(final String https, boolean startInMainThread, final OnGetContentListener listener){
        if (listener==null) return;
         if(startInMainThread)
             startIt(https, listener);
        else
        new Thread(new Runnable() {
            @Override
            public void run() {
                startIt(https, listener);
            }
        }).start();
    }


    /**
     * WORK IN CURRENT THREAD!
     * @param url
     * @param writer
     * @throws Exception
     */
    public void start(String url, OutputStream writer) throws Exception {
        HttpsURLConnection con = (HttpsURLConnection) new URL(url).openConnection();
        getContent(con, writer);
    }




    private  void startIt(String url, final OnGetContentListener listener){
        try {
            HttpsURLConnection con = (HttpsURLConnection)new URL(url).openConnection();
            getContent(con, listener);
        } catch (MalformedURLException e) {
            listener.onGetContent(null, e);
        } catch (IOException e) {
            listener.onGetContent(null, e);
        }
    }

   private void getContent(HttpsURLConnection con, final OnGetContentListener listener) {
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

    private void getContent(HttpsURLConnection con, final OutputStream writer) throws IOException {
        if (con == null||writer==null)
            throw new NullPointerException("NULL: connection = "  +  con + ", writer =  " + writer);
            InputStream br = con.getInputStream();
            byte [] buf= new byte[4096];
            int size=0;
            while ((size=br.read(buf)) >0)
              writer.write(buf, 0, size);
            br.close();

    }

}
