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

    public ChangePasswordDialogFragment(){}

    // Nouvelle instance du DialogFragment : On définit l'unique argument UserID pour la création dudit DialogFragment
    public static ChangePasswordDialogFragment newInstance(int userId) {
        ChangePasswordDialogFragment fragment = new ChangePasswordDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, userId);
        fragment.setArguments(args);
        return fragment;
    }

    // Création du DialogFragment
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // On récupère l'unique argument défini dans l'instanciation
        if (getArguments() != null) {
            userId = getArguments().getInt(ARG_PARAM1);
        }

        // Définition du layout du DialogFragment
        View rootView = this.getLayoutInflater().inflate(R.layout.fragment_change_password, null);

        // Références aux éléments du layout
        EditText editTextOldPassword = (EditText) rootView.findViewById(R.id.change_password_old_password);
        EditText editTextNewPassword = (EditText) rootView.findViewById(R.id.change_password_new_password);
        EditText editTextConfirmNewPassword = (EditText) rootView.findViewById(R.id.change_password_confirm_new_password);
        Button buttonConfirm = (Button) rootView.findViewById(R.id.change_password_btn_confirm);

        // Instanciation du DatabaseHelper (Singleton)
        DatabaseHelper db = DatabaseHelper.GetInstance(getContext());

        // Récupération des informations de l'utilisateur à l'aide de son ID
        User user = db.FindUserByID(userId);

        // OnClick du bouton Confirm
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPasswordText = editTextOldPassword.getText().toString();
                String newPasswordText = editTextNewPassword.getText().toString();
                String confirmNewPasswordText = editTextConfirmNewPassword.getText().toString();

                // Si tous les champs ne sont pas remplis, on affiche différents messages d'erreur...
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
                // ... Sinon, on vérifie l'égalité entre l'ancien mot de passe entré par l'utilisateur, et le mot de passe enregistré en base de donnée. Si inégalité, on affiche un message d'erreur. ...
                else if (!oldPasswordText.equals(user.GetPassword())) {
                    editTextOldPassword.setError(getResources().getText(R.string.incorrect_password));
                    editTextOldPassword.requestFocus();
                }
                // ... Sinon, on vérifie l'égalité entre les champs pour ce qui est du nouveau mot de passe. Si inégalité, on affiche un message d'erreur ...
                else if (!newPasswordText.equals(confirmNewPasswordText)) {
                    editTextConfirmNewPassword.setError(getResources().getText(R.string.fields_not_match));
                    editTextConfirmNewPassword.requestFocus();
                }else{
                    // ... Sinon on modifie le mot de passe de l'utilisateur dans la base de données.
                    db.SetUserPassword(userId, newPasswordText);
                    Toast.makeText(getContext(),R.string.password_successfully_updated,Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        // Affichage du DialogFragment
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(rootView);
        return builder.create();
    }

    // Rédéfinition des dimensions du DialogFragment lors de l'évènement onResume()
    @Override
    public void onResume() {
        super.onResume();
        int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 350, getResources().getDisplayMetrics());
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 500, getResources().getDisplayMetrics());
        getDialog().getWindow().setLayout(width,height);
    }

    // Constante pour le nom de l'argument lors de l'instanciation
    private static final String ARG_PARAM1 = "userId";

    // Differentes variables du fragment
    private int userId;
}
