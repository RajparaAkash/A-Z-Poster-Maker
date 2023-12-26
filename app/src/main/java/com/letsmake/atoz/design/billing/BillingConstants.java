package com.letsmake.atoz.design.billing;

import java.util.Arrays;
import java.util.List;

public final class BillingConstants {

    // SKU for our subscription
    public static final String SKU_ONE_MONTHLY = "poster_1month";
    public static final String SKU_SIX_MONTHLY = "poster_6month";
    public static final String SKU_YEARLY = "poster_yearly";

    private static final String[] SUBSCRIPTIONS_SKUS = {SKU_ONE_MONTHLY,SKU_SIX_MONTHLY, SKU_YEARLY};

    private BillingConstants() {
    }

    public static List<String> getSkuList() {
        return (Arrays.asList(SUBSCRIPTIONS_SKUS));
    }

}
