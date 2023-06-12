module HW1.T4
  ( tfoldr,
    treeToList,
  )
where

import HW1.T3 (Tree (..))

tfoldr :: (a -> b -> b) -> b -> Tree a -> b
tfoldr _ ac Leaf             = ac
tfoldr f ac (Branch _ l v r) = tfoldr f (v `f` tfoldr f ac r) l

treeToList :: Tree a -> [a]    -- output list is sorted
treeToList = tfoldr (:) []
