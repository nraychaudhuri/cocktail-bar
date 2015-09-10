package actors;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import com.fasterxml.jackson.databind.JsonNode;
import common.LogEvent;
import common.Utils;
import play.libs.Json;
import scala.PartialFunction;
import scala.concurrent.duration.Duration;
import scala.runtime.BoxedUnit;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class LogStreamer extends AbstractLoggingActor {

  private final ActorRef out;

  private Pattern logPattern = Pattern.compile("\\[(INFO|DEBUG)\\] - ([a-z ]*)");

  public LogStreamer(ActorRef out) {
    this.out = out;
  }

  @Override
  public void preStart() throws Exception {
    Runnable r = () -> self().tell(rawEvent(), ActorRef.noSender());
    getContext().system().scheduler().schedule(
            Duration.create(100, TimeUnit.MILLISECONDS),
            Duration.create(500, TimeUnit.MILLISECONDS),
            r,
            context().dispatcher());
  }

  private String rawEvent() {
    if(new Random().nextBoolean())
      return "[INFO] - some info log message";
    return "[DEBUG] - some debug log message";
  }

  public static Props props(ActorRef out) {
    return Props.create(LogStreamer.class, () -> new LogStreamer(out));
  }

  @Override
  public PartialFunction<Object, BoxedUnit> receive() {
    return ReceiveBuilder.match(String.class, raw -> {
      Optional.of(raw).map(this::toEvent).map(this::toJson).ifPresent(json -> {
        out.tell(json, self());
      });
    }).build();
  }

  private JsonNode toJson(LogEvent e) {
    final JsonNode jsonNode = Json.toJson(e);
    return jsonNode;
  }

  private LogEvent toEvent(String line) {
    return Utils.toEvent(line);
  }

}
