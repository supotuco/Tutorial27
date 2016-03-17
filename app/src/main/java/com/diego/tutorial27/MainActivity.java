package com.diego.tutorial27;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    ArrayList<String> android_versions;
    ArrayAdapter<String> adapter;
    ListView listView;
    ArrayList<String> selection = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view_android_versions);
        android_versions = new ArrayList<>( Arrays.asList(getResources().getStringArray(R.array.android_versions)));
        adapter = new ArrayAdapter<String>(this, R.layout.layout_row, R.id.row_text_view_item , android_versions);
        listView.setAdapter(adapter);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                if(checked){
                    selection.add(android_versions.get(position));
                }else{
                    selection.remove(android_versions.get(position));
                }
                mode.setTitle(selection.size() + " selected");
            }

            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                MenuInflater menuInflater = getMenuInflater();
                menuInflater.inflate(R.menu.my_menu, menu);

                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {

                switch (item.getItemId()){
                    case R.id.menu_item_delete:
                        for(String element: selection){
                            android_versions.remove(element);
                        }
                        adapter.notifyDataSetChanged();
                        mode.finish();
                        return true;
                    case R.id.menu_item_share:

                        mode.finish();
                        return true;
                }
                return false;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                selection.clear();

            }
        });

    }
}
