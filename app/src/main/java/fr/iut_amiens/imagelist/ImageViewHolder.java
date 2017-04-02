package fr.iut_amiens.imagelist;

import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import fr.iut_amiens.imagelist.model.Image;

public final class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView titleView;

    private final TextView descriptionView;

    private final ImageView imageView;

    private final ProgressBar progressBar;

    private Image image;

    private ImageAdapter.OnItemClickListener listener;

    public ImageViewHolder(View itemView) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.titleView);
        descriptionView = (TextView) itemView.findViewById(R.id.descriptionView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        itemView.setOnClickListener(this);
    }

    public void setListener(ImageAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public void bind(Image image) {
        this.image = image;
        titleView.setText(image.getTitle());
        descriptionView.setText(image.getDescription());

        progressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.INVISIBLE);

        Picasso.with(imageView.getContext())
                .load(Uri.parse(image.getThumbnailUrl().toString()))
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

    public void unbind() {
        this.image = null;
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClick(image);
        }
    }
}
