package com.typesafe.reactive.workshop;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.io.Serializable;

public class CocktailBar extends AbstractLoggingActor {

  public static Props props() {
    return Props.create(CocktailBar.class, () -> new CocktailBar());
  }


  public static final class CreateGuest implements Serializable {
    public static final CreateGuest Instance = new CreateGuest();
    private CreateGuest() {}

  }

  public CocktailBar() {
    log().info("Cocktail bar is open!");
  }

  @Override
  public PartialFunction<Object, BoxedUnit> receive() {
    return ReceiveBuilder
            .match(CreateGuest.class, this::createGuest)
            .build();
  }

  private ActorRef createGuest(CreateGuest createGuest) {
    return getContext().actorOf(Guest.props());
  }






}
