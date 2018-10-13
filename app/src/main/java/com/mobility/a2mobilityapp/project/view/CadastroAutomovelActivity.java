package com.mobility.a2mobilityapp.project.view;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mobility.a2mobilityapp.R;

public class CadastroAutomovelActivity extends AppCompatActivity {

    private Button btnCadastrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_automovel);

        btnCadastrar = (Button) findViewById(R.id.button);

        btnCadastrar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    btnCadastrar.setBackgroundColor(Color.rgb(0, 100, 0));
                } else if (event.getAction() == KeyEvent.ACTION_UP) {
                    btnCadastrar.setBackgroundColor(getResources().getColor(R.color.text_background_verde));
                }
                return false;
            }
        });

    }
}
