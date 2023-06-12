{-# LANGUAGE TypeOperators #-}

module HW0.T1 where

data a <-> b = Iso (a -> b) (b -> a)

flipIso :: (a <-> b) -> (b <-> a)
flipIso (Iso f g) = Iso g f

runIso :: (a <-> b) -> (a -> b)
runIso (Iso f _) = f

distrib :: Either a (b, c) -> (Either a b, Either a c)
distrib (Left a) = (Left a, Left a)
distrib (Right (b, c)) = (Right b, Right c)

assocPair :: (a, (b, c)) <-> ((a, b), c)
assocPair = Iso lr rl
  where
    lr (a, (b, c)) = ((a, b), c)
    rl ((a, b), c) = (a, (b, c))

assocEither :: Either a (Either b c) <-> Either (Either a b) c
assocEither = Iso lr rl
  where
    lr (Left a) = Left (Left a)
    lr (Right (Left b)) = Left (Right b)
    lr (Right (Right c)) = Right c
    rl (Left (Left a)) = Left a
    rl (Left (Right b)) = Right (Left b)
    rl (Right c) = Right (Right c)
