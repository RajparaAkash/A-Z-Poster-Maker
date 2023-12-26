package com.letsmake.atoz.design.poster_builder;

import java.util.ArrayList;

public class Data_Provider {

     ArrayList<BG_Image> mCategoryLists = new ArrayList();
     ArrayList<BG_Image> mLoadMoreItems = new ArrayList();
     ArrayList<BG_Image> mLoadMoreStickerItems = new ArrayList();
     ArrayList<BG_Image> mStickerCategoryLists = new ArrayList();

     ArrayList<Object> mLoadMorePosterItems = new ArrayList();
     ArrayList<Object> mObjects = new ArrayList();


    public ArrayList<BG_Image> get_Load_More_Items() {
        int size = this.mLoadMoreItems.size();
        for (int i = size; i < size + 16; i++) {
            if (i < this.mCategoryLists.size()) {
                this.mLoadMoreItems.add(this.mCategoryLists.get(i));
            }
        }
        return this.mLoadMoreItems;
    }

    public ArrayList<BG_Image> get_Load_More_Sticker_Items() {
        int size = this.mLoadMoreStickerItems.size();
        for (int i = size; i < size + 20; i++) {
            if (i < this.mStickerCategoryLists.size()) {
                this.mLoadMoreStickerItems.add(this.mStickerCategoryLists.get(i));
            }
        }
        return this.mLoadMoreStickerItems;
    }

    public ArrayList<Object> get_Load_Poster_Items() {
        int size = this.mLoadMorePosterItems.size();
        for (int i = size; i < size + 10; i++) {
            if (i < this.mObjects.size()) {
                this.mLoadMorePosterItems.add(this.mObjects.get(i));
            }
        }
        return this.mLoadMorePosterItems;
    }

    public ArrayList<BG_Image> get_Load_More_Itemss() {
        int size = this.mLoadMoreItems.size();
        for (int i = size; i < size + 16; i++) {
            if (i < this.mCategoryLists.size()) {
                this.mLoadMoreItems.add(this.mCategoryLists.get(i));
            }
        }
        return this.mLoadMoreItems;
    }

    public ArrayList<BG_Image> getLoad_More_StickerItemsS() {
        int size = this.mLoadMoreStickerItems.size();
        for (int i = size; i < size + 20; i++) {
            if (i < this.mStickerCategoryLists.size()) {
                this.mLoadMoreStickerItems.add(this.mStickerCategoryLists.get(i));
            }
        }
        return this.mLoadMoreStickerItems;
    }

    public ArrayList<Object> getLoad_More_PosterItemsS() {
        int size = this.mLoadMorePosterItems.size();
        for (int i = size; i < size + 10; i++) {
            if (i < this.mObjects.size()) {
                this.mLoadMorePosterItems.add(this.mObjects.get(i));
            }
        }
        return this.mLoadMorePosterItems;
    }

    public void apply_Background_List(ArrayList<BG_Image> arrayList) {
        this.mCategoryLists = arrayList;
    }

    public void apply_Poster_List(ArrayList<Object> arrayList) {
        this.mObjects = arrayList;
    }

    public void setStickerList(ArrayList<BG_Image> arrayList) {
        this.mStickerCategoryLists = arrayList;
    }
}
