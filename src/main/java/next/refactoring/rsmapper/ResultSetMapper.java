package next.refactoring.rsmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetMapper {
	Object mappingStrategy(ResultSet resultSet) throws SQLException;
}
