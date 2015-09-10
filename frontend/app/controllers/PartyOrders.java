package controllers;

import common.PartyOrderBodyParser;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;

public class PartyOrders extends Controller {

  @BodyParser.Of(PartyOrderBodyParser.class)
  public Result placeOrder() {
    return ok("got " + request().body().asText() + " chunks");
  }

}
