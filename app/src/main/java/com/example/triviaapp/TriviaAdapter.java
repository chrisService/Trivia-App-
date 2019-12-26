package com.example.triviaapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TriviaAdapter extends RecyclerView.Adapter<TriviaAdapter.TriviaViewHolder> {

    private  List<Question> questions;

    private boolean[] answeredQuestions;

    public TriviaAdapter(List<Question> questions) {
        this.questions = questions;
        answeredQuestions = new boolean[questions.size()];
    }

    @NonNull
    @Override
    public TriviaAdapter.TriviaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_question, parent, false);

//        TriviaViewHolder triviaViewHolder = new TriviaViewHolder(view);

        return new TriviaViewHolder(view);
    }

    public List<Question> getQuestions(){
        return questions;
    }

    @Override
    public void onBindViewHolder(@NonNull final TriviaAdapter.TriviaViewHolder holder, int position) {

        final Question question = questions.get(holder.getAdapterPosition());

        List<String> totalAnswers;

        holder.tvQuestion.setText(question.getQuestion());

        String correctAnswer = question.getCorrect_answer();

        String incAnswer1 = question.getIncorrect_answer1();
        String incAnswer2 = question.getIncorrect_answer2();
        String incAnswer3 = question.getIncorrect_answer3();

        if(incAnswer2 == null && incAnswer3 == null){
            totalAnswers = new ArrayList<>();
            totalAnswers.add(correctAnswer);
            totalAnswers.add(incAnswer1);

            Collections.shuffle(totalAnswers);

            holder.one.setText(totalAnswers.get(0));
            holder.two.setText(totalAnswers.get(1));

            holder.one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) view;
                    answeredQuestions[holder.getAdapterPosition()] = radioButton.getText().toString().equals(question.getCorrect_answer());
                }
            });

            holder.two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) view;
                    answeredQuestions[holder.getAdapterPosition()] = radioButton.getText().toString().equals(question.getCorrect_answer());
                }
            });

            holder.three.setVisibility(View.GONE);
            holder.four.setVisibility(View.GONE);

        } else {

            totalAnswers = new ArrayList<>();
            totalAnswers.add(correctAnswer);
            totalAnswers.add(incAnswer1);
            totalAnswers.add(incAnswer2);
            totalAnswers.add(incAnswer3);

            Collections.shuffle(totalAnswers);

            holder.one.setText(totalAnswers.get(0));
            holder.two.setText(totalAnswers.get(1));
            holder.three.setText(totalAnswers.get(2));
            holder.four.setText(totalAnswers.get(3));

            holder.one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) view;
                    answeredQuestions[holder.getAdapterPosition()] = radioButton.getText().toString().equals(question.getCorrect_answer());
                }
            });

            holder.two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) view;
                    answeredQuestions[holder.getAdapterPosition()] = radioButton.getText().toString().equals(question.getCorrect_answer());
                }
            });

            holder.three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) view;
                    answeredQuestions[holder.getAdapterPosition()] = radioButton.getText().toString().equals(question.getCorrect_answer());
                }
            });

            holder.four.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    RadioButton radioButton = (RadioButton) view;
                    answeredQuestions[holder.getAdapterPosition()] = radioButton.getText().toString().equals(question.getCorrect_answer());
                }
            });
        }
    }

    boolean[] getUserInput() {
        return answeredQuestions;
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class TriviaViewHolder extends RecyclerView.ViewHolder {

        TextView tvQuestion;
        RadioGroup radioGroup;
        RadioButton one;
        RadioButton two;
        RadioButton three;
        RadioButton four;

        public TriviaViewHolder(@NonNull View itemView) {
            super(itemView);

            tvQuestion = itemView.findViewById(R.id.question_text);
            radioGroup = itemView.findViewById(R.id.radioGroup);
            one = itemView.findViewById(R.id.one);
            two = itemView.findViewById(R.id.two);
            three = itemView.findViewById(R.id.three);
            four = itemView.findViewById(R.id.four);


        }
    }
}
