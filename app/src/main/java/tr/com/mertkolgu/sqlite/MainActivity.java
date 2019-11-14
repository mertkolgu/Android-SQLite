package tr.com.mertkolgu.sqlite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // veritabanı işlemleri try-catch bloğunun içinde yapılır.
        try {
            // veritabanı mevcutsa açar, mevcut değilse oluşturur
            SQLiteDatabase database = this.openOrCreateDatabase("Musicians", MODE_PRIVATE, null);

            // tablo oluşturma
            database.execSQL("CREATE TABLE IF NOT EXISTS musicians (id INTEGER PRIMARY KEY, name VARCHAR, age INTEGER)");

            // tabloya veri ekleme
            database.execSQL("INSERT INTO musicians (name, age) VALUES ('James', 54)");
            database.execSQL("INSERT INTO musicians (name, age) VALUES ('Kirk', 45)");
            database.execSQL("INSERT INTO musicians (name, age) VALUES ('Lars', 67)");

            // güncelleme
            database.execSQL("UPDATE musicians SET age = 61 WHERE name = 'Lars'");
            database.execSQL("UPDATE musicians SET name = 'Kirk Hammet' WHERE id = 2");

            // silme
            database.execSQL("DELETE FROM musicians WHERE id = 2");

            // tabloyu okumak
            Cursor cursor = database.rawQuery("SELECT * FROM musicians", null);

            // filtreleyerek tabloyu okumak
            // Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE age > 52", null);
            // Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE name LIKE '%s'", null);
            // Cursor cursor = database.rawQuery("SELECT * FROM musicians WHERE name LIKE 'J%'", null);

            int idIndex = cursor.getColumnIndex("id");
            int nameIndex = cursor.getColumnIndex("name");
            int ageIndex = cursor.getColumnIndex("age");

            while (cursor.moveToNext()) {
                System.out.println("ID : " + cursor.getInt(idIndex));
                System.out.println("Name : " + cursor.getString(nameIndex));
                System.out.println("Age : " + cursor.getInt(ageIndex));
            }
            cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
