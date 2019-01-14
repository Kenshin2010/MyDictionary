package vn.manroid.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import vn.manroid.model.Dictionary;
import vn.manroid.mydictionary.R;

/**
 * Created by Manroid on 01/04/2017.
 */

public class DictionaryAdapter extends BaseAdapter {
    LayoutInflater inflater;
    ArrayList<Dictionary> arrDictionary;

    public DictionaryAdapter(LayoutInflater inflater, ArrayList<Dictionary> arrDictionary) {
        this.inflater = inflater;
        this.arrDictionary = arrDictionary;
    }

    @Override
    public int getCount() {
        return arrDictionary.size();
    }

    @Override
    public Object getItem(int position) {
        return arrDictionary.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        ViewHolder holder;
        if (v == null){
            v = this.inflater.inflate(R.layout.item_dictionary,null);
            holder = new ViewHolder(v);
            v.setTag(holder);
        }

        holder = (ViewHolder) v.getTag();
        Dictionary word = (Dictionary) getItem(position);
        holder.txtWord.setText(word.getMean());

        return v;
    }

    class ViewHolder{
        private TextView txtWord;
        public ViewHolder(View view){
            txtWord = (TextView) view.findViewById(R.id.txt_list_word);
        }
    }
}

