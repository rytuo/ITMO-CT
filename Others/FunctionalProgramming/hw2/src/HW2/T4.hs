module HW2.T4
  ( State (..)
  , mapState
  , wrapState
  , joinState
  , modifyState
  , Prim (..)
  , Expr (..)
  , eval
  ) where

import Control.Monad (ap)
import HW2.T1 (Annotated (..))

newtype State s a = S {runS :: s -> Annotated s a}

mapState :: (a -> b) -> State s a -> State s b
mapState ab state = S $ \s -> let a :# ns = runS state s in ab a :# ns

wrapState :: a -> State s a
wrapState a = S $ \s -> a :# s

joinState :: State s (State s a) -> State s a
joinState state = S $ \s -> let newState :# ns = runS state s in runS newState ns

modifyState :: (s -> s) -> State s ()
modifyState ss = S $ \s -> () :# ss s

instance Functor (State s) where
  fmap = mapState

instance Applicative (State s) where
  pure = wrapState
  -- ap :: Monad m => m (a -> b) -> m a -> m b
  p <*> q = Control.Monad.ap p q

instance Monad (State s) where
  m >>= f = joinState (fmap f m)

data Prim a
  = Add a a -- (+)
  | Sub a a -- (-)
  | Mul a a -- (*)
  | Div a a -- (/)
  | Abs a -- abs
  | Sgn a -- signum
--  deriving Show

data Expr = Val Double | Op (Prim Expr)
--  deriving Show

instance Num Expr where
  -- (+), (*), abs, signum, fromInteger, (negate | (-))
  x + y = Op (Add x y)
  x * y = Op (Mul x y)
  abs x = Op (Abs x)
  signum x = Op (Sgn x)
  fromInteger x = Val (fromInteger x)
  x - y = Op (Sub x y)

instance Fractional Expr where
  -- fromRational, (/)
  fromRational x = Val (fromRational x)
  x / y = Op (Div x y)

-- (3.14 + 1.618 :: Expr) == Op (Add (Val 3.14) (Val 1.618))
-- runS (do modifyState f; modifyState g; return a) x ≡ a :# g (f x)

eval :: Expr -> State [Prim Double] Double
evalBinOp :: (Double -> Double -> Double) -> (Double -> Double -> Prim Double) -> Expr -> Expr -> State [Prim Double] Double
evalUnOp :: (Double -> Double) -> (Double -> Prim Double) -> Expr -> State [Prim Double] Double

evalBinOp op s l r = do
  a <- eval l
  b <- eval r
  modifyState (s a b :)
  return (op a b)

evalUnOp op s a = do
  b <- eval a
  modifyState (s b :)
  return (op b)

eval (Val d) = pure d
eval (Op op) = case op of
  (Add l r) -> evalBinOp (+) Add l r
  (Sub l r) -> evalBinOp (-) Sub l r
  (Mul l r) -> evalBinOp (*) Mul l r
  (Div l r) -> evalBinOp (/) Div l r
  (Abs a) -> evalUnOp abs Abs a
  (Sgn a) -> evalUnOp signum Sgn a
