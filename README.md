# Horse Racing Simulator

## Introduction

The Horse Racing Simulator, created using [Java](https://www.java.com/download/ie_manual.jsp) and Java Swing is a visual application designed to allow users to simulate races and customise Horses. This application was designed as a university project to enhance my knowledge of graphical user interfaces and object oriented programming. 

![HorseRace](https://i.imgur.com/7SLC05o.png)

## 📌 Program Description

This program contains two folders; Part1 and Part2. The part1 folder contains a simple terminal text based interface for horse racing. Its meant to show the inital revision of my HorseRacingSimulator Before i adapted it to a fully functional graphical user interface. The part2 folder contains all of the files relating to the Graphical adaption of the HorseRacingSimulator. The program initialises at the MainMenu where users can pick to navigate to the tutorial page, statistics page or the horse customisation page. The tutorial page contains a step by step tutorial to navigating the program. The statistics menu displays the confidence value, name, symbol, accessory option, breed and color of each Horse added to the program. The horse customisation page contains three seperate panels containing the confidence value, name, symbol, accessory option, breed and color for each Horse for the user to add to the program before racing. The reset option works as a edit option and removes any prior horse information from the program. From the Horse customisation page, once all three Horses and their information have been added to the program the user can navigate to the Start Race Gui menu and begin racing with the horses. The confidence level of each Horse determines the likelihood for a Horse to either move forward or to fall over. The higher the confidence the more likely a Horse will fall over during a race and the more likely the horse will move forward. The opposite occurs for lower confidence values. When a Horse wins the race its confidence value is increased by 0.05 while if a Horse falls over its confidence value is decreased by 0.05. At the end of each Race the Results of the race are saved to the race results file and displayed for the user to see in the statistics menu. In the event of multiple winners a draw message is returned and in the event of no winners (all Horses falling) then a no winner message is displayed. From the racing menu users can choose to either navigate back to the horse customisation page and edit their horses , reset the race or view the statistics in the statistics menu. 

The program contains several files:

- `MainMenu.GUI` - Main entry point of the program containing the `tutorial button`, `horse customisation button` and `statistics button`.
- `Horse.java` - Contains the Horse accessor/mutator methods.
- `Race.java` - Contains the race logic such as moving the Horse , announcing winners or checking if a Horse has fallen. 
- `StartRaceGUI.java` - Contains the GUI output for the Horse Race and buttons to navigate back to `Statistics` and `Horse Customisation`.
- `Statistics.java` - Contains a page that displays all current Horses on the page and the results of each previous race. 
- `Tutorial.java` - Contains a tutorial on how to use the program. 
- `CustomiseHorse.java` - Contains options to reset Horse information and add Horses to the program.
- `horseAttribute.txt` - Contains the Horse attributes such as confidence etc.
- `raceResult.txt` - Contains the Race results.
- `HorseRaceSim.bat` - Executable to run the program easier. 


## 📌 Installation

### 1. Installing the Java Development Kit

- Visit the official Oracle website: [Oracle JDK Downloads](https://www.oracle.com/java/technologies/javase-jdk15-downloads.html).
- Download the JDK version 17.0.8 for your operating system.

### 2. Installing the repository 

- Select the current release `V.1.0.0` and download the zip file.
- Unzip the file and save it to your personal computer. 

### 3. Running the application

- Navigate to the directory where you saved the zip file containing the codebase and run the `HorseRaceSim.bat` executable.

## ⚙ Dependencies
- Java (At least JDK 17.0.8)

## 📝 Contributing
Your contributions are always welcome. For major revisions, please start by opening an issue to discuss what you'd like to change.

## 📜 License
This project is licensed under the [MIT License](https://opensource.org/licenses/MIT).

## 💼 Contact
For any inquiries, suggestions, or feedback, don't hesitate to email me at [ostrynskimaks@gmail.com](mailto:ostrynskimaks@gmail.com).
We encourage suggestions for improvements and new features!

###### KashiCode © 2024
