module HW1.T5
  ( splitOn,
    joinWith,
  )
where

import Data.List.NonEmpty hiding (map)

splitOn :: Eq a => a -> [a] -> NonEmpty [a]
splitOn _ [] = [] :| []
splitOn c (x : xs) = if x == c then [] :| t : ts else (x : t) :| ts
  where (t :| ts) = splitOn c xs

joinWith :: a -> NonEmpty [a] -> [a]
joinWith c (x :| xs) = x ++ concatMap (c :) xs
