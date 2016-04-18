package br.com.gustavofao.jsonapisample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.gustavofao.jsonapi.JSONApiConverter;
import com.gustavofao.jsonapi.Models.JSONApiObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import br.com.gustavofao.jsonapisample.V2.City;
import br.com.gustavofao.jsonapisample.V2.Contact;
import br.com.gustavofao.jsonapisample.V2.Conversation;
import br.com.gustavofao.jsonapisample.V2.Financial;
import br.com.gustavofao.jsonapisample.V2.FinancialResume;
import br.com.gustavofao.jsonapisample.V2.Message;
import br.com.gustavofao.jsonapisample.V2.User;

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JSONApiConverter api = new JSONApiConverter(FinancialResume.class, Contact.class, User.class, Message.class, Conversation.class, City.class, Financial.class);

        InputStream is = getResources().openRawResource(R.raw.data);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
            is.close();
        } catch (Exception ex) {}

        String json = writer.toString();
        JSONApiObject obj = api.fromJson(json);
        Toast.makeText(MainActivity.this, String.format("Total of %d messages", obj.getData().size()), Toast.LENGTH_SHORT).show();
        Conversation con = ((Conversation) obj.getData(0));
        Log.d("BackToJSON", api.toJson(con));
    }

}
