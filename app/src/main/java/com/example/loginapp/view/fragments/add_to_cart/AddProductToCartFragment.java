package com.example.loginapp.view.fragments.add_to_cart;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loginapp.databinding.FragmentAddProductToCartBinding;
import com.example.loginapp.model.entity.Product;
import com.example.loginapp.presenter.AddProductToCartPresenter;
import com.example.loginapp.utils.Constant;
import com.example.loginapp.view.commonUI.AppMessage;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;


public class AddProductToCartFragment extends BottomSheetDialogFragment implements AddProductToCartView {

    public static final String TAG = AddProductToCartFragment.class.getSimpleName();

    private AddProductToCartPresenter presenter;

    private FragmentAddProductToCartBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new AddProductToCartPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddProductToCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.setFragment(this);
        presenter.initData();
    }

    public void onAddToCartClick() {
        presenter.addProductToCart();
    }

    @Override
    public void bindQuantity(String quantity) {
        binding.quantity.setText(quantity);
    }

    @Override
    public void getSharedData() {
        if (getArguments() != null) {
            Product product = (Product) getArguments().getSerializable(Constant.PRODUCT_KEY);
            if (product != null) presenter.setProduct(product);
        }
    }

    @Override
    public void bindProduct(Product product) {
        binding.setProduct(product);
    }

    @Override
    public void dismissAddProductToCartFragment() {
        this.dismiss();
    }

    @Override
    public void onMessage(int message) {
        AppMessage.showMessage(requireContext(), getString(message));
    }

    public void onPlusButtonClick() {
        presenter.increasingTheNumber();
    }

    public void onMinusButtonClick() {
        presenter.reduceTheNumberOf();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
    }
}