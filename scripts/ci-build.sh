#!/bin/sh

#sbt test "+ publishSigned" sonatypeReleaseAll
sbt "publishSigned" sonatypeReleaseAll
