package snowsan0113.calculator_android_app;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText edit_text = findViewById(R.id.editTextNumber);

        for (MathButton math_button : MathButton.values()) {
            int id = math_button.getButtonID();
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    edit_text.setText(edit_text.getText().toString() + math_button.getSendText());
                }
            });
        }
    }

    public enum MathButton {
        ZERO(R.id.zero_button,"0"),
        ONE(R.id.one_button,"1"),
        TWO(R.id.two_button,"2"),
        THREE(R.id.there_button,"3"),
        FOUR(R.id.four_button,"4"),
        FIVE(R.id.five_button,"5"),
        SIX(R.id.six_button,"6"),
        SEVEN(R.id.seven_button,"7"),
        EIGHT(R.id.eight_button,"8"),
        NINE(R.id.nine_button, "9"),

        PLUS(R.id.plus_button, "+"),
        MINE(R.id.minus_button, "-"),
        MULTIPLICATION(R.id.multiplication_button, "×"),
        DIVISION(R.id.division_button, "÷"),

        DECIMAL(R.id.decimal_button, "."),
        PERCENT(R.id.percent_button, "％"),
        ROOT(R.id.root_button, "√");


        private final int button_id;
        private final String send_text;

        MathButton(int button_id, String send_text) {
            this.button_id = button_id;
            this.send_text = send_text;

        }

        public String getSendText() {
            return send_text;
        }

        public int getButtonID() {
            return button_id;
        }
    }
}