package com.example.shotgurnquiz.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.shotgurnquiz.Database.DatabaseHelper;
import com.example.shotgurnquiz.Database.Tables.User;
import com.example.shotgurnquiz.R;

public class ChangePasswordDialogFragment extends DialogFragment {

    private static final String ARG_PARAM1 = "userId";

    private int userId;

    public ChangePasswordDialogFragment(){}

    public static ChangePasswordDialogFragment newInstance(int userId) {
        ChangePasswordDialogFragment fragment = new ChangePasswordDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_PARAM1);
        }

        View rootView = this.getLayoutInflater().inflate(R.layout.fragment_change_password, null);

        EditText editTextOldPassword = (EditText) rootView.findViewById(R.id.change_password_old_password);
        EditText editTextNewPassword = (EditText) rootView.findViewById(R.id.change_password_new_password);
        EditText editTextConfirmNewPassword = (EditText) rootView.findViewById(R.id.change_password_confirm_new_password);
        Button buttonConfirm = (Button) rootView.findViewById(R.id.change_password_btn_confirm);

        DatabaseHelper db = DatabaseHelper.GetInstance(getContext());
        User user = db.FindUserByID(userId);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPasswordText = editTextOldPassword.getText().toString();
                String newPasswordText = editTextNewPassword.getText().toString();
                String confirmNewPasswordText = editTextConfirmNewPassword.getText().toString();

                if(oldPasswordText.isEmpty()) {
                    editTextOldPassword.setError(getResources().getText(R.string.empty_field));
                    editTextOldPassword.requestFocus();
                }
                else if(newPasswordText.isEmpty()) {
                    editTextNewPassword.setError(getResources().getText(R.string.empty_field));
                    editTextNewPassword.requestFocus();
                }
                else if(confirmNewPasswordText.isEmpty()) {
                    editTextConfirmNewPassword.setError(getResources().getText(R.string.empty_field));
                    editTextConfirmNewPassword.requestFocus();
                }
                else if (!oldPasswordText.equals(user.GetPassword())) {
                    editTextOldPassword.setError(getResources().getText(R.string.incorrect_password));
                    editTextOldPassword.requestFocus();
                }
                else if (!newPasswordText.equals(confirmNewPasswordText)) {
                    editTextConfirmNewPassword.setError(getResources().getText(R.string.fields_not_match));
                    editTextConfirmNewPassword.requestFocus();
                }else{
                    db.SetUserPassword(userId, newPasswordText);
                    Toast.makeText(getContext(),R.string.password_successfully_updated,Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        return builder.create();
    }

    @Override
    public void onResume() {
        super.onResume();
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics());
        getDialog().getWindow().setLayout(width,height);
    }
}
