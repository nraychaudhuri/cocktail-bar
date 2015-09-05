/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */
package com.typesafe.reactive.workshop;

import java.io.Serializable;

import static com.google.common.base.Preconditions.checkNotNull;

public interface TerminalCommand extends Serializable{

    long serialVersionUID = 1;

    final class Guest implements TerminalCommand{

        private static final long serialVersionUID = 1L;

        public final int count;

        public final Drink drink;

        public final int maxDrinkCount;

        public Guest(final int count, final Drink drink, final int maxDrinkCount){
            checkNotNull(drink, "Drink cannot be null");
            this.count = count;
            this.drink = drink;
            this.maxDrinkCount = maxDrinkCount;
        }

        @Override
        public String toString(){
            return "Guest{"
                + "count=" + count + ", "
                + "drink=" + drink + ", "
                + "maxDrinkCount=" + maxDrinkCount
                + "}";
        }

        @Override
        public boolean equals(Object o){
            if (o == this) return true;
            if (o instanceof Guest) {
                Guest that = (Guest) o;
                return (this.count == that.count)
                    && (this.drink.equals(that.drink))
                    && (this.maxDrinkCount == that.maxDrinkCount);
            }
            return false;
        }

        @Override
        public int hashCode(){
            int h = 1;
            h *= 1000003;
            h ^= count;
            h *= 1000003;
            h ^= drink.hashCode();
            h *= 1000003;
            h ^= maxDrinkCount;
            return h;
        }
    }

    final class Status implements TerminalCommand{

        private static final long serialVersionUID = 1L;

        public static final Status Instance = new Status();

        private Status(){
        }
    }

    final class Quit implements TerminalCommand{

        private static final long serialVersionUID = 1L;

        public static final Quit Instance = new Quit();

        private Quit(){
        }
    }

    final class Unknown implements TerminalCommand{

        private static final long serialVersionUID = 1L;

        public final String command;

        public Unknown(final String command){
            checkNotNull(command, "Command cannot be null");
            this.command = command;
        }

        @Override
        public String toString(){
            return "Unknown{command=" + command + "}";
        }

        @Override
        public boolean equals(Object o){
            if (o == this) return true;
            if (o instanceof Unknown) {
                Unknown that = (Unknown) o;
                return (this.command.equals(that.command));
            }
            return false;
        }

        @Override
        public int hashCode(){
            int h = 1;
            h *= 1000003;
            h ^= command.hashCode();
            return h;
        }
    }
}
