package next.model;

import java.math.BigDecimal;
import java.util.Date;

public class Question {
	/**
	 * questionId 			bigint				auto_increment,
	 * writer				varchar(30)			NOT NULL,
	 * title				varchar(50)			NOT NULL,
	 * contents			varchar(5000)		NOT NULL,
	 * createdDate			timestamp			NOT NULL,
	 * countOfAnswer int,
	 */

	private Long questionId;
	private String writer;
	private String title;
	private String contents;
	private Date createdDate;
	private int countOfAnswer;

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public int getCountOfAnswer() {
		return countOfAnswer;
	}

	public void setCountOfAnswer(int countOfAnswer) {
		this.countOfAnswer = countOfAnswer;
	}
}
