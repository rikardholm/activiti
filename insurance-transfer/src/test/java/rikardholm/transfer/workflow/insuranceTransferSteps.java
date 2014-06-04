package rikardholm.transfer.workflow;

import cucumber.api.java.sv.När;
import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.insurance.InsuranceRepository;

@Component
@ContextConfiguration("classpath*:test/cucumber.xml")
public class InsuranceTransferSteps {
    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;


    @När("^vi får ett meddelande med personnummer (\\d{6}-\\d{4})$")
    public void vi_får_ett_meddelande_med_personnummer_(String personnummer) throws Throwable {

        runtimeService.startProcessInstanceByKey("insurance-transfer");

    }
}
