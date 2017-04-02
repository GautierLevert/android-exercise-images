package fr.iut_amiens.imagelist;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fr.iut_amiens.imagelist.model.Image;

public final class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Image image);
    }

    private final LayoutInflater layoutInflater;

    private List<Image> content = Collections.emptyList();

    private final List<ImageViewHolder> holders = new ArrayList<>();

    private OnItemClickListener listener;

    public ImageAdapter(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public Image getItem(int position) {
        return content.get(position);
    }

    @Override
    public ImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ImageViewHolder holder = new ImageViewHolder(layoutInflater.inflate(R.layout.listitem_image, parent, false));
        holders.add(holder);
        return holder;
    }

    @Override
    public void onBindViewHolder(ImageViewHolder holder, int position) {
        holder.bind(getItem(position));
        holder.setListener(listener);
    }

    @Override
    public void onViewRecycled(ImageViewHolder holder) {
        holder.unbind();
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public void setContent(List<Image> content) {
        this.content = content;
        notifyDataSetChanged();
    }
}
