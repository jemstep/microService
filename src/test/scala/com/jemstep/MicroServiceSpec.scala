/*
 * JEMSTEP CONFIDENTIAL
 * __________________
 *
 * Copyright (c) 2016 Jemstep Incorporated
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
package com.jemstep

import com.jemstep.HelloWorld._
import zio._
import zio.console._
import zio.test.Assertion.equalTo
import zio.test._
import zio.test.environment._
import sttp.client._
import org.http4s.circe._
import io.circe.literal._


object HelloWorld {
  def sayHello: ZIO[Console, Nothing, Unit] = {
    val request = basicRequest.get(uri"localhost:8082")
    implicit val backend = HttpURLConnectionBackend()
    val response = request.send()
    console.putStrLn(response.body.toString)
  }
}

object HelloWorldSpec
  extends DefaultRunnableSpec(
    suite("HelloWorldSpec")(
      testM("sayHello correctly displays output") {
        for {
        _      <- sayHello
        output <- TestConsole.output
        } yield assert(output, equalTo(Vector(Right(json"""{"hello":"world"}"""))))
      }
    )
  )
