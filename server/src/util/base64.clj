(ns util.base64
  (:import java.util.Base64))

(defn encode
  "s는 반드시 문자열"
  [s]
  (let [encoder (Base64/getEncoder)
        target-bytes (.getBytes s)]
    (-> (.encode encoder target-bytes)
        (String.))))

(defn decode
  "s는 반드시 문자열"
  [s]
  (let [decoder (Base64/getDecoder)
        target-bytes (.getBytes s)]
    (-> (.decode decoder target-bytes)
        (String.))))

(comment
  (def s "hello")
  (encode s)
  (decode (encode s)))
