module HW0.T4
  ( repeat'
  , map'
  , fib
  , fac
  ) where

import Data.Function (fix)
import GHC.Natural (Natural)

repeat' :: a -> [a]             -- behaves like Data.List.repeat
repeat' x = fix (x:)

map' :: (a -> b) -> [a] -> [b]  -- behaves like Data.List.map
map' f = fix (\re a -> case a of
  (s:xs) -> f s : re xs
  []     -> []
  )

fib :: Natural -> Natural       -- computes the n-th Fibonacci number
fib = fix (\re a b n ->
  if n == 0
    then a
    else re b (a + b) (n - 1)
  ) 0 1

fac :: Natural -> Natural       -- computes the factorial
fac = fix (\re n ->
  if n <= 1
    then 1
    else n * re (n-1)
  )
