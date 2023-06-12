module HW1.T2
  ( N (..),
    nplus,
    nmult,
    nsub,
    ncmp,
    nFromNatural,
    nToNum,
    nEven,
    nOdd,
    ndiv,
    nmod,
  )
where

import Data.Maybe (fromJust, isJust)
import Numeric.Natural

data N
  = Z
  | S N
  deriving Show

nplus :: N -> N -> N        -- addition
nplus Z r     = r
nplus (S n) r = S (n `nplus` r)

nmult :: N -> N -> N        -- multiplication
nmult Z _     = Z
nmult (S l) r = r `nplus` (l `nmult` r)

nsub :: N -> N -> Maybe N   -- subtraction     (Nothing if result is negative)
nsub Z (S _)     = Nothing
nsub l Z         = Just l
nsub (S l) (S r) = l `nsub` r

ncmp :: N -> N -> Ordering  -- comparison      (Do not derive Ord)
ncmp Z Z         = EQ
ncmp (S _) Z     = GT
ncmp Z (S _)     = LT
ncmp (S l) (S r) = l `ncmp` r

nFromNatural :: Natural -> N
nFromNatural 0 = Z
nFromNatural n = S (nFromNatural (n - 1))

nToNum :: Num a => N -> a
nToNum Z     = 0
nToNum (S n) = 1 + nToNum n

nEven, nOdd :: N -> Bool    -- parity checking
nEven Z     = True
nEven (S n) = not $ nEven n
nOdd n = not $ nEven n

ndiv :: N -> N -> N         -- integer division
ndiv _ Z = undefined
ndiv a b = if isJust t
           then S (fromJust t `ndiv` b)
           else Z
             where t = a `nsub` b

nmod :: N -> N -> N         -- modulo operation
nmod a b = if isJust t
           then fromJust t `nmod` b
           else a
             where t = a `nsub` b
