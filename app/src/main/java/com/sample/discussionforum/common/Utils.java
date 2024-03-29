package com.sample.discussionforum.common;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import androidx.annotation.Nullable;

public class Utils {

    private static final String TAG = "Utils";

    /**
     * This method will read a given file from Raw folder.
     */
    @Nullable
    public static String readRawFile(Context context, int resourceId) {
        InputStream inputStream = context.getResources().openRawResource(resourceId);

        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;

        try {
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            inputStream.close();
            return result.toString();
        } catch (IOException e) {
            Log.e(TAG, "Error while reading resource " + context.getResources().getResourceName(resourceId), e);
            return null;
        }
    }
}
