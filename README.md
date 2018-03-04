hocon-utils
===========

A utility for working with HOCON files from the command line. This is mostly to
help avoid having multiple sources of truth for config values which are in HOCON
files, and would be useful to also access from bash scripts.

Installation
============

Download a pre-built JAR from the releases, or compile from source:

```
$ git clone --depth 1 https://github.com/morgen-peschke/hocon-utils.git hocon-utils
$ cd hocon-utils
$ sbt assembly
$ cp target/scala-2.12/hocon-utils.jar <anywhere you want>
```

Usage
=====

```
Usage: java -jar hocon-utils [options]

  -c, --config <file>    Add additional config files. Later files take priority over files earlier on the command line
  -q, --query <path>     Query and print the config at path, resolved using the configs previously passed.
  -p, --load-properties  Load system properties, priority is set by the flag position on the command line.
  -e, --load-env         Load environment variables, priority is set by the flag position on the command line.

  -s, --show             Print the current state of aggregated configs
  -f, --format <value>   Format used to display configs, this is a global setting. 
                         Defaults to concise-json, must be one of: concise-json pretty-json concise-hocon pretty-hocon

  --help

version: 0.1.0
```

