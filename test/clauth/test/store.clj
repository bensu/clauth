(ns clauth.test.store
  (:use [clauth.token])
  (:use [clauth.store])
  (:use [clojure.test])
  )


  (deftest memory-store-implementaiton
    (let [st (create-memory-store)]
      (is (= 0 (count (entries st))))
      (is (= [] (entries st)))
      (is (nil? (fetch st "item")))

      (let [item (store st :key {:key  "item" :hello "world"})]
        (is (= 1 (count (entries st))))
        (is (= item (fetch st "item")))
        (is (= [item] (entries st)))
        (do
          (reset-store! st)
          (is (= 0 (count (entries st))))
          (is (= [] (entries st)))
          (is (nil? (fetch st "item")))))))

 (deftest token-store-implementation
   (clauth.store/reset-memory-store!)
   (is (= 0 (count (tokens))) "starts out empty")
   (let 
      [record (oauth-token "my-client" "my-user")]
      (is (nil? (fetch-token (:token record))))
      (do
        (store-token record)
        (is (= record (fetch-token (:token record))))
        (is (= 1 (count (tokens))) "added one"))))
