package lk.dinuka.logger;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private LogManager logger = new LogManager(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        logger.log("Application initialized", LogLevel.INFO);
        viewTraceLog();
    }

    public void writeLog(View view) {
        String content = ((EditText) findViewById(R.id.editText)).getText().toString();
        String level = ((EditText) findViewById(R.id.editText2)).getText().toString();
        if (!content.isEmpty() && !level.isEmpty()) {
            logger.log(content, LogLevel.valueOf(level));
        }
        viewTraceLog();
    }

    public void viewTraceLog() {
        TextView tv = findViewById(R.id.textView);
        tv.setText("");
        String logContent = logger.readFromFile("trace.log");
        tv.setText(logContent);
    }

}
