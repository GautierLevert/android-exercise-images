package fr.iut_amiens.imagelist;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fr.iut_amiens.imagelist.model.Image;

public final class ViewerActivity extends Activity {

    public static final String EXTRA_IMAGE = "fr.iut_amiens.imagelist.EXTRA_IMAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewer);

        Image image = (Image) getIntent().getSerializableExtra(EXTRA_IMAGE);

        setTitle(image.getTitle());

        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);

        Picasso.with(this)
                .load(Uri.parse(image.getFullUrl().toString()))
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.INVISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                    }
                });
    }
}
