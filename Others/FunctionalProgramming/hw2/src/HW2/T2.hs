module HW2.T2
  ( distOption
  , wrapOption
  , distPair
  , wrapPair
  , distQuad
  , wrapQuad
  , distAnnotated
  , wrapAnnotated
  , distExcept
  , wrapExcept
  , distPrioritised
  , wrapPrioritised
  , distStream
  , wrapStream
  , distList
  , wrapList
  , distFun
  , wrapFun
  ) where

import HW2.T1 hiding (Tree)

-- distF :: (F a, F b) -> F (a, b)

-- distF (wrapF a, wrapF b)  ≅  wrapF (a, b)
-- distF (p, distF (q, r))   ≅  distF (distF (p, q), r)
-- distF (wrapF (), q)  ≅  q
-- distF (p, wrapF ())  ≅  p
-- ((a, b), c)  ≅  (a, (b, c))  -- for associativity
-- ((), b)  ≅  b                -- for left identity
-- (a, ())  ≅  a                -- for right identity
-- distPrioritised must pick the higher priority out of the two.
-- distList must associate each element of the first list with each element of the second list
-- (i.e. the resulting list is of length n × m).
-- You must implement these functions by hand, using only:
-- data types that you defined in HW2.T1
-- (<>) and mempty for Annotated

distOption :: (Option a, Option b) -> Option (a, b)
distOption (_, None) = None
distOption (None, _) = None
distOption (Some a, Some b) = Some (a, b)

distPair :: (Pair a, Pair b) -> Pair (a, b)
distPair (P a1 a2, P b1 b2) = P (a1, b1) (a2, b2)

distQuad :: (Quad a, Quad b) -> Quad (a, b)
distQuad (Q a1 a2 a3 a4, Q b1 b2 b3 b4) = Q (a1, b1) (a2, b2) (a3, b3) (a4, b4)

distAnnotated :: Semigroup e => (Annotated e a, Annotated e b) -> Annotated e (a, b)
distAnnotated (a :# e1, b :# e2) = (a, b) :# (e1 <> e2)

distExcept :: (Except e a, Except e b) -> Except e (a, b)
distExcept (Error e, _) = Error e
distExcept (_, Error e) = Error e
distExcept (Success a, Success b) = Success (a, b)

distPrioritised :: (Prioritised a, Prioritised b) -> Prioritised (a, b)
distPrioritised (High a, High b) = High (a, b)
distPrioritised (High a, Medium b) = High (a, b)
distPrioritised (High a, Low b) = High (a, b)
distPrioritised (Medium a, High b) = High (a, b)
distPrioritised (Medium a, Medium b) = Medium (a, b)
distPrioritised (Medium a, Low b) = Medium (a, b)
distPrioritised (Low a, High b) = High (a, b)
distPrioritised (Low a, Medium b) = Medium (a, b)
distPrioritised (Low a, Low b) = Low (a, b)

distStream :: (Stream a, Stream b) -> Stream (a, b)
distStream (a :> sa, b :> sb) = (a, b) :> distStream (sa, sb)

distList :: (List a, List b) -> List (a, b)
distList (Nil, _) = Nil
distList (a :. ta, b) = distAList a b
  where
    distAList _ Nil = distList (ta, b)
    distAList c (d :. k) = (c, d) :. distAList c k

distFun :: (Fun i a, Fun i b) -> Fun i (a, b)
distFun (F ia, F ib) = F $ \i -> (ia i, ib i)

-- wrapF :: a -> F a

wrapOption :: a -> Option a
wrapOption = Some

wrapPair :: a -> Pair a
wrapPair a = P a a

wrapQuad :: a -> Quad a
wrapQuad a = Q a a a a

wrapAnnotated :: Monoid e => a -> Annotated e a
wrapAnnotated a = a :# mempty

wrapExcept :: a -> Except e a
wrapExcept = Success

wrapPrioritised :: a -> Prioritised a
wrapPrioritised = Low

wrapStream :: a -> Stream a
wrapStream a = a :> wrapStream a

wrapList :: a -> List a
wrapList = (:. Nil)

wrapFun :: a -> Fun i a
wrapFun a = F $ const a
