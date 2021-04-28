/*
Project work
SleepTracker
Joona Saloniemi
*/


package com.example.harkka_ver_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DataActivity extends AppCompatActivity {

    Context context = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        context = DataActivity.this;
        readFile();
    }

    // This activity show sleeping history data for user to see by reading xml text file.
    private void readFile() {
        try {
            InputStream ins = context.openFileInput("sleeping_data.txt");
            
            TextView dataText = findViewById(R.id.dataTextView);
            dataText.setText("Sleeping data:");

            DocumentBuilder docB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDoc = docB.parse(ins);

            NodeList nodeList = xmlDoc.getElementsByTagName("start");

            for (int i = 0; i < nodeList.getLength(); i++) {
                String srt = xmlDoc.getElementsByTagName("start").item(i).getTextContent();
                String stp = xmlDoc.getElementsByTagName("stop").item(i).getTextContent();
                String tim = xmlDoc.getElementsByTagName("time").item(i).getTextContent();
                dataText.append("\n\nstart: " + srt + "\nstop: " + stp + "\ntime: " + tim);
            }

            dataText.setMovementMethod(new ScrollingMovementMethod());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
    }
}