package lk.dinuka.logger;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * Created by deuke on 12/1/17.
 */

public class LogManager {
    private static final File appDirectory = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/logs/");
    private static Context context;
    private static File logDirectory;

    public LogManager(Context context) {
        this.context = context;
        this.logDirectory = new File(appDirectory + "/" + BuildConfig.APPLICATION_ID + "/");
        if (isExternalStorageWritable()) {
            if (!appDirectory.exists()) {
                appDirectory.mkdirs();
            }
            if (!logDirectory.exists()) {
                logDirectory.mkdir();
            }
        }
    }

    public void log(String content, LogLevel level) {
        switch (level) {
            case ERROR:
                writeLog(content, "error.log");
                writeLog(content, "trace.log");
                break;
            case INFO:
                writeLog(content, "info.log");
                writeLog(content, "trace.log");
                break;
            case DEBUG:
                writeLog(content, "debug.log");
                writeLog(content, "trace.log");
                break;
        }
    }

    private void writeLog(String content, String fileName) {
        File file = new File(logDirectory, fileName);
        String logContent = System.currentTimeMillis() + "-" + context.getClass().getName() + " : " + content + "\n";
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            writeToFile(logContent, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFile(String data, String fileName) {
        FileOutputStream outputStream;
        File file = new File(logDirectory, fileName);
        try {
            outputStream = new FileOutputStream(file, true);
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            osw.write(data);
            osw.close();
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readFromFile(String fileName) {
        FileInputStream fis = null;
        StringBuilder sb = new StringBuilder();
        File file = new File(logDirectory, fileName);
        try {
            fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    private boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

}
