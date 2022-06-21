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
import android.text.style.IconMarginSpan;
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
    private static int[] gameCharColor = new int[26];
    private static int[] gameCharColor2 = new int[26];
    private int rowOfTextBox = 0;
    private int rowOfTextBox2 = 0;
    private String previousAnswers = "";
    private String previousAnswers2 = "";

    private String answer = "DONOR";
    private String answersFromFile = "";
    private String _inputString = "";
    private String messageString = "";
    static private int componentNow = 0;
    private boolean gameModeIsSelf = true;
    private boolean isFinished = false;
    private boolean isPlayerAWin = false;

    private TextView[] _textBoxes = new TextView[30];
    private Button[] _charButtons = new Button[26];
    private TextView playerText;
    AlertDialog.Builder _builder;

    //private Button _mode;

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

        playerText = findViewById(R.id.playerText);

        setMultipleTimesDialog();
        //read answers
        if(answersFromFile.isEmpty()){
            readAnswersFromFile();
        }

        getRandomAnswer();
    }

    public void charClick(View view){
        //Log.d("onclick", "char : " + ((Button)view).getText().toString());

        if(_inputString.length() < 5 && !isFinished){
            if(componentNow == 0){
                _textBoxes[rowOfTextBox + _inputString.length()].setText(((Button)view).getText().toString());
                _inputString = _inputString + ((Button)view).getText().toString();
            }else {
                _textBoxes[rowOfTextBox2 + _inputString.length()].setText(((Button)view).getText().toString());
                _inputString = _inputString + ((Button)view).getText().toString();
            }
        }
    }

    public void restartClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("新的一局");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //Log.d("onclick", "restart");
                resetGames();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void modeClick(View view){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("切換模式");
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.d("click", "mode button");
                if(((Button)view).getText().length() == 4){
                    ((Button)view).setText("battle");
                    gameModeIsSelf = false;
                    playerText.setVisibility(View.VISIBLE);
                }else{
                    ((Button)view).setText("self");
                    gameModeIsSelf = true;
                    playerText.setVisibility(View.INVISIBLE);
                }
                resetGames();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    public void cancelClick(View view){
        //Log.d("onclick", "cancel");
        if(_inputString.length() > 0){
            if(componentNow == 0){
                _textBoxes[_inputString.length() + rowOfTextBox - 1].setText("");
                _inputString = _inputString.substring(0, _inputString.length() - 1);
            }else{
                _textBoxes[_inputString.length() + rowOfTextBox2 - 1].setText("");
                _inputString = _inputString.substring(0, _inputString.length() - 1);
            }
        }
    }

    public void enterClick(View view){
        //Log.d("onclick", "enter");
        if(_inputString.length() != 5){
            //Log.d("debug", "length != 5");
            return;
        }

        if(gameModeIsSelf){
            int[] inputColor =  getColorWithInput(_inputString.toUpperCase(), answer.toUpperCase());
            previousAnswers += _inputString.toUpperCase();

            //_answerText.append(_inputString + " : ");
            upDateTextBox(inputColor);
            upDateCharButtons();
            boolean gameIsWin = checkGameIsWin(inputColor);
            _inputString = "";

            if(!(rowOfTextBox == 25) && !(gameIsWin)){
                rowOfTextBox += 5;
            }else{
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
        }else{
            if(componentNow == 0){
                int[] inputColor =  getColorWithInput(_inputString.toUpperCase(), answer.toUpperCase());
                previousAnswers += _inputString.toUpperCase();

                upDateTextBox(inputColor);
                upDateCharButtons();
                boolean gameIsWin = checkGameIsWin(inputColor);

                _inputString = "";
                isPlayerAWin = gameIsWin;
                componentNow = 1;
                rowOfTextBox += 5;
                playerText.setText("Player B");
                _builder.create().show();
            }else{
                int[] inputColor =  getColorWithInput(_inputString.toUpperCase(), answer.toUpperCase());
                previousAnswers2 += _inputString.toUpperCase();

                upDateTextBox(inputColor);
                upDateCharButtons();
                boolean gameIsWin = checkGameIsWin(inputColor);
                _inputString = "";

                if(isPlayerAWin){
                    isFinished = true;
                    if(gameIsWin){
                        //同回合猜到
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("遊戲結束");

                        builder.setMessage("恭喜兩位勢均力敵\n答案就是 : " + answer);
                        messageString = "wordle謎底 : " + answer  + "\n" + (rowOfTextBox2 / 5 + 1) + "次就答對了";


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
                    }else{
                        //a贏
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("遊戲結束");

                        builder.setMessage("恭喜player A\n答案就是 : " + answer);
                        messageString = "wordle謎底 : " + answer  + "\n" + (rowOfTextBox2 / 5 + 1) + "次就答對了";


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
                }else{
                    if(gameIsWin){
                        //b贏
                        isFinished = true;
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setTitle("遊戲結束");

                        builder.setMessage("恭喜player B\n答案就是 : " + answer);
                        messageString = "wordle謎底 : " + answer  + "\n" + (rowOfTextBox2 / 5 + 1) + "次就答對了";


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
                }

                if(!(rowOfTextBox2 == 25)){
                    componentNow = 0;
                    rowOfTextBox2 += 5;
                    playerText.setText("Player A");
                    _inputString = "";
                    if(!isFinished){
                        _builder.create().show();
                    }
                }else{
                    //結束了
                    isFinished = true;
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("遊戲結束");

                    builder.setMessage("兩位都沒猜到\n答案就是 : " + answer);
                    messageString = "wordle謎底 : " + answer + "下一場會更好";


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
                    resetGames();
                }
            }
        }
    }

    void setMultipleTimesDialog(){
        _builder = new AlertDialog.Builder(this);
        _builder.setTitle("換人");

        _builder.setMessage("請看完你的提示再點下一位");

        _builder.setPositiveButton("下一位", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                upDateCharButtons();
                upDateAllTextBox();
                dialogInterface.dismiss();
            }
        });
    }

    void resetGames() {
        getRandomAnswer();

        playerText.setText("Player A");
        gameCharColor = new int[26];
        gameCharColor2 = new int[26];
        rowOfTextBox = 0;
        rowOfTextBox2 = 0;
        componentNow = 0;
        isFinished = false;
        isPlayerAWin = false;
        _inputString = "";
        messageString = "";
        previousAnswers = "";
        previousAnswers2 = "";
        for(int n = 0; n < _textBoxes.length;n++){
            _textBoxes[n].setText("");
            _textBoxes[n].setTextColor(Color.BLACK);
            _textBoxes[n].setBackgroundColor(Color.WHITE);
            _textBoxes[n].setBackground(getDrawable(R.drawable.text_box_border));
        }

        for(int n = 0; n < _charButtons.length;n++){
            _charButtons[n].setTextColor(Color.BLACK);
            _charButtons[n].setBackgroundColor(Color.LTGRAY);
        }
    }

    boolean checkGameIsWin(int[] inputColor){
        for(int n = 0; n < answer.length();n++){
            if(!(inputColor[n] == LETTER_GREEN)){
                return false;
            }
        }
        return true;
    }

    void upDateTextBox(int[] inputColor) {
        if(componentNow == 0){
            for(int n = 0; n < answer.length(); n++){
                if(inputColor[n] == LETTER_YELLOW){
                    _textBoxes[n + rowOfTextBox].setBackgroundColor(Color.YELLOW);
                    _textBoxes[n + rowOfTextBox].setTextColor(Color.WHITE);
                }else if(inputColor[n] == LETTER_GREEN){
                    _textBoxes[n + rowOfTextBox].setBackgroundColor(Color.GREEN);
                    _textBoxes[n + rowOfTextBox].setTextColor(Color.WHITE);
                }else if(inputColor[n] == LETTER_GREY){
                    _textBoxes[n + rowOfTextBox].setBackgroundColor(Color.DKGRAY);
                    _textBoxes[n + rowOfTextBox].setTextColor(Color.WHITE);
                }
            }
        }else{
            for(int n = 0; n < answer.length(); n++){
                if(inputColor[n] == LETTER_YELLOW){
                    _textBoxes[n + rowOfTextBox2].setBackgroundColor(Color.YELLOW);
                    _textBoxes[n + rowOfTextBox2].setTextColor(Color.WHITE);
                }else if(inputColor[n] == LETTER_GREEN){
                    _textBoxes[n + rowOfTextBox2].setBackgroundColor(Color.GREEN);
                    _textBoxes[n + rowOfTextBox2].setTextColor(Color.WHITE);
                }else if(inputColor[n] == LETTER_GREY){
                    _textBoxes[n + rowOfTextBox2].setBackgroundColor(Color.DKGRAY);
                    _textBoxes[n + rowOfTextBox2].setTextColor(Color.WHITE);
                }
            }
        }
    }

    void upDateAllTextBox() {
        Log.d("debug_p", previousAnswers);
        Log.d("debug_p2", previousAnswers2);
        Log.d("debug_row", String.valueOf(rowOfTextBox));
        Log.d("debug_row2", String.valueOf(rowOfTextBox2));
        int[] colorFromString = new int[5];
        if(componentNow == 0){
            Log.d("debug", "com = 0");
            int count = rowOfTextBox / 5;
            for(int i = 0; i < count;i++){
                colorFromString = getColorWithInput(previousAnswers.substring(i*5, i*5 + 5), answer);
                for(int n = i*5; n < i*5 + 5;n++){
                    _textBoxes[n].setBackground(getDrawable(R.drawable.text_box_border));
                    _textBoxes[n].setText(previousAnswers.substring(n, n+1));
                    if(colorFromString[n - i*5] == LETTER_GREY){
                        _textBoxes[n].setBackgroundColor(Color.DKGRAY);
                    }else if(colorFromString[n - i*5] == LETTER_GREEN){
                        _textBoxes[n].setBackgroundColor(Color.GREEN);
                    }else if(colorFromString[n - i*5] == LETTER_YELLOW){
                        _textBoxes[n].setBackgroundColor(Color.YELLOW);
                    }
                    _textBoxes[n].setTextColor(Color.WHITE);
                }
            }
        }else{
            Log.d("debug", "com = 1");
            int[] inputColor = new int[5];
            for(int n = rowOfTextBox2; n < rowOfTextBox2 + 5;n++){
                _textBoxes[n].setText("");
                _textBoxes[n].setTextColor(Color.BLACK);
                _textBoxes[n].setBackgroundColor(Color.WHITE);
                _textBoxes[n].setBackground(getDrawable(R.drawable.text_box_border));
            }
            int count = rowOfTextBox2 / 5;
            for(int i = 0; i < count;i++){
                colorFromString = getColorWithInput(previousAnswers2.substring(i*5, i*5 + 5), answer);
                for(int n = i*5; n < i*5 + 5;n++){
                    _textBoxes[n].setBackground(getDrawable(R.drawable.text_box_border));
                    _textBoxes[n].setText(previousAnswers2.substring(n, n+1));
                    if(colorFromString[n - i*5] == LETTER_GREY){
                        _textBoxes[n].setBackgroundColor(Color.DKGRAY);
                    }else if(colorFromString[n - i*5] == LETTER_GREEN){
                        _textBoxes[n].setBackgroundColor(Color.GREEN);
                    }else if(colorFromString[n - i*5] == LETTER_YELLOW){
                        _textBoxes[n].setBackgroundColor(Color.YELLOW);
                    }
                    _textBoxes[n].setTextColor(Color.WHITE);
                }
            }
        }
    }

    void upDateCharButtons() {
        if(componentNow == 0){
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
                }else{
                    _charButtons[n].setBackgroundColor(Color.LTGRAY);
                    _charButtons[n].setTextColor(Color.BLACK);
                }
            }
        }else{
            for(int n = 0; n < _charButtons.length;n++){
                if(gameCharColor2[n] != 0){
                    if(gameCharColor2[n] == 1){
                        _charButtons[n].setBackgroundColor(Color.YELLOW);
                        _charButtons[n].setTextColor(Color.WHITE);
                    }else if(gameCharColor2[n] == 2){
                        _charButtons[n].setBackgroundColor(Color.GREEN);
                        _charButtons[n].setTextColor(Color.WHITE);
                    }else{
                        _charButtons[n].setBackgroundColor(Color.DKGRAY);
                        _charButtons[n].setTextColor(Color.WHITE);
                    }
                }else{
                    _charButtons[n].setBackgroundColor(Color.LTGRAY);
                    _charButtons[n].setTextColor(Color.BLACK);
                }
            }
        }
    }

    void readAnswersFromFile(){
        InputStream myInputStream;
        try {
            myInputStream = getAssets().open(answersFileName);
            int size = myInputStream.available();
            byte[] buffer = new byte[size];
            myInputStream.read(buffer);
            myInputStream.close();
            answersFromFile = new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void getRandomAnswer() {
        if(answersFromFile.isEmpty()){
            Log.d("debug", "read file again");
            readAnswersFromFile();
        }

        Random random = new Random();
        int randomNumber = random.nextInt(answersFromFile.length() - 6);
        Log.d("debug", "random : " + randomNumber);
        while(randomNumber < answersFromFile.length()){
            if(!Character.isLetter(answersFromFile.charAt(randomNumber))){
                int nextChar = randomNumber + 1;
                while(!Character.isLetter(answersFromFile.charAt(nextChar))){
                    nextChar++;
                }
                answer = answersFromFile.substring(nextChar, nextChar + 5);
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
        if(componentNow == 0){
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
        }else{
            for(int n = 0;n < answerToCheck.length(); n++){
                if(charInCorrectSpot(inputToCheck, answerToCheck, n)){
                    returnColor[n] = LETTER_GREEN;
                    gameCharColor2[inputToCheck.charAt(n) - charA] = LETTER_GREEN;
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
                    if(!(gameCharColor2[inputToCheck.charAt(n) - charA] == LETTER_GREEN)){
                        gameCharColor2[inputToCheck.charAt(n) - charA] = LETTER_YELLOW;
                    }
                }else{
                    if(returnColor[n] == 0){
                        returnColor[n] = LETTER_GREY;
                    }
                    //Log.d("debug", "charAt " + String.valueOf(inputToCheck.charAt(n)));
                    if(gameCharColor2[inputToCheck.charAt(n) - charA] == LETTER_BLANK){
                        gameCharColor2[inputToCheck.charAt(n) - charA] = LETTER_GREY;
                    }
                }
            }
        }


        return returnColor;
    }
}

