module HW2.T3 
  ( joinOption
  , joinExcept
  , joinAnnotated
  , joinList
  , joinFun
  ) where

import HW2.T1 (Option (..), Except (..), Annotated (..), List (..), Fun (..))

-- joinF :: F (F a) -> F a
-- joinF (mapF joinF m)  ≡  joinF (joinF m)
-- joinF      (wrapF m)  ≡  m
-- joinF (mapF wrapF m)  ≡  m
-- distF (p, q) = joinF (mapF (\a -> mapF (\b -> (a, b)) q) p)

joinOption :: Option (Option a) -> Option a
joinOption None = None
joinOption (Some a) = a

joinExcept :: Except e (Except e a) -> Except e a
joinExcept (Error e) = Error e
joinExcept (Success a) = a

joinAnnotated :: Semigroup e => Annotated e (Annotated e a) -> Annotated e a
joinAnnotated ((a :# e2) :# e1) = a :# (e1 <> e2)

joinList :: List (List a) -> List a
joinList Nil = Nil
joinList (Nil :. b) = joinList b
joinList ((a :. xa) :. b) = a :. joinList (xa :. b)

joinFun :: Fun i (Fun i a) -> Fun i a
joinFun (F iia) = F $ \i -> let (F ia) = iia i in ia i
