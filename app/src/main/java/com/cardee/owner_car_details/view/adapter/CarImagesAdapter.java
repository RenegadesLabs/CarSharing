package com.cardee.owner_car_details.view.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.cardee.R;
import com.cardee.domain.owner.entity.Image;
import com.cardee.owner_car_details.view.listener.ImageViewListener;

import java.util.ArrayList;
import java.util.List;

public class CarImagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ADD_BUTTON = 0;
    private static final int IMAGE_VIEW = 1;

    private ImageViewListener listener;
    private final List<ImageItemWrapper> items;
    private final LayoutInflater inflater;
    private final RequestManager glideRequestManager;

    public CarImagesAdapter(Context context) {
        items = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        glideRequestManager = Glide.with(context);
    }

    public void addItems(List<Image> images) {
        items.clear();
        items.add(ImageItemWrapper.newAddButtonItem());
        for (Image image : images) {
            items.add(items.size() - 1, ImageItemWrapper.newImageItem(image));
        }
        notifyDataSetChanged();
    }

    public void setImageViewListener(ImageViewListener listener) {
        this.listener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case IMAGE_VIEW:
                View imageView = inflater.inflate(R.layout.view_image, parent, false);
                holder = new ImageHolder(imageView);
                break;
            case ADD_BUTTON:
                View addButtonView = inflater.inflate(R.layout.view_add_button, parent, false);
                holder = new AddButtonHolder(addButtonView);
                break;
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ImageHolder) {
            final Image image = items.get(position).getImage();
            ((ImageHolder) holder).bind(image, glideRequestManager);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onImageClick(image);
                    }
                }
            });
            return;
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onAddNewClick();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getViewType();
    }

    public static class ImageHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private View loadingIndicator;
        private View primaryImageSign;

        public ImageHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            loadingIndicator = itemView.findViewById(R.id.progress_layout);
            primaryImageSign = itemView.findViewById(R.id.primary_image_sign);
        }

        public void bind(Image image, RequestManager imageRequestManager) {
            primaryImageSign.setVisibility(image.getPrimary() ? View.VISIBLE : View.GONE);
            imageRequestManager
                    .load(image.getLink())
                    .listener(new RequestListener<String, GlideDrawable>() {
                        @Override
                        public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                            loadingIndicator.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                            loadingIndicator.setVisibility(View.GONE);
                            return false;
                        }
                    })
                    .error(R.drawable.img_no_car)
                    .into(imageView);
        }
    }

    public static class AddButtonHolder extends RecyclerView.ViewHolder {

        public AddButtonHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ImageItemWrapper {
        private final int viewType;
        private final Image image;

        private ImageItemWrapper(Image image, int viewType) {
            this.image = image;
            this.viewType = viewType;
        }

        public static ImageItemWrapper newAddButtonItem() {
            return new ImageItemWrapper(null, ADD_BUTTON);
        }

        public static ImageItemWrapper newImageItem(Image image) {
            return new ImageItemWrapper(image, IMAGE_VIEW);
        }

        public Image getImage() {
            return image;
        }

        public int getViewType() {
            return viewType;
        }
    }
}
