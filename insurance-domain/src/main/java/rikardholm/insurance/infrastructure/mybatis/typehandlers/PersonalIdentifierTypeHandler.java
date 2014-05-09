package rikardholm.insurance.infrastructure.mybatis.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import rikardholm.insurance.domain.PersonalIdentifier;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class PersonalIdentifierTypeHandler extends BaseTypeHandler<PersonalIdentifier> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, PersonalIdentifier personalIdentifier, JdbcType jdbcType) throws SQLException {
        preparedStatement.setString(i, personalIdentifier.getValue());
    }

    @Override
    public PersonalIdentifier getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return PersonalIdentifier.of(resultSet.getString(s));
    }

    @Override
    public PersonalIdentifier getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return PersonalIdentifier.of(resultSet.getString(i));
    }

    @Override
    public PersonalIdentifier getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return PersonalIdentifier.of(callableStatement.getString(i));
    }
}
