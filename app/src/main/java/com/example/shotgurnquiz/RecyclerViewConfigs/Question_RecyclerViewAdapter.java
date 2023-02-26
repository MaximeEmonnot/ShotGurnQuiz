package com.example.shotgurnquiz.RecyclerViewConfigs;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.shotgurnquiz.Models.QuestionModel;
        import com.example.shotgurnquiz.R;

        import java.util.ArrayList;

public class Question_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public Question_RecyclerViewAdapter(Context context, ArrayList<QuestionModel> questions){
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_CELL){
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_view_question, parent, false);
            return new Question_RecyclerViewAdapter.MyViewHolder(view);
        }else{
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_view_add_button, parent, false);
            return new addButtonViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == VIEW_TYPE_CELL){
            ((MyViewHolder)holder).textViewIndex.setText((position + 1) + "/" + questions.size());
            ((MyViewHolder)holder).textViewTitle.setText(questions.get(position).getTitle());
            ((MyViewHolder)holder).textViewAnswerA.setText(questions.get(position).getAnswerA());
            ((MyViewHolder)holder).textViewAnswerB.setText(questions.get(position).getAnswerB());
            ((MyViewHolder)holder).textViewCorrectAnswer.setText(questions.get(position).getCorrectAnswer() ? "A" : "B");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount() - 1) ? VIEW_TYPE_LAST : VIEW_TYPE_CELL;
    }

    @Override
    public int getItemCount() {
        return questions.size() + 1;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIndex;
        TextView textViewTitle;
        TextView textViewAnswerA;
        TextView textViewAnswerB;
        TextView textViewCorrectAnswer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewIndex = itemView.findViewById(R.id.recycler_view_question_index);
            textViewTitle = itemView.findViewById(R.id.recycler_view_question_title);
            textViewAnswerA = itemView.findViewById(R.id.recycler_view_question_answer_a);
            textViewAnswerB = itemView.findViewById(R.id.recycler_view_question_answer_b);
            textViewCorrectAnswer = itemView.findViewById(R.id.recycler_view_question_correct_answer);
        }
    }

    public static class addButtonViewHolder extends RecyclerView.ViewHolder{

        public addButtonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private final static int VIEW_TYPE_LAST = 0;
    private final static int VIEW_TYPE_CELL = 1;
    private Context context;
    private ArrayList<QuestionModel> questions;
}
