module HW0.T5
  ( Nat
  , nz
  , ns
  , nplus
  , nmult
  , nFromNatural
  , nToNum
  ) where

import GHC.Natural (Natural)

type Nat a = (a -> a) -> a -> a

nz :: Nat a
nz _ = id

ns :: Nat a -> Nat a
ns n f = f . n f

nplus, nmult :: Nat a -> Nat a -> Nat a
nplus n1 n2 f = n1 f . n2 f
nmult n1 n2 = n1 . n2

nFromNatural :: Natural -> Nat a
nFromNatural 0 = nz
nFromNatural x = ns (nFromNatural (x - 1))

nToNum :: Num a => Nat a -> a
nToNum n = n (1 +) 0

--nToNum nz       ==  0
--nToNum (ns x)   ==  1 + nToNum x

--nToNum (nplus a b)   ==   nToNum a + nToNum b
--nToNum (nmult a b)   ==   nToNum a * nToNum b
