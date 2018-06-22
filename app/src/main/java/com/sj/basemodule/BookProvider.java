package com.sj.basemodule;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.LoginFilter;
import android.util.Log;

import org.litepal.LitePal;
import org.litepal.LitePalDB;

import java.security.Provider;

/**
 * Created by 13658 on 2018/6/22.
 */

public class BookProvider extends ContentProvider {
    public static final int BOOK_URI_CODE = 0;
    public static final int USER_URI_CODE = 1;
    private static final String TAG = "BookProvider";
    private static final String AUTHPRITY = "com.sj.basemodule.provider";
    public static final Uri BOOK_CONTENT_URI = Uri.parse("content://" + AUTHPRITY + "/book");
    public static final Uri USER_CONTENT_URI = Uri.parse("content://" + AUTHPRITY + "/user");
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(AUTHPRITY, "book", BOOK_URI_CODE);
        sUriMatcher.addURI(AUTHPRITY, "user", USER_URI_CODE);
    }

    @Override
    public boolean onCreate() {
        Log.i(TAG, "onCreate,current thread: " + Thread.currentThread().getName());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Log.i(TAG, "query,current thread: " + Thread.currentThread().getName());
        String table = getTableName(uri);
        if (table == null) {
            throw new IllegalArgumentException("Unsupported Uri:" + uri);
        }

        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Log.i(TAG, "getType: ");
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        Log.i(TAG, "insert: ");
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(TAG, "delete: ");
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        Log.i(TAG, "update: ");
        return 0;
    }

    private String getTableName(Uri uri) {
        String tableName = null;
        switch (sUriMatcher.match(uri)) {
            case BOOK_URI_CODE:
                tableName = "book";
                break;
            case USER_URI_CODE:
                tableName = "user";
                break;
        }
        return tableName;
    }
}
