{-# LANGUAGE LambdaCase #-}
{-# LANGUAGE TupleSections #-}

module HW3.Evaluator
  ( eval
  ) where

import HW3.Base (HiFun (..), HiExpr (..), HiError (..), HiValue (..), HiAction (..), HiMonad (..))
import Control.Monad.Trans.Except
import qualified Data.Text as T (Text, length, toUpper, toLower, reverse, strip, singleton, index, take, drop, pack, unpack)
import Data.Ratio (numerator, denominator)
import Data.Semigroup (stimes)
import qualified Data.Sequence as Seq (Seq (..), length, reverse, index, drop, take, fromList, (<|), empty)
import Data.ByteString (ByteString)
import Data.ByteString as BS (length, index, take, drop, pack, unpack)
import Data.ByteString.Lazy as LBS (fromStrict, toStrict)
import Data.Foldable (toList)
import Codec.Compression.Zlib (compressWith, decompressWith, defaultCompressParams, defaultDecompressParams, compressLevel, bestCompression)
import Codec.Serialise (serialise, deserialise)
import Data.Text.Encoding (decodeUtf8', encodeUtf8)
import Data.Time (addUTCTime, diffUTCTime, UTCTime)
import Text.Read (readMaybe)
import qualified Data.Map as Map (fromList, Map, keys, elems, fromListWith, lookup, assocs, insertWith)
import Data.Maybe (fromMaybe)

eval :: HiMonad m => HiExpr -> m (Either HiError HiValue)
eval = runExceptT . evalHiExpr

evalHiExpr :: HiMonad m => HiExpr -> ExceptT HiError m HiValue
evalHiExpr = \case
  HiExprValue a   -> return a

  HiExprRun   a   -> do
    val <- evalHiExpr a
    case val of
      HiValueAction b -> ExceptT $ Right <$> runAction b
      _               -> throwE HiErrorInvalidArgument

  HiExprApply a b -> do
    fun <- evalHiExpr a
    case fun of
      (HiValueFunction c) -> evalFun c b
      (HiValueString c)   -> evalString c b
      (HiValueList c)     -> evalSeq c b
      (HiValueBytes c)    -> evalBytes c b
      (HiValueDict c)     -> evalDict c b
      _                   -> throwE HiErrorInvalidFunction

  HiExprDict entriesList  -> HiValueDict . Map.fromList <$>
    mapM (\(k, v) -> (,) <$> evalHiExpr k <*> evalHiExpr v) entriesList

evalFun :: HiMonad m => HiFun -> [HiExpr] -> ExceptT HiError m HiValue
evalFun = \case
  HiFunDiv -> evalFun2 divF
  HiFunMul -> evalFun2 mulF
  HiFunAdd -> evalFun2 addF
  HiFunSub -> evalFun2 subF
  HiFunNot -> evalFun1 notF
  HiFunAnd -> evalLazyFun2 andF
  HiFunOr -> evalLazyFun2 orF
  HiFunLessThan -> evalFun2 lessThanF
  HiFunGreaterThan -> evalFun2 greaterThanF
  HiFunEquals -> evalFun2 equalsF
  HiFunNotLessThan -> evalFun2 notLessThanF
  HiFunNotGreaterThan -> evalFun2 notGreaterThanF
  HiFunNotEquals -> evalFun2 notEqualsF
  HiFunIf -> evalLazyFun3 ifF
  HiFunLength -> evalFun1 lengthF
  HiFunToUpper -> evalFun1 toUpperF
  HiFunToLower -> evalFun1 toLowerF
  HiFunReverse -> evalFun1 reverseF
  HiFunTrim -> evalFun1 trimF
  HiFunList -> evalFunL listF
  HiFunRange -> evalFun2 rangeF
  HiFunFold -> evalFun2 foldF
  HiFunPackBytes -> evalFun1 packBytesF
  HiFunUnpackBytes -> evalFun1 unpackBytesF
  HiFunEncodeUtf8 -> evalFun1 encodeUtf8F
  HiFunDecodeUtf8 -> evalFun1 decodeUtf8F
  HiFunZip -> evalFun1 zipF
  HiFunUnzip -> evalFun1 unzipF
  HiFunSerialise -> evalFun1 serialiseF
  HiFunDeserialise -> evalFun1 deserialiseF
  HiFunRead -> evalFun1 readF
  HiFunWrite -> evalFun2 writeF
  HiFunChDir -> evalFun1 cdF
  HiFunMkDir -> evalFun1 mkdirF
  HiFunParseTime -> evalFun1 parseTimeF
  HiFunRand -> evalFun2 randF
  HiFunEcho -> evalFun1 echoF
  HiFunCount -> evalFun1 countF
  HiFunKeys -> evalFun1 keysF
  HiFunValues -> evalFun1 valuesF
  HiFunInvert -> evalFun1 invertF

evalFun1 :: HiMonad m => (HiValue -> ExceptT HiError m HiValue) -> [HiExpr] -> ExceptT HiError m HiValue
evalFun1 fun [e1] = evalHiExpr e1 >>= fun
evalFun1 _ _ = throwE HiErrorArityMismatch

evalFun2 :: HiMonad m => (HiValue -> HiValue -> ExceptT HiError m HiValue) -> [HiExpr] -> ExceptT HiError m HiValue
evalFun2 fun [e1, e2] = do
  v1 <- evalHiExpr e1
  v2 <- evalHiExpr e2
  fun v1 v2
evalFun2 _ _ = throwE HiErrorArityMismatch

evalFunL :: HiMonad m => ([HiValue] -> ExceptT HiError m HiValue) -> [HiExpr] -> ExceptT HiError m HiValue
evalFunL fun args = mapM evalHiExpr args >>= fun

evalLazyFun2 :: HiMonad m => (HiExpr -> HiExpr -> ExceptT HiError m HiValue) -> [HiExpr] -> ExceptT HiError m HiValue
evalLazyFun2 fun [e1, e2] = fun e1 e2
evalLazyFun2 _ _ = throwE HiErrorArityMismatch

evalLazyFun3 :: HiMonad m => (HiExpr -> HiExpr -> HiExpr -> ExceptT HiError m HiValue) -> [HiExpr] -> ExceptT HiError m HiValue
evalLazyFun3 fun [e1, e2, e3] = fun e1 e2 e3
evalLazyFun3 _ _ = throwE HiErrorArityMismatch

takeIndex :: HiMonad m => Int -> (Int -> HiValue) -> HiExpr -> ExceptT HiError m HiValue
takeSlice :: HiMonad m => Int -> (Int -> Int -> HiValue) -> HiExpr -> HiExpr -> ExceptT HiError m HiValue

validateIndex :: HiMonad m => Int -> HiValue -> ExceptT HiError m Int
validateIndex _ (HiValueNumber n) = toInt n
validateIndex a HiValueNull = return a
validateIndex _ _ = throwE HiErrorInvalidArgument

takeIndex len get e1 = do
  n1 <- evalHiExpr e1 >>= validateIndex (-1)
  return $ if 0 <= n1 && n1 < len
        then get n1
        else HiValueNull

takeSlice len slice e1 e2 = do
  n1 <- evalHiExpr e1 >>= validateIndex 0
  n2 <- evalHiExpr e2 >>= validateIndex len
  let l = if n1 < 0 then len + n1 else n1
      r = if n2 < 0 then len + n2 else n2
  return $ slice l r

evalString :: HiMonad m => T.Text -> [HiExpr] -> ExceptT HiError m HiValue
evalString t [e1] = takeIndex (T.length t) (HiValueString . T.singleton . T.index t) e1
evalString t [e1, e2] = takeSlice (T.length t) (\l r -> HiValueString $ T.drop l (T.take r t)) e1 e2
evalString _ _ = throwE HiErrorArityMismatch

evalSeq :: HiMonad m => Seq.Seq HiValue -> [HiExpr] -> ExceptT HiError m HiValue
evalSeq t [e1] = takeIndex (Seq.length t) (Seq.index t) e1
evalSeq t [e1, e2] = takeSlice (Seq.length t) (\l r -> HiValueList $ Seq.drop l (Seq.take r t)) e1 e2
evalSeq _ _ = throwE HiErrorArityMismatch

evalBytes :: HiMonad m => ByteString -> [HiExpr] -> ExceptT HiError m HiValue
evalBytes a [e1] = takeIndex (BS.length a) (HiValueNumber . toRational . BS.index a) e1
evalBytes a [e1, e2] = takeSlice (BS.length a) (\l r -> HiValueBytes $ BS.drop l (BS.take r a)) e1 e2
evalBytes _ _ = throwE HiErrorArityMismatch

evalDict :: HiMonad m => Map.Map HiValue HiValue -> [HiExpr] -> ExceptT HiError m HiValue
evalDict m [t] = evalHiExpr t >>= \a -> return $ fromMaybe HiValueNull (Map.lookup a m)
evalDict _ _ = throwE HiErrorArityMismatch

divF, mulF, addF, subF, lessThanF, greaterThanF, equalsF, notLessThanF, notGreaterThanF, notEqualsF, rangeF, foldF, writeF, randF :: HiMonad m => HiValue -> HiValue -> ExceptT HiError m HiValue
notF, lengthF, toUpperF, toLowerF, reverseF, trimF, packBytesF, unpackBytesF, encodeUtf8F, decodeUtf8F, zipF, unzipF, serialiseF, deserialiseF, readF, cdF, mkdirF, parseTimeF, echoF, keysF, valuesF, countF, invertF :: HiMonad m => HiValue -> ExceptT HiError m HiValue
listF :: HiMonad m => [HiValue] -> ExceptT HiError m HiValue
andF, orF :: HiMonad m => HiExpr -> HiExpr -> ExceptT HiError m HiValue
ifF :: HiMonad m => HiExpr -> HiExpr -> HiExpr -> ExceptT HiError m HiValue

addF (HiValueNumber a) (HiValueNumber b) = return $ HiValueNumber (a + b)
addF (HiValueString a) (HiValueString b) = return $ HiValueString (a <> b)
addF (HiValueList a) (HiValueList b) = return $ HiValueList (a <> b)
addF (HiValueBytes a) (HiValueBytes b) = return $ HiValueBytes (a <> b)
addF (HiValueTime a) (HiValueNumber b) = return $ HiValueTime (addUTCTime (realToFrac b) a)
addF _ _ = throwE HiErrorInvalidArgument

subF (HiValueNumber a) (HiValueNumber b) = return $ HiValueNumber (a - b)
subF (HiValueTime a) (HiValueTime b) = return $ HiValueNumber (toRational (diffUTCTime a b))
subF _ _ = throwE HiErrorInvalidArgument

mulF (HiValueNumber a) (HiValueNumber b) = return $ HiValueNumber (a * b)
mulF (HiValueString a) (HiValueNumber b) =  toInt b >>= \c -> if c < 1 then throwE HiErrorInvalidArgument else return $ HiValueString $ stimes c a
mulF (HiValueList a) (HiValueNumber b) = toInt b >>= \c -> if c < 1 then throwE HiErrorInvalidArgument else return $ HiValueList $ stimes c a
mulF (HiValueBytes a) (HiValueNumber b) = toInt b >>= \c -> if c < 1 then throwE HiErrorInvalidArgument else return $ HiValueBytes $ stimes c a
mulF _ _ = throwE HiErrorInvalidArgument

divF (HiValueNumber a) (HiValueNumber b) = if b == 0 then throwE HiErrorDivideByZero else return $ HiValueNumber (a / b)
divF (HiValueString a) (HiValueString b) = return $ HiValueString $ a <> T.pack "/" <> b
divF _ _ = throwE HiErrorInvalidArgument

notF (HiValueBool n1) = return $ HiValueBool (not n1)
notF _ = throwE HiErrorInvalidArgument

andF a b = do
  v1 <- evalHiExpr a
  if v1 == HiValueBool False || v1 == HiValueNull
  then return v1
  else evalHiExpr b

orF a b = do
  v1 <- evalHiExpr a
  if (v1 == HiValueBool False) || v1 == HiValueNull
  then evalHiExpr b
  else return v1

equalsF (HiValueNumber a) (HiValueNumber b) = return $ HiValueBool (a == b)
equalsF (HiValueFunction a) (HiValueFunction b) = return $ HiValueBool (a == b)
equalsF (HiValueBool a) (HiValueBool b) = return $ HiValueBool (a == b)
equalsF HiValueNull HiValueNull = return $ HiValueBool True
equalsF (HiValueString a) (HiValueString b) = return $ HiValueBool (a == b)
equalsF (HiValueList a) (HiValueList b) = return $ HiValueBool (a == b)
equalsF (HiValueBytes a) (HiValueBytes b) = return $ HiValueBool (a == b)
equalsF (HiValueAction a) (HiValueAction b) = return $ HiValueBool (a == b)
equalsF (HiValueTime a) (HiValueTime b) = return $ HiValueBool (a == b)
equalsF _ _ = return $ HiValueBool False

notEqualsF a b = equalsF a b >>= \case
  (HiValueBool c) -> return $ HiValueBool (not c)
  _               -> throwE HiErrorInvalidArgument

lessThanF (HiValueNumber a) (HiValueNumber b) = return $ HiValueBool (a < b)
lessThanF (HiValueFunction a) (HiValueFunction b) = return $ HiValueBool (a < b)
lessThanF (HiValueBool a) (HiValueBool b) = return $ HiValueBool (a < b)
lessThanF HiValueNull HiValueNull = return $ HiValueBool False
lessThanF (HiValueString a) (HiValueString b) = return $ HiValueBool (a < b)
--lessThanF (HiValueList a) (HiValueList b) = return $ HiValueBool (a < b)
lessThanF (HiValueBytes a) (HiValueBytes b) = return $ HiValueBool (a < b)
lessThanF (HiValueAction a) (HiValueAction b) = return $ HiValueBool (a < b)
lessThanF (HiValueTime a) (HiValueTime b) = return $ HiValueBool (a < b)
lessThanF (HiValueBool _) (HiValueNumber _) = return $ HiValueBool True
lessThanF (HiValueNumber _) (HiValueBool _) = return $ HiValueBool False
lessThanF _ _ = throwE HiErrorInvalidArgument

greaterThanF (HiValueNumber a) (HiValueNumber b) = return $ HiValueBool (a > b)
greaterThanF (HiValueFunction a) (HiValueFunction b) = return $ HiValueBool (a > b)
greaterThanF (HiValueBool a) (HiValueBool b) = return $ HiValueBool (a > b)
greaterThanF HiValueNull HiValueNull = return $ HiValueBool False
greaterThanF (HiValueString a) (HiValueString b) = return $ HiValueBool (a > b)
--greaterThanF (HiValueList a) (HiValueList b) = return $ HiValueBool (a > b)
greaterThanF (HiValueBytes a) (HiValueBytes b) = return $ HiValueBool (a > b)
greaterThanF (HiValueAction a) (HiValueAction b) = return $ HiValueBool (a > b)
greaterThanF (HiValueTime a) (HiValueTime b) = return $ HiValueBool (a > b)
greaterThanF (HiValueBool _) (HiValueNumber _) = return $ HiValueBool False
greaterThanF (HiValueNumber _) (HiValueBool _) = return $ HiValueBool True
greaterThanF _ _ = throwE HiErrorInvalidArgument

notLessThanF a b = lessThanF a b >>= \case
  (HiValueBool c) -> return $ HiValueBool (not c)
  _               -> throwE HiErrorInvalidArgument
notGreaterThanF a b = greaterThanF a b >>= \case
   (HiValueBool c) -> return $ HiValueBool (not c)
   _               -> throwE HiErrorInvalidArgument

ifF a b c = do
  cond <- evalHiExpr a
  case cond of
    HiValueBool True  -> evalHiExpr b
    HiValueBool False -> evalHiExpr c
    _                 -> throwE HiErrorInvalidArgument

lengthF (HiValueString a) = return $ HiValueNumber $ toRational (T.length a)
lengthF (HiValueList a) = return $ HiValueNumber $ toRational (Seq.length a)
lengthF _ = throwE HiErrorInvalidArgument

toUpperF (HiValueString a) = return $ HiValueString $ T.toUpper a
toUpperF _ = throwE HiErrorInvalidArgument

toLowerF (HiValueString a) = return $ HiValueString $ T.toLower a
toLowerF _ = throwE HiErrorInvalidArgument

reverseF (HiValueString a) = return $ HiValueString $ T.reverse a
reverseF (HiValueList a) = return $ HiValueList $ Seq.reverse a
reverseF _ = throwE HiErrorInvalidArgument

trimF (HiValueString a) = return $ HiValueString $ T.strip a
trimF _ = throwE HiErrorInvalidArgument

listF = return . HiValueList . Seq.fromList

rangeF (HiValueNumber a) (HiValueNumber b) = return $ HiValueList $ Seq.fromList $ map HiValueNumber [a .. b]
rangeF _ _ = throwE HiErrorInvalidArgument

foldF _ (HiValueList Seq.Empty) = return HiValueNull
foldF a (HiValueList (b Seq.:<| c)) = count b c
  where
    count acc Seq.Empty = return acc
    count acc (h Seq.:<| t) = evalHiExpr (HiExprApply (HiExprValue a) [HiExprValue acc, HiExprValue h]) >>= \n -> count n t
foldF _ _ = throwE HiErrorInvalidArgument

packBytesF (HiValueList a) = HiValueBytes . BS.pack . toList <$> mapM getByte a
  where
    getByte (HiValueNumber n)
      | n >= 0 && n <= 255 = fromIntegral <$> toInt n
      | otherwise          = throwE HiErrorInvalidArgument
    getByte _ = throwE HiErrorInvalidArgument
packBytesF _ = throwE HiErrorInvalidArgument

unpackBytesF (HiValueBytes a) = return $ HiValueList . Seq.fromList . map (HiValueNumber . toRational) $ BS.unpack a
unpackBytesF _ = throwE HiErrorInvalidArgument

encodeUtf8F (HiValueString a) = return $ HiValueBytes $ encodeUtf8 a
encodeUtf8F _ = throwE HiErrorInvalidArgument

decodeUtf8F (HiValueBytes a) = return $ case decodeUtf8' a of
  Left _  -> HiValueNull
  Right t -> HiValueString t
decodeUtf8F _ = throwE HiErrorInvalidArgument

zipF (HiValueBytes a) = return $ HiValueBytes . toStrict $ compressWith (defaultCompressParams { compressLevel = bestCompression }) (fromStrict a)
zipF _ = throwE HiErrorInvalidArgument

unzipF (HiValueBytes a) =  return $ HiValueBytes . toStrict $ decompressWith defaultDecompressParams (fromStrict a)
unzipF _ = throwE HiErrorInvalidArgument

serialiseF = return . HiValueBytes . toStrict . serialise

deserialiseF (HiValueBytes a) = return $ deserialise (fromStrict a)
deserialiseF _ = throwE HiErrorInvalidArgument

readF (HiValueString t) = return $ HiValueAction $ HiActionRead $ T.unpack t
readF _ = throwE HiErrorInvalidArgument

writeF (HiValueString a) (HiValueString b) = return $ HiValueAction $ HiActionWrite (T.unpack a) (encodeUtf8 b)
writeF (HiValueString a) (HiValueBytes b) = return $ HiValueAction $ HiActionWrite (T.unpack a) b
writeF _ _ = throwE HiErrorInvalidArgument

mkdirF (HiValueString a) = return $ HiValueAction $ HiActionMkDir $ T.unpack a
mkdirF _ = throwE HiErrorInvalidArgument

cdF (HiValueString a) = return $ HiValueAction $ HiActionChDir $ T.unpack a
cdF _ = throwE HiErrorInvalidArgument

parseTimeF (HiValueString a) = return $ maybe HiValueNull HiValueTime (readMaybe (T.unpack a) :: Maybe UTCTime)
parseTimeF _ = throwE HiErrorInvalidArgument

randF (HiValueNumber a) (HiValueNumber b) = HiValueAction <$> (HiActionRand <$> toInt a <*> toInt b)
randF _ _ = throwE HiErrorInvalidArgument

echoF (HiValueString a) = return $ HiValueAction $ HiActionEcho a
echoF _ = throwE HiErrorInvalidArgument

keysF (HiValueDict a) = return $ HiValueList $ Seq.fromList $ Map.keys a
keysF _ = throwE HiErrorInvalidArgument

valuesF (HiValueDict a) = return $ HiValueList $ Seq.fromList $ Map.elems a
valuesF _ = throwE HiErrorInvalidArgument

countF (HiValueString a) = return $ HiValueDict $ Map.fromListWith addVal $ wrapString $ T.unpack a
  where wrapString = map (\b -> (HiValueString (T.pack [b]), HiValueNumber 1))

countF (HiValueBytes a) = return $ HiValueDict $ Map.fromListWith addVal $ wrapBytes $ BS.unpack a
  where wrapBytes = map (\b -> (HiValueNumber (toRational b), HiValueNumber 1))

countF (HiValueList a) = return $ HiValueDict $ Map.fromListWith addVal $ wrapList $ toList a
  where wrapList = map (, HiValueNumber 1)

countF _ = throwE HiErrorInvalidArgument

addVal :: HiValue -> HiValue -> HiValue
addVal (HiValueNumber l) (HiValueNumber r) = HiValueNumber (l + r)
addVal _ _ = undefined

invertF (HiValueDict a) = return $ HiValueDict $ foldl insertMap emptyMap (Map.assocs a)
  where
    emptyMap = Map.fromList $ map (, HiValueList Seq.empty) (Map.elems a)
    insertMap m (k, v) = Map.insertWith handle v k m
    handle b (HiValueList l) = HiValueList $ b Seq.<| l
    handle _ _ = undefined
invertF _ = throwE HiErrorInvalidArgument


toInt :: HiMonad m => Rational -> ExceptT HiError m Int
toInt r
  | rm == 0   = return (fromIntegral z :: Int)
  | otherwise = throwE HiErrorInvalidArgument
  where
    (z, rm) = quotRem (numerator r) (denominator r)
