package com.example.threads;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    EditText editText1;
    EditText editText2;
    EditText editText3;
    Button button;

    Handler handler;
    private static Object lock = new Object();
    private static int turn = 1;
    private static String stra = "";
    private static int count0 = 1;
    private static int count00 = 1;
    private static int count000 = 1;
    private static int count1 = 0;
    private static int count2 = 0;
    private static int count3 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText1 = findViewById(R.id.ED1);
        editText2 = findViewById(R.id.ED2);
        editText3 = findViewById(R.id.ED3);
        textView = findViewById(R.id.TV);
        button = findViewById(R.id.BT);
        handler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                char[] chars = (char[]) msg.obj;
                textView.setText(String.valueOf(chars));
            }
        };
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count1 = editText1.getText().toString().split("\\s+").length;
                count2 = editText2.getText().toString().split("\\s+").length;
                count3 = editText3.getText().toString().split("\\s+").length;
                final StringBuilder s = new StringBuilder();
                String strri = editText1.getText().toString() + " " + editText2.getText().toString() + " " + editText3.getText().toString() + " ";
                String[] list =strri.split("\\s+");
                Thread oneThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (count0 <= count1) {
                                synchronized (lock) {
                                    while (turn != 1) {
                                        lock.wait();
                                    }
                                        String[] list = editText1.getText().toString().split("\\s+");
                                        s.append(list[count0 - 1]);
                                        System.out.println(list[count0 - 1] + "1");
                                        stra += list[count0 - 1] + " ";
                                    if (count00 <= count2){
                                        turn = 2;
                                    }
                                    else if (count000 <= count3){
                                        turn = 3;
                                    }
                                        count0++;
                                        lock.notifyAll();
                                }
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                Thread twoThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (count00 <= count2) {
                                synchronized (lock) {
                                    while (turn != 2) {
                                        lock.wait();
                                    }
                                    String[] list = editText2.getText().toString().split("\\s+");
                                    s.append(list[count00-1]);
                                    stra += list[count00 - 1] + " ";
                                    System.out.println(list[count00-1] + "2");
                                    if (count000 <= count3){
                                        turn = 3;
                                    }
                                    else if (count0 <= count1){
                                        turn = 1;
                                    }
                                    count00++;
                                    lock.notifyAll();
                                }
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                Thread threeThread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            while (count000 <= count3) {
                                synchronized (lock) {
                                    while (turn != 3) {
                                        lock.wait();
                                    }
                                    String[] list = editText3.getText().toString().split("\\s+");
                                    s.append(list[count000-1]);
                                    stra += list[count000 - 1] + " ";
                                    System.out.println(list[count000-1] + "1");
                                    if (count0 <= count1){
                                        turn = 1;
                                    }
                                    else if (count00 <= count2){
                                        turn = 2;
                                    }
                                    count000++;
                                    lock.notifyAll();
                                }
                            }
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                });
                oneThread.start();
                twoThread.start();
                threeThread.start();
                MyThread myThread = new MyThread(stra);
                myThread.start();
            }


            class MyThread extends Thread {
                private String str;
                private char[] texttoview;
                public MyThread(String str){
                    this.str = str;
                    this.texttoview = new char[str.length()];
                }

                @Override
                public void run() {
                    super.run();
                    char[] chars = str.toCharArray();
                    String punct500 = ".!?;";
                    String punct200 = ",:-";

                    for (int i = 0;i < str.length();i++){
                        texttoview[i] = chars[i];

                        Message msg = new Message();//отправить соо с этого потока на внутренний
                        msg.obj = texttoview;
                        handler.sendMessage(msg);

                        if(punct500.contains(String.valueOf(chars[i]))){
                            try {
                                sleep(500);
                            }catch (InterruptedException e){
                                throw new RuntimeException();
                            }
                        }else if(punct200.contains(String.valueOf(chars[i]))){
                            try {
                                sleep(200);
                            }catch (InterruptedException e){
                                throw new RuntimeException();
                            }
                        }else{
                            try {
                                sleep(100);
                            }catch (InterruptedException e){
                                throw new RuntimeException();
                            }
                        }
                    }
                }
            }
        });
    }
}
                /*String str1 = editText1.getText().toString() + " ";
                List<String> list1 = Arrays.asList(str1.split("\\s+"));
                String str2 = editText2.getText().toString() + " ";
                List<String> list2 = Arrays.asList(str2.split("\\s+"));
                String str3 = editText3.getText().toString() + " ";
                List<String> list3 = Arrays.asList(str3.split("\\s+"));
                List[] list = new List[]{list1, list2, list3};
                String strr = "";
                int[] dlin = new int[0];
                int mx = 0;
                if (list[0].size() > mx) {mx = list[0].size();}
                if (list[1].size() > mx) {mx = list[1].size();}
                if (list[2].size() > mx) {mx = list[2].size();}
                int k = 0;
                while (k < mx){
                    for (int i = 0;i< list.length;i++) {
                        if (list[i].size() > k) {
                            strr += list[i].get(k) + " ";
                        }
                    }
                    k++;
                }
                MyThread myThread = new MyThread(strr);
                myThread.start();*/