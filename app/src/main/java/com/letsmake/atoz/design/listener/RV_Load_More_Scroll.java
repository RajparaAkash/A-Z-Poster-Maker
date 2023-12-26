package com.letsmake.atoz.design.listener;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;
import androidx.recyclerview.widget.RecyclerView.OnScrollListener;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class RV_Load_More_Scroll extends OnScrollListener {

    private LayoutManager mLayoutManager;
    private On_Load_More_Listener mOnLoadMoreListener;
    private int totalItemCount, lastVisibleItem, visibleThreshold = 5;
    private boolean isLoading;

    public RV_Load_More_Scroll(LinearLayoutManager linearLayoutManager) {
        this.mLayoutManager = linearLayoutManager;
    }

    public RV_Load_More_Scroll(GridLayoutManager gridLayoutManager) {
        this.mLayoutManager = gridLayoutManager;
        this.visibleThreshold *= gridLayoutManager.getSpanCount();
    }

    public RV_Load_More_Scroll(StaggeredGridLayoutManager staggeredGridLayoutManager) {
        this.mLayoutManager = staggeredGridLayoutManager;
        this.visibleThreshold *= staggeredGridLayoutManager.getSpanCount();
    }

    public void set_Data_Loaded() {
        this.isLoading = false;
    }

    public int findVisibleLastItem(int[] iArr) {
        int i = 0;
        for (int i2 = 0; i2 < iArr.length; i2++) {
            if (i2 == 0) {
                i = iArr[i2];
            } else if (iArr[i2] > i) {
                i = iArr[i2];
            }
        }
        return i;
    }

    public boolean get_Data_Loaded() {
        return this.isLoading;
    }

    public void set_Data_LoadMore_Listener(On_Load_More_Listener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int i, int i2) {
        super.onScrolled(recyclerView, i, i2);
        if (i2 > 0) {
            this.totalItemCount = this.mLayoutManager.getItemCount();
            LayoutManager layoutManager = this.mLayoutManager;
            if (layoutManager instanceof StaggeredGridLayoutManager) {
                this.lastVisibleItem = findVisibleLastItem(((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(null));
            } else if (layoutManager instanceof GridLayoutManager) {
                this.lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof LinearLayoutManager) {
                this.lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }
            if (!this.isLoading && this.totalItemCount <= this.lastVisibleItem + this.visibleThreshold) {
                On_Load_More_Listener onLoadMoreListener = this.mOnLoadMoreListener;
                if (onLoadMoreListener != null) {
                    onLoadMoreListener.onLoadMore();
                }
                this.isLoading = true;
            }
        }
    }

}
