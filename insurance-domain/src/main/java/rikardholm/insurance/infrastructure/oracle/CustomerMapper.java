package rikardholm.insurance.infrastructure.oracle;

import org.apache.ibatis.annotations.*;

import rikardholm.insurance.domain.customer.Address;
import rikardholm.insurance.domain.customer.Customer;
import rikardholm.insurance.domain.customer.PersonalIdentifier;
import rikardholm.insurance.domain.internal.CustomerImpl;

public interface CustomerMapper {
	@Select("SELECT personal_identifier, address FROM customers WHERE personal_identifier = #{personalIdentifier}")
	@ConstructorArgs({@Arg(column = "personal_identifier", javaType = PersonalIdentifier.class),
			@Arg(column = "address", javaType = Address.class)})
	CustomerImpl findByPersonalIdentifier(PersonalIdentifier personalIdentifier);

	@Insert("INSERT INTO customers (id,personal_identifier,address) values (#{id},#{personalIdentifier},#{address})")
	@SelectKey(statement = "SELECT customers_seq.nextval FROM DUAL",
			keyProperty = "id",
			keyColumn = "id",
			resultType = Long.class, before = true
	)
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
