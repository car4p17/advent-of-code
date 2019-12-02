package com.advent;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Input {
    public static List<String> readInput(int year, int day, String fileName) {
        String yearFolder = "src/com/advent/days/Year" + year + "/";
        String dayFolder = yearFolder + "Day" + day + "/";
        String inputFile = dayFolder + fileName;
        ArrayList<String> lines = new ArrayList<>();
        try {
            File file = new File(inputFile);
            // If the file isn't already downloaded then download it automatically
            if (!file.exists()) {
                Logger.log("Downloading " + inputFile);
                // Create folders
                File dayFile = new File(dayFolder);
                if (!dayFile.exists()) {
                    dayFile.mkdir();
                }
                // Download file from internet
                StringBuilder result = new StringBuilder();
                URL url = new URL("https://adventofcode.com/" + year + "/day/" + day + "/input");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("cookie", "session=" + getSessionIdFromConfig());
                BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String line;
                try (
                    FileWriter fw = new FileWriter(inputFile);
                    BufferedWriter bw = new BufferedWriter(fw);
                    PrintWriter out = new PrintWriter(bw)
                )
                {
                    while ((line = rd.readLine()) != null) {
                        out.println(line);
                    }
                } catch (IOException e) {
                    Logger.log("Error while Downloading Input");
                    Logger.error(e);
                }
                rd.close();
            }
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str;
            while ((str = br.readLine()) != null) {
                lines.add(str.replaceAll("\t", " "));
            }
        } catch (IOException e) {
            Logger.error(e);
        }
        return lines;
    }

    public static String getSessionIdFromConfig() {
        Object obj;
        try {
            obj = new JSONParser().parse(new FileReader("config.json"));
            JSONObject jo = (JSONObject) obj;
            return (String) jo.get("sessionId");
        } catch (Exception e) {
            Logger.error(e);
        }
        Logger.log("Please make sure sessionId is set in your config.json");
        return "";
    }
}
