package com.typesafe.reactive.workshop;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.actor.ActorRef;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.concurrent.duration.FiniteDuration;
import scala.runtime.BoxedUnit;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

public class Guest extends AbstractLoggingActor {

  private final ActorRef waiter;
  private final Drink favoriteDrink;

  //storage
  int drinkCount = 0;


  public static class DrinkFinished implements Serializable {
    public static final DrinkFinished Instance = new DrinkFinished();
    private DrinkFinished() {}

  }

  public Guest(ActorRef waiter, Drink favoriteDrink) {

    this.waiter = waiter;
    this.favoriteDrink = favoriteDrink;

    waiter.tell(new Waiter.ServeDrink(favoriteDrink), self());
  }

  public static Props props(ActorRef waiter, Drink favoriteDrink) {
    return Props.create(Guest.class, () -> new Guest(waiter, favoriteDrink));
  }

  @Override
  public PartialFunction<Object, BoxedUnit> receive() {
    return ReceiveBuilder
            .match(Waiter.DrinkServered.class, ds -> {
              drinkCount += 1;
              log().info("Enjoying my {}. yummy {}!", drinkCount, favoriteDrink);
              scheduleCoffeeFinished();
            })
            .match(DrinkFinished.class, d -> waiter.tell(new Waiter.ServeDrink(favoriteDrink), self()))
            .build();
  }


  private void scheduleCoffeeFinished(){
    context().system()
            .scheduler()
            .scheduleOnce(new FiniteDuration(2, TimeUnit.SECONDS), self(),
                    DrinkFinished.Instance, context().dispatcher(), self());
  }
}
