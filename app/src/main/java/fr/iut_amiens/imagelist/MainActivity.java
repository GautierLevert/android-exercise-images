package fr.iut_amiens.imagelist;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

import fr.iut_amiens.imagelist.model.Image;
import fr.iut_amiens.imagelist.task.DownloadListTask;

public final class MainActivity extends Activity implements ImageAdapter.OnItemClickListener {

    private ImageAdapter imageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageAdapter = new ImageAdapter(getLayoutInflater());
        imageAdapter.setHasStableIds(true);
        imageAdapter.setOnItemClickListener(this);

        getLoaderManager().initLoader(0, null, new LoaderManager.LoaderCallbacks<List<Image>>() {
            @Override
            public Loader<List<Image>> onCreateLoader(int id, Bundle args) {
                return new DownloadListTask(MainActivity.this);
            }

            @Override
            public void onLoadFinished(Loader<List<Image>> loader, List<Image> data) {
                imageAdapter.setContent(data);
            }

            @Override
            public void onLoaderReset(Loader<List<Image>> loader) {
                imageAdapter.setContent(Collections.<Image>emptyList());
            }
        });

        RecyclerView listView = (RecyclerView) findViewById(R.id.listView);
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setHasFixedSize(true);
        listView.setAdapter(imageAdapter);
    }

    @Override
    public void onItemClick(Image image) {
        Intent intent = new Intent(this, ViewerActivity.class);
        intent.putExtra(ViewerActivity.EXTRA_IMAGE, image);
        startActivity(intent);
    }
}
