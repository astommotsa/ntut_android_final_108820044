package com.example.final_108820044;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.provider.FontRequest;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Random;



public class MainActivity extends AppCompatActivity {
    private static final int LETTER_GREY = -1;
    private static final int LETTER_BLANK = 0;
    private static final int LETTER_YELLOW = 1;
    private static final int LETTER_GREEN = 2;
    private static final String answersFileName = "wordle_answers.txt";

    private  static int[] gameCharColor = new int[26];
    private  int rowOfTextBox = 0;
    private String answer = "DONOR";
    private String textAnswers = "";
    private String _inputText = "";
    private boolean isFinished = false;
    private String messageString = "";

    private TextView[] _textBoxes = new TextView[30];
    private Button[] _charButtons = new Button[26];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Find the TextViews by ID
        //_answerText = findViewById(R.id.answerText);

        _textBoxes[0] = findViewById(R.id.textBox);
        _textBoxes[1] = findViewById(R.id.textBox2);
        _textBoxes[2] = findViewById(R.id.textBox3);
        _textBoxes[3] = findViewById(R.id.textBox4);
        _textBoxes[4] = findViewById(R.id.textBox5);
        _textBoxes[5] = findViewById(R.id.textBox6);
        _textBoxes[6] = findViewById(R.id.textBox7);
        _textBoxes[7] = findViewById(R.id.textBox8);
        _textBoxes[8] = findViewById(R.id.textBox9);
        _textBoxes[9] = findViewById(R.id.textBox10);
        _textBoxes[10] = findViewById(R.id.textBox11);
        _textBoxes[11] = findViewById(R.id.textBox12);
        _textBoxes[12] = findViewById(R.id.textBox13);
        _textBoxes[13] = findViewById(R.id.textBox14);
        _textBoxes[14] = findViewById(R.id.textBox15);
        _textBoxes[15] = findViewById(R.id.textBox16);
        _textBoxes[16] = findViewById(R.id.textBox17);
        _textBoxes[17] = findViewById(R.id.textBox18);
        _textBoxes[18] = findViewById(R.id.textBox19);
        _textBoxes[19] = findViewById(R.id.textBox20);
        _textBoxes[20] = findViewById(R.id.textBox21);
        _textBoxes[21] = findViewById(R.id.textBox22);
        _textBoxes[22] = findViewById(R.id.textBox23);
        _textBoxes[23] = findViewById(R.id.textBox24);
        _textBoxes[24] = findViewById(R.id.textBox25);
        _textBoxes[25] = findViewById(R.id.textBox26);
        _textBoxes[26] = findViewById(R.id.textBox27);
        _textBoxes[27] = findViewById(R.id.textBox28);
        _textBoxes[28] = findViewById(R.id.textBox29);
        _textBoxes[29] = findViewById(R.id.textBox30);

        _charButtons[0] = findViewById(R.id.charButton);
        _charButtons[1] = findViewById(R.id.charButton2);
        _charButtons[2] = findViewById(R.id.charButton3);
        _charButtons[3] = findViewById(R.id.charButton4);
        _charButtons[4] = findViewById(R.id.charButton5);
        _charButtons[5] = findViewById(R.id.charButton6);
        _charButtons[6] = findViewById(R.id.charButton7);
        _charButtons[7] = findViewById(R.id.charButton8);
        _charButtons[8] = findViewById(R.id.charButton9);
        _charButtons[9] = findViewById(R.id.charButton10);
        _charButtons[10] = findViewById(R.id.charButton11);
        _charButtons[11] = findViewById(R.id.charButton12);
        _charButtons[12] = findViewById(R.id.charButton13);
        _charButtons[13] = findViewById(R.id.charButton14);
        _charButtons[14] = findViewById(R.id.charButton15);
        _charButtons[15] = findViewById(R.id.charButton16);
        _charButtons[16] = findViewById(R.id.charButton17);
        _charButtons[17] = findViewById(R.id.charButton18);
        _charButtons[18] = findViewById(R.id.charButton19);
        _charButtons[19] = findViewById(R.id.charButton20);
        _charButtons[20] = findViewById(R.id.charButton21);
        _charButtons[21] = findViewById(R.id.charButton22);
        _charButtons[22] = findViewById(R.id.charButton23);
        _charButtons[23] = findViewById(R.id.charButton24);
        _charButtons[24] = findViewById(R.id.charButton25);
        _charButtons[25] = findViewById(R.id.charButton26);

