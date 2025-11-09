package com.example.myapplication.ui.favourite;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteViewModel extends ViewModel {

    private final MutableLiveData<List<FavouriteItem>> favouriteItems;

    public FavouriteViewModel() {
        favouriteItems = new MutableLiveData<>();
        loadFavouriteItems();
    }

    public LiveData<List<FavouriteItem>> getFavouriteItems() {
        return favouriteItems;
    }

    private void loadFavouriteItems() {
        List<FavouriteItem> dummyItems = new ArrayList<>();
        dummyItems.add(new FavouriteItem("Trường Đại học Công nghệ Sài Gòn", "Sài Gòn", "https://images.unsplash.com/photo-1517694712202-14dd9538aa97"));
        dummyItems.add(new FavouriteItem("Trường Đại học Công nghệ Sài Gòn", "Sài Gòn", "https://images.unsplash.com/photo-1454165804606-c3d57bc86b40"));
        dummyItems.add(new FavouriteItem("Trường Đại học Công nghệ Sài Gòn", "Sài Gòn", "https://images.unsplash.com/photo-1496065187959-7f07b8353c55"));
        dummyItems.add(new FavouriteItem("Trường Đại học Công nghệ Sài Gòn", "Sài Gòn", "https://images.unsplash.com/photo-1521321203998-06a090b84f93"));

        favouriteItems.setValue(dummyItems);
    }
}
