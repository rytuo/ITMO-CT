module HW2.T5
  ( ExceptState (..)
  , mapExceptState
  , wrapExceptState
  , joinExceptState
  , modifyExceptState
  , throwExceptState
  , EvaluationError (..)
  , eval
  ) where

import HW2.T1 (Except (..), Annotated (..))
import HW2.T4 (Expr (..), Prim (..))
import Control.Monad (ap, when)

newtype ExceptState e s a = ES {runES :: s -> Except e (Annotated s a)}

mapExceptState :: (a -> b) -> ExceptState e s a -> ExceptState e s b
mapExceptState ab es = ES $ \s -> case runES es s of
  (Error e) -> Error e
  (Success (a :# ns)) -> Success (ab a :# ns)

wrapExceptState :: a -> ExceptState e s a
wrapExceptState a = ES $ \s -> Success (a :# s)

joinExceptState :: ExceptState e s (ExceptState e s a) -> ExceptState e s a
joinExceptState es = ES $ \s -> case runES es s of
  (Error e) -> Error e
  (Success (newEs :# ns)) -> runES newEs ns

modifyExceptState :: (s -> s) -> ExceptState e s ()
modifyExceptState f = ES $ \s -> Success (() :# f s)

throwExceptState :: e -> ExceptState e s a
throwExceptState e = ES $ \_ -> Error e

instance Functor (ExceptState e s) where
  fmap = mapExceptState

instance Applicative (ExceptState e s) where
  pure = wrapExceptState
  -- ap :: Monad m => m (a -> b) -> m a -> m b
  p <*> q = Control.Monad.ap p q

instance Monad (ExceptState e s) where
  m >>= f = joinExceptState (fmap f m)

data EvaluationError = DivideByZero
eval :: Expr -> ExceptState EvaluationError [Prim Double] Double
evalBinOp :: (Double -> Double -> Double) -> (Double -> Double -> Prim Double) -> Expr -> Expr -> ExceptState EvaluationError [Prim Double] Double
evalUnOp :: (Double -> Double) -> (Double -> Prim Double) -> Expr -> ExceptState EvaluationError [Prim Double] Double

evalBinOp op s l r = do
  a <- eval l
  b <- eval r
  modifyExceptState (s a b :)
  when (isErr (s a b)) (throwExceptState DivideByZero)
  return (op a b)
  where
    isErr (Div _ 0) = True
    isErr _         = False

evalUnOp op s a = do
  b <- eval a
  modifyExceptState (s b :)
  return (op b)

eval (Val d) = pure d
eval (Op op) = case op of
  (Add l r) -> evalBinOp (+) Add l r
  (Sub l r) -> evalBinOp (-) Sub l r
  (Mul l r) -> evalBinOp (*) Mul l r
  (Div l r) -> evalBinOp (/) Div l r
  (Abs a) -> evalUnOp abs Abs a
  (Sgn a) -> evalUnOp signum Sgn a

-- runES (eval (2 + 3 * 5 - 7)) [] ≡ Success (10 :# [Sub 17 7, Add 2 15, Mul 3 5])
-- runES (eval (1 / (10 - 5 * 2))) [] ≡ Error DivideByZero
