package com.softstarcompanyltd.starwars.repository.DB;


public final class FeedReaderContract {
    public static class FeedEntry {

        public static final String TABLE_NAME = "Entity";

        public static final String ID_NAME = "Name";
        public static final String DATA_OF_ENTITY = "Description";

        public static String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                        FeedEntry.ID_NAME + " TEXT UNIQUE," +
                        FeedEntry.DATA_OF_ENTITY + " TEXT)";

        public static String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;
    }
}