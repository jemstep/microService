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

import io.circe._
import io.circe.literal._
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl.Http4sDsl
import zio.Task
import zio.interop.catz._

object Routes {
  def configurationRoutes: HttpRoutes[Task] = {

    case class Hello(name: String)

    def hello(name: String): Json =
      json"""{"hello": $name}"""
    val greeting = hello("world")

    implicit val HelloEncoder: Encoder[Hello] =
      Encoder.instance { hello: Hello =>
        json"""{"hello": ${hello.name}}"""
      }

    val dsl = new Http4sDsl[Task] {}
    import dsl._
    HttpRoutes.of[Task] {
      case GET -> Root => Ok(greeting)
    }
  }
}
