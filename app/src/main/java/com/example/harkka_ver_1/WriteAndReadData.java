/*
Project work
SleepTracker
Joona Saloniemi
*/


package com.example.harkka_ver_1;


import android.content.Context;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class WriteAndReadData {

    String sleepingDataOld = null;
    Context context = null;

    // This method write xml text to txt file.
    public void writeFile (Context context, Date startSleepDate, Date stopSleepDate, String timeBetweenString) {
        OutputStreamWriter osw = null;

        DateFormat dateFormatStart = new SimpleDateFormat("hh:mm:ss aa MM/dd/yyyy");
        String startSleepString = dateFormatStart.format(startSleepDate);

        DateFormat dateFormatStop = new SimpleDateFormat("hh:mm:ss aa MM/dd/yyyy");
        String stopSleepString = dateFormatStop.format(stopSleepDate);

        String sleepingDataStart = "<sleepingdata>\n";
        String sleepingDataMid = "   <start>" + startSleepString + "</start>\n" +
                "   <stop>" + stopSleepString + "</stop>\n" +
                "   <time>" + timeBetweenString + "</time>\n";
        String sleepingDataEnd = "</sleepingdata>";

        String s;

        if (sleepingDataOld != null) {
            s = sleepingDataStart + sleepingDataOld + sleepingDataMid + sleepingDataEnd;
        } else {
            s = sleepingDataStart + sleepingDataMid + sleepingDataEnd;
        }

        try {
            osw = new OutputStreamWriter(context.openFileOutput("sleeping_data.txt", context.MODE_PRIVATE));
            osw.write(s);
            osw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("WRITING DONE");
        }
    }

    // This method read the file first for save the old data to sleepingDataOld string.
    public void readFile (Context context) {
        String output = "";
        try {
            InputStream ins = context.openFileInput("sleeping_data.txt");

            DocumentBuilder docB = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document xmlDoc = docB.parse(ins);

            NodeList nodeList = xmlDoc.getElementsByTagName("start");
            String sdo = null;
            for (int i = 0; i < nodeList.getLength(); i++) {
                String srt = xmlDoc.getElementsByTagName("start").item(i).getTextContent();
                String stp = xmlDoc.getElementsByTagName("stop").item(i).getTextContent();
                String tim = xmlDoc.getElementsByTagName("time").item(i).getTextContent();

                if (sdo != null) {
                    sdo = sdo + "   <start>" + srt + "</start>\n" + "   <stop>" + stp + "</stop>\n" + "   <time>" + tim + "</time>\n";
                } else {
                    sdo = "   <start>" + srt + "</start>\n" + "   <stop>" + stp + "</stop>\n" + "   <time>" + tim + "</time>\n";
                }
            }

            if (sleepingDataOld != null) {
                sleepingDataOld = sleepingDataOld + sdo;
            } else {
                sleepingDataOld = sdo;
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } finally {
            System.out.println("READING DONE");
        }
    }
}
