package core.jdbc.refactoring.rsmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetMapper<T> {
	T mappingStrategy(ResultSet resultSet) throws SQLException;
}
