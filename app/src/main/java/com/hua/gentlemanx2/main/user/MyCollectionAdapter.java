package com.hua.gentlemanx2.main.user;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hua.gentlemanx2.R;

import java.util.List;

public class MyCollectionAdapter extends RecyclerView.Adapter<MyCollectionAdapter.ViewHolder> implements View.OnClickListener {

    private Context mContext;
    private List<ProductEntity> listData;

    public OnGoodsItemClickListener mGoodsItemListener;

    public MyCollectionAdapter(Context context, List<ProductEntity> data) {
        this.mContext = context;
        this.listData = data;
    }

    @NonNull
    @Override
    public MyCollectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_goods_list, viewGroup, false);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyCollectionAdapter.ViewHolder viewHolder, int i) {
        ProductEntity entity = listData.get(i);
        String big = entity.getBig();
        Glide.with(mContext)
                .load(big)
                .into(viewHolder.bigImage);
        viewHolder.name.setText(entity.getName());
        viewHolder.description.setText(entity.getDescription());
        viewHolder.itemView.setTag(entity);
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    @Override
    public void onClick(View v) {
        if (mGoodsItemListener != null) {
            mGoodsItemListener.onClick(v, (ProductEntity) v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView bigImage;//商品图片
        private TextView name;//商品名称
        private TextView description;//详情

        public ViewHolder(View itemView) {
            super(itemView);
            bigImage = (ImageView) itemView.findViewById(R.id.goodslist_img);
            name = (TextView) itemView.findViewById(R.id.goodslist_name);
            description = (TextView) itemView.findViewById(R.id.goodslist_description);
        }
    }

    public interface OnGoodsItemClickListener {
        void onClick(View view, ProductEntity entity);
    }

    public void setOnGoodsItemClickListener(OnGoodsItemClickListener listener) {
        this.mGoodsItemListener = listener;
    }
}
