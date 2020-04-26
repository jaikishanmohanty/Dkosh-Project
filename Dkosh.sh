#!/bin/bash

set -x

java -cp /home/priyankag/eclipse-workspace/Library/lib/*:/home/priyankag/eclipse-workspace/Library/bin:/home/priyankag/eclipse-workspace/ProjectDkosh/bin org.testng.TestNG /home/priyankag/eclipse-workspace/ProjectDkosh/TS_001.xml

set +x