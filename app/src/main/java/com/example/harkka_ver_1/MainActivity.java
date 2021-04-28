/*
Project work
SleepTracker
Joona Saloniemi
*/

package com.example.harkka_ver_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Button button;
    TextView text;
    Date startSleepDate;
    String startSleepString;
    Context context;
    String coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        text = (TextView) findViewById(R.id.textView);

        // For WriteAndReadData class:
        context = MainActivity.this;

        // Set spinner:
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        // Set start and stop button.
        button = (Button) findViewById((R.id.startstopButton));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ButtonText = button.getText().toString();
                if (ButtonText.equals("Start sleeping")) {
                    startButton();
                    button.setText("Stop sleeping");
                } else {
                    stopButton();
                    button.setText("Start sleeping");
                }
            }
        });
    }

    /*
    This method calls getStartTime method from TimeCalculator class to get datetime and transfer it to string format.
    Then calls Sunrise class, startSleepString method to set text by sending coordinates string and get time string.
    */
    public void startButton () {

        TimeCalculator timecalculator = TimeCalculator.getInstance();
        startSleepDate = timecalculator.getStarTime();

        // Change startSleepDate to readable startSleepString.
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss aa");
        startSleepString = dateFormat.format(startSleepDate);

        // Return the time of sunrise from Sunrise class.
        Sunrise sunsetsunrise = new Sunrise();
        text.setText("You went to sleep: " + startSleepString + "\n\nSun will rise: " + sunsetsunrise.readJSON(coordinates));
    }

    /*
    This method calls TimeCalculator class, getStopTime and timeBetween methods to get timeBetweenString and stopSleepDate.
    Then it try to check if sleeping_data.txt already exist by trying opening it.
    If not, it send context, tartSleepDate, stopSleepDate, timeBetweenString to writeFile method from WriteAndReadData class.
    If yes, it also send context first to readFile method.
    */
    public void stopButton () {

        TimeCalculator timecalculator = TimeCalculator.getInstance();

        Date stopSleepDate = timecalculator.getStopTime();

        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss aa");
        String stopSleepString = dateFormat.format(stopSleepDate);

        String timeBetweenString = timecalculator.timeBetween();

        text.setText("You went to sleep: " + startSleepString + "\n\nYou wake up: " + stopSleepString + "\n\nSleeping time: " + timeBetweenString);

        // Check if the file is already existing. If yes, file have to read first.
        try {
            context.openFileInput("sleeping_data.txt");

            System.out.println("FILE FOUNDED");
            WriteAndReadData writeandreaddata = new WriteAndReadData();
            writeandreaddata.readFile(context);
            writeandreaddata.writeFile(context, startSleepDate, stopSleepDate, timeBetweenString);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("NO FILE");
            WriteAndReadData writeandreaddata = new WriteAndReadData();
            writeandreaddata.writeFile(context, startSleepDate, stopSleepDate, timeBetweenString);
        }

    }

    // This method gets chosen item from the spinner and transform it to string format and send it to CoordinatesOfCities class.
    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String city = adapterView.getItemAtPosition(i).toString();
        // Return coordinates of cities from CoordinatesOfCities class:
        CoordinatesOfCities coordinatesofcities = new CoordinatesOfCities();
        coordinates = coordinatesofcities.coordinates(city);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
    }

    // This method open new DataActivity.
    public void loadDataActivity (View v) {
        Intent intent = new Intent(MainActivity.this, DataActivity.class);
        startActivity(intent);
    }
}