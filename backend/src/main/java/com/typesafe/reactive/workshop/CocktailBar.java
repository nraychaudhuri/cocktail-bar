package com.typesafe.reactive.workshop;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

public class CocktailBar extends AbstractLoggingActor {

  public static Props props() {
    return Props.create(CocktailBar.class, () -> new CocktailBar());
  }

  public CocktailBar() {
    log().info("Cocktail bar is open!");
  }

  @Override
  public PartialFunction<Object, BoxedUnit> receive() {
    return ReceiveBuilder
            .matchAny(o -> log().info("Welcome to cocktail bar!"))
            .build();
  }
}
