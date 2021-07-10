# Applause Tester Matching

## Technology stack:

- kotlin 1.5.20
- java 11
- junit5
- assertj

## Required to run:

- JDK 11
- Maven 3.6.3

OR

- Docker

## Running options

1. Using maven:
   - using maven plugin
      - `mvn clean compile`
      - `mvn exec:java`
   - running jar directly
      - `mvn clean package`
      - `java -jar ./target/applause-tester-matching-1.0-SNAPSHOT-jar-with-dependencies.jar`

2. Using docker (require only docker to be installed):
   - manually:
      - `mvn clean package`
      - `docker build -t applause .`
      - `docker run -it --rm applause`
   - automatically:
      - `./run.sh`

## Example usage

```
Welcome to Testing Matcher.
Initializing...
Done.
Type "exit" (without quotes) in order to exit the app

Available countries:
[US, GB, JP]
Available devices:
1 -> iPhone 4
2 -> iPhone 4S
3 -> iPhone 5
4 -> Galaxy S3
5 -> Galaxy S4
6 -> Nexus 4
7 -> Droid Razor
8 -> Droid DNA
9 -> HTC One
10 -> iPhone 3


Please enter search criteria separated with comas:
Countries (default: ALL): US,GB

Devices (default: ALL)(you can also input device's ids from the list): 1,2,HTC One
Executing matching for the following criteria: [US, GB] | [iPhone 4, iPhone 4S, HTC One]
Results:
(id: 4): Taybin Rutkin -> 125
(id: 1): Miguel Bautista -> 49
(id: 9): Darshini Thiagarajan -> 30
(id: 2): Michael Lubavin -> 17

Please enter search criteria separated with comas:
Countries (default: ALL): exit
```
