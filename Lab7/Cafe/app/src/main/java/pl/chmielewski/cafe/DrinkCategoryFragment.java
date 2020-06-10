package pl.chmielewski.cafe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class DrinkCategoryFragment extends ListFragment {




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String[] names=new String[Drink.drinks.length];
        for(int i=0;i<names.length;i++){
            names[i]=Drink.drinks[i].getName();

        }
        ArrayAdapter<String> adapter=new ArrayAdapter<>(
                inflater.getContext(),android.R.layout.simple_list_item_1,names);
        setListAdapter(adapter);
        return super.onCreateView(inflater,container,savedInstanceState);
    }
}
