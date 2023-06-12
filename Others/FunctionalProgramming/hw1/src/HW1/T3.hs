module HW1.T3
  ( Tree (..),
    tsize,
    tdepth,
    tmember,
    tinsert,
    tFromList,
  )
where

data Tree a
  = Leaf
  | Branch (Int, Int) (Tree a) a (Tree a)
  deriving (Show)

-- | Size of the tree, O(1).
tsize :: Tree a -> Int
tsize Leaf                  = 0
tsize (Branch (n, _) _ _ _) = n

-- | Depth of the tree.
tdepth :: Tree a -> Int
tdepth Leaf                  = 0
tdepth (Branch (_, d) _ _ _) = d

-- | Check if the element is in the tree, O(log n)
tmember :: Ord a => a -> Tree a -> Bool
tmember _ Leaf = False
tmember val (Branch _ l v r)
  = case val `compare` v of
    EQ -> True
    LT -> tmember val l
    GT -> tmember val r

-- | Helper function
mkBranch :: Tree a -> a -> Tree a -> Tree a
mkBranch l v r = Branch (size, depth) l v r
  where size  = tsize l + 1 + tsize r
        depth = 1 + max (tdepth l) (tdepth r)

leftRotation, rightRotation :: Tree a -> a -> Tree a -> Tree a
leftRotation l v (Branch _ rl rv rr)  = mkBranch (mkBranch l v rl) rv rr
leftRotation _ _ Leaf                 = undefined
rightRotation (Branch _ ll lv lr) v r = mkBranch ll lv (mkBranch lr v r)
rightRotation Leaf _ _                = undefined

balanceLeft, balanceRight, balance :: Tree a -> a -> Tree a -> Tree a
balanceLeft l a rr = leftRotation l a (if cd > rd then rightRotation c b r else rr)
  where
    (Branch _ c b r) = rr
    cd = tdepth c
    rd = tdepth r
balanceRight ll = rightRotation (if cd > ld then leftRotation l b c else ll)
  where
    (Branch _ l b c) = ll
    cd               = tdepth c
    ld               = tdepth l
balance l v r
  | ld > rd + 1 = balanceRight l v r
  | ld + 1 < rd = balanceLeft l v r
  | otherwise   = mkBranch l v r
  where
    ld = tdepth l
    rd = tdepth r

-- | Insert an element into the tree, O(log n)
tinsert :: Ord a => a -> Tree a -> Tree a
tinsert val Leaf = Branch (1, 1) Leaf val Leaf
tinsert val (Branch n l v r)
  = case val `compare` v of
      EQ -> Branch n l v r
      LT -> balance (tinsert val l) v r
      GT -> balance l v (tinsert val r)

-- | Build a tree from a list, O(n log n)
tFromList :: Ord a => [a] -> Tree a
tFromList = foldr tinsert Leaf
