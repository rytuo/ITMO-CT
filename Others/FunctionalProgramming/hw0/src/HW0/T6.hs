module HW0.T6 
  ( a
  , a_whnf
  , b
  , b_whnf
  , c
  , c_whnf
  ) where

import Data.Char (isSpace)
import HW0.T1 (distrib)

a = distrib (Left ("AB" ++ "CD" ++ "EF"))     -- distrib from HW0.T1
b = map isSpace "Hello, World"
c = if (1::Integer) > 0 || error "X" then "Y" else "Z"

a_whnf = (Left "ABCDEF", Left "ABCDEF")
b_whnf = [False, False, False, False, False, False, True, False, False, False, False, False]
c_whnf = "Y"
