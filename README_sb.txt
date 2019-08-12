Change version:
 - in gradle.properties
 - in py4j-python/src/py4j/version.py
 - in py4j-java/ant.properties (never know)

Build:
do once?:
$ gradle init
at top level:
$ ./gradlew buildPython -x py4j-java:test
 * compiles java in py4j-java/build and py4j-java/py4j-<version>.jar
 * builds python wheel in py4j-python/dist
   this wheel contains also the java jar (named py4j<version>.jar) and the javadoc

Note: When using anaconda environment for python, in Intellij:
 - install the python plugin in IDEA
 - setup the python interpreter using the python exec in the conda environment (full path),
 - for each build configuration,
   -- set the environment variable CONDA_DEFAULT_PREFIX to your conda env name
   -- set the PATH to point to the correct anaconda env python interpreter

Clean:
 $ ./gradlew clean
 
 
