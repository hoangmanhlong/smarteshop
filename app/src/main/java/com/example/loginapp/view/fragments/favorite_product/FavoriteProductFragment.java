package com.example.loginapp.view.fragments.favorite_product;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginapp.App;
import com.example.loginapp.R;
import com.example.loginapp.adapter.SwipeHelper;
import com.example.loginapp.adapter.favorite_adapter.FavoriteAdapter;
import com.example.loginapp.adapter.favorite_adapter.FavoriteItemClickListener;
import com.example.loginapp.databinding.FragmentFavoriteProductBinding;
import com.example.loginapp.model.entity.Product;
import com.example.loginapp.presenter.FavoritePresenter;
import com.example.loginapp.utils.Constant;
import com.example.loginapp.view.commonUI.AppMessage;
import com.example.loginapp.view.commonUI.LoginRemindDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class FavoriteProductFragment extends Fragment implements FavoriteView, FavoriteItemClickListener {

    private final FavoritePresenter presenter = new FavoritePresenter(this);

    private FragmentFavoriteProductBinding binding;

    private final FavoriteAdapter adapter = new FavoriteAdapter(this);

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFavoriteProductBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (FirebaseAuth.getInstance().getCurrentUser() != null)  {
            initView();
        } else {
            LoginRemindDialog.show(this, requireContext());
        }
    }

    private void initView() {
        RecyclerView recyclerView = binding.favoriteRecyclerView;
        recyclerView.setAdapter(adapter);
        presenter.initData();
        new SwipeHelper(requireContext(), recyclerView) {
            @Override
            public void instantiateUnderlayButton(RecyclerView.ViewHolder viewHolder, List<UnderlayButton> underlayButtons) {
                underlayButtons.add(new SwipeHelper.UnderlayButton(
                        "Delete",
                        0,
                        Color.parseColor("#FF3C30"),
                        adapter::getProductByPosition
                ));
            }
        };
    }

    @Override
    public void onItemAdded(List<Product> products) {
        adapter.submitList(products);
        adapter.notifyItemInserted(products.size() - 1);
    }

    @Override
    public void onMessage(String message) {
        AppMessage.showMessage(requireContext(), message);
    }

    @Override
    public void notifyItemRemoved(int index) {
        adapter.notifyItemRemoved(index);
    }

    @Override
    public void isWishlistEmpty(Boolean isEmpty) {
        if (isEmpty) {
            binding.listEmptyTextView.setVisibility(View.VISIBLE);
            binding.favoriteRecyclerView.setVisibility(View.GONE);
        } else {
            binding.listEmptyTextView.setVisibility(View.GONE);
            binding.favoriteRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClick(Product product) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constant.PRODUCT_KEY, product);
        NavHostFragment.findNavController(this).navigate(R.id.action_global_productFragment, bundle);
    }

    @Override
    public void getProductByPosition(int productId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage(R.string.dialog_message)
                .setPositiveButton(R.string.positive_button_title, (dialog, which) -> presenter.deleteFavoriteProduct(productId))
                .setNegativeButton(R.string.negative_button_title, (dialog, which) -> {
                })
                .setCancelable(false)
                .show();
    }
}