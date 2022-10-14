# lein2deps

A leiningen `project.clj` to Clojure CLI `deps.edn` converter.

## [API]((API.md))

## Usage

Via [babashka](https://babashka.org/):

``` shell
bb -Sdeps '{:deps {io.github.borkdude/lein2deps {:git/sha "..."}}}' \
   -m lein2deps.api --print --write-file deps.edn
```

Via [bbin](https://github.com/babashka/bbin):

``` shell
bbin install https://raw.githubusercontent.com/borkdude/tools/main/lein2deps.clj
lein2deps --print --write-file deps.edn
```

In Clojure:

``` shell
clj -Sdeps '{:deps {io.github.borkdude/lein2deps {:git/sha "..."}}}' \
    -M -m lein2deps.api --print --write-file deps.edn
```

## Java compilation

This tool respects `:java-source-paths` in `project.clj` and adds a `:prep/lib`
entry to your `deps.edn` so users of your project will have to execute:

```
clj -X:deps prep
```

to compile Java sources before being able to use your project.

For some reason this doesn't work from inside the project. There you execute:

``` shell
clojure -X:lein2deps compile-java
```

to compile Java.

## License

Copyright (c) 2022 Michiel Borkent.

Licensed under MIT, see [LICENSE](LICENSE)
