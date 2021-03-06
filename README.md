# Data readings generator

Version: `1.1.0`

## About

Ever felt the need to generate random signal strength reading between four coordinates? If so, then you are in luck.

## What's new?

- Possibility to select all or specific tower carriers;
- Data readings are now renderer over time (over 1 day, 1 week, 1 month, a trimeter, a semester or 1 year)
- File size shown when CSV saved;
- Various UI (user interface) and UX (user experience) improvements;
- Various miscellaneous improvements;

## How to use?

First, when you open the main window, you will find a (small) series of features:

- The amount of readings to generate
- The 4 coordinates (they can be anything really, but this is meant for coordinates)

![Picture 1](https://raw.githubusercontent.com/thedrummeraki/java-generate-data-gui/master/img/datagen-pic1.png)

When clicking on `Generate`, the process will start. You will know how far you are in the process since it may take quite some time to complete.

![Picture 2](https://raw.githubusercontent.com/thedrummeraki/java-generate-data-gui/master/img/datagen-pic2.png)

Once complete, you will need to `Save as CSV...` in order to save your changes. After that, you should be golden.

![Picture 3](https://raw.githubusercontent.com/thedrummeraki/java-generate-data-gui/master/img/datagen-pic3.png)

## How to open (IDE)?

You can open this project with IntelliJ (actually, this is **_highly_** recommended). Because Java Swing is used, there is no guarantee that this will work under any other IDE. In the event that dependencies are missing, you are welcome to check them out in the `pom.xml` file located at the root this project.

Once the project is cloned, you can open IntelliJ and select this project as a Maven Project. All dependencies will be found and you should be able to run the application.

> Note: If you have proxy issues with Maven, simple clone the project and open it as a regular project on IntelliJ. Proxy issues may cause building the project with Maven which may lead to compile errors (that shouldn't be there) within IntelliJ.

## How to build?

Here are the requirements for building this application:
- [Maven](https://maven.apache.org/)
- An active internet connection

Here are the direct instructions:

```
$ git clone git@github.com:thedrummeraki/java-generate-data-gui.git
$ cd java-generate-data-gui/
$ mvn clean install assembly:single
$ java -jar target/java-datagen-$(currentversion)-jar-with-dependencies.jar
```

`$(currentversion)` can be found near the title of this readme.
