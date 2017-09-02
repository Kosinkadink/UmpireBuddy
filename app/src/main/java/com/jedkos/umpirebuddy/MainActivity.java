package com.jedkos.umpirebuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private UmpireBuddyHelper umpireBuddyHelper;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resources = getResources();
        // Create helper
        umpireBuddyHelper = new UmpireBuddyHelper();
        // Initialize texts
        updateAllText();
    }

    /* When the screen orientation changes, data should persist */
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save umpireBuddyHelper data to be restored
        savedInstanceState.putInt("strikes",umpireBuddyHelper.getStrikeCount());
        savedInstanceState.putInt("balls",umpireBuddyHelper.getBallCount());
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore umpireBuddyHelper data
        umpireBuddyHelper.setStrikeCount(savedInstanceState.getInt("strikes"));
        umpireBuddyHelper.setBallCount(savedInstanceState.getInt("balls"));
        // Update texts to restore visible state
        updateAllText();
    }

    /* Action for Strike button */
    public void incrementStrikeCount(View view) {
        umpireBuddyHelper.incrementStrikeCount();
        updateStrikeText();
    }

    /* Action for Ball button */
    public void incrementBallCount(View view) {
        umpireBuddyHelper.incrementBallCount();
        updateBallText();
    }

    /* Update Texts */
    private void updateAllText() {
        updateStrikeText();
        updateBallText();
    }

    private void updateStrikeText() {
        TextView strikeTextView = (TextView)findViewById(R.id.strikeView);
        strikeTextView.setText(getStrikeText());
        if (umpireBuddyHelper.enoughStrikes()) {
            displayOutDialog();
        }
    }

    private void updateBallText() {
        TextView ballTextView = (TextView)findViewById(R.id.ballView);
        ballTextView.setText(getBallText());
        if (umpireBuddyHelper.enoughBalls()) {
            displayWalkDialog();
        }
    }

    /* Generate Text Strings */
    public String getStrikeText() {
        return resources.getString(R.string.strike) + ": " + umpireBuddyHelper.getStrikeCount();
    }

    public String getBallText() {
        return resources.getString(R.string.ball) + ": " + umpireBuddyHelper.getBallCount();
    }

    /* Create a Dialog and display it*/
    private void displayOutDialog() {
        displayDialog(getString(R.string.out_exclaim));
    }

    private void displayWalkDialog() {
        displayDialog(getString(R.string.walk_exclaim));
    }

    private void displayDialog(String message) {
        // Make sure dialog can't be "skipped" by clicking outside of it with setCancelable(false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        umpireBuddyHelper.resetAllCounts();
                        updateAllText();
                    }
                })
                .setCancelable(false);
        builder.create();
        builder.show();
    }

}
