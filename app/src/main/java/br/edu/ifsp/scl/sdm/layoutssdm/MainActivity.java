package br.edu.ifsp.scl.sdm.layoutssdm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private CheckBox notificacoesCheckBox;
    private RadioGroup notificacoesRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout_activity_main);

        notificacoesCheckBox = findViewById(R.id.notificacoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);

        // Exibindo/ocultando RadioGroup por evento de Click Listener
        /*
        notificacoesCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox) v).isChecked())
                {
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else
                {
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });*/

        notificacoesCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    notificacoesRadioGroup.setVisibility(View.VISIBLE);
                }
                else
                {
                    notificacoesRadioGroup.setVisibility(View.GONE);
                }
            }
        });
    }


}
