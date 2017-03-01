NBT   [![Coverage Status](https://coveralls.io/repos/github/nickrobson/nbt/badge.svg?branch=master)](https://coveralls.io/github/nickrobson/nbt?branch=master)
=========

A simple-to-use Java library for manipulating the Named Binary Tag (NBT) format.

### Features
* Supports GZIP and ZLIB compression
* Heirarchical structure
* Read/Write using Netty's ByteBuf
* Gson-like API
* Support for little and big endianness

### Maven

This library is on [Maven Central](http://repo1.maven.org/maven2/xyz/nickr/nbt/), and so does not require any extra repository details.

```xml
<dependencies>
    ...
    <dependency>
        <groupId>xyz.nickr</groupId>
        <artifactId>nbt</artifactId>
        <version>1.1.1</version>
    </dependency>
    ...
</dependencies>
```