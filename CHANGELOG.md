# Changelog

[lein2deps](https://github.com/borkdude/lein2deps): A leiningen `project.clj` to Clojure CLI `deps.edn` converter.

## 0.1.1

- [#10](https://github.com/borkdude/lein2deps/issues/10): set the `:fn` option when calling the edamame parser

## 0.1.0

- `lein lein2deps` plugin. See [docs](https://github.com/borkdude/lein2deps#lein-plugin).
- [#7](https://github.com/borkdude/lein2deps/issues/7): maintain order of deps in `project.clj` and no namespacing of maps
- [#2](https://github.com/borkdude/lein2deps/issues/2): accept regex in `project.clj` when safe-parsing
- [#3](https://github.com/borkdude/lein2deps/issues/3): add clojure -Ttool support
- [#4](https://github.com/borkdude/lein2deps/issues/4): convert `:repositories` to `:mvn/repos`
- Add `"resources"` automatically to `:paths`
- Improve quality of generated `deps.edn` ([@jeroenvandijk](https://github.com/jeroenvandijk))
