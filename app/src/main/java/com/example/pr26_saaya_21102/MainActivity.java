import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mInfoTextView;
    private NotificationBroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("NotificationListenerService Demo");

        mInfoTextView = findViewById(R.id.textView);
        mReceiver = new NotificationBroadcastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("ru.alexanderklimov.NOTIFICATION_LISTENER_EXAMPLE");
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public void onButtonClicked(View view) {
        if (view.getId() == R.id.buttonCreateNotification) {
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setContentTitle("Важное уведомление")
                    .setContentText("Пора кормить кота!")
                    .setTicker("Хозяин, проснись!")
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setAutoCancel(true);
            manager.notify((int) System.currentTimeMillis(), builder.build());
        } else if (view.getId() == R.id.buttonClearNotification) {
            Intent intent = new Intent("ru.alexanderklimov.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
            intent.putExtra("command", "clearall");
            sendBroadcast(intent);
        } else if (view.getId() == R.id.buttonListNotification) {
            Intent intent = new Intent("ru.alexanderklimov.NOTIFICATION_LISTENER_SERVICE_EXAMPLE");
            intent.putExtra("command", "list");
            sendBroadcast(intent);
        }
    }

    class NotificationBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String temp = intent.getStringExtra("notification_event") + "\n" + mInfoTextView.getText();
            mInfoTextView.setText(temp);
        }
    }
}