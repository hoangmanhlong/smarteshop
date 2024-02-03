package com.example.loginapp.model.interator;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loginapp.data.Constant;
import com.example.loginapp.model.entity.FirebaseProduct;
import com.example.loginapp.model.listener.CartListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

public class CartInterator {

    private final String TAG = this.toString();

    private final DatabaseReference cartRef = Constant.cartRef;

    private final String uid = Constant.currentUser.getUid();

    private final CartListener listener;

    public CartInterator(CartListener listener) {
        this.listener = listener;
    }

    public void getFavoriteProductFromFirebase() {
        cartRef.child(uid).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FirebaseProduct product = snapshot.getValue(FirebaseProduct.class);
                listener.notifyItemAdded(product, previousChildName);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                FirebaseProduct product = snapshot.getValue(FirebaseProduct.class);
                listener.notifyItemChanged(product, previousChildName);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                FirebaseProduct product = snapshot.getValue(FirebaseProduct.class);
                listener.notifyItemRemoved(product);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void updateQuantity(int id, int quantity) {
        cartRef.child(uid).child(String.valueOf(id)).child("quantity").setValue(String.valueOf(quantity));
    }

    public void updateChecked(int id, boolean checked) {
        cartRef.child(uid).child(String.valueOf(id)).child("checked").setValue(checked);
    }

    public void deleteProductInFirebase(int id) {
        cartRef.child(uid).child(String.valueOf(id)).removeValue();
    }
}
