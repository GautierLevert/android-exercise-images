package fr.iut_amiens.imagelist.task;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import fr.iut_amiens.imagelist.model.Image;
import fr.iut_amiens.imagelist.service.ImageDownloader;

public final class DownloadListTask extends AsyncTaskLoader<List<Image>> {

    private final ImageDownloader downloader;

    private List<Image> images = null;

    public DownloadListTask(Context context) {
        super(context);
        downloader = new ImageDownloader(context);
    }

    @Override
    public List<Image> loadInBackground() {
        try {
            return downloader.downloadList();
        } catch (IOException e) {
            Log.e(DownloadListTask.class.getSimpleName(), "Error while downloading image", e);
            return Collections.emptyList();
        }
    }

    @Override
    protected void onStartLoading() {
        if (images != null) {
            deliverResult(images);
        }

        if (takeContentChanged() || images == null) {
            forceLoad();
        }
    }

    @Override
    public void deliverResult(List<Image> data) {
        images = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }
}
