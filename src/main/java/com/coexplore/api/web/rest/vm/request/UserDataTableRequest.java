package com.coexplore.api.web.rest.vm.request;

public class UserDataTableRequest {
	private Integer draw;
	private Integer start;
	private Integer length;

	public UserDataTableRequest() {
		super();
	}

	public UserDataTableRequest(Integer draw, Integer start, Integer length) {
		super();
		this.draw = draw;
		this.start = start;
		this.length = length;
	}

	public Integer getDraw() {
		return draw;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}

	public Integer getLength() {
		return length;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

}
