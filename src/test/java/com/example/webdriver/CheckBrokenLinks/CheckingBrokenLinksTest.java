package com.example.webdriver.CheckBrokenLinks;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import java.io.FileWriter;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CheckingBrokenLinksTest {

    @BeforeAll
    public static void setUpAll() {
        Configuration.browser = "firefox";
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("https://www.kaznu.kz/");
    }

    @Test
    public void check() throws IOException, InterruptedException {
        List<SelenideElement> elementList = $$("a");

        System.out.println("Link List size: " + elementList.size());

        List<String> brokenLinksList = new ArrayList<>();

        for (int i = 0; i < elementList.size(); i++) {
            SelenideElement element = elementList.get(i);
            String url = element.getAttribute("href");

            if (url != null && isValid(url)) {
                try {
                    URL link = new URL(url);
                    HttpURLConnection connection = (HttpURLConnection) link.openConnection();
                    Thread.sleep(2000);

                    System.out.println((i + 1) + ":" + url);
                    System.out.println("Connecting...");

                    connection.connect();

                    System.out.println("Connection established!");
                    System.out.println("Response code: " + connection.getResponseCode());

                    if (connection.getResponseCode() >= 400) {
                        brokenLinksList.add(url + '\n');
                    }
                    connection.disconnect();
                } catch (IOException e) {
                    System.out.println("Invalid URL: " + url);
                }
            }
        }

        outputResults(brokenLinksList);
    }

    private static boolean isValid(String url) {
        if (url.contains("mailto"))
            return false;

        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void outputResults(List<String> list) {
        try {
            for (String s : list) {
                Files.write(Paths.get("/Users/raiymbekduldyiev/IdeaProjects/Webdriver/src/test/java/com/example/webdriver/CheckBrokenLinks/brokenLinksList.txt"),
                        s.getBytes(),
                        StandardOpenOption.APPEND);
                System.out.println("Found a broken link.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
