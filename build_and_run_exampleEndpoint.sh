 #! /bin/bash
 . /opt/jemstep/code/stormsend/conf/env.sh

 sbt "assembly"

 cd /opt/jemstep/code/microServiceExample/target/scala-2.12/

 java -jar "microServiceExample-assembly-0.1.jar"
