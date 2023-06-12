module HW2.T6
  ( ParseError (..)
  , Parser (..)
  , runP
  , pChar
  , parseError
  , pEof
  , parseExpr
  ) where

import Control.Applicative
import Control.Monad
import qualified Data.Char (digitToInt, isDigit, isSpace)
import Data.Maybe (fromMaybe)
import qualified Data.Scientific as Scientific (scientific, toRealFloat)
import HW2.T1 (Except (..), Annotated (..))
import HW2.T4 (Expr (..), Prim (..))
import HW2.T5 (ExceptState (..), mapExceptState, wrapExceptState)
import Numeric.Natural

newtype ParseError = ErrorAtPos Natural
--  deriving Show

newtype Parser a = P (ExceptState ParseError (Natural, String) a)

instance Functor Parser where
  fmap f (P es) = P $ mapExceptState f es

instance Applicative Parser where
  pure a = P $ wrapExceptState a
  (P es1) <*> (P es2) = P $ es1 <*> es2

instance Monad Parser where
  (P es) >>= f = P $
    ES $ \s -> case runES es s of
      (Error e) -> Error e
      (Success (a :# ns)) -> let (P newES) = f a in runES newES ns

runP :: Parser a -> String -> Except ParseError a
runP (P es) s = case runES es (0, s) of
  (Error e) -> Error e
  (Success (a :# _)) -> Success a

-- | if input string is empty returns Error
-- | else returns current char and
-- | changes state from (pos, s:cs) to (pos + 1, cs) e.g. goes to the next symbol
pChar :: Parser Char
pChar = P $
  ES $ \(pos, s) ->
    case s of
      [] -> Error (ErrorAtPos pos)
      (c : cs) -> Success (c :# (pos + 1, cs))

parseError :: Parser a
parseError = P $ ES $ \(pos, _) -> Error (ErrorAtPos pos)

instance Alternative Parser where
  empty = parseError
  (<|>) (P es1) (P es2) = P $
    ES $ \s -> case runES es1 s of
      (Success a) -> Success a
      (Error _) -> runES es2 s

-- empty <|> p  ≡  p
-- p <|> empty  ≡  p

instance MonadPlus Parser

pEof :: Parser ()
pEof = P $
  ES $ \(pos, s) ->
    if s == ""
      then Success (() :# (pos, s))
      else Error (ErrorAtPos pos)

-- fold results  == msum     :: (Foldable t, MonadPlus m) => t (m a) -> m a
-- result if     == mfilter  :: MonadPlus m => (a -> Bool) -> m a -> m a
-- one or none   == optional :: Alternative f => f a -> f (Maybe a)
-- zero or more  == many     :: f a -> f [a]
-- one or more   == some     :: f a -> f [a]
-- discard value == void     :: Functor f => f a -> f ()

-- EXAMPLE:
-- pAbbr :: Parser String
-- pAbbr = do
--   abbr <- some (mfilter Data.Char.isUpper pChar)
--   pEof
--   pure abbr

-- Expr = Poly
-- Poly = Mono Op1
-- Op1  = + Mono Op1 | - Mono Op1 | E
-- Mono = Val Op2
-- Op2  = * Val Op2 | / Val Op2 | E
-- Val  = ws Num ws | ws (Poly) ws

pExpr, pPoly, pMono, pVal :: Parser Expr
pOp :: Parser Expr -> [Parser (Expr -> Expr -> Prim Expr)] -> Parser Expr
pExpr = do
  res <- pPoly
  pEof
  return res

pOp pElem ops = do
  a <- pElem
  opList <- many $ do
    op <- foldl1 (<|>) ops
    r <- pElem
    return $ \l -> Op (op l r)
  return $ foldl (\l f -> f l) a opList

-- return $ foldl (flip ($)) a opList

pPoly = pOp pMono [pAdd, pSub]
  where
    pAdd = mfilter (== '+') pChar >> return Add
    pSub = mfilter (== '-') pChar >> return Sub

pMono = pOp pVal [pMul, pDiv]
  where
    pMul = mfilter (== '*') pChar >> return Mul
    pDiv = mfilter (== '/') pChar >> return Div

pVal = do
  pSkipWs
  val <- pNum <|> pBrackets
  pSkipWs
  return val
  where
    pSkipWs = void $ many $ mfilter Data.Char.isSpace pChar

    pDigits = some $ mfilter Data.Char.isDigit pChar
    pSkipPoint = void $ mfilter (== '.') pChar
    toScientific int frac =
      Scientific.scientific
        (foldl1 (\acc x -> 10 * acc + x) (map (toInteger . Data.Char.digitToInt) $ int ++ frac))
        (negate $ length frac)

    pNum = do
      int <- pDigits
      frac <- optional $ pSkipPoint >> pDigits
      return $ Val $ Scientific.toRealFloat $ toScientific int (fromMaybe "" frac)

    pSkipOpen = void $ mfilter (== '(') pChar
    pSkipClose = void $ mfilter (== ')') pChar
    pBrackets = do
      pSkipOpen
      v <- pPoly
      pSkipClose
      return v

parseExpr :: String -> Except ParseError Expr
parseExpr = runP pExpr
