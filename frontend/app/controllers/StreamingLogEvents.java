package controllers;

import actors.LogStreamer;
import com.fasterxml.jackson.databind.JsonNode;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.WebSocket;

public class StreamingLogEvents extends Controller {

  public Result viewer() {
    return ok(views.html.log.render());
  }
  public WebSocket<JsonNode> logs() {
    return WebSocket.withActor(LogStreamer::props);
  }
}
