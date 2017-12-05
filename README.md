# Data readings generator

Version: `1.0.0`

## About

Ever felt the need to generate random signal strength reading between four coordinates? If so, then you are in luck.

## How to use?

First, when you open the main window, you will find a (small) series of features:

- The amount of readings to generate
- The 4 coordinates (they can be anything really, but this is meant for coordinates)

[Picture 1](https://github.com/thedrummeraki/java-generate-data-gui/img/datagen-pic1.png)

When clicking on `Generate`, the process will start. You will know how far you are in the process since it may take quite some time to complete.

[Picture 2](https://github.com/thedrummeraki/java-generate-data-gui/img/datagen-pic2.png)

Once complete, you will need to `Save as CSV...` in order to save your changes. After that, you should be golden.

[Picture 3](https://github.com/thedrummeraki/java-generate-data-gui/img/datagen-pic3.png)

## How to build?

Here are the requirements for building this application:
- [Maven](https://maven.apache.org/)
- An active internet connection

Here are the direct instructions:

```
$ git clone
$ cd java-generate-data-gui/
$ mvn clean install
$ java -jar target/java-datagen-$(currentversion)-jar-with-dependencies.jar
```

`$(currentversion)` can be found near the title of this readme.




