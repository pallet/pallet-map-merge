(defproject pallet-map-merge "0.1.0-SNAPSHOT"
  :description "A library to allow merging of clojure maps using per-key merge policies."
  :url "https://github.com/pallet/pallet-map-merge"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.3.0"]]
  :profiles {:dev {:dependencies [[codox-md "0.1.0"]
                                  [codox/codox.core "0.6.2-SNAPSHOT"]]}}
  :codox {:writer codox-md.writer/write-docs
          :output-dir "doc/0.1"})
