# pallet-map-merge

A library to allow merging of clojure maps using per-key merge policies.

## Usage

By default, `merge-keys` does a deep merge:

```clj
(merge-keys {} {:a {:c 2}} {:a {:b 1}})
  => {:a {:b 1 :c 2}}
```

You can override this by specifying a merge policy on a per key basis.

```clj
(merge-keys {:a :union} {:a #{1}} {:a #{2}})
  => {:a #{1 2}}
```

```clj
(merge-keys
 {:a :concat :b :union}
 {:a [1] :b #{1}}
 {:a [2] :b #{2}})
  => {:a [1 2] :b #{1 2}}
```

## Installation

To use pallet-thread, add the following to your :dependencies:

[pallet-thread "0.1.0-SNAPSHOT"]

## License

Copyright © 2012 Hugo Duncan

Distributed under the Eclipse Public License.
