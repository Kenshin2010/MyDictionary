package vn.manroid.utils;

import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;

import java.util.ArrayList;

import vn.manroid.activity.DictionaryActivity;
import vn.manroid.model.Dictionary;
import yami.blackcode.database.Sqlite;

/**
 * Created by Manroid on 03/04/2017.
 */

public class RuntimeWord extends AsyncTask<String,Void,ArrayList<Dictionary>> {

    private Cursor cursor;

    @Override
    protected ArrayList<Dictionary> doInBackground(String... params) {

        return listWord(params[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Dictionary> dictionaries) {
        super.onPostExecute(dictionaries);
        DictionaryActivity.adapter.notifyDataSetChanged();
        DictionaryActivity.progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        DictionaryActivity.progressBar.setVisibility(View.VISIBLE);

    }

    public ArrayList<Dictionary> listWord(String s){
        if (s.equals("")){
            DictionaryActivity.arrDictionary.clear();
            cursor = Sqlite.allWord(DictionaryActivity.activity);
            cursor.moveToFirst();
            while (cursor.moveToNext()){
                DictionaryActivity.arrDictionary.add(new Dictionary(cursor.getString(1)));
                if (DictionaryActivity.arrDictionary.size()==20){
                    break;
                }
            }
            cursor.close();
        }else {
            DictionaryActivity.arrDictionary.clear();
            cursor = Sqlite.searchWord(DictionaryActivity.activity,s);
            while (cursor.moveToNext()){
                DictionaryActivity.arrDictionary.add(new Dictionary(cursor.getString(1)));
                if (DictionaryActivity.arrDictionary.size() == 40){
                    break;
                }
            }
            cursor.close();
        }
        Sqlite.closeDatabase();
        return DictionaryActivity.arrDictionary;
    }
}
