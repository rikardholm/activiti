package rikardholm.insurance.infrastructure.oracle;

import org.apache.ibatis.annotations.*;

import rikardholm.insurance.domain.Customer;
import rikardholm.insurance.domain.PersonalIdentifier;
import rikardholm.insurance.domain.internal.CustomerImpl;

public interface CustomerMapper {
	@Select("SELECT personal_identifier FROM customers WHERE personal_identifier = #{personalIdentifier}")
	@ConstructorArgs(@Arg(column = "personal_identifier", javaType = PersonalIdentifier.class))
	CustomerImpl findByPersonalIdentifier(PersonalIdentifier personalIdentifier);

	@Insert("INSERT INTO customers (id,personal_identifier) values (#{id},#{personalIdentifier})")
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

	@Select("SELECT personal_identifier FROM customers WHERE id = #{customerId}")
	@ConstructorArgs(@Arg(column = "personal_identifier", javaType = PersonalIdentifier.class))
	CustomerImpl findById(Integer customerId);
}
