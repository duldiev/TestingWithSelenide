package com.example.webdriver.LeetcodeTesting;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class LeetcodeTest {

    @BeforeAll
    public static void setUpAll() {
        Configuration.browserSize = "1280x800";
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @BeforeEach
    public void setUp() {
        open("https://leetcode.com/accounts/login/");
    }

    @Test
    @Order(0)
    public void logInThenLogOut() {
        // Login
        $x("//input[@id='id_login']").sendKeys("raiymbek132@gmail.com");
        $x("//input[@id='id_password']").sendKeys("geqvyp-syjsuj-8Xyqco");
        $("button.btn__3Y3g.fancy-btn__2prB.primary__lqsj.light__3AfA.btn__1z2C.btn-md__M51O").click();

        // Logout
        $x("//div[@class='nav-item-container__16kF' and @data-tour-index='7']").click();
        $$x("//li[@class='ant-dropdown-menu-item' and @role='menuitem']")
                .last()
                .shouldBe(Condition.visible)
                .click();
    }
}