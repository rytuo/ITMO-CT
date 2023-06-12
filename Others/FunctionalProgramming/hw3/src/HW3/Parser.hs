module HW3.Parser
  ( parse
  , errorBundlePretty
  ) where

import HW3.Base (HiFun (..), HiValue (..), HiExpr (..), HiAction (..))
import Data.Void (Void)
import Text.Megaparsec (ParseErrorBundle, Parsec, runParser, eof, errorBundlePretty, sepBy, sepEndBy, between, manyTill, count, satisfy, many, notFollowedBy, choice, try, sepBy1)
import Text.Megaparsec.Char (space, string, char, hexDigitChar)
import Text.Megaparsec.Char.Lexer (scientific, signed, charLiteral)
import Control.Monad.Combinators (some, optional, (<|>))
import Control.Monad.Combinators.Expr (makeExprParser, Operator (..))
import Data.Text (pack)
import qualified Data.ByteString as BS (pack)
import Numeric (readHex)
import Data.Char (isAlphaNum, isAlpha)

type Parser = Parsec Void String

parse :: String -> Either (ParseErrorBundle String Void) HiExpr
parse = runParser pLine "error_log.txt"

pLine :: Parser HiExpr
pLine = pHiExprInfix <* eof

pHiExpr :: Parser HiExpr
pHiExpr = pHiVal >>= pHiExprN

pHiExprN :: HiExpr -> Parser HiExpr
pHiExprN e1 = do
  brackets <- optional $ some (pBrackets pArgs <|> pDot)
  let e2 = case brackets of
             Nothing -> e1
             Just a  -> foldl HiExprApply e1 a
  run <- optional $ char '!'
  space
  case run of
    Nothing   -> return e2
    Just _    -> pHiExprN (HiExprRun e2)

pDot :: Parser [HiExpr]
pDot = (: []) . HiExprValue . HiValueString . pack <$> (char '.' >> pId)

pId :: Parser String
pId = foldl1 (\s1 s2 -> s1 ++ "-" ++ s2) <$> (((:) <$> satisfy isAlpha <*> many (satisfy isAlphaNum)) `sepBy1` char '-')

pBrackets :: Parser a -> Parser a
pBrackets = between (space >> char '(') (char ')' >> space)

pArgs :: Parser [HiExpr]
pArgs = pHiExprInfix `sepBy` char ','

pHiVal :: Parser HiExpr
pHiVal = between space space ((
  HiExprValue <$> (
    pHiString <|>
    pHiBool <|>
    pHiNum <|>
    pHiFun <|>
    pHiNull <|>
    pHiBytes <|>
    pHiAction)) <|>
  pHiList <|>
  pBrackets pHiExprInfix <|>
  pHiMap)

pHiNum :: Parser HiValue
pHiNum = HiValueNumber . toRational <$> signed space scientific

pHiBool :: Parser HiValue
pHiBool = HiValueBool <$>
  ((string "true" >> return True) <|>
   (string "false" >> return False))

pHiNull :: Parser HiValue
pHiNull = string "null" >> return HiValueNull

pHiString :: Parser HiValue
pHiString = HiValueString . pack <$> (char '\"' >> manyTill charLiteral (char '\"'))

pHiList :: Parser HiExpr
pHiList = HiExprApply (HiExprValue (HiValueFunction HiFunList)) <$>
  between (char '[' >> space) (space >> char ']') pArgs

pHiBytes :: Parser HiValue
pHiBytes = HiValueBytes . BS.pack <$> between (string "[#" >> space) (string "#]")
  ((fst . head . readHex <$> count 2 hexDigitChar) `sepEndBy` (char ' ' >> space))

pHiMap :: Parser HiExpr
pHiMap = HiExprDict <$> between (char '{' >> space) (space >> char '}')
  (pEntry `sepBy` char ',')
  where
    pEntry = do
      space
      k <- pHiExprInfix
      space >> char ':' >> space
      v <- pHiExprInfix
      space
      return (k, v)

pHiAction :: Parser HiValue
pHiAction = HiValueAction <$> choice
  [ pStr "cwd" HiActionCwd
  , pStr "now" HiActionNow]
  where pStr s r = string s >> return r

pHiFun :: Parser HiValue
pHiFun = HiValueFunction <$> choice
  [ pStr "add" HiFunAdd
  , pStr "sub" HiFunSub
  , pStr "mul" HiFunMul
  , pStr "div" HiFunDiv
  , pStr "and" HiFunAnd
  , pStr "or" HiFunOr
  , pStr "less-than" HiFunLessThan
  , pStr "greater-than" HiFunGreaterThan
  , pStr "equals" HiFunEquals
  , pStr "not-less-than" HiFunNotLessThan
  , pStr "not-greater-than" HiFunNotGreaterThan
  , pStr "not-equals" HiFunNotEquals
  , pStr "not" HiFunNot
  , pStr "if" HiFunIf
  , pStr "length" HiFunLength
  , pStr "to-upper" HiFunToUpper
  , pStr "to-lower" HiFunToLower
  , pStr "reverse" HiFunReverse
  , pStr "trim" HiFunTrim
  , pStr "list" HiFunList
  , pStr "range" HiFunRange
  , pStr "fold" HiFunFold
  , pStr "pack-bytes" HiFunPackBytes
  , pStr "unpack-bytes" HiFunUnpackBytes
  , pStr "zip" HiFunZip
  , pStr "unzip" HiFunUnzip
  , pStr "encode-utf8" HiFunEncodeUtf8
  , pStr "decode-utf8" HiFunDecodeUtf8
  , pStr "serialise" HiFunSerialise
  , pStr "deserialise" HiFunDeserialise
  , pStr "read" HiFunRead
  , pStr "write" HiFunWrite
  , pStr "mkdir" HiFunMkDir
  , pStr "cd" HiFunChDir
  , pStr "parse-time" HiFunParseTime
  , pStr "rand" HiFunRand
  , pStr "echo" HiFunEcho
  , pStr "count" HiFunCount
  , pStr "keys" HiFunKeys
  , pStr "values" HiFunValues
  , pStr "invert" HiFunInvert]
  where pStr s r = string s >> return r

pHiExprInfix :: Parser HiExpr
pHiExprInfix = makeExprParser pHiExpr operatorTable

operatorTable :: [[Operator Parser HiExpr]]
operatorTable =
  [ [ InfixL $ try (char '/' <* notFollowedBy (char '=')) >> makeOp HiFunDiv
    , InfixL $ char '*' >> makeOp HiFunMul ]
  , [ InfixL $ char '+' >> makeOp HiFunAdd
    , InfixL $ char '-' >> makeOp HiFunSub ]
  , [ InfixN $ string "==" >> makeOp HiFunEquals
    , InfixN $ string "/=" >> makeOp HiFunNotEquals ]
  , [ InfixN $ string ">=" >> makeOp HiFunNotLessThan
    , InfixN $ string "<=" >> makeOp HiFunNotGreaterThan
    , InfixN $ try (char '<' <* notFollowedBy (char '=')) >> makeOp HiFunLessThan
    , InfixN $ try (char '>' <* notFollowedBy (char '=')) >> makeOp HiFunGreaterThan ]
  , [ InfixR $ string "&&" >> makeOp HiFunAnd ]
  , [ InfixR $ string "||" >> makeOp HiFunOr ] ]
  where
    makeOp fun = return (\l r -> HiExprApply (HiExprValue (HiValueFunction fun)) [l, r])
