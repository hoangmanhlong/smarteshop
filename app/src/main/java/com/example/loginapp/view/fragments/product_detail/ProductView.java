package com.example.loginapp.view.fragments.product_detail;

import com.example.loginapp.model.entity.Comment;
import com.example.loginapp.model.entity.Product;

import java.util.List;

public interface ProductView {

    void onMessage(String message);

    void enableFavorite(Boolean compare);

    void saveToBasketSuccess();

    void getComments(List<Comment> comments);

    void getCommentCount(String number);

    void bindProduct(Product product);

    void hasNewFavoriteProduct();

    void getSimilarProducts(List<Product> products);
}