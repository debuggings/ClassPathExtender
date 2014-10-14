#ClassPathExtender

ClassPathExtender is an open source **Java CLASSPATH management tool** built with Java.


## Goals

When we need to use a third-pary library, the first thing we need to do is to add them to the CLASSPATH.
There are some most common ways to do that:
* Copy to a location where the path is added to the CLASSPATH. But this will mix up all the jars.
* Copy to your project. Still has the problem above.
* Add the jars to the CLASSPATH. I don't want to see the disgusting Environment Variable at all.

So I decide to change the way managing the third-pary jars.

**NOTE:** this tool is only suitable for JDK1.6 or later.

## Guide

Take a look at our wiki page for a detailed [installation guide][3].

## Feedback

Any questions or bug reporting, please contact debugging#163.com(relpace # with @).

[1]http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/classpath.html