        //read answers
        if(textAnswers.isEmpty()){
            readAnswersFromFile();
        }
    }

    public void charClick(View view){
        //Log.d("onclick", "char : " + ((Button)view).getText().toString());

        if(_inputText.length() < 5 && !isFinished){
            _textBoxes[rowOfTextBox + _inputText.length()].setText(((Button)view).getText().toString());
            _inputText = _inputText + ((Button)view).getText().toString();
        }
    }

    public void restartClick(View view){
        //Log.d("onclick", "restart");

        getRandomAnswer();

        _inputText = "";
        //_answerText.setText("");
        rowOfTextBox = 0;
        isFinished = false;
        messageString = "";
        for(int n = 0; n < _textBoxes.length;n++){
            _textBoxes[n].setText("");
            _textBoxes[n].setTextColor(Color.BLACK);
            _textBoxes[n].setBackgroundColor(Color.WHITE);
            _textBoxes[n].setBackground(getDrawable(R.drawable.text_box_border));
        }

        for(int n = 0; n < _charButtons.length;n++){
            _charButtons[n].setTextColor(Color.BLACK);
            _charButtons[n].setBackgroundColor(Color.LTGRAY);
            gameCharColor[n] = LETTER_BLANK;
        }
    }


    public void cancelClick(View view){
        //Log.d("onclick", "cancel");
        if(_inputText.length() > 0){
            _textBoxes[_inputText.length() + rowOfTextBox - 1].setText("");
            _inputText = _inputText.substring(0, _inputText.length() - 1);
        }
    }

    public void enterClick(View view){
        //Log.d("onclick", "enter");
        if(_inputText.length() != 5){
            //Log.d("debug", "length != 5");
            return;
        }

        int[] inputColor =  getColorWithInput(_inputText.toUpperCase(), answer.toUpperCase());

        //_answerText.append(_inputText + " : ");
        boolean gameIsWin = true;
        for(int n = 0; n < answer.length(); n++){
            if(inputColor[n] == LETTER_YELLOW){
                //_answerText.append("1");
                gameIsWin = false;
                _textBoxes[n + rowOfTextBox].setBackgroundColor(Color.YELLOW);
                _textBoxes[n + rowOfTextBox].setTextColor(Color.WHITE);
            }else if(inputColor[n] == LETTER_GREEN){
                //_answerText.append("2");
                _textBoxes[n + rowOfTextBox].setBackgroundColor(Color.GREEN);
                _textBoxes[n + rowOfTextBox].setTextColor(Color.WHITE);
            }else if(inputColor[n] == LETTER_GREY){
                //_answerText.append("-1");
                gameIsWin = false;
                _textBoxes[n + rowOfTextBox].setBackgroundColor(Color.DKGRAY);
                _textBoxes[n + rowOfTextBox].setTextColor(Color.WHITE);
            }
            //_answerText.append(" ");
        }
        //_answerText.append("\n");

        upDateCharButtons();
        _inputText = "";

        if(rowOfTextBox == 25 || gameIsWin){
            isFinished = true;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("遊戲結束");
            //builder.setIcon(R.mipmap.ic_launcher_round);

            if(gameIsWin){
                builder.setMessage("恭喜猜到了\n答案就是 : " + answer);
                messageString = "wordle謎底 : " + answer  + "\n" + (rowOfTextBox / 5 + 1) + "次就答對了";
            }else{
                builder.setMessage("可惜沒猜到\n答案是 : " + answer);
                messageString = "wordle謎底 : " + answer  + "\n" + "沒有答對QQ";
            }


            //確定 取消 一般 這三種按鈕就看你怎麼發揮你想置入的功能囉
            builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.setNegativeButton("複製結果", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    ClipboardManager myClipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("simple text", messageString);
                    myClipboard.setPrimaryClip(clip);
                    dialogInterface.dismiss();
                }
            });
            builder.create().show();
        }
        rowOfTextBox += 5;
    }

    void readAnswersFromFile(){
        InputStream myInputStream;
        try {
            myInputStream = getAssets().open(answersFileName);
            int size = myInputStream.available();
            byte[] buffer = new byte[size];
            myInputStream.read(buffer);
            myInputStream.close();
            textAnswers = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void resetCharButtons() {
        for(int n = 0; n < _charButtons.length;n++){
            _charButtons[n].setTextColor(Color.BLACK);
            _charButtons[n].setBackgroundColor(Color.LTGRAY);
        }
    }

    void upDateCharButtons() {
        for(int n = 0; n < _charButtons.length;n++){
            if(gameCharColor[n] != 0){
                if(gameCharColor[n] == 1){
                    _charButtons[n].setBackgroundColor(Color.YELLOW);
                    _charButtons[n].setTextColor(Color.WHITE);
                }else if(gameCharColor[n] == 2){
                    _charButtons[n].setBackgroundColor(Color.GREEN);
                    _charButtons[n].setTextColor(Color.WHITE);
                }else{
                    _charButtons[n].setBackgroundColor(Color.DKGRAY);
                    _charButtons[n].setTextColor(Color.WHITE);
                }
            }
        }
    }

    void getRandomAnswer() {
        if(textAnswers.isEmpty()){
            Log.d("debug", "read file again");
            readAnswersFromFile();
        }

        Random random = new Random();
        int randomNumber = random.nextInt(textAnswers.length() - 6);
        Log.d("debug", "random : " + randomNumber);
        while(randomNumber < textAnswers.length()){
            if(!Character.isLetter(textAnswers.charAt(randomNumber))){
                int nextChar = randomNumber + 1;
                while(!Character.isLetter(textAnswers.charAt(nextChar))){
                    nextChar++;
                }
                answer = textAnswers.substring(nextChar, nextChar + 5);
                Log.d("debug", "answer change to : " + answer);
                break;
            }else{
                randomNumber++;
            }
        }
    }

    static boolean charInAnswer (char charToCheck, String answerToCheck){
        if(answerToCheck.indexOf(charToCheck) == -1){
            return false;
        }else{
            return true;
        }
    }

    static boolean charInCorrectSpot (String inputToCheck, String answerToCheck, int SpotToCheck){
        return answerToCheck.charAt(SpotToCheck) == inputToCheck.charAt(SpotToCheck);
    }

     static int[] getColorWithInput (String inputToCheck, String answerToCheck){
        String inputWithoutGreen = "", answerWithoutGreen = "";
        int[] returnColor = new int[answerToCheck.length()];
        char charA = 'A';
        for(int n = 0;n < answerToCheck.length(); n++){
            if(charInCorrectSpot(inputToCheck, answerToCheck, n)){
                returnColor[n] = LETTER_GREEN;
                gameCharColor[inputToCheck.charAt(n) - charA] = LETTER_GREEN;
                inputWithoutGreen = inputWithoutGreen + '_';
                answerWithoutGreen = answerWithoutGreen + '=';
            }else{
                inputWithoutGreen = inputWithoutGreen + inputToCheck.charAt(n);
                answerWithoutGreen = answerWithoutGreen + answerToCheck.charAt(n);
            }
        }

        for(int n = 0; n < answerWithoutGreen.length(); n++){
            if(charInAnswer(inputWithoutGreen.charAt(n), answerWithoutGreen)){
                returnColor[n] = LETTER_YELLOW;
                if(!(gameCharColor[inputToCheck.charAt(n) - charA] == LETTER_GREEN)){
                    gameCharColor[inputToCheck.charAt(n) - charA] = LETTER_YELLOW;
                }
            }else{
                if(returnColor[n] == 0){
                    returnColor[n] = LETTER_GREY;
                }
                //Log.d("debug", "charAt " + String.valueOf(inputToCheck.charAt(n)));
                if(gameCharColor[inputToCheck.charAt(n) - charA] == LETTER_BLANK){
                    gameCharColor[inputToCheck.charAt(n) - charA] = LETTER_GREY;
                }
            }
        }

        return returnColor;
    }
}

