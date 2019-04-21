[![Build Status](https://travis-ci.org/piotrkot/oojson.svg?branch=master)](https://travis-ci.org/piotrkot/oojson)
[![Coverage Status](https://coveralls.io/repos/github/piotrkot/oojson/badge.svg?branch=master)](https://coveralls.io/github/piotrkot/oojson?branch=master)

# Object-oriented Java API for JSON

Simple object-oriented Java API for JSON transformation. It is a wrapper around
[JSON-P](https://javaee.github.io/jsonp/).

## Creating JSON arrays

From values

```java
new JsonArr<>(
    true,
    "a",
    1,
    new JsonArr(),
    new JsonObj()
);
```

or from a string

```java
new JsonArr(
    new StringReader("[false,\"x\",1,[],{}]")
);
```

or from JSON-P array

```java
new JsonArr<>(
    Json.createArrayBuilder().add(0).build()
);
```

## Creating JSON objects

From object attributes

```java
new JsonObj(
    new Attr<>("bool", true),
    new Attr<>("str", "a"),
    new Attr<>("num", 0),
    new Attr<>("arr", new JsonArr()),
    new Attr<>("obj", new JsonObj())
);
```

or from a string

```java
new JsonObj(
    new StringReader(
        "{\"bool\":false,\"str\":\"A\",\"num\":1,\"arr\":[],\"obj\":{}}"
    )
);
```

or from JSON-P object

```java
new JsonObj(
    Json.createObjectBuilder().add("name", "John").build()
);
```

## Transforming JSON arrays (with help of [Cactoos](http://www.cactoos.org/) library)

In the example, numerical values are filtered and mapped to string values

```java
JsonArr<Integer> array = new JsonArr<>(
    10,
    20
);
new JsonArr<>(
    new Mapped<>(
        elem -> elem + " points",
        new Filtered<>(
            elem -> elem > 15,
            array.value()
        )
    )
).jsonValue().toString();
```

The result is array `["20 points"]`.

## Making JSON objects fit

In the example, value of `num` attribute is multiplied by 2.

```java
JsonObj object = new JsonObj(
    new Attr<>("str", "A"),
    new Attr<>("num", 1)
);
new FitValUpd(
    "num",
    (object.<Integer>get("num")) * 2
).make(object).jsonValue().toString();
```

The result is object `{"str":"A","num":2}`.

In the example, attribute with name `delete` is removed and attribute
with name `info` is replaced with attribute `"moreInfo"` and value `true`.

```java
JsonObj object = new JsonObj(
    new Attr<>("delete", "private"),
    new Attr<>("info", "public info")
);
new FitChain<>(
    new FitAttrDel("delete"),
    new FitAttrRepl("info", new Attr<>("moreInfo", true))
).make(object).jsonValue().toString();
```

The result is object `{"moreInfo":true}`.

Library supports [JSON specification](https://json.org/).

Please, note the library is still in early development and it's API can
frequently change.

To get started, add dependency to your project:
```xml
<dependency>
    <groupId>com.github.piotrkot</groupId>
    <artifactId>oojson</artifactId>
    <version>1.6.4</version>
</dependency>
```

You may need to add [JSON-P](https://javaee.github.io/jsonp/) dependency:
```xml
<dependency>
    <groupId>javax.json</groupId>
    <artifactId>javax.json-api</artifactId>
    <version>1.1.4</version>
    <scope>provided</scope>
</dependency>
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.json</artifactId>
    <version>1.1.4</version>
    <scope>runtime</scope>
</dependency>
```

Feel free to fork me on GitHub, report bugs or post comments.

For Pull Requests, please run `mvn clean package`, first.
