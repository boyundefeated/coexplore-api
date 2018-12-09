package com.coexplore.api.common.response;

/**
 * @author duy.nh
 */

public class DatatableResponse<T> {
	private Long draw;
	private Long recordsTotal;
	private Long recordsFiltered;
	private T data;

	
	
	public DatatableResponse() {
		super();
	}

	public DatatableResponse(Long draw, Long recordsTotal, T data) {
		super();
		this.draw = draw;
		this.recordsTotal = recordsTotal;
		this.recordsFiltered = recordsTotal;
		this.data = data;
	}

	public Long getDraw() {
		return draw;
	}

	public void setDraw(Long draw) {
		this.draw = draw;
	}

	public Long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(Long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}

	public Long getRecordsFiltered() {
		return recordsFiltered;
	}

	public void setRecordsFiltered(Long recordsFiltered) {
		this.recordsFiltered = recordsFiltered;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
