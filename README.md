# Implementing Reactive Apps workshop

### This is a one day Reactive workshop to implement Reactive application using Akka and Play with Java and Scala examples.


## Case study

We are going to build the Cocktail bar:

* Drinks like Akkarita, Mai Play and Pina Scalada are served to guests.
* Guests can get drunk, waiters can get frustrated
* Barkeepers can become bottlenecks
* Mission: Keep Hakky Hour healthy! 

## Prerequisites

This course is best suited for individuals with experience in Java 8 (Scala is plus)

Software: Java SDK 1.8

## Setup instructions

* Download Activator 1.3.6 minimal from [here](http://downloads.typesafe.com/typesafe-activator/1.3.6/typesafe-activator-1.3.6-minimal.zip)
* Unzip typesafe-activator-1.3.5-minimal.zip to your local disk
* Add activator to your PATH
  * Unix systems: export PATH=$PATH:/path/to/activator-dir
  * Windows: Update the path in the global environment variables
* Clone this repo
* cd to the _cocktail-bar_ directory
* start activator: `cocktail-bar$ activator`
  This should take you to the activator interactive shell
* Play with some activator commands listed below  
    
*activator is our build tool for this project*


## Summary of important Activator Commands

* exit ends the activator session
* help lists the available commands
* compile compiles the main sources
* console starts the REPL with the project on the classpath
* run looks for an application and runs it
* clean deletes all output in the target directory
* reload reloads the activator session

## Setup an IDE (IntelliJ IDEA)

* Download and install IntelliJ 14 Community Edition and install the Scala plugin
* Open IntelliJ and choose cocktail-bar project
* In the Import Project from SBT window select:
  - Use auto-import
  - Download sources and docs
  - Download SBT sources and docs
  - Java 1.8 as the Project SDK

## Setup an IDE (Eclipse)

- You can download the prepackaged and preconfigured Scala IDE for your platform from scala-ide.org/download/sdk.html
- Go to your cocktail-var activator console and run the command eclipse with-source=true

  `<activator prompt>eclipse with-source=true`

- This will generate eclipse project files.
- Import the cocktail-bar project in eclipse

## For windows users only

The case study makes heavy use of the command line.
If you use Windows, we recommend to install the following tools:

- ConEmu: Terminal with multi-tab support
- Tail: Monitor log file changes
