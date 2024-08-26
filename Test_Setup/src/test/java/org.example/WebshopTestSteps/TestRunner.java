package org.example.WebshopTestSteps;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(glue = { "org.example" }, features = {
        "src/test/resources/features/Desktop.feature"},
        plugin = {"pretty","html:target/HTMLReports/testReport.html"})
public class TestRunner {
}
