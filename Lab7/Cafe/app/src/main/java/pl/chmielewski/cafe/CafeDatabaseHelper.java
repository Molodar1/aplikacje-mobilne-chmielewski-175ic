package pl.chmielewski.cafe;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CafeDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME="cafe";
    private static final int DB_VERSION=2;
    public CafeDatabaseHelper( Context context) {
     super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    updateMyDatabase(db,0,DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    updateMyDatabase(db,oldVersion,newVersion);
    }
    private static void insertDrink(SQLiteDatabase db, String name, String description, int resourceId){
        ContentValues drinkValues=new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK",null,drinkValues);
    }
    private static void insertCake(SQLiteDatabase db, String name, String description, int resourceId){
        ContentValues cakeValues=new ContentValues();
        cakeValues.put("NAME", name);
        cakeValues.put("DESCRIPTION", description);
        cakeValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("CAKE",null,cakeValues);
    }
    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion){
        if(oldVersion<1){
            db.execSQL("CREATE TABLE DRINK(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"NAME TEXT, "
                    +"DESCRIPTION TEXT, "
                    +"IMAGE_RESOURCE_ID INTEGER);");
            insertDrink(db,"Latte","Czarne espresso z goracym mlekiem i mleczną pianką.",R.drawable.latte);
            insertDrink(db,"Cappuccino","Czarne espresso z dużą ilością spienionego mleka.",R.drawable.cappuccino);
            insertDrink(db,"Espresso","Czarna kawa ze świeżo mielonych ziaren najwyższej jakości..",R.drawable.espresso);
            db.execSQL("CREATE TABLE CAKE(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    +"NAME TEXT, "
                    +"DESCRIPTION TEXT, "
                    +"IMAGE_RESOURCE_ID INTEGER);");
            insertCake(db,"Szarlotka","Pyszna gorąca szarlotka.",R.drawable.szarlotka);
            insertCake(db,"Sernik","Pyszny chłodny sernik.",R.drawable.sernik);
            insertCake(db,"Makowiec","Pyszny makowiec.",R.drawable.makowiec);
        }
        if(oldVersion<2){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVOURITE NUMERIC;");


            db.execSQL("ALTER TABLE CAKE ADD COLUMN FAVOURITE NUMERIC;");
        }
    }
}
