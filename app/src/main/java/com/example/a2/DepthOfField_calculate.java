package com.example.a2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.a2.Model.DepthOfField;
import com.example.a2.Model.LensManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static java.lang.String.format;

public class DepthOfField_calculate extends AppCompatActivity {
    static int  lens_index;
    LensManager lensManager = LensManager.getInstance();

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depth_of_field_calculate);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //gettinglens();

        TextView textView = (TextView) findViewById(R.id.lens_info);
        textView.setText(" " + lensManager.get_retrieve(lens_index));

        Button btn = (Button) findViewById(R.id.cal_dof);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // For COF
                EditText editText = (EditText) findViewById(R.id.editText8);
                String temp1 = editText.getText().toString();
                double COF = Double.valueOf(temp1);

                // FOR Subject Distance
                EditText editText1 = (EditText) findViewById(R.id.editText9);
                String temp2 = editText1.getText().toString();
                double Subject_distance = Double.valueOf(temp2);

                // For Aperature
                EditText editText2 = (EditText) findViewById(R.id.editText10);
                String temp3 = editText2.getText().toString();
                double select_aperture =  Double.valueOf(temp3);


                if( select_aperture <  lensManager.get_retrieve1(lens_index).getMax_aperture() || select_aperture < 1.4){

                    TextView textView1 = (TextView) findViewById(R.id.editText4);
                    textView1.setText("Invalid Aperture");

                    TextView textView2 = (TextView) findViewById(R.id.editText5);
                    textView2.setText("Invalid Aperture");

                    TextView textView3 = (TextView) findViewById(R.id.editText6);
                    textView3.setText("Invalid Aperture");

                    TextView textView4 = (TextView) findViewById(R.id.editText7);
                    textView4.setText("Invalid Aperture");
                }
                else if( Subject_distance <= 0){

                    TextView textView1 = (TextView) findViewById((R.id.editText4));
                    textView1.setText("Invalid Subject distance");

                    TextView textView2 = (TextView) findViewById((R.id.editText5));
                    textView2.setText("Invalid Subject distance");

                    TextView textView3 = (TextView) findViewById((R.id.editText6));
                    textView3.setText("Invalid Subject distance");

                    TextView textView4 = (TextView) findViewById((R.id.editText7));
                    textView4.setText("Invalid Subject distance");
                }

                else if(COF == 0){
                    TextView textView1 = (TextView) findViewById(R.id.editText4);
                    textView1.setText("NaN");

                    TextView textView2 = (TextView) findViewById(R.id.editText5);
                    textView2.setText("NaN");

                    TextView textView3 = (TextView) findViewById(R.id.editText6);
                    textView3.setText("NaN");

                    TextView textView4 = (TextView) findViewById(R.id.editText7);
                    textView4.setText("NaN");
                }

                else{
                    DepthOfField dof = new DepthOfField(lensManager.get_retrieve1(lens_index), Subject_distance, select_aperture);
                    TextView textView5 = (TextView) findViewById(R.id.editText4);

                    textView5.setText(" " + String.valueOf(format("%.2f", dof.Nearfocal()/1000)) + "m" );

                    TextView textView6 = (TextView) findViewById(R.id.editText5);
                    textView6.setText(" " + String.valueOf(format("%.2f", dof.Farfocal()/1000)) + "m" );

                    TextView textView7 = (TextView) findViewById(R.id.editText6);
                    textView7.setText(" " + String.valueOf(format("%.2f", dof.depthoffield()/1000)) + "m" );

                    TextView textView8 = (TextView) findViewById(R.id.editText7);
                    textView8.setText(" " + String.valueOf(format("%.2f", dof.Hyperfocal()/1000)) + "m" );

                }
            }
        });
    }

    public static Intent makeIntent(Context context, int lensIndex){
        Intent intent = new Intent(context, DepthOfField_calculate.class);
        lens_index = lensIndex;
        return intent;

    }
}
