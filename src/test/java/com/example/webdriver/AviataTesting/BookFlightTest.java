package com.example.webdriver.AviataTesting;

import com.codeborne.selenide.*;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class BookFlightTest {
    AviataMainPage aviataMainPage = new AviataMainPage();

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("https://aviata.kz/");
    }

    @Test
    public void book() throws InterruptedException {
        SelenideElement fromCityInputs = $x("//label[@class='font-normal relative flex items-center w-full h-full ux-searchform-city-from']");
        SelenideElement toCityInputs = $x("//label[@class='font-normal relative flex items-center w-full h-full ux-searchform-city-to']");

        // Select city from and to
        fromCityInputs.find("input").click();
        fromCityInputs.sendKeys("Нур-Султан");
        Wait().until(isTrue -> $x("//ul[@class='overflow-auto']").isDisplayed());
        $x("//ul[@class='overflow-auto']")
                .find("li")
                .click();

        Wait().until(isTrue -> !$x("//ul[@class='overflow-auto']").isDisplayed());

        toCityInputs.find("input").click();
        toCityInputs.sendKeys("ALA");
        Wait().until(isTrue -> $x("//ul[@class='overflow-auto']").isDisplayed());
        $x("//ul[@class='overflow-auto']")
                .find("li")
                .click();

        aviataMainPage.searchDiv.click();

        $x("//div[@class='flex flex-col justify-center items-center']")
                .shouldBe(Condition.visible, Duration.ofSeconds(10))
                .find("button")
                .click();

        $x("//div[@class='py-5 px-14 flex items-center justify-between']")
                .shouldBe(Condition.visible);
        $x("//div[@class='py-5 px-14 flex items-center justify-between']")
                .find("button")
                .click();

        // Fill the empty fields with personal data
        $x("//input[@name='passengers[ADT-0][lastName]']")
                .shouldBe(Condition.visible)
                .sendKeys("DULIYEV");
        $x("//input[@name='passengers[ADT-0][firstName]']")
                .shouldBe(Condition.visible)
                .sendKeys("RAIYMBEK");
        $x("//label[@data-qa-id='desktop-booking-gender-f']")
                .shouldBe(Condition.visible)
                .click();
        $x("//input[@name='passengers[ADT-0][dob]']")
                .shouldBe(Condition.visible)
                .sendKeys("09-12-2002");
        $x("//input[@name='passengers[ADT-0][document][number]']")
                .shouldBe(Condition.visible)
                .sendKeys("045207346");
        $x("//input[@name='passengers[ADT-0][document][expiresAt]']")
                .shouldBe(Condition.visible)
                .sendKeys("10-12-2028");
        $x("//input[@name='passengers[ADT-0][iin]']")
                .shouldBe(Condition.visible)
                .sendKeys("021209551042");
        $x("//input[@name='contacts[email]']")
                .shouldBe(Condition.visible)
                .sendKeys("raiymbek132@gmail.com");
        $x("//input[@name='contacts[phone]']")
                .shouldBe(Condition.visible)
                .sendKeys("7089807609");

        // Mark as checked for agreement
        SelenideElement lastSection = $x("//section[@class='p-5 bg-white rounded-b-md']");
        lastSection.find("label").find("input").click();
        // Finish the booking
        lastSection.find("div").find("button").click();

        printCurrentURL();
    }

    private void printCurrentURL() {
        System.out.println(WebDriverRunner.getWebDriver().getCurrentUrl());
    }
}