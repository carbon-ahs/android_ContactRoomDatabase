package com.axiagroups.contactroom.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.axiagroups.contactroom.data.ContactDao;
import com.axiagroups.contactroom.model.Contact;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Contact.class}, version = 1, exportSchema = false)
public abstract class ContactRoomDatabase extends RoomDatabase {
    public static final int NUMBER_OF_THREADS = 4;
    public static final String DB_NAME = "contact_database";
    public abstract ContactDao contactDao();
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile ContactRoomDatabase INSTANCE;

    public static final RoomDatabase.Callback sRoomdatabaseCallback
            = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriteExecutor.execute(() -> {
                ContactDao contactDao = INSTANCE.contactDao();
                contactDao.deleteAll();
                Contact contact= new Contact("Shehanuk", "Kamla");
                contactDao.insert(contact);
                contact = new Contact("Sorowar", "Comp Engg.");
                contactDao.insert(contact);
                contact = new Contact("Masud Rana", "Spy");
                contactDao.insert(contact);
            });
        }
    };
    public static ContactRoomDatabase getDatabase(final Context context){
        if (INSTANCE == null) {
            synchronized (ContactRoomDatabase.class){
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ContactRoomDatabase.class, DB_NAME)
                            .addCallback(sRoomdatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }




}
