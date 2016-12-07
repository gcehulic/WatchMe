package hr.foi.air602.watchme.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import hr.foi.air602.watchme.database.entities.User;

/**
 * Created by Mateo on 7.12.2016..
 */

public class UserAdapter extends DataAdapter{

    private static final String TABLE = "User";
    public static final String KEY_ID = "id";

    public UserAdapter(Context context) {
        super(context);
    }

    // dodavanje novog korisnika
    public long insertUser(User user){
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", user.name);
        contentValues.put("surname", user.surname);
        contentValues.put("mail", user.mail);
        contentValues.put("username", user.username);
        contentValues.put("password", user.password);

        SQLiteDatabase db = openToWrite();

        return db.insert(TABLE, null, contentValues);
        }

    // pregled svih korisnika return null;
    public List<User> getAllUsers(){
        List<User> result = new ArrayList<User>();

        String[] columns = new String[]{KEY_ID, "name", "surname", "mail", "username", "password"};
        SQLiteDatabase db = openToRead();

        Cursor cursor = db.query(TABLE, columns, null, null, null, null, null);

        for(cursor.moveToFirst(); !(cursor.isAfterLast()); cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String surname = cursor.getString(cursor.getColumnIndex("surname"));
            String mail = cursor.getString(cursor.getColumnIndex("mail"));
            String username = cursor.getString(cursor.getColumnIndex("username"));
            String password = cursor.getString(cursor.getColumnIndex("password"));
            User user = new User(name, surname, mail, username, password);
            result.add(user);
        }

        return result;

        }

}