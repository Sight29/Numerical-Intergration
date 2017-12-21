; trapezium method
; r is range
; n is nFloat
(defn trapezium [a b n r ]
      (reduce + (map (fn [i] (+(/ (* i r)n)a)) (range 1 (+ b 1))))
)
(trapezium 10 3 2 10)


(defn simpsons1 [a b n r ]
      (reduce + (map (fn [i] (+(/ (* (+ i 0.5) r)n)a)) (range 1 (+ b 1))))
)

(simpsons1 10 3 2 10)


(defn simpsons2 [a b n r ]
      (reduce + (map (fn [i] (+(/ (* 1 r)n)a)) (range 1 (+ b 1))))
)

(simpsons2 10 3 2 10)