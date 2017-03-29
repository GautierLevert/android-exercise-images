package fr.iut_amiens.imagelist.task;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;

import fr.iut_amiens.imagelist.ImageAdapter;
import fr.iut_amiens.imagelist.ImageViewHolder;
import fr.iut_amiens.imagelist.model.Image;
import fr.iut_amiens.imagelist.service.ImageDownloader;

/**
 * Created by gautier on 28/02/2017.
 */

public final class DownloadThumbnailTask extends AsyncTask<Void, Void, Bitmap> {

    private final ImageViewHolder holder;

    private final Image image;

    private final ImageDownloader imageDownloader;

    private final ImageView imageView;

    private final ProgressBar progressBar;

    public DownloadThumbnailTask(ImageViewHolder holder, Image image, ImageDownloader imageDownloader, ImageView imageView, ProgressBar progressBar) {
        this.holder = holder;
        this.image = image;
        this.imageDownloader = imageDownloader;
        this.imageView = imageView;
        this.progressBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        imageView.setImageDrawable(null);
        imageView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected Bitmap doInBackground(Void... params) {
        try {
            return imageDownloader.downloadThumbnail(image);
        } catch (IOException e) {
            Log.e(DownloadThumbnailTask.class.getSimpleName(), "Error downloading thumbnail", e);
            cancel(false);
            return null;
        }
    }

    @Override
    protected void onCancelled() {
        imageView.setImageDrawable(null);
        imageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        imageView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        holder.cancel();
    }
}
