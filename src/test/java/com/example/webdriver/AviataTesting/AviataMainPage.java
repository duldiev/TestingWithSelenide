package com.example.webdriver.AviataTesting;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.*;

// https://aviata.kz
public class AviataMainPage {
    public SelenideElement searchDiv = $x("//div[@class='flex search-form__row space-x-2 lg:space-x-0 lg:ml-px']").find("button");
}
