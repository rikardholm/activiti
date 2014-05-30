package rikardholm.insurance.transfer;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import org.activiti.engine.delegate.DelegateExecution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rikardholm.insurance.application.messaging.OutboxRepository;
import rikardholm.insurance.application.messaging.OutgoingMessage;
import rikardholm.insurance.application.messaging.message.InsuranceInformationResponse;
import rikardholm.insurance.application.messaging.message.NoInsurancesResponse;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceRepository;

import java.util.List;

import static com.google.common.collect.Lists.transform;

public class MessageSender {
    private static Logger log = LoggerFactory.getLogger(MessageSender.class);

    private OutboxRepository outboxRepository;
    private CustomerRepository customerRepository;
    private InsuranceRepository insuranceRepository;

    public MessageSender(OutboxRepository outboxRepository, CustomerRepository customerRepository, InsuranceRepository insuranceRepository) {
        this.outboxRepository = outboxRepository;
        this.customerRepository = customerRepository;
        this.insuranceRepository = insuranceRepository;
    }

    public void sendNoInsurancesResponse(PersonalIdentifier personalIdentifier) {
        OutgoingMessage outgoingMessage = new NoInsurancesResponse(personalIdentifier.getValue());

        log.info("Sending 'No Insurance Information' message for {}", personalIdentifier);

        outboxRepository.save(outgoingMessage);
    }

    public void sendInsuranceInformationResponse(DelegateExecution execution) {
        PersonalIdentifier personalIdentifier = (PersonalIdentifier) execution.getVariable("personnummer");

        Optional<? extends Customer> customer = customerRepository.findBy(personalIdentifier);

        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());

        List<Long> insuranceNumbers = transform(insurances, toInsuranceNumber());

        OutgoingMessage outgoingMessage = new InsuranceInformationResponse(personalIdentifier.getValue(), insuranceNumbers);

        log.info("Sending insurance information for {} with {} insurances.",personalIdentifier,insuranceNumbers.size());

        outboxRepository.save(outgoingMessage);
    }

    private Function<Insurance, Long> toInsuranceNumber() {
        return new Function<Insurance, Long>() {
            @Override
            public Long apply(Insurance input) {
                return input.getInsuranceNumber().getValue();
            }
        };
    }
}
