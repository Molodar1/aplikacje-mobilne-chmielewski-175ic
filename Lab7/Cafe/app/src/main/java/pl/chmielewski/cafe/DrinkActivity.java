package pl.chmielewski.cafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {
public static final String EXTRA_DRINKID="drinkId";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);
        int drinkId=(Integer)getIntent().getExtras().get(EXTRA_DRINKID);
        try {
            SQLiteOpenHelper cafeDatabaseHelper = new CafeDatabaseHelper(this);
            SQLiteDatabase db = cafeDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("DRINK",
                    new String[]{"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID","FAVOURITE"},
                    "_id=?",
                    new String[]{Integer.toString(drinkId)},
                    null, null, null);
            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavourite = (cursor.getInt(3)==1);

                TextView name = (TextView) findViewById(R.id.drinkName);
                name.setText(nameText);

                TextView description = findViewById(R.id.drinkDescription);
                description.setText(descriptionText);

                ImageView photo = (ImageView) findViewById(R.id.drinkPhoto);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
                CheckBox favourite=(CheckBox)findViewById(R.id.favourite);
                favourite.setChecked(isFavourite);
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

    public void onFavouriteClicked(View view) {
        int drinkId=(Integer)getIntent().getExtras().get(EXTRA_DRINKID);
        new UpdateDrinkTask().execute(drinkId);

//        CheckBox favourite = findViewById(R.id.favourite);
//    ContentValues drinkValues=new ContentValues();
//        drinkValues.put("FAVOURITE",favourite.isChecked());
//
//    SQLiteOpenHelper cafeDatabaseHelper=new CafeDatabaseHelper(DrinkActivity.this);
//        try {
//        SQLiteDatabase db=cafeDatabaseHelper.getWritableDatabase();
//        db.update("DRINK",drinkValues,"_id = ?",new String[]{Integer.toString(drinkId)});
//        db.close();
//    }catch (SQLiteException e){
//        Toast toast=Toast.makeText(this,"Baza jest niedostepna",Toast.LENGTH_SHORT);
//        toast.show();
//    }
        new UpdateDrinkTask().execute(drinkId);
}
    private class UpdateDrinkTask extends AsyncTask<Integer,Void, Boolean> {
        ContentValues drinkValues;

        @Override
        protected void onPreExecute() {
            CheckBox favourite=(CheckBox)findViewById(R.id.favourite);
            drinkValues=new ContentValues();
            drinkValues.put("FAVOURITE",favourite.isChecked());
        }

        @Override
        protected Boolean doInBackground(Integer... drinks) {
            int drinkId=drinks[0];
            SQLiteOpenHelper cafeDatabaseHelper=new CafeDatabaseHelper(DrinkActivity.this);
            try{
                SQLiteDatabase db=cafeDatabaseHelper.getWritableDatabase();
                db.update("DRINK",drinkValues,"_id = ?",new String[]{Integer.toString(drinkId)});
                db.close();
                return true;
            }catch (SQLiteException e){
                return false;
            }

        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (!success){
                Toast toast=Toast.makeText(DrinkActivity.this,"Baza danych jest niedostepna",Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }
}
