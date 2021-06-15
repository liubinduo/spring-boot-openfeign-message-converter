package com.v1ok.feign;

import java.util.List;
import lombok.Data;

@Data
public class Page<T> {

  Integer totalPages;

  Long totalElements;

  List<T> content;

  Pageable pageable;

}
