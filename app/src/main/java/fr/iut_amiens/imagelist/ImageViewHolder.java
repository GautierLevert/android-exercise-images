package fr.iut_amiens.imagelist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import fr.iut_amiens.imagelist.model.Image;
import fr.iut_amiens.imagelist.service.ImageDownloader;
import fr.iut_amiens.imagelist.task.DownloadThumbnailTask;

public final class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private final TextView titleView;

    private final TextView descriptionView;

    private final ImageView imageView;

    private final ProgressBar progressBar;

    private final ImageDownloader imageDownloader;

    private Image image;

    private DownloadThumbnailTask task;

    private ImageAdapter.OnItemClickListener listener;

    public ImageViewHolder(View itemView, ImageDownloader imageDownloader) {
        super(itemView);
        titleView = (TextView) itemView.findViewById(R.id.titleView);
        descriptionView = (TextView) itemView.findViewById(R.id.descriptionView);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        this.imageDownloader = imageDownloader;
        itemView.setOnClickListener(this);
    }

    public void setListener(ImageAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public void bind(Image image) {
        this.image = image;
        titleView.setText(image.getTitle());
        descriptionView.setText(image.getDescription());
        task = new DownloadThumbnailTask(this, image, imageDownloader, imageView, progressBar);
        task.execute();
    }

    public void unbind() {
        this.image = null;
        cancel();
    }

    public void cancel() {
        if (task != null) {
            task.cancel(false);
            task = null;
        }
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onItemClick(image);
        }
    }
}
