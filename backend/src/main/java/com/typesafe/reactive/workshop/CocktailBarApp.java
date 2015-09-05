package com.typesafe.reactive.workshop;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.event.Logging;
import akka.event.LoggingAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CocktailBarApp implements Terminal{

  public static final Pattern optPattern = Pattern.compile("(\\S+)=(\\S+)");

  private final ActorSystem system;

  private final LoggingAdapter log;

  @SuppressWarnings("unused")
  private final ActorRef bar;

  public CocktailBarApp(final ActorSystem system){
    this.system = system;
    log = Logging.getLogger(system, getClass().getName());
    bar = createCocktailBar();
  }

  public static void main(final String[] args) throws IOException{
    final Map<String, String> opts = argsToOpts(Arrays.asList(args));
    applySystemProperties(opts);
    final String name = opts.getOrDefault("name", "cocktail-bar");

    final ActorSystem system = ActorSystem.create(String.format("%s-system", name));
    final CocktailBarApp cocktailBarApp = new CocktailBarApp(system);
    cocktailBarApp.run();
  }

  public static Map<String, String> argsToOpts(final List<String> args){
    final Map<String, String> opts = new HashMap<>();
    for (final String arg : args) {
      final Matcher matcher = optPattern.matcher(arg);
      if (matcher.matches()) opts.put(matcher.group(1), matcher.group(2));
    }
    return opts;
  }

  public static void applySystemProperties(final Map<String, String> opts){
    opts.forEach((key, value) -> {
      if (key.startsWith("-D")) System.setProperty(key.substring(2), value);
    });
  }

  private void run() throws IOException{
    log.warning(
            String.format("{} running%nEnter commands into the terminal, e.g. 'q' or 'quit'"),
            getClass().getSimpleName()
    );
    commandLoop();
    system.awaitTermination();
  }

  protected ActorRef createCocktailBar(){
    return system.actorOf(CocktailBar.props(), "cocktail-bar");
  }

  private void commandLoop() throws IOException{
    BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    while (true) {
      TerminalCommand tc = Terminal.create(in.readLine());
      if (tc instanceof TerminalCommand.Guest) {
        TerminalCommand.Guest tcg = (TerminalCommand.Guest) tc;
        createGuest(tcg.count, tcg.drink, tcg.maxDrinkCount);
      } else if (tc == TerminalCommand.Status.Instance) {
        getStatus();
      } else if (tc == TerminalCommand.Quit.Instance) {
        system.shutdown();
        break;
      } else {
        TerminalCommand.Unknown u = (TerminalCommand.Unknown) tc;
        log.warning("Unknown terminal command {}!", u.command);
      }
    }
  }

  protected void createGuest(int count, Drink drink, int maxDrinkCount){
  }

  protected void getStatus(){
  }
}
