package br.ufc.dc.sd4mp.todo.mytodolist;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by Caio on 11/05/2015.
 */
public class TasksProvider extends ContentProvider {

    static final String AUTHORITY_NAME = "br.ufc.dc.sd4mp.provider.Task";
    static final String URL = "content://" + AUTHORITY_NAME + "/tasks";
    static final Uri CONTENT_URI = Uri.parse(URL);

    static final String ID = "id";
    static final String TITLE = "title";
    static final String DESCRIPTION = "description";
    static final String STATUS = "status";
    static final String DATE = "date";

    private static HashMap<String, String> TASKS_PROJECTION_MAP;


    static final int TASKS = 1;
    static final int TASK_ID = 2;
    static final int TASK_TODO = 3;
    static final int TASK_DONE = 4;

    static final UriMatcher uriMatcher;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY_NAME, "tasks", TASKS);
        uriMatcher.addURI(AUTHORITY_NAME, "tasks/#", TASK_ID);
        uriMatcher.addURI(AUTHORITY_NAME, "tasks/status/todo", TASK_TODO);
        uriMatcher.addURI(AUTHORITY_NAME, "tasks/status/done", TASK_DONE);
    }

    private SQLiteDatabase database;
    static final String DATABASE_NAME = "MyTodoList.db";
    static final int DATABASE_VERSION = 5;
    static final String TASKS_TABLE_NAME = "tasks";
    static final String CREATE_DB_TABLE = "create table " + TASKS_TABLE_NAME +
            " (id integer primary key autoincrement," +
            " title text, description text, status text, date text)";

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper helper = new DatabaseHelper(context);
        database = helper.getWritableDatabase();

        return (database == null) ? false : true;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case TASKS:
                return "vnd.android.cursor.dir/vnd.br.ufc.dc.sd4mp.todo.mytodolist.tasks";
            case TASK_ID:
                return "vnd.android.cursor.item/vnd.br.ufc.dc.sd4mp.todo.mytodolist.tasks";
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(TASKS_TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case TASKS:
                queryBuilder.setProjectionMap(TASKS_PROJECTION_MAP);
                break;
            case TASK_ID:
                queryBuilder.appendWhere(ID + "=" + uri.getPathSegments().get(1));
                break;
            case TASK_TODO:
                queryBuilder.appendWhere(STATUS + "='false'");
                break;
            case TASK_DONE:
                queryBuilder.appendWhere(STATUS + "='true'");
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        if (sortOrder == null || sortOrder.isEmpty()) {
            sortOrder = ID;
        }

        Cursor cursor = queryBuilder.query(database, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowID = database.insert(TASKS_TABLE_NAME, "", values);

        if (rowID > 0) {
            Uri uriAux = ContentUris.withAppendedId(CONTENT_URI, rowID);
            getContext().getContentResolver().notifyChange(uriAux, null);
            return uriAux;
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case TASKS:
                count = database.delete(TASKS_TABLE_NAME, selection, selectionArgs);
                break;
            case TASK_ID:
                String id = uri.getPathSegments().get(1);
                count = database.delete(TASKS_TABLE_NAME, ID + " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)) {
            case TASKS:
                count = database.update(TASKS_TABLE_NAME, values,
                        selection, selectionArgs);
                break;
            case TASK_ID:
                count = database.update(TASKS_TABLE_NAME, values, ID +
                        " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +
                                selection + ')' : ""), selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase database) {
            database.execSQL(CREATE_DB_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
            database.execSQL("drop table if exists " + TASKS_TABLE_NAME);
            onCreate(database);
        }
    }
}
