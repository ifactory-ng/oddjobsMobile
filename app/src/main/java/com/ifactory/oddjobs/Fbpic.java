package com.ifactory.oddjobs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * Created by smilecs on 4/15/15.
 */
public class Fbpic {

    public static Bitmap profile_pic(String id){
        Bitmap pic = null;

        FutureTask<Bitmap> task = new FutureTask<Bitmap>(new Facebook_pic(id));
        ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(task);
        try{
            pic = task.get();
        }
        catch (InterruptedException i){
            i.printStackTrace();
        }
        catch (ExecutionException e){
            e.printStackTrace();
        }
        es.shutdown();
        return pic;

    }
    public static Bitmap getRoundedShape(Bitmap scaleBitmapImage) {
        int targetWidth = 50;
        int targetHeight = 50;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth, targetHeight), null);
        return targetBitmap;
    }
    public static class Facebook_pic implements Callable<Bitmap> {

        String id;

        public Facebook_pic(String id){
            this.id = id;

        }

        @Override
        public Bitmap call() throws Exception {

            Bitmap bitmap;

            final String nomimg = "https://graph.facebook.com/"+id+"/picture?type=large";
            URL imageURL = new URL(nomimg);
            Log.d("url", nomimg);
            HttpURLConnection connection = (HttpURLConnection) imageURL.openConnection();
            connection.setDoInput(true);
            connection.setInstanceFollowRedirects( true );
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;


        }
    }

}
