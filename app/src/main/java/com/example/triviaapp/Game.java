package com.example.triviaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.QuickContactBadge;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Game extends AppCompatActivity {

    private static final String BASE_URL = "https://opentdb.com/api.php?";

    RecyclerView rvQuestions;


    String broj;
    String tezina;
    String tip;
    String kategorija;

    static ProgressBar progressBar;
    static LinearLayout gameLinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressBar = findViewById(R.id.progresBar);
        gameLinearLayout = findViewById(R.id.gameLayout);

        progressBar.setVisibility(View.VISIBLE);
        gameLinearLayout.setVisibility(View.GONE);

        Intent intent = getIntent();

        broj = intent.getStringExtra("BROJ");
        tezina = intent.getStringExtra("DIF");

        tip = intent.getStringExtra("TYPE");
        kategorija = intent.getStringExtra("CATEGORY");

        rvQuestions = findViewById(R.id.rvQuestions);
        rvQuestions.setLayoutManager(new LinearLayoutManager(this));

        final TriviaAsyncTast triviaAsyncTast = new TriviaAsyncTast(
                rvQuestions,
                broj,
                tezina,
                tip,
                kategorija);
        triviaAsyncTast.execute();



        Button finishBtn = findViewById(R.id.finishBtn);

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int counter = 0;
                TriviaAdapter triviaAdapter = (TriviaAdapter) rvQuestions.getAdapter();
                for (int i=0; i<triviaAdapter.getUserInput().length; i++) {
                    if (triviaAdapter.getUserInput()[i]) {
                        counter++;
                    }
                }

                ArrayList<Question> questionsResults = (ArrayList<Question>) triviaAdapter.getQuestions();

                if (counter == questionsResults.size()){
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Game.this);
                    dialog.setTitle("BRAVO" + "\n" + "YOU KNOW EVERYTHING" + "\n" + "You scored:" + counter + "/" + questionsResults.size());
                    dialog.setMessage("Play Again");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                          Intent intentR = new Intent(Game.this, MainActivity.class);
                          startActivity(intentR);
                        }
                    });
                    dialog.show();
                } else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Game.this);
                    dialog.setTitle("Your score is:" + counter + "/" + questionsResults.size());
                    dialog.setMessage("Play Again");
                    dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intentR = new Intent(Game.this, MainActivity.class);
                            startActivity(intentR);
                        }
                    });
                    dialog.show();
                }
            }
        });
    }

    static class TriviaAsyncTast extends AsyncTask<Void, Void, ArrayList<Question>> {

        WeakReference<RecyclerView> rvQuestions;
        String broj;
        String tezina;
        String tip;
        String kategorija;

        public TriviaAsyncTast(RecyclerView rvQuestions, String broj, String tezina, String tip, String kategorija) {
            this.rvQuestions = new WeakReference<>(rvQuestions);
            this.broj = broj;
            this.tezina = tezina;
            this.tip = tip;
            this.kategorija = kategorija;
        }

        @Override
        protected ArrayList<Question> doInBackground(Void... voids) {


            ArrayList<Question> questionsResults = new ArrayList<>();


            Uri.Builder uri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter("amount", broj);

            if (!tezina.equalsIgnoreCase("any")){
                uri.appendQueryParameter("difficulty", tezina);
            }
            if (!tip.equalsIgnoreCase("any")){
                uri.appendQueryParameter("type", tip);
            }
            if(!kategorija.equalsIgnoreCase("0")){
                uri.appendQueryParameter("category", kategorija);
            }

            uri.build();



            try {
                URL url = new URL(uri.toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();
                int resposeCode = httpURLConnection.getResponseCode();
                if (resposeCode == 200) {
                    InputStream inputStream = httpURLConnection.getInputStream();
                    String results = convertIsToString(inputStream);

                    JSONObject jsonResponseObject = new JSONObject(results);

                    JSONArray jsonArray = jsonResponseObject.getJSONArray("results");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        ArrayList<String> incorrectAnswers = new ArrayList<>();

                        JSONObject jsonObject = (JSONObject) jsonArray.get(i);

                        String question = (String) jsonObject.get("question");

                        String correctAnswer = (String) jsonObject.get("correct_answer");

                        JSONArray jsonIncorrectAnswers = jsonObject.getJSONArray("incorrect_answers");

                        for (int j = 0; j < jsonIncorrectAnswers.length(); j++){
                            String incorectAnswer = jsonIncorrectAnswers.getString(j);
                            incorrectAnswers.add(incorectAnswer);
                        }
                        if (incorrectAnswers.size() == 1) {
                            String incAnswer1 = incorrectAnswers.get(0);

                            questionsResults.add(new Question(question, correctAnswer, incAnswer1));
                        }else {
                            String incAnswer1 = incorrectAnswers.get(0);
                            String incAnswer2 = incorrectAnswers.get(1);
                            String incAnswer3 = incorrectAnswers.get(2);

                            questionsResults.add(new Question(question, correctAnswer, incAnswer1, incAnswer2, incAnswer3));
                        }

                    }





                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return questionsResults;
        }

        @Override
        protected void onPostExecute(ArrayList<Question> questions) {
            super.onPostExecute(questions);
            TriviaAdapter adapter = new TriviaAdapter(questions);
            rvQuestions.get().setAdapter(adapter);

            Game.progressBar.setVisibility(View.GONE);
            Game.gameLinearLayout.setVisibility(View.VISIBLE);

        }

        private String convertIsToString(InputStream inputStream) throws IOException {
            StringBuilder sb = new StringBuilder();
            InputStreamReader isReader = new InputStreamReader(inputStream);
            BufferedReader reader = new BufferedReader(isReader);
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        }


    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
