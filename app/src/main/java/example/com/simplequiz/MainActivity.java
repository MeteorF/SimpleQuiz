package example.com.simplequiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.ArrayList;
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

    private RelativeLayout quizLinearLayout; // layout that contains the quiz
    private TextView result;
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

        quizCountriesList = new ArrayList<>();
        shakeAnimation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.incorrect_shake); //***must use getApplicationContext()
        shakeAnimation.setRepeatCount(3);
        flagImageView = (ImageView) findViewById(R.id.imageView2);
        quizLinearLayout = (RelativeLayout) findViewById(R.id.activity_main);
        handler = new Handler();
        quizCountriesList = new ArrayList<>();
        quizCountriesList.add("JAPAN");
        quizCountriesList.add("CHINA");
        correctAnswer = quizCountriesList.get(0);
        correctAnswers = 0;
        btnA = (Button) findViewById(R.id.button);
        btnB = (Button) findViewById(R.id.button2);
        btnC = (Button) findViewById(R.id.button3);
        btnD = (Button) findViewById(R.id.button4);
        result = (TextView) findViewById(R.id.textViewResult);
        questionNumberTextView = (TextView) findViewById(R.id.qTextView);

        questionNumberTextView.setText(getString(R.string.question, (correctAnswers + 1), FLAGS_IN_QUIZ));
        //<string name="question">Question %1$d of %2$d</string>
        //%1 = parameter 1

        final TextView result = (TextView) findViewById(R.id.textView2);

        btnA.setText("Hong Kong");
        btnB.setText("Japan");
        btnC.setText("Britian");
        btnD.setText("China");

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(btnA.getText().toString().equalsIgnoreCase(correctAnswer), btnA);
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(btnB.getText().toString().equalsIgnoreCase(correctAnswer), btnB);
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(btnC.getText().toString().equalsIgnoreCase(correctAnswer), btnC);
            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(btnD.getText().toString().equalsIgnoreCase(correctAnswer), btnD);
            }
        });
    }

    private void animate(boolean animateOut) {
// prevent animation into the the UI for the first flag
        // if (correctAnswers == 0)
        //     return;
        // calculate center x and center y
        int centerX = (quizLinearLayout.getLeft() +
                quizLinearLayout.getRight()) / 2; // calculate center x
        int centerY = (quizLinearLayout.getTop() +
                quizLinearLayout.getBottom()) / 2; // calculate center y
        // calculate animation radius
        int radius = Math.max(quizLinearLayout.getWidth(),
                quizLinearLayout.getHeight());
        Animator animator;
// if the quizLinearLayout should animate out rather than in
        if (animateOut) {
// create circular reveal animation
            animator = ViewAnimationUtils.createCircularReveal(
                    quizLinearLayout, centerX, centerY, radius, 0);
            animator.addListener(
                    new AnimatorListenerAdapter() {
                        // called when the animation finishes
                        @Override
                        public void onAnimationEnd(Animator animation) {
// loadNextFlag();
                            result.setText("");
                            correctAnswer = quizCountriesList.get(1);
//                            flagImageView.setImageResource(R.drawable.achina);
                        }
                    }
            );
        } else { // if the quizLinearLayout should animate in
            animator = ViewAnimationUtils.createCircularReveal(
                    quizLinearLayout, centerX, centerY, 0, radius);
        }
        animator.setDuration(500); // set animation duration to 500 ms
        animator.start(); // start the animation
    }

    private void checkAnswer(boolean correct, Button buttonName) {
        totalGuesses++;

        if (correct) {
            correctAnswers++;
            result.setText("Your answer is correct!");
            handler.postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            animate(true);
                        }
                    }, 2000); //2000 milliseconds for 2-second delay
            //result.setText("");
            btnA.setEnabled(true);
            btnB.setEnabled(true);
            btnC.setEnabled(true);
            btnD.setEnabled(true);

// if the user has correctly identified FLAGS_IN_QUIZ flags
            if (correctAnswers == FLAGS_IN_QUIZ) {
                Toast.makeText(MainActivity.this, getString(R.string.results,
                        totalGuesses, (correctAnswers / (double) totalGuesses) * 100), Toast.LENGTH_SHORT).show();

            } else {
                questionNumberTextView.setText(getString(R.string.question, (correctAnswers), FLAGS_IN_QUIZ));
            }
        } else {

            result.setText("Incorrect!");
            flagImageView.startAnimation(shakeAnimation);//play shake
            buttonName.setEnabled(false);
        }
    }
}
