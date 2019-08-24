package com.example.bounce.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.example.bounce.R;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import static android.content.ContentValues.TAG;

public class BottomActivity extends BottomSheetDialogFragment {
    private  ButtonClicked buttonClickedListener;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.referal_code, container, false);
        final Button button = view.findViewById(R.id.next_bt);
        final EditText referalCode = view.findViewById(R.id.referal_code_et);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = referalCode.getText().toString().trim();
                if (code.length()<=0){
                    Log.e(TAG, "onClick: "+"if condition" );
                    new AlertDialog.Builder(getContext())
                            .setTitle("Alert")
                            .setMessage("Referral code is empty.")
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            }).show();
                }
                else {
                    buttonClickedListener.buttonClicked(code);
                    getDialog().cancel();
                }
            }
        });
        return  view;
    }

    public interface ButtonClicked{
        void buttonClicked(String referealCode);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            buttonClickedListener = (ButtonClicked) context;
        }
        catch (ClassCastException c){
            Log.e(TAG, "onAttach: "+c );
        }



    }
}

