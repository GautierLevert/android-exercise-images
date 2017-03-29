package fr.iut_amiens.imagelist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import fr.iut_amiens.imagelist.model.Image;
import fr.iut_amiens.imagelist.service.ImageDownloader;
import fr.iut_amiens.imagelist.task.DownloadListTask;

public final class MainActivity extends Activity implements ImageAdapter.OnItemClickListener {

    private ImageDownloader imageDownloader;

    private ImageAdapter imageAdapter;

    private DownloadListTask downloadListTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageDownloader = new ImageDownloader(this);

        imageAdapter = new ImageAdapter(getLayoutInflater(), imageDownloader);
        imageAdapter.setHasStableIds(true);
        imageAdapter.setOnItemClickListener(this);

        RecyclerView listView = (RecyclerView) findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setHasFixedSize(true);
        listView.setAdapter(imageAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        downloadListTask = new DownloadListTask(imageDownloader, imageAdapter);
        downloadListTask.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();
        downloadListTask.cancel(false);
        imageAdapter.cancelAllDownloads();
    }

    @Override
    public void onItemClick(Image image) {
        Intent intent = new Intent(this, ViewerActivity.class);
        intent.putExtra(ViewerActivity.EXTRA_IMAGE, image);
        startActivity(intent);
    }
}
