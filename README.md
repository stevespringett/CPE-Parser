[![Build Status](https://github.com/stevespringett/CPE-Parser/workflows/Maven%20CI/badge.svg)](https://github.com/stevespringett/CPE-Parser/actions?workflow=Maven+CI)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/us.springett/cpe-parser/badge.svg)](https://maven-badges.herokuapp.com/maven-central/us.springett/cpe-parser)
[![License](https://img.shields.io/badge/license-Apache%202.0-brightgreen.svg)][License]


CPE Parser
=========

A utility for parsing, validating, and building Common Platform Enumeration (CPE)
v2.2 and v2.3 as originally defined by MITRE and maintained by NIST.

The implementation's matching deviates slightly from the official matching 
specification:
- matching only returns true or false as opposed to set relations (DISJOINT, SUBSET, SUPERSET, EQUAL, UNDEFINED) specified in the [matching standard](https://nvlpubs.nist.gov/nistpubs/Legacy/IR/nistir7696.pdf).
- `undefined` matches are mapped to either `true` or `false` based on the implementors best judgment; examples: 
  - `ANY` will match `NA` and return `true` instead of `undefined`
  - `ANY` will match `m + wild cards` and return `true` instead of `undefined`
  - `NA` will not match `m + wild cards` and return `false` instead of `undefined`
  - `i` will match `m + wild cards` if `i` matches `m + wild cards` when `m + wild cards` is evalauted as text instead of processing the `wild cards`.
  - `m1 + wild cards` will match `m2 + wild cards` if the expression `m1 + wild cards` matches `m2 + wild cards` when `m2 + wild cards` is treated as text instead of process the `wild cards`


Maven Usage
-------------------

```xml
<dependency>
    <groupId>us.springett</groupId>
    <artifactId>cpe-parser</artifactId>
    <version>3.0.2</version>
</dependency>
```

Example Usage
-------------------

```java
CpeBuilder builder = new CpeBuilder();
Cpe apache = builder.part(Part.APPLICATION).vendor("apache").build();

Cpe parsed = CpeParser.parse("cpe:2.3:a:apache:commons-text:1.6:*:*:*:*:*:*:*");

if (apache.matches(parsed)) {
    System.out.println("Parsed CPE value is an application CPE for the vendor 'apache'");
}
```


Copyright & License
-------------------

CPE Parser is Copyright (c) Steve Springett. All Rights Reserved.

Permission to modify and redistribute is granted under the terms of the 
Apache 2.0 license. See the [LICENSE] file for the full license.

[License]: https://github.com/stevespringett/CPE-Parser/blob/master/LICENSE
