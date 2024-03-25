package com.example.loginapp.model.interator;

import androidx.annotation.NonNull;

import com.example.loginapp.utils.Constant;
import com.example.loginapp.model.entity.DeliveryAddress;
import com.example.loginapp.model.listener.SelectDeliveryAddressListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SelectDeliveryAddressInteractor {

    private SelectDeliveryAddressListener listener;

    private FirebaseUser user;

    public SelectDeliveryAddressInteractor(SelectDeliveryAddressListener listener) {
        this.listener = listener;
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    public void clear() {
        listener = null;
        user = null;
    }

    public void getDeliveryAddresses() {

        Constant.deliveryAddressRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<DeliveryAddress> deliveryAddresses = new ArrayList<>();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        deliveryAddresses.add(dataSnapshot.getValue(DeliveryAddress.class));
                    if (listener != null) listener.getDeliveryAddresses(deliveryAddresses);
                } else {
                    if (listener != null) listener.isDeliveryAddressEmpty();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
