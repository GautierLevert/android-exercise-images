package fr.iut_amiens.imagelist;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;

import fr.iut_amiens.imagelist.model.Image;
import fr.iut_amiens.imagelist.service.ImageDownloader;
import fr.iut_amiens.imagelist.task.DownloadFullResolutionTask;

public final class ViewerActivity extends Activity {

    public static final String EXTRA_IMAGE = "fr.iut_amiens.imagelist.EXTRA_IMAGE";

    private DownloadFullResolutionTask download;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        Image image = (Image) getIntent().getSerializableExtra(EXTRA_IMAGE);

        setTitle(image.getTitle());

        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        ImageDownloader imageDownloader = new ImageDownloader(this);

        download = new DownloadFullResolutionTask(imageDownloader, image, progressBar, imageView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        download.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        download.cancel(false);
    }
}
