package com.example.loginapp.adapter.product_adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.model.entity.Product;
import com.example.loginapp.databinding.LayoutProductItemBinding;

public class HomeAdapter extends ListAdapter<Product, HomeAdapter.ItemViewHolder> {

    private final OnItemClickListener onItemClickListener;

    public HomeAdapter(OnItemClickListener listener) {
        super(DiffCallback);
        this.onItemClickListener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemViewHolder itemViewHolder = new ItemViewHolder(
            LayoutProductItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent,
                false
            ), onItemClickListener
        );

        itemViewHolder.itemView.setOnClickListener(v -> {
            int position = itemViewHolder.getAdapterPosition();
            onItemClickListener.onItemClick(getItem(position));
        });
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View

        .OnClickListener {
        private final LayoutProductItemBinding binding;

        private final OnItemClickListener onItemClickListener;

        public ItemViewHolder(LayoutProductItemBinding binding, OnItemClickListener onItemClickListener) {
            super(binding.getRoot());
            this.onItemClickListener = onItemClickListener;
            this.binding = binding;
            binding.getRoot().setOnClickListener(this);
        }

        public void bind(Product product) {
            binding.setProduct(product);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == binding.getRoot().getId())
                onItemClickListener.onItemClick(getItem(getAdapterPosition()));
        }
    }

    public static final DiffUtil.ItemCallback<Product> DiffCallback = new DiffUtil.ItemCallback<Product>() {
        @Override
        public boolean areItemsTheSame(Product oldItem, Product newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(Product oldItem, Product newItem) {
            return oldItem.equals(newItem);
        }
    };
}
