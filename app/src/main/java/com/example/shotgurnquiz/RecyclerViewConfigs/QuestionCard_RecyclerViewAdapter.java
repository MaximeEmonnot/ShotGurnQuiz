package com.example.shotgurnquiz.RecyclerViewConfigs;

        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import com.example.shotgurnquiz.Database.Tables.Question;
        import com.example.shotgurnquiz.R;

        import java.util.ArrayList;

public class QuestionCard_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public QuestionCard_RecyclerViewAdapter(Context context){
        this.context = context;
        this.questions = new ArrayList<Question>();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_CELL){
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_view_question_card, parent, false);
            return new QuestionCard_RecyclerViewAdapter.MyViewHolder(view);
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
            ((MyViewHolder)holder).textViewTitle.setText(questions.get(position).GetTitle());
            ((MyViewHolder)holder).textViewAnswerA.setText(questions.get(position).GetChoice1());
            ((MyViewHolder)holder).textViewAnswerB.setText(questions.get(position).GetChoice2());
            ((MyViewHolder)holder).textViewCorrectAnswer.setText(questions.get(position).GetAnswer() ? "A" : "B");
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount() - 1) ? VIEW_TYPE_LAST : VIEW_TYPE_CELL;
    }

    public void push(Question question){
        questions.add(question);
        this.notifyDataSetChanged();
    }

    public void set(int index, Question question){
        questions.set(index, question);
        this.notifyDataSetChanged();
    }

    public void remove(int index){
        questions.remove(index);
        this.notifyDataSetChanged();
    }

    public ArrayList<Question> getAllItems(){
        return questions;
    }

    public Question getItem(int index){
        return questions.get(index);
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
    private ArrayList<Question> questions;
}
