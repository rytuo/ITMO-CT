{-# LANGUAGE LambdaCase #-}
{-# LANGUAGE DerivingVia #-}

module HW3.Action
  ( HiPermission (..)
  , PermissionException (..)
  , HIO (..)
  , setPermissions
  ) where

import qualified Data.Sequence as Seq (fromList)
import qualified Data.Text as T (pack, unpack)
import HW3.Base
import Control.Exception (Exception, throwIO)
import Data.Set (Set, fromList, member)
import Control.Applicative ()
import System.Directory
  ( createDirectory
  , listDirectory
  , setCurrentDirectory
  , getCurrentDirectory
  , doesFileExist
  , doesDirectoryExist)
import Control.Monad.IO.Class (MonadIO (..))
import qualified Data.ByteString as BS (readFile, writeFile)
import Data.Text.Encoding (decodeUtf8')
import Data.Time (getCurrentTime)
import System.Random.Stateful (uniformR, getStdGen, setStdGen)
import Control.Monad.Trans.Reader (ReaderT (..))

data HiPermission =
    AllowRead
  | AllowWrite
  | AllowTime
  deriving (Show, Ord, Eq, Enum, Bounded)

newtype PermissionException
  = PermissionRequired HiPermission
  deriving Show

setPermissions :: [HiPermission] -> Set HiPermission
setPermissions = fromList

instance Exception PermissionException

newtype HIO a = HIO { runHIO :: Set HiPermission -> IO a }
  deriving (Functor, Applicative, Monad, MonadIO) via (ReaderT (Set HiPermission) IO)

--instance Functor HIO where
--  fmap f hio = HIO $ \perm -> fmap f (runHIO hio perm)
--
--instance Applicative HIO where
--  pure a = HIO $ const (pure a)
--  hioF <*> hioA = HIO $ \perm -> runHIO hioF perm <*> runHIO hioA perm
--
--instance Monad HIO where
--  ma >>= a2HIO = HIO $ \perm -> runHIO ma perm >>= (\a -> runHIO (a2HIO a) perm)
--
--instance MonadIO HIO where
--  liftIO io = HIO (const io)

instance HiMonad HIO where
  runAction = \case
    HiActionMkDir p   -> checkIOPermission AllowWrite $ HiValueNull <$ createDirectory p
    HiActionWrite p b -> checkIOPermission AllowWrite $ HiValueNull <$ BS.writeFile p b
    HiActionCwd       -> checkIOPermission AllowRead $ HiValueString . T.pack <$> getCurrentDirectory
    HiActionChDir p   -> checkIOPermission AllowRead $ HiValueNull <$ setCurrentDirectory p
    HiActionRead  p   -> checkIOPermission AllowRead $ reader p
    HiActionNow       -> checkIOPermission AllowTime $ HiValueTime <$> getCurrentTime
    HiActionRand a b  -> rand a b
    HiActionEcho t    -> checkIOPermission AllowWrite $ HiValueNull <$ putStrLn (T.unpack t)


checkIOPermission :: HiPermission -> IO a -> HIO a
checkIOPermission p io = HIO $ \perm ->
  if p `member` perm
  then io
  else throwIO $ PermissionRequired p

reader :: FilePath -> IO HiValue
reader path = do
  isDir <- doesDirectoryExist path
  if isDir
  then HiValueList . Seq.fromList . map (HiValueString . T.pack) <$> listDirectory path
  else do
    isFile <- doesFileExist path
    if isFile
    then do
      content <- BS.readFile path
      return $ case decodeUtf8' content of
        Left _   -> HiValueBytes content
        Right s  -> HiValueString s
    else return HiValueNull

rand :: Int -> Int -> HIO HiValue
rand a b = do
  stdGen <- getStdGen
  let (res, state) = uniformR (a, b) stdGen
  setStdGen state
  return $ HiValueNumber $ fromIntegral res
