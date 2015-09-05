/**
 * Copyright © 2014, 2015 Typesafe, Inc. All rights reserved. [http://www.typesafe.com]
 */
package com.typesafe.reactive.workshop;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface Terminal{

    Pattern createGuestPattern = Pattern.compile("(\\d+)?\\s*(?:guest|g)\\s*(A|a|M|m|P|p)?\\s*(\\d+)?");
    Pattern getStatusPattern = Pattern.compile("status|s");
    Pattern quitPattern = Pattern.compile("quit|q");

    static TerminalCommand create(final String s){

        final Matcher guestMatcher = createGuestPattern.matcher(s);
        if (guestMatcher.matches()) {

            final String countGroup = guestMatcher.group(1);
            final int count = countGroup != null ? Integer.parseInt(countGroup) : 1;

            final String drinkGroup = guestMatcher.group(2);
            final Drink drink = drinkGroup != null ? Drinks.order(drinkGroup) : new Akkarita(false);

            final String maxDrinkCountGroup = guestMatcher.group(3);
            final int maxDrinkCount =
                maxDrinkCountGroup != null ? Integer.parseInt(maxDrinkCountGroup) : Integer.MAX_VALUE;

            return new TerminalCommand.Guest(count, drink, maxDrinkCount);
        }
        if (getStatusPattern.matcher(s).matches()) return TerminalCommand.Status.Instance;
        if (quitPattern.matcher(s).matches()) return TerminalCommand.Quit.Instance;
        return new TerminalCommand.Unknown(s);
    }
}
