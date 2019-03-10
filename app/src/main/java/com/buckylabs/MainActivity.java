package com.buckylabs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import eu.chainfire.Shell;

public class MainActivity extends Activity {

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.x = -20;
        params.height = 1000;
        params.width = 290;
        params.y = -10;
        params.gravity = Gravity.END;
        this.getWindow().setAttributes(params);


        background b = new background(context);
        b.execute();

        final ImageButton shutdown = findViewById(R.id.shutdown);
        ImageButton reboot = findViewById(R.id.reboot);
        ImageButton recovery = findViewById(R.id.recovery);
        ImageButton bootloader = findViewById(R.id.bootloader);


        shutdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                shutdown("Shutting Down ");
            }
        });

        reboot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                reboot("Rebooting ");
            }
        });

        recovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recovery("Recovery ");
            }
        });

        bootloader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                bootloader("Bootloader ");
            }
        });


    }


    public void shutdown(final String message) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (Shell.SU.available()) {
                    Dialog(message);
                    threadSleep(5000);
                    Shell.SH.run("reboot -p");
                } else {
                    toast(context, "Root Not Available");
                }
            }
        }).start();

    }


    public void reboot(final String message) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (Shell.SU.available()) {
                    Dialog(message);
                    threadSleep(5000);
                    Shell.SH.run("reboot");
                } else {
                    toast(context, "Root Not Available");

                }
            }
        }).start();
    }


    public void recovery(final String message) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (Shell.SU.available()) {
                    Dialog(message);
                    threadSleep(5000);
                    Shell.SH.run("reboot recovery");
                } else {
                    toast(context, "Root Not Available");

                }
            }
        }).start();
    }

    public void bootloader(final String message) {

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (Shell.SU.available()) {
                    Dialog(message);
                    threadSleep(5000);

                    Shell.SH.run("reboot bootloader");
                } else {
                    toast(context, "Root Not Available");

                }
            }
        }).start();
    }


    public void toast(final Context context, final String text) {

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void Dialog(final String message) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                Dialog dialog = new Dialog(context, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
                dialog.setContentView(R.layout.climax);
                ProgressBar progressBar = dialog.findViewById(R.id.progressBar);
                TextView textView = dialog.findViewById(R.id.message);
                textView.setText(message);
                dialog.show();

            }
        });


    }


    public void threadSleep(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

class background extends AsyncTask<Void, Void, Void> {
    private Context context;

    background(Context context) {
        this.context = context;
    }

    private void toast(final Context context, final String text) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected Void doInBackground(Void... voids) {

        if (Shell.SU.available()) {
            toast(context, "ROOT Access Granted");

        } else {
            toast(context, "Oops! ROOT Access Not Available");

        }

        return null;
    }
}