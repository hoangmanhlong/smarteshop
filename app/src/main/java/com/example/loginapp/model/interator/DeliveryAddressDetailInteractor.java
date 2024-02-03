package com.example.loginapp.model.interator;

import com.example.loginapp.data.Constant;
import com.example.loginapp.model.entity.DeliveryAddress;
import com.example.loginapp.model.listener.DeliveryAddressDetailListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

public class DeliveryAddressDetailInteractor {

    private final String TAG = this.toString();

    private final DeliveryAddressDetailListener listener;

    DatabaseReference ref = Constant.deliveryAddressRef.child(Constant.currentUser.getUid());

    public DeliveryAddressDetailInteractor(DeliveryAddressDetailListener listener) {
        this.listener = listener;
    }

    public void updateDeliveryAddress(Boolean isNewDeliveryAddress, DeliveryAddress deliveryAddress) {
        int countTemp = 0;
        ref.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DataSnapshot snapshot = task.getResult();
                if (snapshot.exists()) {
                    int count = (int) snapshot.getChildrenCount();
                    if (count == 0) {
                        update(deliveryAddress);
                    } else {
                        if (deliveryAddress.getIsDefault()) {
                            if (isNewDeliveryAddress) {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    DeliveryAddress deliveryAddress1 = dataSnapshot.getValue(DeliveryAddress.class);
                                    ref.child(deliveryAddress1.getDeliveryAddressId())
                                            .child("isDefault")
                                            .setValue(false)
                                            .addOnCompleteListener(task1 -> {})
                                            .addOnFailureListener(e -> listener.isUpdateSuccess(false));
                                }
                                update(deliveryAddress);
                            } else {
                                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                    DeliveryAddress deliveryAddress1 = dataSnapshot.getValue(DeliveryAddress.class);
                                    if (deliveryAddress.getDeliveryAddressId().equals(deliveryAddress1.getDeliveryAddressId()))
                                        update(deliveryAddress);
                                    else {
                                        ref.child(deliveryAddress1.getDeliveryAddressId())
                                                .child("isDefault")
                                                .setValue(false)
                                                .addOnCompleteListener(s -> {
                                                })
                                                .addOnFailureListener(e -> listener.isUpdateSuccess(false));
                                    }
                                }
                            }
                        } else {
                            update(deliveryAddress);
                        }
                    }
                }
            }
        });
    }

    private void update(DeliveryAddress deliveryAddress) {
        ref.child(deliveryAddress.getDeliveryAddressId())
                .setValue(deliveryAddress)
                .addOnCompleteListener(s -> listener.isUpdateSuccess(true))
                .addOnFailureListener(e -> listener.isUpdateSuccess(false));

    }

    public void deleteDeliveryAddress(String id) {
        Constant.deliveryAddressRef
                .child(Constant.currentUser.getUid())
                .child(id).removeValue()
                .addOnCompleteListener(task -> listener.deleteSuccess())
                .addOnFailureListener(task -> listener.onMessage("Error"));
    }
}