package com.v1ok.feign;

import com.v1ok.commons.Head;
import com.v1ok.commons.IRestResponse;
import lombok.Data;

@Data
public class Response<Body> implements IRestResponse<Body> {

  private Head head;
  private Body body;

  public Response() {
    this.head = new Head();
  }


  @Override
  public IRestResponse<Body> data(Body data) {
    this.body = data;
    return this;
  }

}
