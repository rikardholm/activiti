package rikardholm.insurance.infrastructure.mybatis.typehandlers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import rikardholm.insurance.domain.Address;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressTypeHandler extends BaseTypeHandler<Address> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Address parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getValue());
    }

    @Override
    public Address getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return Address.of(rs.getString(columnName));
    }

    @Override
    public Address getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return Address.of(rs.getString(columnIndex));
    }

    @Override
    public Address getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return Address.of(cs.getString(columnIndex));
    }
}
