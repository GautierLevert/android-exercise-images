package fr.iut_amiens.imagelist.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.joda.time.Instant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import fr.iut_amiens.imagelist.R;
import fr.iut_amiens.imagelist.converter.InstantConverter;
import fr.iut_amiens.imagelist.model.Image;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ImageDownloader {

    private final Context context;

    private final Gson gson;

    private final IotdService iotdService;

    public ImageDownloader(Context context) {
        this.context = context;
        gson = new GsonBuilder()
                .registerTypeAdapter(Instant.class, new InstantConverter())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://image-of-the-day-1153.appspot.com/api/v1/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        iotdService = retrofit.create(IotdService.class);
    }

    public List<Image> downloadList() throws IOException {
        List<Image> images = iotdService.downloadImages("bing", Instant.now(), 20).execute().body();
        Log.d(ImageDownloader.class.getSimpleName(), gson.toJson(images));
        return images;
    }

    public List<Image> mockDownloadList() {
        Reader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(
                            context.getResources().openRawResource(R.raw.sample),
                            Charset.forName("UTF-8")
                    )
            );
            return gson.fromJson(reader, new TypeToken<List<Image>>() {}.getType());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private Bitmap downloadBitmap(URL url) throws IOException {
        InputStream inputStream = null;
        try {
            inputStream = url.openStream();
            return BitmapFactory.decodeStream(inputStream);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    public Bitmap downloadThumbnail(Image image) throws IOException {
        return downloadBitmap(image.getThumbnailUrl());
    }

    public Bitmap downloadFullResolution(Image image) throws IOException {
        return downloadBitmap(image.getFullUrl());
    }
}
