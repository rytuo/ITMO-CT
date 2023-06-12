{-# LANGUAGE LambdaCase #-}

module HW3.Pretty
  ( prettyValue
  ) where

import HW3.Base (HiValue (..), HiFun (..), HiAction (..))
import Prettyprinter (Doc, pretty)
import Prettyprinter.Render.Terminal (AnsiStyle)
import Data.Scientific (fromRationalRepetendUnlimited, floatingOrInteger)
import Data.Ratio
import Data.Sequence (Seq (..))
import qualified Data.ByteString as BS (head, tail, foldl, length, ByteString)
import Data.Word (Word8)
import Numeric (showHex, showFFloat)
import Data.Maybe (isJust)
import Data.Text (Text, unpack)
import Data.Time (UTCTime)
import Data.Map (Map, assocs, size)

prettyValue :: HiValue -> Doc AnsiStyle
prettyValue = pretty . prettyVal

prettyVal :: HiValue -> String
prettyVal = \case
  HiValueNumber n     -> prettyNumber n
  HiValueFunction f   -> prettyFun f
  HiValueBool b       -> prettyBool b
  HiValueNull         -> prettyNull
  HiValueString t     -> prettyString t
  HiValueList s       -> prettySeq s
  HiValueBytes b      -> prettyBytes b
  HiValueAction a     -> prettyAction a
  HiValueTime t       -> prettyTime t
  HiValueDict m       -> prettyMap m

prettyMap :: Map HiValue HiValue -> String
prettyMap m
  | size m == 0 = "{ }"
  | otherwise   = "{ " ++ foldl1 connect (map toString (assocs m)) ++ " }"
  where
    toString (k, v) = prettyVal k ++ ": " ++ prettyVal v
    connect a b = a ++ ", " ++ b

prettyTime :: UTCTime -> String
prettyTime time = "parse-time(\"" ++ show time ++ "\")"

prettyFun :: HiFun -> String
prettyFun = \case
  HiFunDiv            -> "div"
  HiFunMul            -> "mul"
  HiFunAdd            -> "add"
  HiFunSub            -> "sub"
  HiFunNot            -> "not"
  HiFunAnd            -> "and"
  HiFunOr             -> "or"
  HiFunLessThan       -> "less-than"
  HiFunGreaterThan    -> "greater-than"
  HiFunEquals         -> "equals"
  HiFunNotLessThan    -> "not-less-than"
  HiFunNotGreaterThan -> "not-greater-than"
  HiFunNotEquals      -> "not-equals"
  HiFunIf             -> "if"
  HiFunLength         -> "length"
  HiFunToUpper        -> "to-upper"
  HiFunToLower        -> "to-lower"
  HiFunReverse        -> "reverse"
  HiFunTrim           -> "trim"
  HiFunList           -> "list"
  HiFunRange          -> "range"
  HiFunFold           -> "fold"
  HiFunPackBytes      -> "pack-bytes"
  HiFunUnpackBytes    -> "unpack-bytes"
  HiFunEncodeUtf8     -> "encode-utf8"
  HiFunDecodeUtf8     -> "decode-utf8"
  HiFunZip            -> "zip"
  HiFunUnzip          -> "unzip"
  HiFunSerialise      -> "serialise"
  HiFunDeserialise    -> "deserialise"
  HiFunRead           -> "read"
  HiFunWrite          -> "write"
  HiFunChDir          -> "cd"
  HiFunMkDir          -> "mkdir"
  HiFunParseTime      -> "parse-time"
  HiFunRand           -> "rand"
  HiFunEcho           -> "echo"
  HiFunCount          -> "count"
  HiFunKeys           -> "keys"
  HiFunValues         -> "values"
  HiFunInvert         -> "invert"

prettyAction :: HiAction -> String
prettyAction = \case
   HiActionRead  path     -> "read(\"" ++ path ++ "\")"
   HiActionWrite path bs  -> "write(\"" ++ path ++ "\", " ++ prettyBytes bs ++ ")"
   HiActionMkDir path     -> "mkdir(\"" ++ path ++ "\")"
   HiActionChDir path     -> "cd(\"" ++ path ++ "\")"
   HiActionCwd            -> "cwd"
   HiActionNow            -> "now"
   HiActionRand a b       -> "rand(" ++ show a ++ ", " ++ show b ++ ")"
   HiActionEcho t         -> "echo(" ++ prettyString t ++ ")"

prettyBool :: Bool -> String
prettyBool b = if b then "true" else "false"

prettyNull :: String
prettyNull = "null"

prettyString :: Text -> String
prettyString t = "\"" ++ unpack t ++ "\""

prettySeq :: Seq HiValue -> String
prettySeq Empty = "[ ]"
prettySeq (h :<| t) = "[ " ++ foldl join (prettyVal h) t ++ " ]"
  where
    join a b = a ++ ", " ++ prettyVal b

prettyBytes :: BS.ByteString -> String
prettyBytes bs
  | BS.length bs == 0 = "[# #]"
  | otherwise         = "[# " ++ BS.foldl prettyByte prettyFst (BS.tail bs) ++ " #]"
  where
    prettyByte p next = p ++ " " ++ prettyHex next
    prettyFst = prettyHex $ BS.head bs

prettyHex :: Word8 -> String
prettyHex w
  | w < 16    = "0" ++ showHex w ""
  | otherwise = showHex w ""

prettyNumber :: Rational -> String
prettyNumber n =
  if isJust mbR
    then prettyInfiniteNumber n
    else prettyFiniteNumber $ floatingOrInteger s
  where
    (s, mbR) = fromRationalRepetendUnlimited n
    
prettyFiniteNumber :: Either Double Integer -> String
prettyFiniteNumber (Left f)  = showFFloat Nothing (f :: Double) ""
prettyFiniteNumber (Right i) = show i

prettyInfiniteNumber :: Rational -> String
prettyInfiniteNumber s
  | z == 0    = (if signed then "-" else "") ++ toStringR
  | otherwise = toStringZ ++ (if signed then " - " else " + ") ++ toStringR
  where
    (signed, positive) = if s < 0 then (True, -s) else (False, s)
    num = numerator positive
    den = denominator positive
    (z, r) = quotRem num den

    toStringZ = (if signed then "-" else "") ++ show z
    toStringR = show r ++ "/" ++ show den
