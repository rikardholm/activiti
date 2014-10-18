package rikardholm.insurance.indexing;

import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.insurance.Insurance;

public interface Indexer {
    void index(Insurance insurance);

    void index(Customer customer);
}
