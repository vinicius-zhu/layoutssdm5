package br.edu.ifsp.scl.sdm.layoutssdm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private final String ESTADO_NOTIFICACAO_CHECKBOX = "ESTADO_NOTIFICACAO_CHECKBOX";
    private final String NOTIFICACAO_RADIOBUTTON_SELECIONADO = "NOTIFICACAO_RADIOBUTTON_SELECIONADO";


    private CheckBox notificacoesCheckBox;
    private RadioGroup notificacoesRadioGroup;
    private EditText nomeEditText;
    private EditText telefoneEditText;
    private EditText emailEditText;
    private LinearLayout telefoneLinearLayout;
    private ArrayList<View> telefoneArrayList;
    private LayoutInflater layoutInflater;
    private LinearLayout emailLinearLayout;
    private ArrayList<View> emailArrayList;
    private HashMap<String, Integer> telefoneList;
    private HashMap<String, Integer> emailList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scroll_view_activity_main);


        notificacoesCheckBox = findViewById(R.id.notificacoesCheckBox);
        notificacoesRadioGroup = findViewById(R.id.notificacoesRadioGroup);
        nomeEditText = findViewById(R.id.nomeEditText);
        telefoneEditText = findViewById(R.id.telefoneEditText);
        emailEditText = findViewById(R.id.emailEditText);
        telefoneLinearLayout = findViewById(R.id.telefoneLinearLayout);
        telefoneArrayList = new ArrayList<>();
        emailLinearLayout = findViewById(R.id.emailLinearLayout);
        emailArrayList = new ArrayList<>();
        if (telefoneList == null) {
            telefoneList = new HashMap<>();
        }
        if (emailList == null) {
            emailList = new HashMap<>();
        }
        layoutInflater = getLayoutInflater();

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

        // Exibindo/ocultando RadioGroup por evento de On Checked Change Listener
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(ESTADO_NOTIFICACAO_CHECKBOX, notificacoesCheckBox.isChecked());
        outState.putInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, notificacoesRadioGroup.getCheckedRadioButtonId());

        emailList.clear();
        for (View v : emailArrayList)
        {
            EditText innerEmailEditText = v.findViewById(R.id.innerEmailEditText);
            Spinner innerTipoEmailSpinner = v.findViewById(R.id.innerTipoEmailSpinner);
            emailList.put(innerEmailEditText.getText().toString(),innerTipoEmailSpinner.getSelectedItemPosition());
        }
        outState.putSerializable("LISTA_EMAILS",emailList);

        telefoneList.clear();
        for (View v : telefoneArrayList)
        {
            EditText innerTelefoneEditText = v.findViewById(R.id.innerTelefoneEditText);
            Spinner innerTipoTelefoneSpinner = v.findViewById(R.id.innerTipoTelefoneSpinner);
            telefoneList.put(innerTelefoneEditText.getText().toString(),innerTipoTelefoneSpinner.getSelectedItemPosition());
        }
        outState.putSerializable("LISTA_TELEFONES",telefoneList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        notificacoesCheckBox.setChecked(savedInstanceState.getBoolean(ESTADO_NOTIFICACAO_CHECKBOX, false));
        // Exibe/Esconde radio group caso o evento implementado seja no OnClick
        /*if (notificacoesCheckBox.isChecked())
        {
            notificacoesRadioGroup.setVisibility(View.VISIBLE);
        }
        else
        {
            notificacoesRadioGroup.setVisibility(View.GONE);
        }*/
        int idRadioButtonSelecionado = savedInstanceState.getInt(NOTIFICACAO_RADIOBUTTON_SELECIONADO, -1);
        if (idRadioButtonSelecionado != -1) {
            notificacoesRadioGroup.check(idRadioButtonSelecionado);
        }

        emailList = (HashMap<String,Integer>)savedInstanceState.getSerializable("LISTA_EMAILS");
        for (Map.Entry<String, Integer> v : emailList.entrySet())
        {
            View novoEmailLayout = layoutInflater.inflate(R.layout.novo_email_layout,null);
            ((EditText)novoEmailLayout.findViewById(R.id.innerEmailEditText)).setText(v.getKey());
            ((Spinner)novoEmailLayout.findViewById(R.id.innerTipoEmailSpinner)).setSelection(v.getValue());
            emailArrayList.add(novoEmailLayout);
            emailLinearLayout.addView(novoEmailLayout);

        }

        telefoneList = (HashMap<String, Integer>)savedInstanceState.getSerializable("LISTA_TELEFONES");
        for (Map.Entry<String, Integer> v : telefoneList.entrySet())
        {
            View novoTelefoneLayout = layoutInflater.inflate(R.layout.novo_telefone_layout,null);
            ((EditText)novoTelefoneLayout.findViewById(R.id.innerTelefoneEditText)).setText(v.getKey());
            ((Spinner)novoTelefoneLayout.findViewById(R.id.innerTipoTelefoneSpinner)).setSelection(v.getValue());
            telefoneArrayList.add(novoTelefoneLayout);
            telefoneLinearLayout.addView(novoTelefoneLayout);

        }
    }

    public void limparFormulario(View botao){
        notificacoesCheckBox.setChecked(false);
        notificacoesRadioGroup.clearCheck();
        nomeEditText.setText("");
        telefoneEditText.setText("");
        emailEditText.setText("");
        telefoneArrayList.clear();
        telefoneLinearLayout.removeAllViews();
        emailArrayList.clear();
        emailLinearLayout.removeAllViews();
        nomeEditText.requestFocus();

    }

    public void adicionarTelefone(View botao){
        if (botao.getId() == R.id.adicionarTelefoneButton){

            View novoTelefoneLayout = layoutInflater.inflate(R.layout.novo_telefone_layout,null);
            telefoneArrayList.add(novoTelefoneLayout);
            telefoneLinearLayout.addView(novoTelefoneLayout);
        }
    }
    public void adicionarEmail(View botao){
        if (botao.getId() == R.id.adicionarEmailButton){

            View novoEmailLayout = layoutInflater.inflate(R.layout.novo_email_layout,null);
            emailArrayList.add(novoEmailLayout);
            emailLinearLayout.addView(novoEmailLayout);
        }
    }
}
