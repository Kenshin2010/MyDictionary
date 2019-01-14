package vn.manroid.activity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

import vn.manroid.mydictionary.R;

public class WordActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtHead, txtBody;
    private TextToSpeech speechUS,speechUK;
    private ImageView btnSpeakUK, btnSpeakUS;
    private String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_word);

        getSupportActionBar().setTitle("Nghĩa của từ");
        //Mở nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtHead = (TextView) findViewById(R.id.txt_head);
        txtBody = (TextView) findViewById(R.id.txt_body);
        btnSpeakUK = (ImageView) findViewById(R.id.btn_uk);
        btnSpeakUS = (ImageView) findViewById(R.id.btn_us);

        Intent intent = getIntent();

        str = intent.getStringExtra("head");

        txtHead.setText(intent.getStringExtra("head"));
        txtBody.setText(intent.getStringExtra("body"));

        btnSpeakUS.setOnClickListener(this);
        btnSpeakUK.setOnClickListener(this);


        speechUS = new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = speechUS.setLanguage(Locale.US);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(WordActivity.this, "Ngôn ngữ không được hỗ trợ !!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        speechUK = new TextToSpeech(getBaseContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = speechUK.setLanguage(Locale.UK);
                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Toast.makeText(WordActivity.this, "Ngôn ngữ không được hỗ trợ !!!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            //    finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_uk:
                speechUK.speak(str,TextToSpeech.QUEUE_FLUSH,null);
                break;
            case R.id.btn_us:
                speechUS.speak(str,TextToSpeech.QUEUE_FLUSH,null);
                break;
        }
    }
}
