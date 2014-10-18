package rikardholm.insurance.indexing.internal;

import org.elasticsearch.client.Client;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.indexing.Indexer;

import java.util.HashMap;
import java.util.Map;

public class ElasticSearchIndexer implements Indexer {

    private final Client client;

    public ElasticSearchIndexer(Client client) {
        this.client = client;
    }

    @Override
    public void index(Insurance insurance) {
        Map<String, Object> source = new HashMap<>();
        source.put("insuranceNumber", insurance.getInsuranceNumber().getValue());
        client.prepareIndex("insurances", "insurance")
                .setId(insurance.getInsuranceNumber().getValue().toString())
                .setSource(source)
                .execute()
                .actionGet();
    }

    @Override
    public void index(Customer customer) {
        Map<String, Object> source = new HashMap<>();
        source.put("personalIdentifier", customer.getPersonalIdentifier().getValue());
        source.put("address", customer.getAddress().getValue());
        client.prepareIndex("insurances", "customer")
                .setSource(source)
                .execute()
                .actionGet();
    }
}
