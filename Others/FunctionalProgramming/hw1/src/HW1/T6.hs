module HW1.T6
  ( mcat,
    epart,
  )
where

import Data.Bifunctor (bimap)
import Data.Either (partitionEithers)
import Data.Foldable (fold)
import Data.Maybe (catMaybes)

mcat :: Monoid a => [Maybe a] -> a
mcat a = fold $ catMaybes a

epart :: (Monoid a, Monoid b) => [Either a b] -> (a, b)
epart a = bimap fold fold $ partitionEithers a
