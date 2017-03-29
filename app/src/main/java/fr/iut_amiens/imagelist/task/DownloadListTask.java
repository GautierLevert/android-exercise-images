package fr.iut_amiens.imagelist.task;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import fr.iut_amiens.imagelist.ImageAdapter;
import fr.iut_amiens.imagelist.model.Image;
import fr.iut_amiens.imagelist.service.ImageDownloader;

public final class DownloadListTask extends AsyncTask<Void, Void, List<Image>> {

    private final ImageDownloader downloader;

    private final ImageAdapter imageAdapter;

    public DownloadListTask(ImageDownloader downloader, ImageAdapter imageAdapter) {
        this.downloader = downloader;
        this.imageAdapter = imageAdapter;
    }

    @Override
    protected List<Image> doInBackground(Void... params) {
        try {
            return downloader.downloadList();
        } catch (IOException e) {
            Log.e(DownloadListTask.class.getSimpleName(), "Error while downloading image", e);
            return Collections.emptyList();
        }
    }

    @Override
    protected void onPostExecute(List<Image> images) {
        imageAdapter.setContent(images);
    }
}
