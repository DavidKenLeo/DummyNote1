package com.dkl.auser.dummynote1;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    boolean skipMessage;
    AlertDialog.Builder alert;
    public CheckBox dontShowAgain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 宣告對話框
        CKDiagbox();

        // 如果沒有勾選"不再顯示",則顯示對話框
        if (!isCheckboxStateEnabled())
            alert.show();

    }

    public void CKDiagbox() {

        alert = new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        View checkboxLayout = adbInflater.inflate(R.layout.activity_main, null);
        dontShowAgain = (CheckBox) checkboxLayout.findViewById(R.id.skip);
        alert.setView(checkboxLayout);
        alert.setTitle(R.string.settitle);
        alert.setIcon(android.R.drawable.ic_dialog_alert);
        alert.setMessage(R.string.toGoogle);

        alert.setPositiveButton("離開", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                boolean checkBoxResult = false;
                if (dontShowAgain.isChecked())
                    checkBoxResult = true;

                setCheckboxState(checkBoxResult);

                // 關閉對話框
                finish();
            }
        });

        alert.setNegativeButton("進入", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                boolean checkBoxResult = false;

                if (dontShowAgain.isChecked())
                    checkBoxResult = true;

                setCheckboxState(checkBoxResult);

                Uri uri = Uri.parse("http://www.google.com/");
                Intent it = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(it);

            }
        });

    }

    public void setCheckboxState(boolean chk) {
        // 記錄勾選方塊是否被打勾
        SharedPreferences settings = getSharedPreferences("showit", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("skipMessage", chk);
        editor.commit();
    }

    public boolean isCheckboxStateEnabled() {
        // 讀取勾選方塊是否被打勾,預設值是未打勾(fasle)
        SharedPreferences settings = getSharedPreferences("showit", 0);
        skipMessage = settings.getBoolean("skipMessage", false);

        return skipMessage;
    }

}