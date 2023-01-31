# lein2deps

A leiningen `project.clj` to Clojure CLI `deps.edn` converter.

## [API](API.md)

## Usage

With [babashka](https://babashka.org/):

``` shell
bb -Sdeps '{:deps {io.github.borkdude/lein2deps {:git/sha "..."}}}' \
   -m lein2deps.api --print --write-file deps.edn
```

With [bbin](https://github.com/babashka/bbin):

``` shell
bbin install https://raw.githubusercontent.com/borkdude/tools/main/lein2deps.clj
lein2deps --print --write-file deps.edn
```

With Clojure:

``` shell
clj -Sdeps '{:deps {io.github.borkdude/lein2deps {:git/sha "..."}}}' \
    -M -m lein2deps.api --print --write-file deps.edn
```

With Clojure CLI Tools:

``` shell
clojure -Ttools install-latest :lib io.github.borkdude/lein2deps :as lein2deps

clojure -Tlein2deps lein2deps :print true :write-file "deps.edn"
```

## Java compilation

This tool respects `:java-source-paths` in `project.clj` and adds a `:deps/prep-lib`
entry to your `deps.edn` so users of your project will have to execute:

```
clj -X:deps prep
```

to compile Java sources before being able to use your project.

For some reason this doesn't work from inside the project. To compile Java locally:

``` shell
clojure -X:lein2deps compile-java
```

## Lein plugin

You can use the leiningen plugin to automatically synchronize your `project.clj` to a `deps.edn`.

Add:

``` clojure
:plugins [[io.github.borkdude/lein-lein2deps "0.1.0"]]
```

to your `project.clj` and then run:

``` shell
lein lein2deps --write-file deps.edn --print false
```

To run the plugin on any invocation of `lein`, add it to `:prep-tasks`:

``` clojure
(defproject my-project "0.1.0"
  :plugins [[io.github.borkdude/lein-lein2deps "0.1.0"]]
  :dependencies [[org.clojure/clojure "1.11.1"]]
  :prep-tasks [["lein2deps" "--write-file" "deps.edn" "--print" "false"]])
```

## Test runner

Check out [neil](https://github.com/babashka/neil#add-test) for easily adding a test runner to `deps.edn`.

## How to proceed

If you are new to `deps.edn`, check out [this blog article](https://blog.michielborkent.nl/new-clojure-project-quickstart.html) on how to get started quickly.

## License

Copyright (c) 2022 Michiel Borkent.

Licensed under MIT, see [LICENSE](LICENSE)
