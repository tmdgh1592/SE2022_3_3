import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.soft.hanger.R;

public class Main extends AppCompatActivity {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();

    Button btn;
    EditText edit1, edit2, edit3, edit4, edit5;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.);
        //내용 입력
        //btn = findViewById();
        //edit1 = findViewById(); 이름

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addData(edit1.getText().toString(),);
            }
        });
    }

    public void addData(String name, String size, String url, String memo, ){

        Clothdata data = new Clothdata(name, size, url, memo, );
        return databaseReference.child("Clothdata").child(name).setValue(data);
    }
}
