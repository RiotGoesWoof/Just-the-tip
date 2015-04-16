package edu.carthage.zorpix.phonechooser;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


public class ViewWaiters extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_waiters);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_view_waiters, menu);
        return true;
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

        if (id == R.id.action_DisplayTips) {
            Log.d("MainActivity", "Displaying Tips");
            Intent intent = new Intent(this, DisplayTips.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void getwait(View view)
    {
        final EditText editText = (EditText) findViewById(R.id.name);
        String name = editText.getText().toString();
        final EditText editText3 = (EditText) findViewById(R.id.restaurant);
        String restaurant = editText3.getText().toString();
        String notes = "hi";
        String tipercent = "hi";
        if (!DBHelper.getInstance(this).checkWaiter(name, tipercent, restaurant, notes))
        {
            ListView Listy = (ListView)findViewById(R.id.TipList);
            TextView Texty = (TextView)findViewById(R.id.notes);
            SimpleCursorAdapter tipAdapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_1, DBHelper.getInstance(this).getTips(name, restaurant), new String[]{"_id"}, new int[]{android.R.id.text1}, 0);
            Listy.setAdapter(tipAdapter);

            Cursor notetext = (Cursor) DBHelper.getInstance(this).getNotes(name, restaurant);
            notetext.moveToFirst();
            Texty.setText(notetext.getString(0));
        }
        else
        {
            Log.d("DisplayTips", "Well that's not a relief." + name);
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Uh oh");
            alertDialog.setMessage("A waiter with that name at that restaurant does not exist!");
            alertDialog.setButton("Try again", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    editText.setText("");
                    editText3.setText("");
                }
            });
            alertDialog.show();
        }
    }
}
