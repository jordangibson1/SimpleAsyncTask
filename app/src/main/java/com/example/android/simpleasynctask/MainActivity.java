package com.example.android.simpleasynctask;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * The SimpleAsyncTask app contains a button that launches an AsyncTask
 * which sleeps in the asynchronous thread for a random amount of time.
 */

public class MainActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button btn;
    Integer count = 1;

    // Key for saving the state of the TextView
    private static final String TEXT_STATE = "currentText";

    // The TextView where we will show results
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(10);
        btn = (Button) findViewById(R.id.button);
        mTextView = findViewById(R.id.textView1);
        View.OnClickListener listener = new View.OnClickListener() {
            public void onClick(View view) {
                count = 1;
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                switch (view.getId()) {
                    case R.id.button:
                    new SimpleAsyncTask().execute(10);
                    break;
                }
            }
        };
        btn.setOnClickListener(listener);
    }

    class SimpleAsyncTask extends AsyncTask<Integer, Integer, String> {
    @Override
    protected String doInBackground(Integer... params) {
        int s = 0;
        for (; count <= params[0]; count++) {
            try {
                Random r = new Random();
                int n = r.nextInt(11);
                s = n * 200;
                Thread.sleep(s);
                publishProgress(count);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return "Awake at last after sleeping for " + s + " milliseconds!";
    }
    @Override
    protected void onPostExecute(String result) {
        progressBar.setVisibility(View.GONE);
        mTextView.setText(result);
        btn.setText("Start");
    }
    @Override
    protected void onPreExecute() {
        mTextView.setText("Setting up...");
    }
    @Override
    protected void onProgressUpdate(Integer... values) {
        mTextView.setText("Napping...");
        progressBar.setProgress(values[0]);
    }
    }
}
