package com.hua.gentlemanx2.main.sort;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hua.gentlemanx2.R;

import java.util.List;

public class ContentRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {

    private Context mContext;
    private List<SectionContentItemEntity> mRightData;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public ContentRecyclerAdapter(Context context, List<SectionContentItemEntity> rightData) {
        this.mContext = context;
        this.mRightData = rightData;

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_category_list_right, viewGroup, false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(params);
        ViewHolder holder = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final ContentRecyclerAdapter.ViewHolder newHolder = (ContentRecyclerAdapter.ViewHolder) viewHolder;
        final SectionContentItemEntity sectionContentItemEntity = mRightData.get(i);

        //适配item数据
        newHolder.name.setText(sectionContentItemEntity.getGoodsName());
        final String thumb = sectionContentItemEntity.getGoodsThumb();
        Glide.with(mContext)
                .load(thumb)
                .into(newHolder.image);
        viewHolder.itemView.setTag(sectionContentItemEntity);
    }

    @Override
    public int getItemCount() {
        return mRightData.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (SectionContentItemEntity) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.category_item_have_picture_text_1);
            image = (ImageView) itemView.findViewById(R.id.category_item_have_picture_image_1);
        }
    }

}
