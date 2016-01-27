(ns pallet.map-merge-test
  (:use
   clojure.test
   pallet.map-merge))

(deftest merge-keys-default-test
  (is (= {:a 1}
         (merge-keys {} {:a 1} {:a 1})))
  (is (= {:a 1}
         (merge-keys {} {:a 1} {:a 1})))
  (is (= {:a {:b 1}}
         (merge {:a {:c 2}} {:a {:b 1}})))
  (is (= {:a {:b 1 :c 2}}
         (merge-keys {} {:a {:c 2}} {:a {:b 1}}))))

(deftest merge-keys-replace-test
  (is (= {:a {:b 1}}
         (merge-keys {:a :replace} {:a {:c 2}} {:a {:b 1}}))))

(deftest merge-keys-merge-test
  (is (= {:a {:b 1 :c 2}}
         (merge-keys {:a :merge} {:a {:c 2}} {:a {:b 1}}))))

(deftest merge-keys-union-test
  (is (= {:a #{1 2}}
         (merge-keys {:a :union} {:a #{1}} {:a #{2}})))
  (is (= {:a {:b #{1 2}}}
         (merge-keys {:a :merge-union} {:a {:b #{1}}} {:a {:b #{2}}}))))

(deftest merge-keys-concat-test
  (is (= {:a [1 2]}
         (merge-keys {:a :concat} {:a [1]} {:a [2]})))
  (is (= {:a [1 1]}
         (merge-keys {:a :concat} {:a [1]} {:a [1]}))))

(deftest merge-keys-concat-distinct-test
  (is (= {:a [1]}
         (merge-keys {:a :concat-distinct} {:a [1]} {:a [1]}))))

(deftest merge-keys-comp-test
  (is (= 2
         ((:a (merge-keys {:a :comp} {:a inc} {:a inc}))
          0)))
  (is (= 2
         ((-> (merge-keys {:a :merge-comp} {:a {:b inc}} {:a {:b inc}}) :a :b)
          0))))

(deftest merge-keys-test
  (is (= {:a [1 2] :b #{1 2}}
         (merge-keys
          {:a :concat :b :union}
          {:a [1] :b #{1}}
          {:a [2] :b #{2}}))))

(deftest merge-keys-nested-test
  (is (= {:c {:a [1 2] :b #{1 2}}}
         (merge-keys
          {:a :concat :b :union}
          {:c {:a [1] :b #{1}}}
          {:c {:a [2] :b #{2}}}))))
