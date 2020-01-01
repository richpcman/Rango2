package com.hero.rango.feul;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.hero.rango.App;
import com.hero.rango.R;
import com.hero.rango.db.DaoSession;
import com.hero.rango.db.FeulDao;

import java.util.ArrayList;
import java.util.List;

public class FeulActivity extends AppCompatActivity {
    private Button addFeulBtn,
            cancelBtn;
    private TextView mTxtResult;
    FeulDao feulDao ;
    List<Feul> feuls =new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feul);
        DaoSession daoSession = ((App) getApplication()).getDaoSession();
        feulDao = daoSession.getFeulDao();
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.feulRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        feuls = feulDao.loadAll();

        System.out.println("--1111111111-------"+feuls.size());
        final FeulItem feulItem = new FeulItem(feuls);
        recyclerView.setAdapter(feulItem);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupDialog(feulItem);
            }
        });
    }

    private void showPopupDialog(final FeulItem feulItem) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(FeulActivity.this);
        alertDialogBuilder.setTitle("Add User Name.");
        LayoutInflater inflater = LayoutInflater.from(FeulActivity.this);
        final View v =inflater.inflate(R.layout.alert_dialog_feul_create, null);
        alertDialogBuilder.setView(v);
        alertDialogBuilder.setPositiveButton("save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                EditText editText = (EditText) v.findViewById(R.id.editText3);
                EditText editText2 = (EditText) v.findViewById(R.id.editText4);
                Feul feul = new Feul(editText.getText().toString(), editText2.getText().toString());
                feuls.add(feul);
                feulDao.insert(feul);

                feulItem.notifyDataSetChanged();
                //feuls= feulDao.loadAll();
                Toast.makeText(getApplicationContext(), "你的id是" +
                editText.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        feulItem.notifyItemChanged(feuls.size()+1);
        alertDialogBuilder.show();
    }                                                                                                                                               

}
