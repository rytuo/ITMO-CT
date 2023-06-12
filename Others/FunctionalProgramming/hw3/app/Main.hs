module Main where

import HW3.Parser (parse, errorBundlePretty)
import HW3.Evaluator (eval)
import HW3.Pretty (prettyValue)
import HW3.Action (runHIO, setPermissions, HiPermission (..))
import HW3.Base ()
import System.Console.Haskeline (InputT, runInputT, defaultSettings, getInputLine, outputStrLn)
import Control.Monad.IO.Class (liftIO)

main :: IO ()
main = runInputT defaultSettings loop

loop :: InputT IO ()
loop = do
  minput <- getInputLine "hi> "
  case minput of
    Nothing -> return ()
    Just "q" -> return ()
    Just input -> do
      res <- tryEval input
      outputStrLn res
      loop

tryEval :: String -> InputT IO String
tryEval s = case parse s of
  Left  err -> return $ errorBundlePretty err
  Right val -> do
    res <- liftIO $ runHIO (eval val) (setPermissions [AllowRead, AllowWrite, AllowTime])
    case res of
      Left  err -> return $ show err
      Right v   -> return $ show $ prettyValue v
