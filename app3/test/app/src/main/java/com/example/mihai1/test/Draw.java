package com.example.mihai1.test;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class Draw extends View implements View.OnTouchListener {

    private Paint mPaint = new Paint();
    RectF oval = new RectF();
   private float x,y,x2=670,y2=409,x3=1430,y3=410,x4=1690,y4=410;

    private List<Float> xx=new ArrayList<>();
    private List<Float> yy=new ArrayList<>();
    private int[] mod={0,60,0,0,0};
    private boolean[] stare={true,true,true,true,true};
   public boolean stare_noua=true;
    public Handler pod1,pod3,pod4;
    public static boolean acess_stare=true;
    public int aces_rep=0,aces_rep1=0,var_ader=3;

    public Draw(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        canvas.drawPaint(mPaint);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.sus1);
        canvas.drawBitmap(bitmap, 1130, 150, null);

        Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.jos1);
        canvas.drawBitmap(bitmap1, 1130, 550, null);

        Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.sus1);
        canvas.drawBitmap(bitmap2, 1620, 150, null);

        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.jos1);
        canvas.drawBitmap(bitmap3, 1620, 550, null);


        Bitmap bitmap5 = BitmapFactory.decodeResource(getResources(), R.drawable.deapta1);
        canvas.drawBitmap(bitmap5, 490, 690, null);

       Bitmap bitmap6 = BitmapFactory.decodeResource(getResources(), R.drawable.stinga1);
        canvas.drawBitmap(bitmap6, 80, 690, null);

        Bitmap bitmap7 = BitmapFactory.decodeResource(getResources(), R.drawable.push1);
        canvas.drawBitmap(bitmap7, 1370, 225, null);

        Bitmap bitmap8 = BitmapFactory.decodeResource(getResources(), R.drawable.push2);
        canvas.drawBitmap(bitmap8, 1370, 475, null);

