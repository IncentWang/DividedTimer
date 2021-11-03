// Author: IncentWang
// Date: 2021/11/2

package com.example.dividedtimer;

import android.widget.TextView;

public class TimerThread extends Thread{
    public boolean timerStopped = false;
    public boolean timerEnded = false;
    TextView timerText;
    long startTime = 0;
    long stoppedTime = 0;
    long stopStartTime = 0;
    long lastTime = -1;
    long totalStopTime = 0;

    public TimerThread(TextView tv, long startTime){
        timerText = tv;
        this.startTime = startTime;
    }

    public void changeTimerStopped(boolean isStop){
        if (isStop){
            stopStartTime = System.currentTimeMillis();
            timerStopped = true;
        }
        else{
            totalStopTime += System.currentTimeMillis() - stopStartTime;
            timerStopped = false;
        }
    }

    @Override
    public void run(){
            while (!timerEnded){
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                timerText.post(new Runnable() {
                    public void run() {
                        if (!timerStopped){

                            timerText.setText(format(System.currentTimeMillis() - startTime - totalStopTime));
                        }
                        else{
                            stoppedTime = (System.currentTimeMillis() - stopStartTime);
                        }
                    }
                });
            }
            lastTime = System.currentTimeMillis() - totalStopTime - startTime;
    }

    private String format(long time){
        // sec time/1000
        // min sec/60
        // hour min/60
        int sec = (int) time / 1000;
        int min = (int) sec / 60;
        int hour = (int) min / 60;

        return String.format("%02d:%02d:%02d", hour, min % 60, sec % 60);
    }
}
