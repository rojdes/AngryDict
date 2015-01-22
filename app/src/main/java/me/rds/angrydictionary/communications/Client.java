package me.rds.angrydictionary.communications;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import me.rds.angrydictionary.AppPrefs;

/**
 * NEED CHECK AND UPDATE
 */
public class Client {

    private static final String TAG = "SERVER";


    public static final synchronized String sendDataServer(Context context, String what) {
        String data = null;
        Socket socketin = null;
        try {
            socketin = new Socket(AppPrefs.getServerIp(context), AppPrefs.getServerPort(context));
            BufferedWriter outstream = new BufferedWriter(
                    new OutputStreamWriter(socketin.getOutputStream()));
            BufferedReader instream = new BufferedReader(
                    new InputStreamReader(socketin.getInputStream(), "UTF-8"));
            if (socketin.isConnected())
                try {
                    outstream.write(what + '\n');
                    outstream.flush();
                    data = instream.readLine();
                    if (data == null) return "";
                    socketin.close();
                    Log.e(TAG, " socket portB sending = " + what);
                } catch (IOException e) {
                    Log.e(TAG, "error from get data B iostream " + e.toString());
                }
        } catch (IOException ee) {
            Log.e("SERVER", "error from get data server B " + ee.toString());
        }
        return data;
    }


    public static final synchronized void sendDataServer(Context context, String what, BufferedWriter reader) {
        String data = null;
        Socket socketin = null;
        try {
            socketin = new Socket(AppPrefs.getServerIp(context), AppPrefs.getServerPort(context));
            socketin.setReceiveBufferSize(1024);
            BufferedWriter outstream = new BufferedWriter(
                    new OutputStreamWriter(socketin.getOutputStream()));
            BufferedReader instream = new BufferedReader(new InputStreamReader(socketin.getInputStream(), "UTF-8"), 1024);
            if (socketin.isConnected())
                try {
                    outstream.write(what + '\n');
                    outstream.flush();
                    Log.e("DATA", "available = " + socketin.getInputStream().available());
                    while ((data = instream.readLine()) != null) {
                        //Log.e("DATA", "data = " + data.length());
                        reader.write(data);
                        reader.flush();
                    }
                    Log.e("DATA", "DATA NULL");

                    socketin.close();
                    Log.i(TAG, " socket portB sending = " + what);
                } catch (IOException e) {
                    Log.e(TAG, "error from get data B iostream " + e.toString());
                }
        } catch (IOException ee) {
            Log.e("SERVER", "error from get data server B " + ee.toString());
        }
        return;
    }

}