/*for(int i=0;i<xx.size();++i) {
    if (xx.get(i) >= 80 && xx.get(i) <= 250 && yy.get(i) >= 690 && yy.get(i) <= 860) {
        Log.e("prim1:","1");
    } else if (xx.get(i) >= 490 && xx.get(i) <= 660 && yy.get(i) >= 690 && yy.get(i) <= 860) {
        Log.e("prim2:","2");
    }else if(xx.get(i) >= 1200 && xx.get(i) <= 1370 && yy.get(i) >= 150 && yy.get(i) <= 320) {
        Log.e("prim3:","3");
    }else if (xx.get(i) >= 1200 && xx.get(i) <= 1370 && yy.get(i) >= 550 && yy.get(i) <= 720) {
        Log.e("prim4:","4");
    }
}*/



        mPaint.setColor(Color.RED);
        oval.set(85, 50, 715, 650);
        canvas.drawArc(oval, 0, -180, true, mPaint);

        oval.set(85, 80, 715, 680);
        canvas.drawArc(oval, 0, 180, true, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(400, 365, 250, mPaint);

        Paint p;
        p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setTextSize(21);

        int inc=0;

        mPaint.setColor(Color.BLACK);
        for(float i= (float)-0.12;i>-3.1;i-=0.17){
        double x11 = 250 * Math.cos(i) + 400,
                y11 = 250 * Math.sin(i) + 365;

            double x12 = 360 * Math.cos(i) + 400,
                    y12 = 360 * Math.sin(i) + 365;

            double x13 = 230 * Math.cos(i) + 390,
                    y13 = 230 * Math.sin(i) + 365;

            canvas.drawLine((float)x11,(float)y11,(float)x12,(float)y12,mPaint);
            canvas.drawCircle((float)x11,(float)y11,2,mPaint);
           // canvas.drawCircle((float)x12,(float)y12,7,mPaint);
            canvas.drawText(""+inc,(float) x13, (float)y13, p);
            inc+=10;
          }


        mPaint.setColor(Color.WHITE);
        for(float i= (float)-0.09;i>-3.07;i-=0.035){
            double x11 = 250 * Math.cos(i) + 400,
                    y11 = 250 * Math.sin(i) + 365;

            double x12 = 290 * Math.cos(i) + 400,
                    y12 = 290 * Math.sin(i) + 365;
            canvas.drawLine((float) x11, (float) y11, (float) x12, (float) y12, mPaint);
           // canvas.drawCircle((float)x11,(float)y11,7,mPaint);
            // canvas.drawCircle((float)x12,(float)y12,7,mPaint);
        }

        //testat partea a 2
        inc=0;
        mPaint.setColor(Color.BLACK);
        for(float i= (float)0.12;i<3.1;i+=0.17){
            double x11 = 250 * Math.cos(i) + 400,
                    y11 = 250 * Math.sin(i) + 365;

            double x12 = 360 * Math.cos(i) + 400,
                    y12 = 360 * Math.sin(i) + 365;

            double x13 = 230 * Math.cos(i) + 390,
                    y13 = 230 * Math.sin(i) + 365;

            canvas.drawLine((float)x11,(float)y11,(float)x12,(float)y12,mPaint);
            canvas.drawCircle((float)x11,(float)y11,2,mPaint);
            // canvas.drawCircle((float)x12,(float)y12,7,mPaint);
            canvas.drawText(""+inc,(float) x13, (float)y13, p);
            inc+=10;
        }


        mPaint.setColor(Color.WHITE);
        for(float i= (float)0.09;i<3.07;i+=0.035){
            double x11 = 250 * Math.cos(i) + 400,
                    y11 = 250 * Math.sin(i) + 365;

            double x12 = 290 * Math.cos(i) + 400,
                    y12 = 290 * Math.sin(i) + 365;
            canvas.drawLine((float) x11, (float) y11, (float) x12, (float) y12, mPaint);

        }

        p.setTextSize(35);
        canvas.drawText("Move:" + mod[0], 340, 312, p);
        canvas.drawText("Move1:"+mod[1],340,350, p);
        canvas.drawText("Move2:"+mod[2],340,388, p);
        canvas.drawText("Move4:"+mod[3],340,426, p);


        Bitmap bitmap4 = BitmapFactory.decodeResource(getResources(), R.drawable.revers);
        canvas.drawBitmap(bitmap4, 265, 235, null);

      /*  Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.img_sus);
        canvas.drawBitmap(bitmap2, 1000, 150, null);

        Bitmap bitmap3 = BitmapFactory.decodeResource(getResources(), R.drawable.img_jos);
        canvas.drawBitmap(bitmap3, 1000, 550, null);*/


        //canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.imagine), 10, 10, null);

/*

//manipulator raspunde de miscare 360 grade
        mPaint.setColor(Color.RED);
        canvas.drawCircle(411, 430, 280, mPaint);

        mPaint.setColor(Color.WHITE);
        canvas.drawCircle(411, 430, 260, mPaint);

//desenam djostic pentru brate!!
        mPaint.setColor(Color.RED);
        canvas.drawRect(1500, 170, 1580, 700, mPaint);
        canvas.drawCircle(1540, 170, 40, mPaint);
        canvas.drawCircle(1540, 700, 40, mPaint);

        mPaint.setColor(Color.RED);
        canvas.drawRect(1750, 170, 1830, 700, mPaint);
        canvas.drawCircle(1790, 170, 40, mPaint);
        canvas.drawCircle(1790, 700, 40, mPaint);


float[] x1=new float[10];
        float[] y1=new float[10];

x1[0]=0;x1[1]=0;x1[2]=0;
        y1[0]=0;y1[1]=0;y1[2]=0;

       for(int j=0;j<xx.size();++j) {
           x1[j] = xx.get(j);
           y1[j] = yy.get(j);

           for (float i = 0; i < 6.2; i += 0.01) {
               double x11 = 270 * Math.cos(i) + 411,
                       y11 = 270 * Math.sin(i) + 430;
               if (x1[j] + 60 > x11 && x1[j] - 60 < x11 && y1[j] + 60 > y11 && y1[j] - 60 < y11) {
                   x2 = (float) x11;
                   y2 = (float) y11;
                   break;
               }
           }

           // Log.e("intrat:", "y3:" + y3);
           if (x1[j] >= 1450 && x1[j] <= 1630 && y1[j] >= 170 && y1[j] <= 600)y3 = y1[j];
           if (x1[j] >= 1690 && x1[j] <= 1810 && y1[j] >= 170 && y1[j] <= 600) y4 = y1[j];
       }

        mPaint.setColor(Color.GREEN);
        canvas.drawCircle(x2, y2, 80, mPaint);
        canvas.drawOval(x3, y3, x3 + 210, y3+100, mPaint);
        canvas.drawOval(x4, y4, x4 + 210, y4+100, mPaint);
*/
    }





    @Override
    public boolean onTouch(View v, MotionEvent event) {


        if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
           /* xx.add(event.getX());
            yy.add(event.getY());*/
              stare[0]=true;
            Log.e("Stare:","0");
            if (event.getX()>=80 && event.getX() <= 250 && event.getY() >= 690 && event.getY() <= 860 ){
                if(var_ader==3)thread_plus(179, 0, 0, 0);
                else thread_plus(179, 0, 4, 4);
               // aces_rep=1;
            } else if (event.getX() >= 490 && event.getX() <= 660 &&event.getY() >= 690 && event.getY() <= 860) {
                if(var_ader==3)thread_minus(1,0,0,0);
                else thread_minus(1,0,4,4);
               // aces_rep=2;
            }else if(event.getX() >= 1130 && event.getX() <= 1400 && event.getY() >= 150 && event.getY() <= 320) {
                thread_plus(170,0,1,1);
                //aces_rep1=3;
            }else if (event.getX() >= 1130 && event.getX() <= 1400 && event.getY() >= 550 && event.getY() <= 720) {
                thread_minus(60,0,1,1);
                //aces_rep1=4;
            }else if(event.getX() >= 1370 && event.getX() <= 1640 && event.getY() >= 225 && event.getY() <= 495){
                thread_plus(179,0,2,2);

            }else if(event.getX() >= 1370 && event.getX() <= 1640 && event.getY() >= 475 && event.getY() <= 745){
                thread_minus(1,0,2,2);

            }else if(event.getX() >= 1620 && event.getX() <= 1890 && event.getY() >= 150 && event.getY() <= 320){
                thread_minus(1, 0, 3, 3);
            }else if(event.getX() >= 1620 && event.getX() <= 1890 && event.getY() >= 550 && event.getY() <= 720){
                thread_plus(179, 0, 3, 3);
            }
            else if(event.getX() >= 265 && event.getX() <= 535 && event.getY() >= 235 && event.getY() <= 505){
               if(stare_noua==true){var_ader=2;stare_noua=false;}
                else {var_ader=3;stare_noua=true;}
            }


            Log.e("Action_down:x" + event.getX(), "y:" + event.getY());
         }
        else if(event.getActionMasked()==MotionEvent.ACTION_POINTER_DOWN ){
            /*xx.add(event.getX(event.getActionIndex()));
            yy.add(event.getY(event.getActionIndex()));*/
            if(event.getActionIndex()<2) {
                stare[event.getActionIndex()] = true;
                Log.e("Stare:", "" + event.getActionIndex());
                if (event.getX(event.getActionIndex()) >= 80 && event.getX(event.getActionIndex()) <= 250 &&
                        event.getY(event.getActionIndex()) >= 690 && event.getY(event.getActionIndex()) <= 860 ) {

                    if(var_ader==3)thread_plus(179, event.getActionIndex(), 0, 0);
                    else thread_plus(179, event.getActionIndex(), 4, 4);

                } else if (event.getX(event.getActionIndex()) >= 490 && event.getX(event.getActionIndex()) <= 660 &&
                        event.getY(event.getActionIndex()) >= 690 && event.getY(event.getActionIndex()) <= 860) {
                    if(var_ader==3)thread_minus(1, event.getActionIndex(), 0, 0);
                    else thread_minus(1, event.getActionIndex(), 4, 4);

                } else if (event.getX(event.getActionIndex()) >= 1200 && event.getX(event.getActionIndex()) <= 1370 &&
                        event.getY(event.getActionIndex()) >= 150 && event.getY(event.getActionIndex()) <= 320 ) {

                    thread_plus(170, event.getActionIndex(), 1, 1);

                } else if (event.getX(event.getActionIndex()) >= 1200 && event.getX(event.getActionIndex()) <= 1370 &&
                        event.getY(event.getActionIndex()) >= 550 && event.getY(event.getActionIndex()) <= 720 ) {
                    thread_minus(60, event.getActionIndex(), 1, 1);

                }else if (event.getX(event.getActionIndex()) >= 1370 && event.getX(event.getActionIndex()) <= 1640 &&
                        event.getY(event.getActionIndex()) >= 225 && event.getY(event.getActionIndex()) <= 495 ) {
                    thread_plus(179, event.getActionIndex(),2,2);

                } else if (event.getX(event.getActionIndex()) >= 1370 && event.getX(event.getActionIndex()) <= 1640 &&
                        event.getY(event.getActionIndex()) >= 475 && event.getY(event.getActionIndex()) <= 745 ) {
                    thread_minus(1,event.getActionIndex(),2,2);

                }else if(event.getX(event.getActionIndex()) >= 1620 && event.getX(event.getActionIndex()) <= 1890 &&
                        event.getY(event.getActionIndex()) >= 150 && event.getY(event.getActionIndex()) <= 320){

                    thread_minus(1, event.getActionIndex(), 3, 3);
                }else if(event.getX(event.getActionIndex()) >= 1620 && event.getX(event.getActionIndex()) <= 1890 &&
                        event.getY(event.getActionIndex()) >= 550 && event.getY(event.getActionIndex()) <= 720){

                    thread_plus(179, event.getActionIndex(), 3, 3);
                }
                else if(event.getX(event.getActionIndex()) >= 265 && event.getX(event.getActionIndex()) <= 535 &&
                        event.getY(event.getActionIndex()) >= 235 && event.getY(event.getActionIndex()) <= 505){

                    if(stare_noua==true){var_ader=2;stare_noua=false;}
                    else {var_ader=3;stare_noua=true;}
                }

                Log.e("Action_pointer_down:x" + event.getX(), "y:" + event.getY());
            }
        }
        else if(event.getActionMasked()==MotionEvent.ACTION_POINTER_UP){
          /*  xx.remove(event.getActionIndex());
            yy.remove(event.getActionIndex());*/
            stare[event.getActionIndex()]=false;
            Log.e("Stare:",""+event.getActionIndex());
            Log.e("Action_Pointer_up:x" + event.getX(), "y:" + event.getY());

        }
        else if(event.getActionMasked()==MotionEvent.ACTION_UP)
        {
            /*xx.clear();
            yy.clear();*/
            for(int i=0;i<5;i++)stare[i]=false;
            Log.e("Action_up:x" + event.getX(), "y:" + event.getY());
        }

        this.invalidate();

        return true;
    }


    public void thread_minus(final int val_min, final int st_index, final int mod_index,final int val_s)
    {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (stare[st_index]) {
                            if (mod[mod_index] >= val_min) {
                                mod[mod_index] -= 1;
                                pod1.sendEmptyMessage(1);
                            }
                            pod4.sendEmptyMessage(val_s);
                            SystemClock.sleep(75);

                        }
                    }
                }).start();

    }

    public void thread_plus(final int val_max,final int st_index,final int mod_index,final int val_s)
    {

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (stare[st_index]) {
                        if (mod[mod_index] <= val_max) {
                            mod[mod_index] += 1;
                            pod1.sendEmptyMessage(1);
                        }
                        pod3.sendEmptyMessage(val_s);
                        SystemClock.sleep(75);
                    }

                }
            }).start();
    }



}
