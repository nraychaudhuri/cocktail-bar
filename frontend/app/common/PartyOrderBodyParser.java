package common;

import play.mvc.BodyParser;
import play.mvc.Http;

public class PartyOrderBodyParser implements BodyParser {

  @Override
  public play.api.mvc.BodyParser<Http.RequestBody> parser(long maxLength) {
    return Utils.bodyParser();
  }
}