package com.msa.delivery.presentation.response;

import java.util.List;

import org.springframework.data.domain.Page;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PageResponse<T>(
	List<T> content,
	int pageNumber,
	int pageSize,
	long totalElements,
	int totalPages,
	boolean first,
	boolean last
) {
	public static <T> PageResponse<T> of(Page<T> page) {
		return new PageResponse<>(
			page.getContent(),
			page.getNumber(),
			page.getSize(),
			page.getTotalElements(),
			page.getTotalPages(),
			page.isFirst(),
			page.isLast()
		);
	}
}




