package example.com.simplequiz;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.security.SecureRandom;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "FlagQuiz Activity";

    private static final int FLAGS_IN_QUIZ = 10;

    private List<String> fileNameList; // flag file names
    private List<String> quizCountriesList; // countries in current quiz
    private Set<String> regionsSet; // world regions in current quiz
    private String correctAnswer; // correct country for the current flag
    private int totalGuesses; // number of guesses made
    private int correctAnswers; // number of correct guesses
    private int guessRows; // number of rows displaying guess Buttons
    private SecureRandom random; // used to randomize the quiz
    private Handler handler; // used to delay loading next flag
    private Animation shakeAnimation; // animation for incorrect guess

    private LinearLayout quizLinearLayout; // layout that contains the quiz
    private TextView questionNumberTextView; // shows current question #
    private ImageView flagImageView; // displays a flag
    private LinearLayout[] guessLinearLayouts; // rows of answer Buttons
    private TextView answerTextView; // displays correct answer

    private Button btnA;
    private Button btnB;
    private Button btnC;
    private Button btnD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnA = (Button) findViewById(R.id.button);
        btnB = (Button) findViewById(R.id.button2);
        btnC = (Button) findViewById(R.id.button3);
        btnD = (Button) findViewById(R.id.button4);

        final TextView result = (TextView) findViewById(R.id.textView2);

        btnA.setText("Hong Kong");
        btnB.setText("Japan");
        btnC.setText("Britian");
        btnD.setText("China");

        btnA.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                result.setText("Incorrect!");
            }
        });

        btnB.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                result.setText("Correct!");
                Intent startQ2Activity = new Intent(v.getContext(), Main2Activity.class);
                startActivity(startQ2Activity);
            }
        });
    }

}
