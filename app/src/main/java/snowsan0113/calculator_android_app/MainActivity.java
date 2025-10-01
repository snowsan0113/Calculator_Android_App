package snowsan0113.calculator_android_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import net.objecthunter.exp4j.ExpressionBuilder;

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
        edit_text.setShowSoftInputOnFocus(false);

        for (MathButton math_button : MathButton.values()) {
            int id = math_button.getButtonID();
            Button button = findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onClick(View view) {
                    edit_text.setText(edit_text.getText().toString() + math_button.getSendText());
                    edit_text.setSelection(edit_text.length());
                }
            });
        }

        Button equal_button = findViewById(R.id.equal_button);
        equal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    double result = new ExpressionBuilder(MathButton.replaceLibraryText(edit_text.getText().toString())).build().evaluate();
                    edit_text.setText(String.valueOf(result));
                } catch (IllegalArgumentException e) {
                    edit_text.setText("計算できません");
                }
            }
        });

        Button ac_button = findViewById(R.id.ac_button);
        ac_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edit_text.setText("");
            }
        });

        Button delete_button = findViewById(R.id.delete_button);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable editable = edit_text.getText();
                String text = editable.toString();
                if (editable.length() >= 1) {
                    edit_text.setText(text.substring(0, editable.length() - 1));
                }
            }
        });

        ImageButton img_button = findViewById(R.id.imageButton);
        img_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(MainActivity.this, view);
                popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.menu_item_history) {
                            Toast.makeText(MainActivity.this, "まだ実装されてません", Toast.LENGTH_SHORT).show();
                        }
                        else if (item.getItemId() == R.id.menu_item_version) {
                            Toast.makeText(MainActivity.this, "まだ実装されてません", Toast.LENGTH_SHORT).show();
                        }
                        else if (item.getItemId() == R.id.menu_item_exit) {
                            finish();
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });
    }

    public enum MathButton {
        ZERO(R.id.zero_button,"0", "0"),
        ONE(R.id.one_button,"1", "1"),
        TWO(R.id.two_button,"2", "2"),
        THREE(R.id.there_button,"3", "3"),
        FOUR(R.id.four_button,"4", "4"),
        FIVE(R.id.five_button,"5", "5"),
        SIX(R.id.six_button,"6", "6"),
        SEVEN(R.id.seven_button,"7", "7"),
        EIGHT(R.id.eight_button,"8", "8"),
        NINE(R.id.nine_button, "9", "9"),
        PLUS(R.id.plus_button, "+", "+"),
        MINE(R.id.minus_button, "-", "-"),
        MULTIPLICATION(R.id.multiplication_button, "*", "×"), //「×」は使えないため、「*」をライブラリに
        DIVISION(R.id.division_button, "/", "÷"), // 「÷」は使えないため、「 / 」 をライブラリに

        PARENTHESES(R.id.parentheses_button, "()", "()"),
        PIE(R.id.pie_button, String.valueOf(Math.PI), "π"),
        DECIMAL(R.id.decimal_button, ".", "."),
        PERCENT(R.id.percent_button, "/100", "%"), // 「％」は使えないため、「 / 100」 をライブラリに
        ROOT(R.id.root_button, "√", "√");

        private final int button_id;
        private final String library_text;
        private final String send_text;

        MathButton(int button_id, String library_text, String send_text) {
            this.button_id = button_id;
            this.library_text = library_text;
            this.send_text = send_text;
        }

        /**
         * @return アプリ上に送る文字を返す。（ライブラリと異なる場合は変える）
         */
        public String getSendText() {
            return send_text;
        }

        /**
         * @return ライブラリ上に送るテキスト
         */
        public String getLibraryText() {
            return library_text;
        }

        public int getButtonID() {
            return button_id;
        }

        public static String replaceLibraryText(String text) {
            for (MathButton button : MathButton.values()) {
                String send_text = button.getSendText();
                String library_text = button.getLibraryText();
                if (text.contains(send_text)) {
                    text = text.replace(send_text, library_text);
                }
            }
            return text;
        }
    }
}