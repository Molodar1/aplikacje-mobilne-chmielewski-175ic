package pl.chmielewski.cafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CakeActivity extends AppCompatActivity {
    public static final String EXTRA_CAKEID="cakeId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake);
        int cakeId=(Integer)getIntent().getExtras().get(EXTRA_CAKEID);
        try {
            SQLiteOpenHelper cafeDatabaseHelper = new CafeDatabaseHelper(this);
            SQLiteDatabase db = cafeDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("CAKE",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID"},
                    "_id=?",
                    new String[]{Integer.toString(cakeId)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                TextView name = (TextView) findViewById(R.id.cakeName);
                name.setText(nameText);

                TextView description = findViewById(R.id.cakeDescription);
                description.setText(descriptionText);

                ImageView photo = (ImageView) findViewById(R.id.cakePhoto);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e){
            Toast toast=Toast.makeText(this,"Baza danych jest niedostępna",Toast.LENGTH_SHORT);
            toast.show();
        }

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
}