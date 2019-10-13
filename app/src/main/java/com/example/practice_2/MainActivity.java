package com.example.practice_2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;

import java.net.URISyntaxException;

public class MainActivity extends AppCompatActivity {
    Button button;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button=findViewById(R.id.proccess);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


        imageView=findViewById(R.id.image);

        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inMutable=true;
        Bitmap b=BitmapFactory.decodeResource(getApplication().getResources(),R.drawable.hamster2,options);
        Paint p=new Paint();
        p.setStrokeWidth(5);
        p.setColor(Color.RED);
        p.setStyle(Paint.Style.STROKE);
        Bitmap mybit=Bitmap.createBitmap(b.getWidth(),b.getHeight(),Bitmap.Config.RGB_565);
        Canvas c=new Canvas(mybit);
        c.drawBitmap(b,0,0,null);
        FaceDetector faceDetector=new FaceDetector.Builder(getApplicationContext()).setTrackingEnabled(false).build();
        if(!faceDetector.isOperational()){
            new AlertDialog.Builder(imageView.getContext()).setMessage("could not set up facedetector");
            return;
        }
        Frame frame=new Frame.Builder().setBitmap(b).build();
        SparseArray<Face> face=faceDetector.detect(frame);
        for (int i=0;i<face.size();i++){
            Face thisface=face.valueAt(i);
            float x1=thisface.getPosition().x;
            float y1=thisface.getPosition().y;
            float x2=x1+thisface.getHeight();
            float y2=y1+thisface.getHeight();
            c.drawRoundRect(new RectF(x1,y1,x2,y2),2,2,p);
        }
        imageView.setImageDrawable(new BitmapDrawable(getResources(),mybit));
            }
        });

    }



}
