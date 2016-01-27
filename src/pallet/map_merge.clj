(ns pallet.map-merge
  "Map merging algorithms. Enables specification of merging function on a
   per key basis."
  (:require
   [clojure.set :as set]))

(defmulti merge-key
  "Merge function that dispatches on the map entry key"
  (fn [algorithms key val-in-result val-in-latter]
    (try
      (algorithms key :deep-merge)
      (catch Exception e
        (throw
         (ex-info (str "Cannot merge: "
                       (pr-str val-in-result)
                       (pr-str val-in-latter)
                       "in key " key)
                  {:type :map-merge-fail}
                  e))))))

(defn- map-or-nil? [x]
  (or (nil? x) (map? x)))

(defn merge-keys
  "Returns a map that consists of the rest of the maps conj-ed onto
  the first.  If a key occurs in more than one map, the mapping(s)
  from the latter (left-to-right) will be combined with the mapping in
  the result by calling:
    (merge-key key-algorithms key val-in-result val-in-latter)."
  [key-algorithms & maps]
  {:pre [(every? map-or-nil? maps)]}
  (when (some identity maps)
    (let [merge-entry (fn [m e]
                        (let [k (key e) v (val e)]
                          (if (contains? m k)
                            (assoc m k (merge-key key-algorithms k (get m k) v))
                            (assoc m k v))))
          merge2 (fn [m1 m2]
                   (reduce merge-entry (or m1 {}) (seq m2)))]
      (reduce merge2 maps))))

(defmethod merge-key :replace
  [_ _ val-in-result val-in-latter]
  val-in-latter)

(defmethod merge-key :merge
  [_ _ val-in-result val-in-latter]
  (merge val-in-result val-in-latter))

(defmethod merge-key :deep-merge
  [algorithms _ val-in-result val-in-latter]
  (if (and (map-or-nil? val-in-result) (map-or-nil? val-in-latter))
    (merge-keys algorithms val-in-result val-in-latter)
    (or val-in-latter val-in-result)))

(defmethod merge-key :merge-comp
  [_ _ val-in-result val-in-latter]
  (merge-with comp val-in-latter val-in-result))

(defmethod merge-key :comp
  [_ _ val-in-result val-in-latter]
  (comp val-in-latter val-in-result))

(defmethod merge-key :merge-union
  [_ _ val-in-result val-in-latter]
  (merge-with set/union val-in-result val-in-latter))

(defmethod merge-key :union
  [_ _ val-in-result val-in-latter]
  (set/union val-in-result val-in-latter))

(defmethod merge-key :concat
  [_ _ val-in-result val-in-latter]
  (concat val-in-result val-in-latter))

(defmethod merge-key :concat-distinct
  [_ _ val-in-result val-in-latter]
  (distinct (concat val-in-result val-in-latter)))
