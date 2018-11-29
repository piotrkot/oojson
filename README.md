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

## Making JSON objects fit

In the example, value of `num` attribute is multiplied by 2.

```java
JsonObj object = new JsonObj(
    new JsonObj.Attr("str", new Vstr("A")),
    new JsonObj.Attr("num", new Vnum(1))
);
new FitValUpd(
    "num",
    new Vnum(((Number) object.get("num").value()).intValue() * 2)
).make(object).jsonValue().toString();
```

The result is object `{"str":"A","num":2}`.

In the example, attribute with name `delete` is removed and attribute
with name `info` is replaced with attribute `"moreInfo"` and value `true`.

```java
JsonObj object = new JsonObj(
    new JsonObj.Attr("delete", new Vstr("private")),
    new JsonObj.Attr("info", new Vstr("public info"))
);
new FitChain<>(
    new FitAttrDel("delete"),
    new FitAttrRepl("info", new JsonObj.Attr("moreInfo", new Vbool(true)))
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
    <version>1.4.1</version>
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
