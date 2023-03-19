package com.example.shotgurnquiz.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

import androidx.fragment.app.DialogFragment;

import com.example.shotgurnquiz.Database.Tables.Question;
import com.example.shotgurnquiz.R;
import com.example.shotgurnquiz.RecyclerViewConfigs.QuestionCard_RecyclerViewAdapter;

public class AddQuestionDialogFragment extends DialogFragment {

    public AddQuestionDialogFragment(QuestionCard_RecyclerViewAdapter questionAdapter, int index) {
        this.questionAdapter = questionAdapter;
        this.index = index;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View rootView = this.getLayoutInflater().inflate(R.layout.fragment_add_question, null);

        EditText editTextTitle = (EditText) rootView.findViewById(R.id.question_title);
        EditText editTextAnswerA = (EditText) rootView.findViewById(R.id.answer_a);
        EditText editTextAnswerB = (EditText) rootView.findViewById(R.id.answer_b);

        Button buttonSave = (Button) rootView.findViewById(R.id.btn_save);
        RadioButton radioAnswerA = (RadioButton) rootView.findViewById(R.id.radio_answer_a);
        RadioButton radioAnswerB = (RadioButton) rootView.findViewById(R.id.radio_answer_b);
        ImageButton buttonDelete = (ImageButton) rootView.findViewById(R.id.button_delete);

        Question question;

        if(index == questionAdapter.getItemCount() - 1){
            question = new Question(getResources().getString(R.string.this_is_my_question), getResources().getString(R.string.answer_a), getResources().getString(R.string.answer_b), true);
            questionAdapter.push(question);
        }else{
            question = questionAdapter.getItem(index);
        }

        editTextTitle.setText(question.GetTitle());
        editTextAnswerA.setText(question.GetChoice1());
        editTextAnswerB.setText(question.GetChoice2());

        if(question.GetAnswer() == true)
            radioAnswerA.setChecked(true);
        else
            radioAnswerB.setChecked(true);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editTextTitle.getText().toString();
                String choice1 = editTextAnswerA.getText().toString();
                String choice2 = editTextAnswerB.getText().toString();
                boolean answer = radioAnswerA.isChecked();
                Question question = new Question(title, choice1, choice2, answer);

                questionAdapter.set(index, question);
                dismiss();
            }
        });

        radioAnswerA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioAnswerB.setChecked(false);
            }
        });

        radioAnswerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                radioAnswerA.setChecked(false);
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                questionAdapter.remove(index);
                dismiss();
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
        int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 600, getResources().getDisplayMetrics());
        getDialog().getWindow().setLayout(width,height);
    }

    private QuestionCard_RecyclerViewAdapter questionAdapter;
    private int index;
}
