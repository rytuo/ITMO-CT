module HW0.T3 
  ( s
  , k
  , i
  , compose
  , contract
  , permute  
  ) where

s :: (a -> b -> c) -> (a -> b) -> (a -> c)
s f g x = f x (g x)

k :: a -> b -> a
k x _ = x

-- s k :: (a -> b) -> (a -> a)
-- s s :: ((a -> b -> c) -> (a -> b)) -> (a -> b -> c) -> (a -> c)
-- k k :: (a -> b -> a) -> d -> a -> b -> a
-- k s :: s -> d -> (a -> b -> c) -> (a -> b) -> (a -> c)

i :: a -> a
i = s k k

compose :: (b -> c) -> (a -> b) -> (a -> c)
compose = s (k s) k

contract :: (a -> a -> b) -> (a -> b)
contract = s s (s k)

permute :: (a -> b -> c) -> (b -> a -> c)
permute = s (s (k (s (k s) k)) s) (k k)
