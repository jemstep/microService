/*
 * JEMSTEP CONFIDENTIAL
 * __________________
 *
 * Copyright (c) 2019 Jemstep Incorporated
 * All Rights Reserved.
 *
 * NOTICE:  All information contained herein is, and remains
 * the property of Jemstep Incorporated and its suppliers,
 * if any.  The intellectual and technical concepts contained
 * herein are proprietary to Jemstep Incorporated
 * and its suppliers and may be covered by U.S. and Foreign Patents,
 * patents in process, and are protected by trade secret or copyright law.
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained
 * from Jemstep Incorporated.
 */
package main.scala.com.jemstep

import cats.effect.ExitCode
import zio.console.Console
import zio.interop.catz._
import zio.{App, ZIO}

object Main extends App {
  def run(args: List[String]): ZIO[Console, Nothing, Int] =
    ZIO
      .runtime[Console]
      .flatMap { implicit rts =>
        Server.hostServer.compile.drain.as(ExitCode.Success.code)
      }
      .catchAll(e => {
        ZIO.succeed(ExitCode.Error.code)
      })
}
