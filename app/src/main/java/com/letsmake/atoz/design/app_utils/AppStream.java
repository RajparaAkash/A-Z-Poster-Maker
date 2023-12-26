package com.letsmake.atoz.design.app_utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class AppStream {
    public static final int IO_BUFFER_SIZE = 8192;

    public static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[8192];
        int read = inputStream.read(bArr);
        while (read != -1) {
            outputStream.write(bArr, 0, read);
            read = inputStream.read(bArr);
        }
    }
}
