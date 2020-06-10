package pl.chmielewski.cafe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class CakeCategoryActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor cursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake_category);

        ListView listCakes = (ListView) findViewById(R.id.list_cakes);
        SQLiteOpenHelper cafeDatabaseHelper = new CafeDatabaseHelper(this);
        try {
            db = cafeDatabaseHelper.getReadableDatabase();
            cursor = db.query("CAKE", new String[]{"_id", "NAME"}, null, null, null, null, null);
            SimpleCursorAdapter listAdapter = new SimpleCursorAdapter(
                    this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1},
                    0);
            listCakes.setAdapter(listAdapter);
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Baza danych jest niedostępna", Toast.LENGTH_SHORT);
            toast.show();
        }


        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> listCakes,View itemView,int position,long id){
                Intent intent=new Intent(CakeCategoryActivity.this,CakeActivity.class);
                intent.putExtra(CakeActivity.EXTRA_CAKEID,(int) id);
                startActivity(intent);
            }
        };
        listCakes.setOnItemClickListener(itemClickListener);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
