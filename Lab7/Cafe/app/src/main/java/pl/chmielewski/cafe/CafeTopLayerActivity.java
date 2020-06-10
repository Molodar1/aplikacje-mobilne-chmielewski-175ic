package pl.chmielewski.cafe;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;





public class CafeTopLayerActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    private Cursor favouritesCursor;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menumain,menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_info:
            Intent intent=new Intent(this,InfoActivity.class);
            startActivity(intent);
            return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupOptionsListView();
        setupFavouritesListView();
        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }



    private void setupOptionsListView(){
        AdapterView.OnItemClickListener itemClickListener=new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View itemView, int position, long id) {
                if(position==0)
                {
                    Intent intent=new Intent(CafeTopLayerActivity.this,DrinkCategoryActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Intent intent=new Intent(CafeTopLayerActivity.this,CakeCategoryActivity.class);
                    startActivity(intent);
                }
            }
        };
        ListView listView=findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);
    }

    private void setupFavouritesListView() {
        ListView listFavourites=findViewById(R.id.list_favourites);
        try {
            SQLiteOpenHelper cafeDatabaseHelper=new CafeDatabaseHelper(this);
            db=cafeDatabaseHelper.getReadableDatabase();
            favouritesCursor=db.query("DRINK",new String[]{"_id","NAME"},"FAVOURITE=1",null,null,null,null);
            CursorAdapter favouriteAdapter=new SimpleCursorAdapter(CafeTopLayerActivity.this,
                    android.R.layout.simple_list_item_1, favouritesCursor,
                    new String[]{"NAME"},
                    new int[]{android.R.id.text1, 0}) {
            };
            listFavourites.setAdapter(favouriteAdapter);
        }catch (SQLiteException e){
            Toast toast=Toast.makeText(this,"Baza danych jest niedostępna",Toast.LENGTH_SHORT);
            toast.show();
        }
        listFavourites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(CafeTopLayerActivity.this,DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKID,(int) id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Cursor newCursor=db.query("DRINK",new String[]{"_id","NAME"},"FAVOURITE=1",null,null,null,null);
        ListView listFavourites=findViewById(R.id.list_favourites);
        CursorAdapter adapter=(CursorAdapter)listFavourites.getAdapter();
        adapter.changeCursor(newCursor);
        favouritesCursor=newCursor;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favouritesCursor.close();
        db.close();
    }
}
