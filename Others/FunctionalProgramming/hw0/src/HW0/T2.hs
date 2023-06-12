module HW0.T2 
  ( Not
  , doubleNeg
  , reduceTripleNeg
  ) where

import Data.Void (Void)

type Not a = a -> Void

doubleNeg :: a -> Not (Not a)
--doubleNeg :: a -> (a -> void) -> void
doubleNeg a neg = neg a

reduceTripleNeg :: Not (Not (Not a)) -> Not a
--reduceTripleNeg :: ((Not (Not a)) -> void) -> a -> void
reduceTripleNeg tripleNeg = tripleNeg . doubleNeg
