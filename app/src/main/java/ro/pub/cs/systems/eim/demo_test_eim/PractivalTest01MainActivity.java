package ro.pub.cs.systems.eim.demo_test_eim;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.pub.cs.systems.eim.demo_test_eim.Constants;

public class PractivalTest01MainActivity extends AppCompatActivity {

    private final static int SECONDARY_ACTIVITY_REQUEST_CODE = 1;

    private Button navigateToSecondaryActivityButton = null;

    private int serviceStatus = Constants.SERVICE_STOPPED;

    private ButtonClickListener buttonClickListener = new ButtonClickListener();
    private class ButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View view) {
            EditText et1 = findViewById(R.id.textView1);
            EditText et2 = findViewById(R.id.textView2);

            int fst_value = Integer.parseInt(String.valueOf(et1.getText()));
            int snd_value = Integer.parseInt(String.valueOf(et2.getText()));
            switch (view.getId()) {
                case R.id.navigate_to_secondary_activity_button:
                    Intent intent = new Intent(getApplicationContext(), PracticalTest01SecondaryActivity.class);
                    int numberOfClicks = Integer.parseInt(et1.getText().toString()) +
                            Integer.parseInt(et2.getText().toString());
                    intent.putExtra("numberOfClicks", numberOfClicks);
                    startActivityForResult(intent, SECONDARY_ACTIVITY_REQUEST_CODE);
                    break;

                case R.id.button1:
                    et1.setText((fst_value + 1) + "");
                    break;

                case R.id.button2:
                    et2.setText((snd_value + 1) + "");
                    break;
            }

            if (fst_value + snd_value > Constants.NUMBER_OF_CLICKS_THRESHOLD
                    && serviceStatus == Constants.SERVICE_STOPPED) {
                Log.d(Constants.TAG, "Sent to service " + fst_value + " and " + snd_value);
                Intent intent = new Intent(getApplicationContext(), PracticalTest01Service.class);
                intent.putExtra("firstNumber", fst_value);
                intent.putExtra("secondNumber", snd_value);
                getApplicationContext().startService(intent);
                serviceStatus = Constants.SERVICE_STARTED;
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practival_test01_main);


        findViewById(R.id.button1).setOnClickListener(buttonClickListener);
        findViewById(R.id.button2).setOnClickListener(buttonClickListener);


        EditText et1 = findViewById(R.id.textView1);
        EditText et2 = findViewById(R.id.textView2);
        if (savedInstanceState == null) {
            Log.d(Constants.TAG, "onCreate() method was invoked without a previous state");
            et1.setText(String.valueOf(0));
            et2.setText(String.valueOf(0));
        } else {
            Log.d(Constants.TAG, "onCreate() method was invoked WITH a previous state");
            if (savedInstanceState.containsKey("leftCount")) {
                et1.setText(savedInstanceState.getString("leftCount"));
            } else {
                et1.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey("rightCount")) {
                et2.setText(savedInstanceState.getString("rightCount"));
            } else {
                et2.setText(String.valueOf(0));
            }
        }

        findViewById(R.id.navigate_to_secondary_activity_button).setOnClickListener(buttonClickListener);


        for (int index = 0; index < Constants.actionTypes.length; index++) {
            intentFilter.addAction(Constants.actionTypes[index]);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        Log.d(Constants.TAG, "onActivityResult()");
        if (requestCode == SECONDARY_ACTIVITY_REQUEST_CODE) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRestart() {
        super.onRestart();
        Log.d(Constants.TAG, "onRestart() method was invoked");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(Constants.TAG, "onStart() method was invoked");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(Constants.TAG, "onResume() method was invoked");
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
        Log.d(Constants.TAG, "onPause() method was invoked");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(Constants.TAG, "onStop() method was invoked");
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(this, PracticalTest01Service.class);
        stopService(intent);
        Log.d(Constants.TAG, "onDestroy() method was invoked");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(Constants.TAG, "onSaveInstanceState()");
        outState.putString("leftCount", ((EditText) findViewById(R.id.textView1)).getText().toString());
        outState.putString("rightCount", ((EditText) findViewById(R.id.textView2)).getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.d(Constants.TAG, "onRestoreInstanceState()");
        EditText et1 = findViewById(R.id.textView1);
        EditText et2 = findViewById(R.id.textView2);

        if (savedInstanceState.containsKey("leftCount")) {
            et1.setText(savedInstanceState.getString("leftCount"));
        } else {
            et1.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("rightCount")) {
            et2.setText(savedInstanceState.getString("rightCount"));
        } else {
            et2.setText(String.valueOf(0));
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }
    private IntentFilter intentFilter = new IntentFilter();
}

