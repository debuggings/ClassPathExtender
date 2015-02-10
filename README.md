#ClassPathExtender

[![Join the chat at https://gitter.im/debuggings/ClassPathExtender](https://badges.gitter.im/Join%20Chat.svg)](https://gitter.im/debuggings/ClassPathExtender?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

ClassPathExtender is an open source **Java CLASSPATH management tool** built with Java.


## Goals

When we need to use a third-pary library, the first thing we need to do is to add them to the CLASSPATH.
There are some most common ways to do that:
* Copy to a location where the path is added to the CLASSPATH. But this will mix up all the jars.
* Copy to your project. Still has the problem above.
* Add the jars to the CLASSPATH. I don't want to see the disgusting Environment Variable at all.

So I decide to change the way managing the third-pary jars.

**NOTE:** this tool is only suitable for [JDK1.6 or later][1].

## Guide

1. Add a path to the Environment Variable for a folder you want to put the indexing file for all the other jar files.
    For example：```D:\java\mylibs\index\*```
2. Move ClassPathExtender.jar to this folder.
3. Move the third-party jars to the folder in ```D:\java\mylibs\third-party-lib1\```(I suggests), or leave them where they are.
    But suppose the jars are in the folder ```D:\java\mylibs\third-party-lib1\```.
4. Open the CMD and cd to ```D:\java\mylibs\```.
5. Type command ```java net.debugging.utils.ClassPathExtender ./third-party-lib1```.
6. The command above will generate a index jar (\*\*\*-index.jar) file in current path:```D:\java\mylibs\```.
    Move it to the folder ```D:\java\mylibs\index\*```.

Now the jars are added to the CLASSPATH.

If you want remove a lib, just move the corresponding index jar file out of ```D:\java\mylibs\index\*```.

If you want use an another version of a third-pary library, just move the right version of index jar file in and others versions out.

It's getting simple, the world is getting better!


## Feedback

Any questions or bug reporting, please contact debugging#163.com(relpace # with @).

[1]http://docs.oracle.com/javase/1.5.0/docs/tooldocs/windows/classpath.html
