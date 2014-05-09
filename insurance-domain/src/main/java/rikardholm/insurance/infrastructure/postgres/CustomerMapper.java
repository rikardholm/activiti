package rikardholm.insurance.infrastructure.postgres;

import org.apache.ibatis.annotations.*;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.internal.CustomerImpl;

public interface CustomerMapper {
    @Select("SELECT personal_identifier FROM customers WHERE personal_identifier = #{personalIdentifier}")
    @ConstructorArgs(@Arg(column = "personal_identifier", javaType = PersonalIdentifier.class))
    CustomerImpl findByPersonalIdentifier(PersonalIdentifier personalIdentifier);

    @Insert("INSERT INTO customers (personal_identifier) values (#{personalIdentifier})")
    void insert(PersonalIdentifier personalIdentifier);

    @Delete("DELETE FROM customers WHERE personal_identifier = #{personalIdentifier}")
    void delete(PersonalIdentifier personalIdentifier);
}
