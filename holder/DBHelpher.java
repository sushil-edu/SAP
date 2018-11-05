package in.kestone.sap.holder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBHelpher extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "ScanReport.db";
    public static final String TABLE_NAME = "tblScan";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_USER_ID = "uID";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_SCAN_ON = "scanOn";
    public static final String COLUMN_UPLOAD = "UPLOADED";

    public DBHelpher(Context context, String DATABASE_NAME) {
        super( context, DATABASE_NAME, null, 1 );
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_ID + " TEXT," + COLUMN_CODE + " TEXT,"
                + COLUMN_SCAN_ON + " TEXT, " + COLUMN_UPLOAD + " INT )";

        db.execSQL( CREATE_TABLE );
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );

        // Create tables again
        onCreate( db );
    }

    // code to add the new contact
    public void addInfo(Scan scan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put( COLUMN_USER_ID, scan.getRefLoginID() );
        values.put( COLUMN_CODE, scan.getScanCode() );
        values.put( COLUMN_SCAN_ON, scan.getScanON() );
        values.put( COLUMN_UPLOAD, 0 );
        db.insert( TABLE_NAME, null, values );

        db.close(); // Closing database connection
    }

    //    //get detail by id
    public void updateUploadFlag(String[] code) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_UPLOAD + " = 1"
                + " WHERE " + COLUMN_CODE + " IN (" + makePlaceholders( code.length ) + ")";

        Cursor cursor = db.rawQuery( query, code );
        cursor.moveToNext();

    }

    //Delete all data from database
    public void deleteAll(String TABLE_NAME) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL( "delete from " + TABLE_NAME );
        db.close();
    }

    public List<Scan> getAllInfo() {
        List<Scan> list = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_NAME + " ORDER BY " + COLUMN_SCAN_ON + " DESC ";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery( selectQuery, null );

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                // Adding to list
                list.add( new Scan( cursor.getString( 1 ), cursor.getString( 2 ), cursor.getString( 3 ), cursor.getInt( 4 ) ) );
            } while (cursor.moveToNext());
        }

        // return contact list
        return list;
    }

    String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException( "No placeholders" );
        } else {
            StringBuilder sb = new StringBuilder( len * 2 - 1 );
            sb.append( "?" );
            for (int i = 1; i < len; i++) {
                sb.append( ",?" );
            }
            return sb.toString();
        }
    }
}