package rikardholm.insurance.indexing;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.CustomerBuilder;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.insurance.Insurance;
import rikardholm.insurance.domain.insurance.InsuranceBuilder;
import rikardholm.insurance.domain.insurance.InsuranceNumber;
import rikardholm.insurance.indexing.internal.ElasticSearchIndexer;

import java.util.Map;

public class IndexerTest {

    private Node node;
    private Indexer indexer;

    @Before
    public void setUp() throws Exception {
        Settings settings = ImmutableSettings.settingsBuilder()
                .put("index.store.type", "memory")
                .build();
        node = NodeBuilder.nodeBuilder()
                .local(true)
                .settings(settings)
                .node();
        indexer = new ElasticSearchIndexer(node.client());
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("shutdown");
        node.close();
    }

    @Test
    public void adds_item_to_index() throws Exception {
        Customer customer = CustomerBuilder.aCustomer()
                .withPersonalIdentifier(PersonalIdentifier.of("454568-0999"))
                .withAddress(Address.of("asdfadsf"))
                .build();

        Insurance insurance = InsuranceBuilder.anInsurance()
                .withInsuranceNumber(InsuranceNumber.of(4545687l))
                .belongsTo(customer)
                .build();

        indexer.index(customer);
        indexer.index(insurance);

        Client client = node.client();
        client.admin().indices().prepareRefresh().execute().actionGet();
        SearchResponse searchResponse = client.prepareSearch("insurances")
                .setQuery(QueryBuilders.queryString("*4545*"))
                .execute()
                .actionGet();

        System.out.println("searchResponse = " + searchResponse);

        for (SearchHit searchHit : searchResponse.getHits().hits()) {
            String type = searchHit.getType();
            Map<String, Object> source = searchHit.getSource();
            switch (type) {
                case "insurance":
                    System.out.println("Insurance: " + source.get("insuranceNumber"));
                    break;
                case "customer":
                    System.out.println("Customer: " + source.get("personalIdentifier"));
            }
        }
    }
}