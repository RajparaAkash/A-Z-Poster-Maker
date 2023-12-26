package com.letsmake.atoz.design.activity;

import com.bumptech.glide.load.Key;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

public class IntegerVersionSignature implements Key {
    private int currentVersion;

    public IntegerVersionSignature(int i) {
        this.currentVersion = i;
    }

    public boolean equals(Object obj) {
        boolean z = false;
        if (!(obj instanceof IntegerVersionSignature)) {
            return false;
        }
        if (this.currentVersion == ((IntegerVersionSignature) obj).currentVersion) {
            z = true;
        }
        return z;
    }

    public int hashCode() {
        return this.currentVersion;
    }

    public void updateDiskCacheKey(MessageDigest messageDigest) {
        messageDigest.update(ByteBuffer.allocate(32).putInt(this.currentVersion).array());
    }
}
