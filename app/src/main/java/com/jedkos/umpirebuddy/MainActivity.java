package com.jedkos.umpirebuddy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String PREFERENCES_NAME = "UmpireBuddyPreferences";
    private UmpireBuddyHelper umpireBuddyHelper;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resources = getResources();
        // Create helper
        umpireBuddyHelper = new UmpireBuddyHelper();

        // ~~~ Add Button Listeners ~~~

        // Strike Button
        final Button strikeButton = (Button)findViewById(R.id.strikeButton);
        strikeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                incrementStrikeCount(v);
            }
        });
        // Ball Button
        final Button ballButton = (Button)findViewById(R.id.ballButton);
        ballButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                incrementBallCount(v);
            }
        });
        // Get saved preferences
        SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
        umpireBuddyHelper.setOutCount(settings.getInt("outs", 0));
        // Initialize texts
        updateAllText();
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFERENCES_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("outs", umpireBuddyHelper.getOutCount());
        editor.apply();
    }

    // Create options menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }

    // Add options menu actions
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.resetMenuButton:
                umpireBuddyHelper.resetAllCounts();
                updateAllText();
                return true;
            case R.id.aboutMenuButton:
                // start AboutActivity
                this.startActivity(new Intent(this, AboutActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
        updateOutText();
    }

    private void updateStrikeText() {
        TextView strikeTextView = (TextView)findViewById(R.id.strikeView);
        strikeTextView.setText(getStrikeText());
        if (umpireBuddyHelper.enoughStrikes()) {
            umpireBuddyHelper.incrementOutCount();
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

    private void updateOutText() {
        TextView outTextView = (TextView)findViewById(R.id.outView);
        outTextView.setText(getOutText());
    }

    /* Generate Text Strings */
    public String getStrikeText() {
        return resources.getString(R.string.strike) + ": " + umpireBuddyHelper.getStrikeCount();
    }

    public String getBallText() {
        return resources.getString(R.string.ball) + ": " + umpireBuddyHelper.getBallCount();
    }

    public String getOutText() {
        return resources.getString(R.string.total_outs) + ": " + umpireBuddyHelper.getOutCount();
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
