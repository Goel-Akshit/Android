package in.akshit;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.gridlayout.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    boolean isRed = true;
    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    int[] gameState = {2,2,2,2,2,2,2,2,2}; //yellow: 0 red:1
    boolean active = true;
    int ringsSpent = 0;
    boolean winnerFound = false;

    public void dropIt(View view){
        ImageView myView = (ImageView) view;
        if( active && gameState[Integer.parseInt(myView.getTag().toString())] == 2){
            MyAnimate(view);
        }

    }

    public void MyAnimate(View view){
        ImageView img = (ImageView) view;
        TextView winnerHolder = (TextView) findViewById(R.id.textView);
        Button rePlay = (Button) findViewById(R.id.button);
        img.setTranslationY(-1500);
        if(isRed){
            gameState[Integer.parseInt(img.getTag().toString())] = 0;
            img.setImageResource(R.drawable.yellow);
            isRed = false;
        }else{
            gameState[Integer.parseInt(img.getTag().toString())] = 1;
            img.setImageResource(R.drawable.red);
            isRed = true;
        }
        img.animate().translationYBy(1500).rotationBy(3600).setDuration(300);
        ringsSpent += 1;
        if(ringsSpent >= 5){
            if(hasWon()){
                    winningMsg(winnerHolder, rePlay, (isRed)? "Red Won": "Yellow Won"); //ternary operator here
            }
        }

        if(ringsSpent == 9 && !winnerFound){
            winningMsg(winnerHolder, rePlay, "Draw");
        }
    }

    public boolean hasWon() {
        for (int[] winningPosition : winningPositions) {
            if (gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] != 2)
                return true;
        }
        return false;
    }

    public void winningMsg(TextView winnerHolder, Button rePlay, String msg){
        active = false;
        winnerFound= true;
        winnerHolder.setVisibility(View.VISIBLE);
        rePlay.setVisibility(View.VISIBLE);
        winnerHolder.setText(msg);
    }

    public void rePlayGame(View view){
        isRed = true;
        Arrays.fill(gameState, 2); //nice trick to reset all value to same value suggested by intellij
        active = true;
        ringsSpent = 0;
        winnerFound=false;
        TextView winnerHolder = (TextView) findViewById(R.id.textView);
        Button rePlay = (Button) findViewById(R.id.button);
        winnerHolder.setVisibility(View.INVISIBLE);
        rePlay.setVisibility(View.INVISIBLE);
        GridLayout grid = (GridLayout) findViewById(R.id.gridLayout);
        for(int i=0; i<grid.getChildCount(); i++){
            ImageView counter = (ImageView) grid.getChildAt(i);
            counter.setImageDrawable(null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}