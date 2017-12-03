package core.jdbc.refactoring.result;

import java.math.BigDecimal;

public class ExecuteResult<T1, T2> {
	private int effectedRowCount;
	private String query;
	private T1 data;
	private T2 queryResult;
	private Long autoGenerateId;

	public Long getAutoGenerateId() {
		return autoGenerateId;
	}

	public void setAutoGenerateId(Long autoGenerateId) {
		this.autoGenerateId = autoGenerateId;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public int getEffectedRowCount() {
		return effectedRowCount;
	}

	public void setEffectedRowCount(int effectedRowCount) {
		this.effectedRowCount = effectedRowCount;
	}

	public T1 getData() {
		return data;
	}

	public void setData(T1 data) {
		this.data = data;
	}

	public T2 getQueryResult() {
		return queryResult;
	}

	public void setQueryResult(T2 queryResult) {
		this.queryResult = queryResult;
	}
}
