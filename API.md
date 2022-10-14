# Table of contents
-  [`lein2deps.api`](#lein2deps.api) 
    -  [`-main`](#lein2deps.api/-main)
    -  [`lein2deps`](#lein2deps.api/lein2deps) - Converts project.clj to deps.edn.
-  [`lein2deps.build`](#lein2deps.build) 
    -  [`basis`](#lein2deps.build/basis)
    -  [`compile-java`](#lein2deps.build/compile-java)
-  [`lein2deps.internal`](#lein2deps.internal) 
    -  [`add-prep-lib`](#lein2deps.internal/add-prep-lib)
    -  [`convert-dep`](#lein2deps.internal/convert-dep)
    -  [`defproject`](#lein2deps.internal/defproject)
    -  [`qualify-dep-name`](#lein2deps.internal/qualify-dep-name)
    -  [`safe-parse`](#lein2deps.internal/safe-parse)

-----
# <a name="lein2deps.api">lein2deps.api</a>






## <a name="lein2deps.api/-main">`-main`</a> [:page_facing_up:](borkdude/lein2deps/blob/main/src/lein2deps/api.clj#L43-L53)
<a name="lein2deps.api/-main"></a>
``` clojure

(-main & args)
```


## <a name="lein2deps.api/lein2deps">`lein2deps`</a> [:page_facing_up:](borkdude/lein2deps/blob/main/src/lein2deps/api.clj#L9-L41)
<a name="lein2deps.api/lein2deps"></a>
``` clojure

(lein2deps opts)
```


Converts project.clj to deps.edn.

  Options:
  * `:project-clj` - defaults to `project.clj` in working directory. In case the specified file does not exist, the input is treated as a string.
  * `:eval` - evaluate code in `project.clj`. Defaults to `false`
  * `:write-file` - write `deps.edn` to specified file. Defaults to not writing.
  * `:print` - print `deps.edn` to stdout. Defaults to `true`.

-----
# <a name="lein2deps.build">lein2deps.build</a>






## <a name="lein2deps.build/basis">`basis`</a> [:page_facing_up:](borkdude/lein2deps/blob/main/src/lein2deps/build.clj#L4-L4)
<a name="lein2deps.build/basis"></a>

## <a name="lein2deps.build/compile-java">`compile-java`</a> [:page_facing_up:](borkdude/lein2deps/blob/main/src/lein2deps/build.clj#L6-L8)
<a name="lein2deps.build/compile-java"></a>
``` clojure

(compile-java _)
```


-----
# <a name="lein2deps.internal">lein2deps.internal</a>






## <a name="lein2deps.internal/add-prep-lib">`add-prep-lib`</a> [:page_facing_up:](borkdude/lein2deps/blob/main/src/lein2deps/internal.clj#L40-L55)
<a name="lein2deps.internal/add-prep-lib"></a>
``` clojure

(add-prep-lib deps-edn project-edn)
```


## <a name="lein2deps.internal/convert-dep">`convert-dep`</a> [:page_facing_up:](borkdude/lein2deps/blob/main/src/lein2deps/internal.clj#L30-L38)
<a name="lein2deps.internal/convert-dep"></a>
``` clojure

(convert-dep [name version & {:keys [classifier exclusions]}])
```


## <a name="lein2deps.internal/defproject">`defproject`</a> [:page_facing_up:](borkdude/lein2deps/blob/main/src/lein2deps/internal.clj#L6-L13)
<a name="lein2deps.internal/defproject"></a>
``` clojure

(defproject & [_name _version & body])
```


## <a name="lein2deps.internal/qualify-dep-name">`qualify-dep-name`</a> [:page_facing_up:](borkdude/lein2deps/blob/main/src/lein2deps/internal.clj#L25-L28)
<a name="lein2deps.internal/qualify-dep-name"></a>
``` clojure

(qualify-dep-name d)
```


## <a name="lein2deps.internal/safe-parse">`safe-parse`</a> [:page_facing_up:](borkdude/lein2deps/blob/main/src/lein2deps/internal.clj#L15-L23)
<a name="lein2deps.internal/safe-parse"></a>
``` clojure

(safe-parse input)
```

