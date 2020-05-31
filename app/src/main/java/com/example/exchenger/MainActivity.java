package com.example.exchenger;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import static com.example.exchenger.utils.NetvorkUtils.genetayedURL;
import static com.example.exchenger.utils.NetvorkUtils.getResponseFromURL;

public class MainActivity extends AppCompatActivity {

    private EditText among;
    private Button perevesti;
    private TextView result;
    private Spinner fromvalue;
    private Spinner tovalue;


    class Zapros extends AsyncTask<URL,Void, String>{
        @Override
        protected void onPreExecute(){
            ///indikator.setVisibility(View.VISIBLE);
        }


        @Override
        protected String doInBackground(URL... urls) {
            String response = null;
            try {
                response= getResponseFromURL(urls[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }
        @Override
        protected void onPostExecute(String response) {
            if (response != null && !response.equals("")){
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONObject jsonRates = jsonResponse.getJSONObject("rates");
                    double eroGet;
                    double perevedeno;
                    if (fromvalue.getSelectedItemPosition() == 3) {
                        eroGet = Double.parseDouble(among.getText().toString());

                    } else {
                        eroGet = Double.parseDouble(among.getText().toString()) / jsonRates.getDouble(socrValuts[fromvalue.getSelectedItemPosition()]);
                    }
                    if (tovalue.getSelectedItemPosition() == 3) {
                        perevedeno = eroGet;
                    } else {
                        perevedeno = eroGet * jsonRates.getDouble(socrValuts[tovalue.getSelectedItemPosition()]);
                    }
                    result.setText(String.format("%.2f", perevedeno));

                } catch (JSONException e) {
                    e.printStackTrace();
                    result.setText("Произошла ошибка, попробуйте еще раз");
                }
        }else {
                result.setText("Произошла ошибка, попробуйте еще раз");
            }


        }


    }

    private final String [] valuts = {"канадский доллар","американский доллар","рубль","евро","Гонконгский доллар","Исландская кронa"," Филиппинское песо","Датская крона","Венгерский форинт","Чешская крона","Австралийский доллар","Румынский лей","Шведская крона","Индонезийская рупия","Индийская рупия","Бразильский реал","Хорватская куна","Японская иена","Тайский бат","Швейцарский франк","Сингапурский доллар","Польский злотый","Болгарский лев","Турецкая лира","Китайский юань","Норвежская крона","Новозеландский доллар","Южноафриканский рэнд"," Мексиканское песо","Новый израильский шекель","Фунт стерлингов","Южнокорейская вона","Малайзийский ринггит"};
    private String [] socrValuts = {"CAD","USD","RUB","EUR","HKD","ISK","PHP","DKK","HUF","CZK","AUD","RON","SEK","IDR","INR","BRL","HRK","JPY","THB","CHF","SGD","PLN","BGN","TRY","CNY","NOK","NZD","ZAR","MXN","ILS","GBP","KRW","MYR"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        among=findViewById(R.id.et_summa_perevoda);
        perevesti=findViewById(R.id.btn_perevesti);
        result=findViewById(R.id.tv_result);
        fromvalue = findViewById(R.id.sp_kakay_valuta);
        tovalue = findViewById(R.id.sp_vo_chto_perevesti);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valuts);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromvalue.setAdapter(adapter);
        tovalue.setAdapter(adapter);
        perevesti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                URL url = genetayedURL();
                new Zapros().execute(url);
            }
        });

    }


}
