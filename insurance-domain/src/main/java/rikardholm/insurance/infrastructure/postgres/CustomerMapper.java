package rikardholm.insurance.infrastructure.postgres;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.internal.CustomerImpl;

public interface CustomerMapper {
    @Select("SELECT personal_identifier FROM customers WHERE personal_identifier = #{personalIdentifier}")
    String findByPersonalIdentifier(String personalIdentifier);

    @Insert("INSERT INTO customers (personal_identifier) values (#{personalIdentifier})")
    void insert(PersonalIdentifier personalIdentifier);

    @Delete("DELETE FROM customers WHERE personal_identifier = #{personalIdentifier}")
    void delete(PersonalIdentifier personalIdentifier);
}
