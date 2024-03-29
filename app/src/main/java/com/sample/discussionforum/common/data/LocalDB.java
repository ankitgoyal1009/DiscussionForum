package com.sample.discussionforum.common.data;

import android.content.Context;

import com.sample.discussionforum.comments.data.Comment;
import com.sample.discussionforum.comments.data.CommentsDao;
import com.sample.discussionforum.likes.data.Like;
import com.sample.discussionforum.discussions.data.Discussion;
import com.sample.discussionforum.discussions.data.DiscussionsDao;
import com.sample.discussionforum.likes.data.LikesDao;
import com.sample.discussionforum.login.data.User;
import com.sample.discussionforum.login.data.UserConvertor;
import com.sample.discussionforum.login.data.UserDao;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, Discussion.class, Comment.class, Like.class}, version = 4, exportSchema = false)
@TypeConverters({UserConvertor.class})
public abstract class LocalDB extends RoomDatabase {
    private static final Object sLock = new Object();
    private static LocalDB INSTANCE;

    /**
     * get new INSTANCE instance
     *
     * @param context
     * @return
     */
    public static LocalDB getInstance(final Context context) {

        synchronized (sLock) {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LocalDB.class, "localDB.db")
                        .fallbackToDestructiveMigration()
                        .build();
            }
            return INSTANCE;
        }
    }

    public abstract UserDao getUserDao();

    public abstract DiscussionsDao getDiscussionsDao();

    public abstract CommentsDao getCommentsDao();
    public abstract LikesDao getLikesDao();

}
