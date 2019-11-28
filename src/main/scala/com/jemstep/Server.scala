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
import org.http4s._
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.{Logger => httpLogger}
import zio.interop.catz._
import zio.interop.catz.implicits._
import zio.{DefaultRuntime, Task}

object Server {
  implicit val runtime: DefaultRuntime = new DefaultRuntime {}

  def createApp: HttpApp[Task] = {
    httpLogger.httpApp(logHeaders = true, logBody = true)(
      Routes.configurationRoutes.orNotFound
    )
  }

  def hostServer: fs2.Stream[Task, ExitCode] = {
    BlazeServerBuilder[Task]
      .bindHttp(8082, "0.0.0.0")
      .withHttpApp(createApp)
      .serve
  }
}
