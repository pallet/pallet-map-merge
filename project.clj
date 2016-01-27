(defproject pallet-map-merge "0.1.1-SNAPSHOT"
  :description "Merging of clojure maps using per-key merge policies."
  :url "https://github.com/pallet/pallet-map-merge"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.2.1"]]
  :profiles {:dev {:dependencies [[codox-md "0.1.0"]
                                  [codox/codox.core "0.6.1"]]}}
  :codox {:writer codox-md.writer/write-docs
          :output-dir "doc/0.1"})
