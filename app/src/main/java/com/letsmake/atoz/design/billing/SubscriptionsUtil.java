package com.letsmake.atoz.design.billing;

import com.android.billingclient.api.Purchase;
import com.letsmake.atoz.design.utils.LogUtil;

import java.util.List;

public class SubscriptionsUtil {

    private static final String TAG = "SubscriptionsUtil";

    public static boolean isSubscriptionActive(List<Purchase> purchaseList) {
        boolean isActive = false;
        if (purchaseList != null && purchaseList.size() > 0) {
            for (Purchase purchase : purchaseList) {
                if (purchase.getSkus().contains(BillingConstants.SKU_ONE_MONTHLY)) {
                    isActive = purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && purchase.isAcknowledged();
                    LogUtil.logDebug(TAG, "One_Month_Subscription Active: " + isActive);
                } else if (purchase.getSkus().contains(BillingConstants.SKU_SIX_MONTHLY)) {
                    LogUtil.logDebug(TAG, "Six_Months_Subscription Active: " + isActive);
                    isActive = purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && purchase.isAcknowledged();
                } else if (purchase.getSkus().contains(BillingConstants.SKU_YEARLY)) {
                    LogUtil.logDebug(TAG, "One_Year_Subscription Active: " + isActive);
                    isActive = purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && purchase.isAcknowledged();
                }
            }
        }
        return isActive;
    }
}
