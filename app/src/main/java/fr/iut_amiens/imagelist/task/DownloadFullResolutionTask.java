package fr.iut_amiens.imagelist.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.IOException;

import fr.iut_amiens.imagelist.model.Image;
import fr.iut_amiens.imagelist.service.ImageDownloader;

public final class DownloadFullResolutionTask extends AsyncTask<Void, Void, Bitmap> {

    private final ImageDownloader imageDownloader;

    private final Image image;

    private final ProgressBar progressBar;

    private final ImageView imageView;

    public DownloadFullResolutionTask(ImageDownloader imageDownloader, Image image, ProgressBar progressBar, ImageView imageView) {
        this.imageDownloader = imageDownloader;
        this.image = image;
        this.progressBar = progressBar;
        this.imageView = imageView;
    }

    @Override
    protected void onPreExecute() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            return imageDownloader.downloadFullResolution(image);
        } catch (IOException e) {
            Log.e(DownloadFullResolutionTask.class.getSimpleName(), "Error while downloading full resolution image", e);
            cancel(false);
            return null;
        }
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        progressBar.setVisibility(View.INVISIBLE);
        imageView.setImageBitmap(bitmap);
    }
}
