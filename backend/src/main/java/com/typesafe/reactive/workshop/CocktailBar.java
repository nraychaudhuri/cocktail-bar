package com.typesafe.reactive.workshop;

import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.io.Serializable;

public class CocktailBar extends AbstractLoggingActor {

  private ActorRef waiter = createWaiter();

  private ActorRef createWaiter() {
    return getContext().actorOf(Waiter.props(), "waiter");
  }

  public static Props props() {
    return Props.create(CocktailBar.class, () -> new CocktailBar());
  }


  public static final class CreateGuest implements Serializable {
    public final Drink drink;

    public CreateGuest(Drink drink) {
      this.drink = drink;
    }

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
    return getContext().actorOf(Guest.props(waiter, createGuest.drink));
  }






}
