package rikardholm.transfer.workflow;

import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import rikardholm.insurance.service.insurance.InsuranceRepository;

@Component
@ContextConfiguration("classpath*:cucumber.xml")
public class Steps {
    @Autowired
    private InsuranceRepository insuranceRepository;

    @Given("^insurance (\\d+) belongs to customer (\\d{6}-\\d{4})$")
    public void insurance_belongs_to_customer(int insuranceNumber, String personIdentifier) throws Throwable {
        insuranceRepository.create(insuranceNumber, personIdentifier);
        throw new PendingException();
    }

    @When("^a find-insurance message contains personal identifier (\\d+)-(\\d+)$")
    public void a_find_insurance_message_contains_personal_identifier(int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the outgoing message should contain the insurance information$")
    public void the_outgoing_message_should_contain_the_insurance_information() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Given("^no insurance belongs to customer (\\d+)-(\\d+)$")
    public void no_insurance_belongs_to_customer(int arg1, int arg2) throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the outgoing message should be no-information$")
    public void the_outgoing_message_should_be_no_information() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
}
