package com.typesafe.reactive.workshop;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class Guest extends AbstractLoggingActor {

  public static Props props() {
    return Props.create(Guest.class, () -> new Guest());
  }

  @Override
  public PartialFunction<Object, BoxedUnit> receive() {
    return ReceiveBuilder
            .matchAny(o -> {})
            .build();
  }
}
