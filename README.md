[![Build Status](https://travis-ci.org/piotrkot/oojson.svg?branch=master)](https://travis-ci.org/piotrkot/oojson)
[![Coverage Status](https://coveralls.io/repos/github/piotrkot/oojson/badge.svg?branch=master)](https://coveralls.io/github/piotrkot/oojson?branch=master)

# Object-oriented Java API for JSON

Simple object-oriented Java API for JSON transformation. It is a wrapper around
[JSON-P](https://javaee.github.io/jsonp/).

## Creating JSON arrays

From value objects

```java
new JsonArr(
    new Vbool(true),
    new Vstr("a"),
    new Vnum(1),
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

or by combining JSON-P array with value objects

```java
new JsonArr(
    Json.createArrayBuilder().add(0).build(), new Vnum(1)
);
```

## Creating JSON objects

From object attributes

```java
new JsonObj(
    new JsonObj.Attr("bool", new Vbool(true)),
    new JsonObj.Attr("str", new Vstr("a")),
    new JsonObj.Attr("num", new Vnum(0)),
    new JsonObj.Attr("arr", new JsonArr()),
    new JsonObj.Attr("obj", new JsonObj())
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

or by combining JSON-P object with object attributes

```java
new JsonObj(
    Json.createObjectBuilder().add("name", "John").build(),
    new JsonObj.Attr("age", new Vnum(30))
);
```

## Transforming JSON arrays (with help of [Cactoos](http://www.cactoos.org/) library)

In the example, numerical values are filtered and mapped to string values

```java
JsonArr array = new JsonArr(
    new Vnum(10),
    new Vnum(20)
);
new JsonArr(
    new Mapped<>(
        elem -> new Vstr(elem.value() + " points"),
        new Filtered<>(
            elem -> ((Number) elem.value()).intValue() > 15,
            array.elements()
        )
    )
).jsonValue().toString();
```

The result is array `["20 points"]`.

## Transforming JSON objects

In the example, value of `str` attribute is appended with `"Z"` and
value of `num` attribute is multiplied by 2.

```java
JsonObj object = new JsonObj(
    new JsonObj.Attr("str", new Vstr("A")),
    new JsonObj.Attr("num", new Vnum(1))
);
new JsonObj(
    object,
    new Change.Attr(
        "str", str -> new Vstr(((String) str.value()).concat("Z"))
    ),
    new Change.Attr(
        "num",
        num -> new Vnum(((Number) num.value()).intValue() * 2)
    )
).jsonValue().toString();
```

The result is object `{"str":"AZ","num":2}`.

In the example, attributes with name `delete` are removed and attribute
`more` with value `"info"` is added if not already present.

```java
JsonObj object = new JsonObj(
    new JsonObj.Attr("delete", new Vstr("private")),
    new JsonObj.Attr("keep", new Vstr("public"))
);
new JsonObj(
    object,
    new Change.AttrDel("delete"),
    new Change.AttrMiss("more", new Vstr("info"))
).jsonValue().toString(),
```

The result is object `{"keep":"public","more":"info"}`.

Library supports full [JSON specification](https://json.org/) with one exception -
value `"null"` is not accepted.
  

To get started, add dependency to your project:
```xml
<dependency>
    <groupId>com.github.piotrkot</groupId>
    <artifactId>oojson</artifactId>
    <version>1.2.1</version>
</dependency>
```

You may need to add [JSON-P](https://javaee.github.io/jsonp/) implementation,
like:
```xml
<dependency>
    <groupId>org.glassfish</groupId>
    <artifactId>javax.json</artifactId>
    <version>1.1.4</version>
</dependency>
```

Feel free to fork me on GitHub, report bugs or post comments.

For Pull Requests, please run `mvn clean package`, first.
