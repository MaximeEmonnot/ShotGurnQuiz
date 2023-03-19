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

// Adaptateur pour recyclerView permettant l'affichage de question
public class QuestionCard_RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public QuestionCard_RecyclerViewAdapter(Context context){
        this.context = context;
        this.questions = new ArrayList<Question>();
    }

    // Definition du layout à utiliser pour les differents elements
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Si l'element est une question
        if(viewType == VIEW_TYPE_CELL){
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_view_question_card, parent, false);
            return new QuestionCard_RecyclerViewAdapter.MyViewHolder(view);
        }
        // Si l'element est le bouton ajout à la fin de la liste
        else{
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.recycler_view_add_button, parent, false);
            return new addButtonViewHolder(view);
        }
    }


    // Definit quels sont les paramètres à mettre dans l'element de la liste à une position donnée
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

    // Récupére le type de l'element, VIEW_TYPE_LAST si c'est le dernier (bouton ajout), VIEW_TYPE_CELL sinon
    @Override
    public int getItemViewType(int position) {
        return (position == getItemCount() - 1) ? VIEW_TYPE_LAST : VIEW_TYPE_CELL;
    }

    // Permet d'ajouter une question à la liste
    public void push(Question question){
        // Ajoute la question
        questions.add(question);
        // Notifie un changement pour actualiser l'affichage du recyclerView
        this.notifyDataSetChanged();
    }

    // Permet de modifier une question de la liste
    public void set(int index, Question question){
        // Modifie la question à l'index passé en paramètre
        questions.set(index, question);
        // Notifie un changement pour actualiser l'affichage du recyclerView
        this.notifyDataSetChanged();
    }

    // Permet de retirer une question de la liste
    public void remove(int index){
        // Retire la question de la liste
        questions.remove(index);
        // Notifie un changement pour actualiser l'affichage du recyclerView
        this.notifyDataSetChanged();
    }

    // Récupère la liste des questions contenue dans le recyclerView
    public ArrayList<Question> getAllItems(){
        return questions;
    }

    // Récupère la question à une position passée en paramètre
    public Question getItem(int index){
        return questions.get(index);
    }

    // Récupère le nombre d'items dans le recyclerView
    @Override
    public int getItemCount() {
        return questions.size() + 1;
    }


    // Définition du ViewHolder qui définit les paramètres d'un item question
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView textViewIndex;
        TextView textViewTitle;
        TextView textViewAnswerA;
        TextView textViewAnswerB;
        TextView textViewCorrectAnswer;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            // Références aux éléments du layout
            textViewIndex = itemView.findViewById(R.id.recycler_view_question_index);
            textViewTitle = itemView.findViewById(R.id.recycler_view_question_title);
            textViewAnswerA = itemView.findViewById(R.id.recycler_view_question_answer_a);
            textViewAnswerB = itemView.findViewById(R.id.recycler_view_question_answer_b);
            textViewCorrectAnswer = itemView.findViewById(R.id.recycler_view_question_correct_answer);
        }
    }

    // Définition du ViewHolder qui définit les paramètres de l'item d'ajout
    public static class addButtonViewHolder extends RecyclerView.ViewHolder{

        public addButtonViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    // Differentes variables de la class
    private final static int VIEW_TYPE_LAST = 0;
    private final static int VIEW_TYPE_CELL = 1;
    private Context context;
    private ArrayList<Question> questions;
}
