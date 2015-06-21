package com.truong.mobile.quanlythuchi.sqlitehelper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;

/**
 * Created by Hoangdv on 6:53 PM : 0021, Jun, 21, 2015.
 */
public class Dbclass extends Service {
    private Socket socket;
    private Handler handler;
    private String message = "";
    {
        try {
            socket = IO.socket("http://nodejs-chartserver.rhcloud.com/");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        socket.connect();

        socket.on("message", onMsg);
        handler = new Handler();
    }

    private Emitter.Listener onMsg = new Emitter.Listener() {
        @Override
        public void call(Object... args) {
            JSONObject object = (JSONObject) args[0];
            try {
                Log.e("LOggggggg", object.toString());
                message = object.getString("username");
                String msg = object.getString("msg");
                if(msg.equalsIgnoreCase("data0")){
                    SharedPreferences preferences = getSharedPreferences("datakey", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("key", false);
                    editor.putString("msg", message);
                    editor.apply();
                }
                if(msg.equalsIgnoreCase("data1")){
                    SharedPreferences preferences = getSharedPreferences("datakey", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("key", true);
                    editor.putString("msg", message);
                    editor.apply();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int i =  super.onStartCommand(intent, flags, startId);
        socket.emit("adduser", "{ 'username': 'androidQuanlythuchi' }");
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (Thread.currentThread().isAlive()){
                    SharedPreferences preferences = getSharedPreferences("datakey", Context.MODE_PRIVATE);
                    boolean key = preferences.getBoolean("key", false);
                    message = preferences.getString("msg", "");
                    if(key){
                        try {
                            Thread.sleep(10000);
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Dbclass.this, message, Toast.LENGTH_LONG).show();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        return i;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
