package com.typesafe.reactive.workshop;

import akka.actor.AbstractLoggingActor;
import akka.actor.Props;
import akka.japi.pf.ReceiveBuilder;
import scala.PartialFunction;
import scala.runtime.BoxedUnit;

import java.io.Serializable;

public class Waiter extends AbstractLoggingActor {


  public static Props props() {
    return Props.create(Waiter.class, () -> new Waiter());
  }

  public static final class ServeDrink implements Serializable {

    public final Drink drink;

    public ServeDrink(Drink drink) {
      assert drink != null;
      this.drink = drink;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      ServeDrink that = (ServeDrink) o;

      return drink.equals(that.drink);

    }

    @Override
    public int hashCode() {
      return drink.hashCode();
    }
  }

  public static final class DrinkServered implements Serializable {

    public final Drink drink;

    public DrinkServered(Drink drink) {

      this.drink = drink;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;

      DrinkServered that = (DrinkServered) o;

      return !(drink != null ? !drink.equals(that.drink) : that.drink != null);

    }

    @Override
    public int hashCode() {
      return drink != null ? drink.hashCode() : 0;
    }
  }

  @Override
  public PartialFunction<Object, BoxedUnit> receive() {
    return ReceiveBuilder
            .match(ServeDrink.class, sd -> sender().tell(new DrinkServered(sd.drink), self()))
            .build();
  }
}
