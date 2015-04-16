package edu.carthage.zorpix.phonechooser;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;


public class TipActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);

        String message = getIntent().getStringExtra("tippy");

        double dattipdo = Double.parseDouble(message);

        DecimalFormat df = new DecimalFormat("#.00");

        ((TextView)findViewById(R.id.okaytip)).setText("$"+df.format(dattipdo* .1));
        ((TextView)findViewById(R.id.goodtip)).setText("$"+df.format(dattipdo * .15));
        ((TextView)findViewById(R.id.greattip)).setText("$"+df.format(dattipdo * .2));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_GetTip) {
            Log.d("AddWaiter", "Calculating tips");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_AddWaiter) {
            Log.d("MainActivity", "Adding a waiter");
            Intent intent = new Intent(this, AddWaiter.class);
            startActivity(intent);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_TipWaiter) {
            Log.d("MainActivity", "Tipping a waiter");
            Intent intent = new Intent(this, TipOldWaiter.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
