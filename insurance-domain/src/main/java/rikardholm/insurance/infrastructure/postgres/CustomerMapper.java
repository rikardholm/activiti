package rikardholm.insurance.infrastructure.postgres;

import org.apache.ibatis.annotations.*;
import rikardholm.insurance.domain.Address;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.internal.CustomerImpl;

public interface CustomerMapper {
    @Select("SELECT personal_identifier, address FROM customers WHERE personal_identifier = #{personalIdentifier}")
    @ConstructorArgs({@Arg(column = "personal_identifier", javaType = PersonalIdentifier.class),
            @Arg(column = "address", javaType = Address.class)})
    CustomerImpl findByPersonalIdentifier(PersonalIdentifier personalIdentifier);

    @Insert("INSERT INTO customers (personal_identifier, address) values (#{personalIdentifier},#{address})")
    void insert(Customer customer);

    @Delete("DELETE FROM customers WHERE personal_identifier = #{personalIdentifier}")
    void delete(Customer customer);

    @Select("SELECT id FROM customers WHERE personal_identifier = #{personalIdentifier}")
    Integer selectId(Customer customer);

    @Select("SELECT personal_identifier, address FROM customers WHERE id = #{customerId}")
    @ConstructorArgs({@Arg(column = "personal_identifier", javaType = PersonalIdentifier.class),
            @Arg(column = "address", javaType = Address.class)})
    CustomerImpl findById(Integer customerId);
}
