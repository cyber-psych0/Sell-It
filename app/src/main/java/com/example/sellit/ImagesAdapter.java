package com.example.sellit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;

    public ImagesAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item,parent,false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        final Upload upload = mUploads.get(position);
        holder.itemName.setText(upload.getItem_name());
        holder.price.setText(upload.getItem_price());
        holder.senderName.setText(upload.getUsername());

        Picasso.get()
                .load(upload.getDownloaduri())
                .resize(180,150)
                .into(holder.itemImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(mContext,""+upload.getItem_name(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    class ImageViewHolder extends RecyclerView.ViewHolder{

        TextView itemName,senderName,price;
        ImageView itemImage;


        ImageViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.item_name_text_view);
            senderName = itemView.findViewById(R.id.sender_name_text_view);
            price = itemView.findViewById(R.id.price_text_view);
            itemImage = itemView.findViewById(R.id.item_image_view);
        }
    }
}
