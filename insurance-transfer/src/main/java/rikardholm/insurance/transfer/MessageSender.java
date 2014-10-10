package rikardholm.insurance.transfer;

import com.google.common.base.Optional;
import org.activiti.engine.delegate.DelegateExecution;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rikardholm.insurance.application.messaging.Message;
import rikardholm.insurance.application.messaging.MessageBuilder;
import rikardholm.insurance.application.messaging.MessageRepository;
import rikardholm.insurance.application.messaging.OutgoingMessage;
import rikardholm.insurance.application.messaging.message.NoInsurancesResponse;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.CustomerRepository;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceRepository;

import java.io.IOException;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static com.google.common.collect.Lists.transform;

public class MessageSender {
    private static Logger log = LoggerFactory.getLogger(MessageSender.class);

    private MessageRepository messageRepository;
    private CustomerRepository customerRepository;
    private InsuranceRepository insuranceRepository;

    public MessageSender(MessageRepository messageRepository, CustomerRepository customerRepository, InsuranceRepository insuranceRepository) {
        this.messageRepository = messageRepository;
        this.customerRepository = customerRepository;
        this.insuranceRepository = insuranceRepository;
    }

    public void sendNoInsurancesResponse(PersonalIdentifier personalIdentifier) {
        OutgoingMessage outgoingMessage = new NoInsurancesResponse(personalIdentifier.getValue());

        log.info("Sending 'No Insurance Information' message for {}", personalIdentifier);

        Message message = MessageBuilder.aMessage().receivedAt(Instant.now()).withUUID(UUID.randomUUID()).payload("{}").build();
        messageRepository.append(message);
    }

    public void sendInsuranceInformationResponse(DelegateExecution execution) {
        PersonalIdentifier personalIdentifier = (PersonalIdentifier) execution.getVariable("personnummer");

        Optional<? extends Customer> customer = customerRepository.findBy(personalIdentifier);

        List<? extends Insurance> insurances = insuranceRepository.findBy(customer.get());

        List<Long> insuranceNumbers = transform(insurances, input -> input.getInsuranceNumber().getValue());

        log.info("Sending insurance information for {} with {} insurances.", personalIdentifier, insuranceNumbers.size());
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode root = objectMapper.createObjectNode();
        root.put("personalIdentifier", personalIdentifier.getValue());
        ArrayNode arrayNode = root.putArray("insuranceNumbers");
        for (Long insuranceNumber : insuranceNumbers) {
            arrayNode.add(insuranceNumber);
        }

        String payload;
        try {
            payload = objectMapper.writeValueAsString(root);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Message message = MessageBuilder.aMessage().receivedAt(Instant.now()).withUUID(UUID.randomUUID()).payload(payload).build();
        messageRepository.append(message);
    }

}
