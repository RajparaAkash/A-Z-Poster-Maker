package com.letsmake.atoz.design.apiclass;

import com.letsmake.atoz.design.poster_builder.BG_Poster;
import com.letsmake.atoz.design.poster_builder.Key_Poster;
import com.letsmake.atoz.design.poster_builder.List_Poster_Category;
import com.letsmake.atoz.design.poster_builder.Poster_Datas;
import com.letsmake.atoz.design.poster_builder.Poster_Thumbnail;
import com.letsmake.atoz.design.poster_builder.Poster_With_List;
import com.letsmake.atoz.design.poster_builder.Thumb_BG;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {
    @FormUrlEncoded
    @POST("poster/background")
    Call<BG_Poster> getBackground(@Field("device") int i);

    @FormUrlEncoded
    @POST("poster/backgroundlatest")
    Call<Thumb_BG> getBackgrounds(@Field("device") int i);

    @FormUrlEncoded
    @POST("poster/category")
    Call<List_Poster_Category> getPosterCatList(@Field("key") String str, @Field("device") int i);

    @FormUrlEncoded
    @POST("poster/swiperCat")
    Call<Poster_With_List> getPosterCatListFull(@Field("key") String str, @Field("device") int i, @Field("cat_id") int i2, @Field("ratio") String str2);

    @FormUrlEncoded
    @POST("poster/poster")
    Call<Poster_Datas> getPosterDetails(@Field("key") String str, @Field("device") int i, @Field("cat_id") int i2, @Field("post_id") int i3);

    @FormUrlEncoded
    @POST("apps_key")
    Call<Key_Poster> getPosterKey(@Field("device") int i);

    @FormUrlEncoded
    @POST("poster/poster")
    Call<Poster_Thumbnail> getPosterThumbList(@Field("key") String str, @Field("device") int i, @Field("cat_id") int i2);

    @FormUrlEncoded
    @POST("poster/stickerlatest")
    Call<Thumb_BG> getSticker(@Field("device") int i);
}
