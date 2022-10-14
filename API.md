# Table of contents
-  [`lein2deps.api`](#lein2deps.api) 
    -  [`lein2deps`](#lein2deps.api/lein2deps) - Converts project.clj to deps.edn.

-----
# <a name="lein2deps.api">lein2deps.api</a>






## <a name="lein2deps.api/lein2deps">`lein2deps`</a> [:page_facing_up:](https://github.com/borkdude/lein2deps/blob/main/src/lein2deps/api.clj#L11-L44)
<a name="lein2deps.api/lein2deps"></a>
``` clojure

(lein2deps opts)
```


Converts project.clj to deps.edn.

  Options:
  * `:project-clj` - defaults to `project.clj` in working directory. In case the specified file does not exist, the input is treated as a string.
  * `:eval` - evaluate code in `project.clj`. Defaults to `false`
  * `:write-file` - write `deps.edn` to specified file. Defaults to not writing.
  * `:print` - print `deps.edn` to stdout. Defaults to `false`.
